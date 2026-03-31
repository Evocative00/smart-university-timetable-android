package com.syu.smarttimetable.domain.recommendation.constraints;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RequiredLectureConstraint implements RecommendationConstraint {

    @Override
    public boolean isValid(List<Lecture> timetable, RecommendationRequest request) {
        return containsAllFixedLectures(timetable, new HashSet<>(request.getFixedLectureKeys()));
    }

    @Override
    public String getErrorMessage() {
        return "고정 과목이 누락되었습니다.";
    }

    public boolean containsAllFixedLectures(List<Lecture> timetable, Set<String> fixedLectureKeys) {
        if (fixedLectureKeys == null || fixedLectureKeys.isEmpty()) {
            return true;
        }

        Set<String> includedLectureKeys = new HashSet<>();

        for (Lecture lecture : timetable) {
            if (lecture != null) {
                includedLectureKeys.add(buildLectureKey(lecture));
            }
        }

        return includedLectureKeys.containsAll(fixedLectureKeys);
    }

    public static String buildLectureKey(Lecture lecture) {
        return lecture.getCourseCode() + "|"
                + lecture.getCourseName() + "|"
                + lecture.getProfessor();
    }
}