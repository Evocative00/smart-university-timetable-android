package com.syu.smarttimetable.domain.recommendation;

import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.domain.recommendation.rules.AvoidEveningRule;
import com.syu.smarttimetable.domain.recommendation.rules.CompactScheduleRule;
import com.syu.smarttimetable.domain.recommendation.rules.ConsiderTravelTimeRule;
import com.syu.smarttimetable.domain.recommendation.rules.LunchBreakRule;
import com.syu.smarttimetable.domain.recommendation.rules.PreferFreeDayRule;
import com.syu.smarttimetable.domain.recommendation.rules.PreferGradeMatchedMajorRule;
import com.syu.smarttimetable.domain.recommendation.rules.PreferMorningRule;
import com.syu.smarttimetable.domain.recommendation.rules.PreferProfessorRule;
import com.syu.smarttimetable.domain.recommendation.rules.RecommendationRule;

import java.util.ArrayList;
import java.util.List;

public class ScoreCalculator {

    private final List<RecommendationRule> rules;

    public ScoreCalculator() {
        this.rules = new ArrayList<>();
        this.rules.add(new PreferGradeMatchedMajorRule());  // 학년 맞춤 전공 과목 우선
        this.rules.add(new PreferFreeDayRule());
        this.rules.add(new PreferMorningRule());
        this.rules.add(new AvoidEveningRule());
        this.rules.add(new LunchBreakRule());
        this.rules.add(new CompactScheduleRule());
        this.rules.add(new PreferProfessorRule());
        this.rules.add(new ConsiderTravelTimeRule());
    }

    public ScoreCalculator(List<RecommendationRule> rules) {
        this.rules = rules != null ? rules : new ArrayList<>();
    }

    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        int score = 0;

        for (RecommendationRule rule : rules) {
            if (rule == null) {
                continue;
            }
            score += rule.calculateScore(timetable, request);
        }

        return score;
    }
}