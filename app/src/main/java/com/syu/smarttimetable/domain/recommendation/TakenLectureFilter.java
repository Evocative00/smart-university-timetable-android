package com.syu.smarttimetable.domain.recommendation;

import com.syu.smarttimetable.data.model.Lecture;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TakenLectureFilter {

    /**
     * 이미 들은 과목(courseCode 기준)을 전체 강의 목록에서 제거
     */
    public List<Lecture> filterCompletedLectures(List<Lecture> allLectures,
                                                 Set<String> completedCourseCodes) {
        List<Lecture> result = new ArrayList<>();

        if (allLectures == null || allLectures.isEmpty()) {
            return result;
        }

        for (Lecture lecture : allLectures) {
            if (lecture == null) {
                continue;
            }

            if (completedCourseCodes != null
                    && completedCourseCodes.contains(lecture.getCourseCode())) {
                continue;
            }

            result.add(lecture);
        }

        return result;
    }
}