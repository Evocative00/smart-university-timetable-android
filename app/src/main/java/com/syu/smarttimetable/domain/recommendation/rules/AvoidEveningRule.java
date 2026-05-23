package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.FreeTimePreference;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

public class AvoidEveningRule implements RecommendationRule {

    private static final int NOON = 12 * 60;
    private static final int EVENING = 18 * 60;

    // 오전/오후 공강 선호는 '요일 공강'보다 약한 취향 점수로만 반영한다.
    private static final int FREE_TIME_MATCH_BONUS = 35;
    private static final int FREE_TIME_MISMATCH_PENALTY = -15;

    // 저녁 수업 회피는 전체 시간표 단위로 1번만 평가한다.
    private static final int NO_EVENING_BONUS = 15;
    private static final int HAS_EVENING_PENALTY = -25;

    @Override
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        if (timetable == null
                || request == null
                || request.getSoftConstraint() == null
                || request.getSoftConstraint().isSkipped()) {
            return 0;
        }

        boolean hasMorningClass = false;
        boolean hasAfternoonClass = false;
        boolean hasEveningClass = false;

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime time : lecture.getTimes()) {
                if (time == null) {
                    continue;
                }

                if (time.getStartTime() < NOON) {
                    hasMorningClass = true;
                }

                if (time.getStartTime() >= NOON && time.getStartTime() < EVENING) {
                    hasAfternoonClass = true;
                }

                if (time.getEndTime() > EVENING) {
                    hasEveningClass = true;
                }
            }
        }

        int score = hasEveningClass ? HAS_EVENING_PENALTY : NO_EVENING_BONUS;

        FreeTimePreference preference = request.getSoftConstraint().getFreeTimePreference();

        if (preference == FreeTimePreference.MORNING) {
            // 오전 공강 선호 = 오전을 비우고 싶음
            score += hasMorningClass ? FREE_TIME_MISMATCH_PENALTY : FREE_TIME_MATCH_BONUS;
        } else if (preference == FreeTimePreference.AFTERNOON) {
            // 오후 공강 선호 = 오후를 비우고 싶음
            score += hasAfternoonClass ? FREE_TIME_MISMATCH_PENALTY : FREE_TIME_MATCH_BONUS;
        }

        return score;
    }
}
