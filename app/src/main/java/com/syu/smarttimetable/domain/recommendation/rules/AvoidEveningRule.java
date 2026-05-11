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
        if (request == null || request.getSoftConstraint() == null || request.getSoftConstraint().isSkipped()) {
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

                // 1. 공강 선호도가 선택된 경우 공통적으로 저녁 시간대(18시 이후) 페널티 적용
                if (request.getSoftConstraint().getFreeTimePreference() != FreeTimePreference.NONE) {
                    if (time.getEndTime() > EVENING) {
                        score -= 15;
                    }
                }

                // 2. 오전/오후 공강 선호도에 따른 가점 및 감점 로직 분리
                if (request.getSoftConstraint().getFreeTimePreference() == FreeTimePreference.MORNING) {
                    // 오전 공강 선호 (오후 수업 가점, 오전 수업 감점)
                    if (time.getStartTime() >= NOON && time.getStartTime() < EVENING) {
                        score += 12;
                    } else if (time.getStartTime() < NOON) {
                        score -= 10;
                    }
                } else if (request.getSoftConstraint().getFreeTimePreference() == FreeTimePreference.AFTERNOON) {
                    // 오후 공강 선호 (오전 수업 가점, 오후 수업 감점)
                    if (time.getStartTime() < NOON) {
                        score += 12;
                    } else if (time.getStartTime() >= NOON && time.getStartTime() < EVENING) {
                        score -= 10;
                    }
                }
            }
        }

        return score;
    }
}