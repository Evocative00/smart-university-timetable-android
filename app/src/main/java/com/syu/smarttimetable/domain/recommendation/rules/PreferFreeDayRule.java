package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.HashSet;
import java.util.Set;

public class PreferFreeDayRule implements RecommendationRule {

    // 공강 선호는 다른 모든 소프트 제약을 압도하도록 극단적으로 높은 가중치를 설정한다.
    // 공강 미만족: -10000점 (거의 탈락 수준)
    // 공강 만족: +10000점 (다른 모든 이유를 압도)
    private static final int PENALTY_PER_VIOLATED_DAY = 10000;
    private static final int BONUS_PER_SATISFIED_DAY = 10000;

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
                if (time != null && time.getDay() != null) {
                    lectureDays.add(time.getDay());
                }
            }
        }

        int score = 0;

        for (DayOfWeek preferredFreeDay : request.getSoftConstraint().getPreferredFreeDays()) {
            if (lectureDays.contains(preferredFreeDay)) {
                // Apply penalty if preferred free day has lectures
                score -= PENALTY_PER_VIOLATED_DAY;
            } else {
                // Apply bonus if preferred free day is actually free
                score += BONUS_PER_SATISFIED_DAY;
            }
        }

        return score;
    }
}