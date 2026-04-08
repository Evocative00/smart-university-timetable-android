package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.FreeTimePreference;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

public class AvoidEveningRule implements RecommendationRule {

    private static final int NOON = 12 * 60;
    private static final int EVENING = 18 * 60;

    @Override
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        if (request == null
                || request.getSoftConstraint() == null
                || request.getSoftConstraint().isSkipped()) {
            return 0;
        }

        int score = 0;

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime time : lecture.getTimes()) {
                if (time == null) {
                    continue;
                }

                if (request.getSoftConstraint().getFreeTimePreference() == FreeTimePreference.MORNING) {
                    if (time.getStartTime() >= NOON && time.getStartTime() < EVENING) {
                        score += 12;
                    } else if (time.getStartTime() < NOON) {
                        score -= 10;
                    }
                }

                if (time.getEndTime() > EVENING) {
                    score -= 15;
                }
            }
        }

        return score;
    }
}