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

        for (List<LectureScheduleItem> items : byDay.values()) {
            items.sort(Comparator.comparingInt(item -> item.time.getStartTime()));

            for (int i = 0; i < items.size() - 1; i++) {
                LectureScheduleItem current = items.get(i);
                LectureScheduleItem next = items.get(i + 1);

                int gap = next.time.getStartTime() - current.time.getEndTime();
                boolean sameClassroom = safeEquals(current.lecture.getClassroom(), next.lecture.getClassroom());

                if (sameClassroom) {
                    score += 6;
                    continue;
                }

                if (gap < 20) {
                    score -= 12;
                } else if (gap < 40) {
                    score -= 4;
                } else {
                    score += 3;
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