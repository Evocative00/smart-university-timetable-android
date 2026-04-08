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

        for (Lecture lecture : lecturePool) {
            if (lecture == null) {
                continue;
            }

            String lectureKey = RequiredLectureConstraint.buildLectureKey(lecture);
            if (!request.getFixedLectureKeys().contains(lectureKey)) {
                continue;
            }

            if (baseTimetable.hasConflict(lecture)) {
                return results;
            }

            if (usedCourseCodes.contains(lecture.getCourseCode())) {
                return results;
            }

            if (baseTimetable.getTotalCredits() + lecture.getCredits() > request.getMaxCredits()) {
                return results;
            }

            baseTimetable.addLecture(lecture);
            usedCourseCodes.add(lecture.getCourseCode());
            unresolvedFixedKeys.remove(lectureKey);
        }

        if (!unresolvedFixedKeys.isEmpty()) {
            return results;
        }

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