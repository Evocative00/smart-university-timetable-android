package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.HashSet;
import java.util.Set;

public class PreferFreeDayRule implements RecommendationRule {

    // 공강 요일은 중요한 선호 조건이지만, 전공/학년/학점 조건을 완전히 압도하지 않도록 제한한다.
    private static final int BONUS_PER_SATISFIED_DAY = 60;
    private static final int PENALTY_PER_VIOLATED_DAY = -80;

    @Override
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        if (timetable == null
                || request == null
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
                if (time != null && time.getDay() != null) {
                    lectureDays.add(time.getDay());
                }
            }
        }

        int score = 0;

        for (DayOfWeek preferredFreeDay : request.getSoftConstraint().getPreferredFreeDays()) {
            if (preferredFreeDay == null) {
                continue;
            }

            if (lectureDays.contains(preferredFreeDay)) {
                score += PENALTY_PER_VIOLATED_DAY;
            } else {
                score += BONUS_PER_SATISFIED_DAY;
            }
        }

        return score;
    }
}
