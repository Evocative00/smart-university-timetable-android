package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.FreeTimePreference;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

public class PreferMorningRule implements RecommendationRule {

    private static final int NOON = 12 * 60;

    // 오전 수업 선호도 점수 (모든 수업이 오전에 끝나면 최대 점수, 중복 누적 방지)
    private static final int MORNING_COMPLETE_BONUS = 40;      // 모든 수업이 정오 전에 끝남
    private static final int MORNING_PARTIAL_BONUS = 25;       // 일부 오전 수업
    private static final int AFTERNOON_PENALTY = -15;          // 오후 수업 있음

    @Override
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        if (request == null
                || request.getSoftConstraint() == null
                || request.getSoftConstraint().isSkipped()
                || request.getSoftConstraint().getFreeTimePreference() != FreeTimePreference.AFTERNOON) {
            return 0;
        }

        boolean hasAfternoon = false;
        boolean hasAllMorning = true;

        // 시간표 전체에서 오전/오후 비율을 평가 (중복 누적 방지)
        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime time : lecture.getTimes()) {
                if (time == null) {
                    continue;
                }

                if (time.getStartTime() >= NOON) {
                    hasAfternoon = true;
                    hasAllMorning = false;
                    break;
                }
            }

            if (hasAfternoon) {
                break;
            }
        }

        // 시간표 전체 단위로 점수 계산 (시간마다 누적하지 않음)
        if (hasAllMorning) {
            return MORNING_COMPLETE_BONUS;
        } else if (hasAfternoon) {
            return AFTERNOON_PENALTY;
        }

        return MORNING_PARTIAL_BONUS;
    }
}