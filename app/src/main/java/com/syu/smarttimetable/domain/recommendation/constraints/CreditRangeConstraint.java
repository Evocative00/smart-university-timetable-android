package com.syu.smarttimetable.domain.recommendation.constraints;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.List;

public class CreditRangeConstraint implements RecommendationConstraint {

    @Override
    public boolean isValid(List<Lecture> timetable, RecommendationRequest request) {
        return isCreditInRange(timetable, request.getMinCredits(), request.getMaxCredits());
    }

    @Override
    public String getErrorMessage() {
        return "희망 학점 범위를 벗어났습니다.";
    }

    public boolean isCreditInRange(List<Lecture> timetable, int minCredits, int maxCredits) {
        int totalCredits = 0;

        for (Lecture lecture : timetable) {
            if (lecture != null) {
                totalCredits += lecture.getCredits();
            }
        }

        return totalCredits >= minCredits && totalCredits <= maxCredits;
    }
}