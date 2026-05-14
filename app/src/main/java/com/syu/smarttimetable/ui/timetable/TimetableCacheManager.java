package com.syu.smarttimetable.ui.timetable;

import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.SoftConstraint;
import com.syu.smarttimetable.domain.recommendation.RecommendationEngine;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TimetableCacheManager {

    private static final HashMap<String, List<RecommendationEngine.TimetableScoreTuple>> cache = new HashMap<>();

    private TimetableCacheManager() {
        // 유틸 클래스라 객체 생성 방지
    }

    public static String generateCacheKey(RecommendationRequest request) {
        if (request == null) {
            return "null_request";
        }

        return request.getMinCredits()
                + "_"
                + request.getMaxCredits()
                + "_fixed:"
                + sortedListString(request.getFixedLectureKeys())
                + "_completed:"
                + sortedListString(request.getCompletedCourseCodes())
                + "_soft:"
                + buildSoftConstraintKey(request.getSoftConstraint());
    }

    public static List<RecommendationEngine.TimetableScoreTuple> getFromCache(String key) {
        if (key == null) {
            return null;
        }

        return cache.get(key);
    }

    public static void addToCache(String key, List<RecommendationEngine.TimetableScoreTuple> results) {
        if (key == null || results == null) {
            return;
        }

        cache.put(key, results);
    }

    public static void clear() {
        cache.clear();
    }

    private static String sortedListString(Iterable<String> values) {
        if (values == null) {
            return "[]";
        }

        List<String> list = new ArrayList<>();

        for (String value : values) {
            if (value != null) {
                list.add(value);
            }
        }

        Collections.sort(list);
        return list.toString();
    }

    private static String buildSoftConstraintKey(SoftConstraint softConstraint) {
        if (softConstraint == null) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();

        builder.append("skipped=").append(softConstraint.isSkipped());
        builder.append(",freeDays=").append(softConstraint.getPreferredFreeDays());
        builder.append(",freeTime=").append(softConstraint.getFreeTimePreference());
        builder.append(",lunch=").append(softConstraint.isKeepLunch12To13Free());
        builder.append(",avoidGap=").append(softConstraint.isAvoidGapOver3Hours());
        builder.append(",professors=").append(sortedListString(softConstraint.getPreferredProfessors()));
        builder.append(",travel=").append(softConstraint.isConsiderTravelTime());
        builder.append(",blocked=").append(buildBlockedTimeKey(softConstraint.getBlockedTimes()));

        return builder.toString();
    }

    private static String buildBlockedTimeKey(List<LectureTime> blockedTimes) {
        if (blockedTimes == null || blockedTimes.isEmpty()) {
            return "[]";
        }

        List<String> timeKeys = new ArrayList<>();

        for (LectureTime time : blockedTimes) {
            if (time == null) {
                continue;
            }

            timeKeys.add(time.getDay() + "-" + time.getStartTime() + "-" + time.getEndTime());
        }

        Collections.sort(timeKeys);
        return timeKeys.toString();
    }
}