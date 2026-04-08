package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.HashSet;
import java.util.Set;

public class PreferFreeDayRule implements RecommendationRule {

    @Override
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        if (request == null
                || request.getSoftConstraint() == null
                || request.getSoftConstraint().isSkipped()
                || request.getSoftConstraint().getPreferredFreeDays() == null
                || request.getSoftConstraint().getPreferredFreeDays().isEmpty()) {
            return 0;
        }

        Set<DayOfWeek> lectureDays = new HashSet<>();

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime time : lecture.getTimes()) {
                if (time != null) {
                    lectureDays.add(time.getDay());
                }
            }
        }

        int score = 0;

        for (DayOfWeek preferredFreeDay : request.getSoftConstraint().getPreferredFreeDays()) {
            if (lectureDays.contains(preferredFreeDay)) {
                score -= 30;
            } else {
                score += 45;
            }
        }

        return score;
    }
}