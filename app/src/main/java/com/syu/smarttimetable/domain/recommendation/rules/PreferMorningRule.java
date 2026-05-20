package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.FreeTimePreference;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

public class PreferMorningRule implements RecommendationRule {

    private static final int NOON = 12 * 60;

    // 오후 공강 선호 = 오후를 비우고 싶음 = 수업이 오전 쪽에 몰려 있으면 좋음
    private static final int ALL_CLASSES_BEFORE_NOON_BONUS = 20;
    private static final int HAS_AFTERNOON_CLASS_PENALTY = -10;

    @Override
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        if (timetable == null
                || request == null
                || request.getSoftConstraint() == null
                || request.getSoftConstraint().isSkipped()
                || request.getSoftConstraint().getFreeTimePreference() != FreeTimePreference.AFTERNOON) {
            return 0;
        }

        boolean hasAnyClass = false;
        boolean hasAfternoonClass = false;

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime time : lecture.getTimes()) {
                if (time == null) {
                    continue;
                }

                hasAnyClass = true;

                if (time.getStartTime() >= NOON) {
                    hasAfternoonClass = true;
                    break;
                }
            }

            if (hasAfternoonClass) {
                break;
            }
        }

        if (!hasAnyClass) {
            return 0;
        }

        return hasAfternoonClass ? HAS_AFTERNOON_CLASS_PENALTY : ALL_CLASSES_BEFORE_NOON_BONUS;
    }
}
