package com.syu.smarttimetable.domain.recommendation.constraints;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.List;

public interface RecommendationConstraint {

    boolean isValid(List<Lecture> timetable, RecommendationRequest request);

    String getErrorMessage();
}