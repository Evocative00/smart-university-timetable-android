package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

public interface RecommendationRule {
    int calculateScore(Timetable timetable, RecommendationRequest request);
}