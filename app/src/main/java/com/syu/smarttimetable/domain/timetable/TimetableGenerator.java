package com.syu.smarttimetable.domain.timetable;

import android.util.Log;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.ClassParity;
import com.syu.smarttimetable.data.model.enums.CourseCategory;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;
import com.syu.smarttimetable.domain.recommendation.constraints.RequiredLectureConstraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TimetableGenerator {

    private static final String TAG = "TimetableGenerator";
    private static final int MAX_RESULTS = 200;

    public List<Timetable> generateCandidates(List<Lecture> lecturePool, RecommendationRequest request) {
        List<Timetable> results = new ArrayList<>();

        if (lecturePool == null || lecturePool.isEmpty() || request == null) {
            Log.w(TAG, "Invalid input: lecturePool="
                    + (lecturePool == null ? "null" : lecturePool.size())
                    + ", request="
                    + (request == null ? "null" : "ok"));
            return results;
        }

        Log.d(TAG, "Starting with " + lecturePool.size()
                + " lectures, need "
                + request.getMinCredits()
                + "~"
                + request.getMaxCredits()
                + " credits");

        Log.d(TAG, "Fixed lectures required: " + request.getFixedLectureKeys().size());

        Timetable baseTimetable = new Timetable();
        Set<String> usedCourseCodes = new HashSet<>();
        Set<String> usedCourseNames = new HashSet<>();
        Set<String> unresolvedFixedKeys = new HashSet<>(request.getFixedLectureKeys());

        for (Lecture lecture : lecturePool) {
            if (lecture == null) {
                continue;
            }

            String lectureKey = RequiredLectureConstraint.buildLectureKey(lecture);

            if (!request.getFixedLectureKeys().contains(lectureKey)) {
                continue;
            }

            if (baseTimetable.hasConflict(lecture)) {
                Log.w(TAG, "Fixed lecture has time conflict: " + lectureKey);
                return results;
            }

            if (usedCourseCodes.contains(lecture.getCourseCode())) {
                Log.w(TAG, "Fixed lecture already added: " + lecture.getCourseCode());
                return results;
            }

            String normalizedCourseName = normalizeCourseName(lecture.getCourseName());

            if (!normalizedCourseName.isEmpty() && usedCourseNames.contains(normalizedCourseName)) {
                Log.w(TAG, "Skipping duplicate fixed course name: " + normalizedCourseName);
                unresolvedFixedKeys.remove(lectureKey);
                continue;
            }

            if (baseTimetable.getTotalCredits() + lecture.getCredits() > request.getMaxCredits()) {
                Log.w(TAG, "Fixed lectures exceed max credits: "
                        + (baseTimetable.getTotalCredits() + lecture.getCredits())
                        + " > "
                        + request.getMaxCredits());
                return results;
            }

            baseTimetable.addLecture(lecture);
            usedCourseCodes.add(lecture.getCourseCode());

            if (!normalizedCourseName.isEmpty()) {
                usedCourseNames.add(normalizedCourseName);
            }

            unresolvedFixedKeys.remove(lectureKey);

            Log.d(TAG, "Added fixed lecture: " + lectureKey
                    + ", current credits: "
                    + baseTimetable.getTotalCredits());
        }

        if (!request.getFixedLectureKeys().isEmpty() && !unresolvedFixedKeys.isEmpty()) {
            Log.w(TAG, "Could not find some fixed lectures: " + unresolvedFixedKeys);
            return results;
        }

        int baseCredits = baseTimetable.getTotalCredits();
        Log.d(TAG, "Base credits: " + baseCredits);

        List<Lecture> remainingLectures = new ArrayList<>();

        for (Lecture lecture : lecturePool) {
            if (lecture == null) {
                continue;
            }

            String lectureKey = RequiredLectureConstraint.buildLectureKey(lecture);

            if (request.getFixedLectureKeys().contains(lectureKey)) {
                continue;
            }

            if (usedCourseCodes.contains(lecture.getCourseCode())) {
                continue;
            }

            String normalizedCourseName = normalizeCourseName(lecture.getCourseName());

            if (!normalizedCourseName.isEmpty() && usedCourseNames.contains(normalizedCourseName)) {
                continue;
            }

            if (!isValidByStudentIdParity(lecture, request.getStudentId())) {
                continue;
            }

            remainingLectures.add(lecture);
        }

        sortLecturesByGradeAndCategory(remainingLectures, request);

        Log.d(TAG, "Backtracking with " + remainingLectures.size() + " remaining lectures");

        backtrack(
                remainingLectures,
                0,
                baseTimetable,
                usedCourseCodes,
                usedCourseNames,
                request,
                results
        );

        if (baseCredits >= request.getMinCredits() && baseCredits <= request.getMaxCredits()) {
            if (results.isEmpty()) {
                results.add(new Timetable(baseTimetable.getLecturesReadOnly()));
                Log.d(TAG, "Added base timetable");
            }
        }

        Log.d(TAG, "Total results: " + results.size());
        return results;
    }

    private void backtrack(List<Lecture> lecturePool,
                           int startIndex,
                           Timetable current,
                           Set<String> usedCourseCodes,
                           Set<String> usedCourseNames,
                           RecommendationRequest request,
                           List<Timetable> results) {

        if (results.size() >= MAX_RESULTS) {
            Log.d(TAG, "Reached MAX_RESULTS limit");
            return;
        }

        int currentCredits = current.getTotalCredits();

        if (currentCredits >= request.getMinCredits() && currentCredits <= request.getMaxCredits()) {
            results.add(new Timetable(current.getLecturesReadOnly()));
            Log.d(TAG, "Valid timetable found with " + currentCredits + " credits");
        }

        if (currentCredits >= request.getMaxCredits()) {
            Log.d(TAG, "Max credits reached: " + currentCredits);
            return;
        }

        for (int i = startIndex; i < lecturePool.size(); i++) {
            Lecture nextLecture = lecturePool.get(i);

            if (nextLecture == null) {
                continue;
            }

            if (usedCourseCodes.contains(nextLecture.getCourseCode())) {
                continue;
            }

            String normalizedCourseName = normalizeCourseName(nextLecture.getCourseName());

            if (!normalizedCourseName.isEmpty() && usedCourseNames.contains(normalizedCourseName)) {
                continue;
            }

            if (current.hasConflict(nextLecture)) {
                continue;
            }

            if (currentCredits + nextLecture.getCredits() > request.getMaxCredits()) {
                continue;
            }

            current.addLecture(nextLecture);
            usedCourseCodes.add(nextLecture.getCourseCode());

            if (!normalizedCourseName.isEmpty()) {
                usedCourseNames.add(normalizedCourseName);
            }

            backtrack(
                    lecturePool,
                    i + 1,
                    current,
                    usedCourseCodes,
                    usedCourseNames,
                    request,
                    results
            );

            current.removeLecture(nextLecture);
            usedCourseCodes.remove(nextLecture.getCourseCode());

            if (!normalizedCourseName.isEmpty()) {
                usedCourseNames.remove(normalizedCourseName);
            }
        }
    }

    private String normalizeCourseName(String courseName) {
        if (courseName == null) {
            return "";
        }

        String normalized = courseName.trim().replaceAll("\\s+", " ");
        String lowerCase = normalized.toLowerCase();

        if (lowerCase.startsWith("채플") || lowerCase.contains("chapel")) {
            return "채플";
        }

        return normalized;
    }

    private void sortLecturesByGradeAndCategory(List<Lecture> lectures, RecommendationRequest request) {
        if (lectures == null || request == null || request.getUserGrade() <= 0) {
            return;
        }

        int userGrade = request.getUserGrade();

        // 유저가 선호하는 공강 요일 가져오기
        List<DayOfWeek> preferredFreeDays = new ArrayList<>();
        if (request.getSoftConstraint() != null && !request.getSoftConstraint().isSkipped()
                && request.getSoftConstraint().getPreferredFreeDays() != null) {
            preferredFreeDays = request.getSoftConstraint().getPreferredFreeDays();
        }

        List<Lecture> safeGradeMatchedMajors = new ArrayList<>(); // 공강을 지키는 학년 전공
        List<Lecture> conflictGradeMatchedMajors = new ArrayList<>(); // 공강을 깨는 학년 전공
        List<Lecture> safeOtherMajors = new ArrayList<>();
        List<Lecture> conflictOtherMajors = new ArrayList<>();
        List<Lecture> safeGenerals = new ArrayList<>();
        List<Lecture> conflictGenerals = new ArrayList<>();
        List<Lecture> others = new ArrayList<>();

        for (Lecture lecture : lectures) {
            if (lecture == null) continue;

            // 이 과목이 선호 공강 요일을 침범하는지 확인
            boolean violatesFreeDay = false;
            if (lecture.getTimes() != null) {
                for (com.syu.smarttimetable.data.model.LectureTime time : lecture.getTimes()) {
                    if (time != null && preferredFreeDays.contains(time.getDay())) {
                        violatesFreeDay = true;
                        break;
                    }
                }
            }

            if (lecture.getCategory() == CourseCategory.MAJOR) {
                if (lecture.getGrade() == userGrade) {
                    if (violatesFreeDay) conflictGradeMatchedMajors.add(lecture);
                    else safeGradeMatchedMajors.add(lecture);
                } else {
                    if (violatesFreeDay) conflictOtherMajors.add(lecture);
                    else safeOtherMajors.add(lecture);
                }
            } else if (lecture.getCategory() == CourseCategory.GENERAL) {
                if (violatesFreeDay) conflictGenerals.add(lecture);
                else safeGenerals.add(lecture);
            } else {
                others.add(lecture);
            }
        }

        // 각각 학점 순 정렬 등 기존 로직 유지 (교양은 셔플)
        safeGradeMatchedMajors.sort((f, s) -> Integer.compare(s.getCredits(), f.getCredits()));
        conflictGradeMatchedMajors.sort((f, s) -> Integer.compare(s.getCredits(), f.getCredits()));
        safeOtherMajors.sort((f, s) -> Integer.compare(s.getCredits(), f.getCredits()));
        conflictOtherMajors.sort((f, s) -> Integer.compare(s.getCredits(), f.getCredits()));
        Collections.shuffle(safeGenerals, new Random());
        Collections.shuffle(conflictGenerals, new Random());

        lectures.clear();

        // 핵심 포인트: "안전한(Safe)" 과목들을 무조건 먼저 탐색하도록 풀의 앞쪽에 배치
        lectures.addAll(safeGradeMatchedMajors);
        lectures.addAll(safeOtherMajors);
        lectures.addAll(safeGenerals);

        // 만약 안전한 과목들만으로 목표 학점을 못 채우면, 그제서야 공강을 깨는 과목들 탐색 시작
        lectures.addAll(conflictGradeMatchedMajors);
        lectures.addAll(conflictOtherMajors);
        lectures.addAll(conflictGenerals);
        lectures.addAll(others);
    }
    private boolean isValidByStudentIdParity(Lecture lecture, String studentId) {
        if (lecture == null) {
            return false;
        }

        ClassParity classParity = lecture.getClassParity();

        if (classParity == null || classParity == ClassParity.ALL) {
            return true;
        }

        Boolean isStudentEven = isStudentIdEven(studentId);

        if (isStudentEven == null) {
            return true;
        }

        if (classParity == ClassParity.EVEN) {
            return isStudentEven;
        }

        if (classParity == ClassParity.ODD) {
            return !isStudentEven;
        }

        return true;
    }

    private Boolean isStudentIdEven(String studentId) {
        if (studentId == null) {
            return null;
        }

        String trimmed = studentId.trim();

        if (trimmed.isEmpty()) {
            return null;
        }

        for (int i = trimmed.length() - 1; i >= 0; i--) {
            char ch = trimmed.charAt(i);

            if (Character.isDigit(ch)) {
                int digit = ch - '0';
                return digit % 2 == 0;
            }
        }

        return null;
    }
}