package com.syu.smarttimetable.domain.recommendation.constraints;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.SoftConstraint;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PreferredFreeDayConstraint implements RecommendationConstraint {

    @Override
    public boolean isValid(List<Lecture> timetable, RecommendationRequest request) {
        if (request == null) {
            return true;
        }

        SoftConstraint softConstraint = request.getSoftConstraint();
        if (softConstraint == null
                || softConstraint.isSkipped()
                || softConstraint.getPreferredFreeDays() == null
                || softConstraint.getPreferredFreeDays().isEmpty()) {
            return true;
        }

        return hasNoLecturesOnPreferredFreeDays(timetable, softConstraint.getPreferredFreeDays());
    }

    @Override
    public String getErrorMessage() {
        return "선택한 공강 요일에 수업이 있습니다.";
    }

    private boolean hasNoLecturesOnPreferredFreeDays(List<Lecture> timetable, List<DayOfWeek> preferredFreeDays) {
        if (timetable == null || timetable.isEmpty() || preferredFreeDays == null || preferredFreeDays.isEmpty()) {
            return true;
        }

        Set<DayOfWeek> lectureDays = new HashSet<>();

        for (Lecture lecture : timetable) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime time : lecture.getTimes()) {
                if (time != null && time.getDay() != null) {
                    lectureDays.add(time.getDay());
                }
            }
        }

        for (DayOfWeek preferredFreeDay : preferredFreeDays) {
            if (lectureDays.contains(preferredFreeDay)) {
                return false;
            }
        }

        return true;
    }
}

