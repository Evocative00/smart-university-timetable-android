package com.syu.smarttimetable.ui.timetable;

import com.syu.smarttimetable.domain.recommendation.RecommendationEngine;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TimetableCacheManager {
    private static final HashMap<String, List<RecommendationEngine.TimetableScoreTuple>> cache = new HashMap<>();

    public static String generateCacheKey(RecommendationRequest request) {
        return request.getMinCredits() + "_" +
                request.getMaxCredits() + "_" +
                Objects.hash(request.getFixedLectureKeys(), request.getSoftConstraint());
    }

    public static List<RecommendationEngine.TimetableScoreTuple> getFromCache(String key) {
        return cache.get(key);
    }

    public static void addToCache(String key, List<RecommendationEngine.TimetableScoreTuple> results) {
        cache.put(key, results);
    }
}