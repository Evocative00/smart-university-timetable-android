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

            for (int i = 0; i < dayTimes.size() - 1; i++) {
                int gap = dayTimes.get(i + 1).getStartTime() - dayTimes.get(i).getEndTime();

                if (gap > 180) {
                    score -= 30;
                } else if (gap >= 60) {
                    score -= 8;
                } else {
                    score += 4;
                }
            }
        }

        return score;
    }
}