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

public class ConsiderTravelTimeRule implements RecommendationRule {

    // 이동 시간 고려 점수 (이동 거리 시뮬레이션)
    private static final int SAME_CLASSROOM_BONUS = 4;     // 같은 강의실: +4점
    private static final int SHORT_GAP_PENALTY = -8;       // 20분 미만 이동 불가: -8점
    private static final int MODERATE_GAP_PENALTY = -3;    // 20-40분 이동: -3점
    private static final int LONG_GAP_BONUS = 2;           // 40분 이상 이동 가능: +2점

    @Override
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        if (request == null
                || request.getSoftConstraint() == null
                || request.getSoftConstraint().isSkipped()
                || !request.getSoftConstraint().isConsiderTravelTime()) {
            return 0;
        }

        Map<DayOfWeek, List<LectureScheduleItem>> byDay = new EnumMap<>(DayOfWeek.class);

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime time : lecture.getTimes()) {
                if (time != null) {
                    byDay.computeIfAbsent(time.getDay(), key -> new ArrayList<>())
                            .add(new LectureScheduleItem(lecture, time));
                }
            }
        }

        int score = 0;

        // 각 요일별 이동 시간 분석
        for (List<LectureScheduleItem> items : byDay.values()) {
            items.sort(Comparator.comparingInt(item -> item.time.getStartTime()));

            // 연속 수업 간 이동 시간 평가 (개별 점수 누적)
            for (int i = 0; i < items.size() - 1; i++) {
                LectureScheduleItem current = items.get(i);
                LectureScheduleItem next = items.get(i + 1);

                int gap = next.time.getStartTime() - current.time.getEndTime();
                boolean sameClassroom = safeEquals(current.lecture.getClassroom(), next.lecture.getClassroom());

                if (sameClassroom) {
                    score += SAME_CLASSROOM_BONUS;
                    continue;
                }

                if (gap < 20) {
                    score += SHORT_GAP_PENALTY;
                } else if (gap < 40) {
                    score += MODERATE_GAP_PENALTY;
                } else {
                    score += LONG_GAP_BONUS;
                }
            }
        }

        return score;
    }

    private boolean safeEquals(String first, String second) {
        if (first == null) {
            return second == null;
        }
        return first.equals(second);
    }

    private static class LectureScheduleItem {
        private final Lecture lecture;
        private final LectureTime time;

        private LectureScheduleItem(Lecture lecture, LectureTime time) {
            this.lecture = lecture;
            this.time = time;
        }
    }
}