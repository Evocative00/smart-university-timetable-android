package com.syu.smarttimetable.domain.timetable;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;
import com.syu.smarttimetable.domain.recommendation.constraints.RequiredLectureConstraint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TimetableGenerator {

    private static final int MAX_RESULTS = 200;

    public List<Timetable> generateCandidates(List<Lecture> lecturePool, RecommendationRequest request) {
        List<Timetable> results = new ArrayList<>();

        if (lecturePool == null || lecturePool.isEmpty() || request == null) {
            return results;
        }

        Timetable baseTimetable = new Timetable();
        Set<String> usedCourseCodes = new HashSet<>();
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
                // 고정 강의가 충돌하면 추천 불가 - 빈 리스트 반환
                return results;
            }

            if (usedCourseCodes.contains(lecture.getCourseCode())) {
                // 같은 과목을 여러 번 추가하려는 시도 - 빈 리스트 반환
                return results;
            }

            if (baseTimetable.getTotalCredits() + lecture.getCredits() > request.getMaxCredits()) {
                // 고정 강의만으로도 학점 초과 - 빈 리스트 반환
                return results;
            }

            baseTimetable.addLecture(lecture);
            usedCourseCodes.add(lecture.getCourseCode());
            unresolvedFixedKeys.remove(lectureKey);
        }

        // 모든 고정 강의를 찾지 못한 경우
        if (!unresolvedFixedKeys.isEmpty()) {
            return results;
        }

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

            remainingLectures.add(lecture);
        }

        backtrack(
                remainingLectures,
                0,
                baseTimetable,
                usedCourseCodes,
                request,
                results
        );

        // 백트래킹 결과가 없으면, 고정 강의만으로도 학점 조건 만족하면 그것을 결과로 사용
        if (results.isEmpty()
                && baseTimetable.getTotalCredits() >= request.getMinCredits()
                && baseTimetable.getTotalCredits() <= request.getMaxCredits()) {
            results.add(new Timetable(baseTimetable.getLecturesReadOnly()));
        }

        return results;
    }

    private void backtrack(List<Lecture> lecturePool,
                           int startIndex,
                           Timetable current,
                           Set<String> usedCourseCodes,
                           RecommendationRequest request,
                           List<Timetable> results) {

        if (results.size() >= MAX_RESULTS) {
            return;
        }

        int currentCredits = current.getTotalCredits();

        if (currentCredits >= request.getMinCredits() && currentCredits <= request.getMaxCredits()) {
            results.add(new Timetable(current.getLecturesReadOnly()));
        }

        if (currentCredits >= request.getMaxCredits()) {
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

            if (current.hasConflict(nextLecture)) {
                continue;
            }

            if (currentCredits + nextLecture.getCredits() > request.getMaxCredits()) {
                continue;
            }

            current.addLecture(nextLecture);
            usedCourseCodes.add(nextLecture.getCourseCode());

            backtrack(
                    lecturePool,
                    i + 1,
                    current,
                    usedCourseCodes,
                    request,
                    results
            );

            current.removeLecture(nextLecture);
            usedCourseCodes.remove(nextLecture.getCourseCode());
        }
    }
}