package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CompactScheduleRule implements RecommendationRule {

    // 연강 최소화 점수 (3시간 이상 공백 회피)
    private static final int GAP_EXCESSIVE_PENALTY = -20;   // 3시간 이상 공백: -20점/회
    private static final int GAP_MODERATE_PENALTY = -5;     // 1시간 이상 공백: -5점/회
    private static final int GAP_MINIMAL_BONUS = 3;         // 1시간 미만: +3점/회

    @Override
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        if (request == null
                || request.getSoftConstraint() == null
                || request.getSoftConstraint().isSkipped()
                || !request.getSoftConstraint().isAvoidGapOver3Hours()) {
            return 0;
        }

        Map<DayOfWeek, List<LectureTime>> byDay = new EnumMap<>(DayOfWeek.class);

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime time : lecture.getTimes()) {
                if (time != null) {
                    byDay.computeIfAbsent(time.getDay(), key -> new ArrayList<>()).add(time);
                }
            }
        }

        int score = 0;

        for (List<LectureTime> dayTimes : byDay.values()) {
            dayTimes.sort(Comparator.comparingInt(LectureTime::getStartTime));

            // 각 요일별 연강 간격 분석 (총 개수로 일관성 유지)
            for (int i = 0; i < dayTimes.size() - 1; i++) {
                int gap = dayTimes.get(i + 1).getStartTime() - dayTimes.get(i).getEndTime();

                if (gap > 180) {  // 3시간 이상
                    score += GAP_EXCESSIVE_PENALTY;
                } else if (gap >= 60) {  // 1시간 이상 3시간 미만
                    score += GAP_MODERATE_PENALTY;
                } else {  // 1시간 미만 (연강)
                    score += GAP_MINIMAL_BONUS;
                }
            }
        }

        return score;
    }
}