package com.syu.smarttimetable.domain.recommendation;

import android.util.Log;

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

/**
 * 시간표의 종합 점수를 계산하는 클래스
 *
 * 점수 체계:
 * - 각 Rule은 시간표 전체 단위로 정확히 1번만 평가됨
 * - 점수는 하드 제약(필수)과 소프트 제약(선호도)만 포함
 * - 각 Rule별 최대 점수 범위를 관리하여 특정 제약이 과도하게 영향을 주지 않도록 함
 */
public class ScoreCalculator {

    private static final String TAG = "ScoreCalculator";

    private final List<RecommendationRule> rules;

    public ScoreCalculator() {
        this.rules = new ArrayList<>();

        // 우선순위 순서대로 추가
        // 1. 학년별 전공 과목 선호 (학위 이수 관련)
        this.rules.add(new PreferGradeMatchedMajorRule());

        // 2. 소프트 제약 기반 규칙들
        this.rules.add(new PreferFreeDayRule());           // 공강 요일 선호
        this.rules.add(new PreferMorningRule());          // 오전 수업 선호
        this.rules.add(new AvoidEveningRule());           // 저녁 수업 회피
        this.rules.add(new LunchBreakRule());             // 점심시간 보장
        this.rules.add(new CompactScheduleRule());        // 연강 최소화
        this.rules.add(new PreferProfessorRule());        // 선호 교수 선택
        this.rules.add(new ConsiderTravelTimeRule());     // 이동 시간 고려
    }

    public ScoreCalculator(List<RecommendationRule> rules) {
        this.rules = rules != null ? rules : new ArrayList<>();
    }

    /**
     * 시간표의 종합 점수를 계산
     *
     * @param timetable 평가할 시간표
     * @param request 추천 요청 정보
     * @return 계산된 종합 점수
     */
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        int totalScore = 0;

        for (RecommendationRule rule : rules) {
            if (rule == null) {
                continue;
            }

            int ruleScore = rule.calculateScore(timetable, request);
            totalScore += ruleScore;

            // 디버깅 로그: 각 규칙별 점수를 기록
            Log.d(TAG, "Rule: " + rule.getClass().getSimpleName()
                    + " -> Score: " + ruleScore
                    + " (Total: " + totalScore + ")");
        }

        Log.d(TAG, "Final Score: " + totalScore);
        return totalScore;
    }
}