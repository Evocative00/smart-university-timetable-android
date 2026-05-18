package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.FreeTimePreference;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

public class AvoidEveningRule implements RecommendationRule {

    private static final int NOON = 12 * 60;
    private static final int EVENING = 18 * 60;

    // 저녁 회피 점수 (시간표 단위 평가, 중복 누적 방지)
    private static final int NO_EVENING_BONUS = 30;        // 저녁(18시) 수업 없음
    private static final int HAS_EVENING_PENALTY = -40;    // 저녁 수업 있음

    @Override
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        if (timetable == null
                || request == null
                || request.getSoftConstraint() == null
                || request.getSoftConstraint().isSkipped()) {
            return 0;
        }

        boolean hasEvening = false;

        // 시간표 전체에서 저녁 수업 여부 확인 (중복 누적 방지)
        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime time : lecture.getTimes()) {
                if (time != null && time.getEndTime() > EVENING) {
                    hasEvening = true;
                    break;
                }
            }

            if (hasEvening) {
                break;
            }
        }

        // 모든 preference에 대해 저녁 회피 적용
        if (hasEvening) {
            return HAS_EVENING_PENALTY;
        }

        FreeTimePreference preference = request.getSoftConstraint().getFreeTimePreference();

        // 오전/오후 선호도에 따른 추가 점수 (저녁은 없는 경우)
        int score = NO_EVENING_BONUS;

        if (preference == FreeTimePreference.MORNING) {
            // 오전 자유 선호 시 오후 수업이 없으면 추가 보너스
            boolean hasAfternoon = false;
            for (Lecture lecture : timetable.getLecturesReadOnly()) {
                if (lecture == null || lecture.getTimes() == null) {
                    continue;
                }
                for (LectureTime time : lecture.getTimes()) {
                    if (time != null && time.getStartTime() >= NOON && time.getStartTime() < EVENING) {
                        hasAfternoon = true;
                        break;
                    }
                }
                if (hasAfternoon) break;
            }
            if (!hasAfternoon) {
                score += 15;
            } else {
                score -= 8;
            }
        } else if (preference == FreeTimePreference.AFTERNOON) {
            // 오후 자유 선호 시 오전 수업이 없으면 추가 보너스
            boolean hasMorning = false;
            for (Lecture lecture : timetable.getLecturesReadOnly()) {
                if (lecture == null || lecture.getTimes() == null) {
                    continue;
                }
                for (LectureTime time : lecture.getTimes()) {
                    if (time != null && time.getStartTime() < NOON) {
                        hasMorning = true;
                        break;
                    }
                }
                if (hasMorning) break;
            }
            if (!hasMorning) {
                score += 15;
            } else {
                score -= 8;
            }
        }

        return score;
    }
}