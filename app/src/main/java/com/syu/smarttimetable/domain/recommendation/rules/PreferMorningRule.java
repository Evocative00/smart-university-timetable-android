package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.FreeTimePreference;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

public class PreferMorningRule implements RecommendationRule {

    private static final int NOON = 12 * 60;

    @Override
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        if (request == null
                || request.getSoftConstraint() == null
                || request.getSoftConstraint().isSkipped()
                || request.getSoftConstraint().getFreeTimePreference() != FreeTimePreference.AFTERNOON) {
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

                if (time.getEndTime() <= NOON) {
                    score += 12;
                } else if (time.getStartTime() < NOON) {
                    score += 4;
                } else {
                    score -= 10;
                }
            }
        }

        return score;
    }
}