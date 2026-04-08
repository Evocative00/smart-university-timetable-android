package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LunchBreakRule implements RecommendationRule {

    private static final int LUNCH_START = 12 * 60;
    private static final int LUNCH_END = 13 * 60;

    @Override
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        if (request == null
                || request.getSoftConstraint() == null
                || request.getSoftConstraint().isSkipped()
                || !request.getSoftConstraint().isKeepLunch12To13Free()) {
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
            if (dayTimes.isEmpty()) {
                continue;
            }

            if (isLunchFree(dayTimes)) {
                score += 18;
            } else {
                score -= 20;
            }
        }

        return score;
    }

    private boolean isLunchFree(List<LectureTime> dayTimes) {
        for (LectureTime time : dayTimes) {
            if (time.getStartTime() < LUNCH_END && time.getEndTime() > LUNCH_START) {
                return false;
            }
        }
        return true;
    }
}