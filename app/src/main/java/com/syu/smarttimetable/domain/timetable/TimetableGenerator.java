package com.syu.smarttimetable.domain.timetable;

import android.util.Log;

import com.syu.smarttimetable.data.model.Lecture;
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
            Log.w(TAG, "Invalid input: lecturePool=" + (lecturePool == null ? "null" : lecturePool.size()) +
                    ", request=" + (request == null ? "null" : "ok"));
            return results;
        }

        Log.d(TAG, "Starting with " + lecturePool.size() + " lectures, need " +
                request.getMinCredits() + "~" + request.getMaxCredits() + " credits");
        Log.d(TAG, "Fixed lectures required: " + request.getFixedLectureKeys().size());

        Timetable baseTimetable = new Timetable();
        Set<String> usedCourseCodes = new HashSet<>();
        Set<String> usedCourseNames = new HashSet<>();
        Set<String> unresolvedFixedKeys = new HashSet<>(request.getFixedLectureKeys());

        // 고정 강의 추가
        for (Lecture lecture : lecturePool) {
            if (lecture == null) {
                continue;
            }

            String lectureKey = RequiredLectureConstraint.buildLectureKey(lecture);
            if (!request.getFixedLectureKeys().contains(lectureKey)) {
                continue;
            }

            // 고정 강의 추가 가능 여부 확인
            if (baseTimetable.hasConflict(lecture)) {
                Log.w(TAG, "Fixed lecture has time conflict: " + lectureKey);
                // 고정 강의가 충돌하면 추천 불가 - 빈 리스트 반환
                return results;
            }

            if (usedCourseCodes.contains(lecture.getCourseCode())) {
                Log.w(TAG, "Fixed lecture already added: " + lecture.getCourseCode());
                // 같은 과목을 여러 번 추가하려는 시도 - 빈 리스트 반환
                return results;
            }

            String normalizedCourseName = normalizeCourseName(lecture.getCourseName());
            if (!normalizedCourseName.isEmpty() && usedCourseNames.contains(normalizedCourseName)) {
                Log.w(TAG, "Skipping duplicate fixed course name: " + normalizedCourseName);
                unresolvedFixedKeys.remove(lectureKey);
                continue;
            }

            if (baseTimetable.getTotalCredits() + lecture.getCredits() > request.getMaxCredits()) {
                Log.w(TAG, "Fixed lectures exceed max credits: " + (baseTimetable.getTotalCredits() + lecture.getCredits()) + " > " + request.getMaxCredits());
                // 고정 강의만으로도 학점 초과 - 빈 리스트 반환
                return results;
            }

            baseTimetable.addLecture(lecture);
            usedCourseCodes.add(lecture.getCourseCode());
            if (!normalizedCourseName.isEmpty()) {
                usedCourseNames.add(normalizedCourseName);
            }
            unresolvedFixedKeys.remove(lectureKey);
            Log.d(TAG, "Added fixed lecture: " + lectureKey + ", current credits: " + baseTimetable.getTotalCredits());
        }

        // 모든 고정 강의를 찾지 못한 경우 - 요청된 고정 강의가 있으면 반환, 없으면 계속 진행
        if (!request.getFixedLectureKeys().isEmpty() && !unresolvedFixedKeys.isEmpty()) {
            Log.w(TAG, "Could not find some fixed lectures: " + unresolvedFixedKeys);
            return results;
        }

        int baseCredits = baseTimetable.getTotalCredits();
        Log.d(TAG, "Base credits: " + baseCredits);

        // 남은 강의들로 조합 생성
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

            // 학번 홀수/짝수에 따라 필터링
            if (!isValidByStudentIdParity(lecture, request.getStudentId())) {
                continue;
            }

            remainingLectures.add(lecture);
        }

        // 학년 맞춤 전공 과목을 우선 선택하도록 정렬 + 교양은 랜덤
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

        // 고정 강의만으로도 학점 조건 만족하면 그것도 포함
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

        int failedToAddCount = 0;
        for (int i = startIndex; i < lecturePool.size(); i++) {
            Lecture nextLecture = lecturePool.get(i);

            if (nextLecture == null) {
                continue;
            }

            if (usedCourseCodes.contains(nextLecture.getCourseCode())) {
                failedToAddCount++;
                continue;
            }

            String normalizedCourseName = normalizeCourseName(nextLecture.getCourseName());
            if (!normalizedCourseName.isEmpty() && usedCourseNames.contains(normalizedCourseName)) {
                failedToAddCount++;
                continue;
            }

            if (current.hasConflict(nextLecture)) {
                failedToAddCount++;
                continue;
            }

            if (currentCredits + nextLecture.getCredits() > request.getMaxCredits()) {
                failedToAddCount++;
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

        if (failedToAddCount == lecturePool.size() - startIndex) {
            Log.d(TAG, "No more lectures can be added at depth with " + currentCredits + " credits");
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

    /**
     * 사용자 학년에 맞는 전공 과목을 우선적으로 선택하도록 강의를 정렬합니다.
     * 정렬 순서: 학년 맞춤 전공 > 다른 학년 전공 > 교양 (교양은 랜덤)
     */
    private void sortLecturesByGradeAndCategory(List<Lecture> lectures, RecommendationRequest request) {
        if (lectures == null || request == null || request.getUserGrade() <= 0) {
            return;
        }

        final int userGrade = request.getUserGrade();
        Random random = new Random();

        // 먼저 카테고리별로 분류
        List<Lecture> majorGradeMatched = new ArrayList<>();
        List<Lecture> majorOtherGrade = new ArrayList<>();
        List<Lecture> general = new ArrayList<>();

        for (Lecture lecture : lectures) {
            if (lecture == null) continue;

            int priority = getPriority(lecture, userGrade);
            if (priority == 3) {
                majorGradeMatched.add(lecture);
            } else if (priority == 2) {
                majorOtherGrade.add(lecture);
            } else if (priority == 1) {
                general.add(lecture);
            }
        }

        // 각 카테고리 내에서 정렬
        // 학년 맞춤 전공: 학점이 많은 것 먼저
        majorGradeMatched.sort((l1, l2) -> Integer.compare(l2.getCredits(), l1.getCredits()));

        // 다른 학년 전공: 학점이 많은 것 먼저
        majorOtherGrade.sort((l1, l2) -> Integer.compare(l2.getCredits(), l1.getCredits()));

        // 교양: 랜덤 정렬
        Collections.shuffle(general, random);

        // 다시 합치기
        lectures.clear();
        lectures.addAll(majorGradeMatched);
        lectures.addAll(majorOtherGrade);
        lectures.addAll(general);
    }

    private int getPriority(Lecture lecture, int userGrade) {
        if (lecture == null) {
            return 0;
        }

        if (lecture.getCategory() == CourseCategory.MAJOR) {
            if (lecture.getGrade() == userGrade) {
                return 3; // 학년 맞춤 전공 (최우선)
            } else {
                return 2; // 다른 학년 전공
            }
        } else if (lecture.getCategory() == CourseCategory.GENERAL) {
            return 1; // 교양
        }

        return 0;
    }

    /**
     * 학번의 홀수/짝수와 강의의 홀수/짝수(ClassParity)를 비교하여 필터링합니다.
     * - 학번이 짝수면 짝수 강의만 선택
     * - 학번이 홀수면 홀수 강의만 선택
     * - ALL은 모두 선택
     */
    private boolean isValidByStudentIdParity(Lecture lecture, String studentId) {
        if (lecture == null || studentId == null || studentId.isEmpty()) {
            return true; // studentId가 없으면 모두 허용
        }

        ClassParity parity = lecture.getClassParity();
        if (parity == ClassParity.ALL) {
            return true; // ALL은 항상 허용
        }

        try {
            int studentIdNum = Integer.parseInt(studentId);
            boolean isStudentEven = studentIdNum % 2 == 0;

            if (parity == ClassParity.EVEN) {
                return isStudentEven; // 짝수 학번이면 짝수 강의만
            } else if (parity == ClassParity.ODD) {
                return !isStudentEven; // 홀수 학번이면 홀수 강의만
            }
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid student ID: " + studentId);
        }

        return true;
    }
}