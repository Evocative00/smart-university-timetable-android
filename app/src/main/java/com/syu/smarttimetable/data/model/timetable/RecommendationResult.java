package com.syu.smarttimetable.data.model.timetable;

import java.util.ArrayList;
import java.util.List;


    /* 완성된 시간표와 추천 점수 결과를 분리해서 관리하는 패키지
    Timetable → 추천 엔진이 평가 → ScoreDetail 여러 개 생성 → RecommendationResult에 timetable + totalScore + scoreDetails로 합침
    Timetable: 시간표 그 자체
    RecommendationResult: 추천 1건(시간표 + 총점 + 점수근거)
    ScoreDetail: 점수 근거 1개 항목
     */

//Timetable과 ScoreDetail을 분리해서 포함하므로 시간표 내용과 점수 근거를 함께 관리할 수 있음

public class RecommendationResult {
    // Room 확장 시: Timetable 1개와 ScoreDetail 여러 개를 관계형으로 나누면 조회가 쉬워집니다.
    //추천 결과 식별자
    private String recommendationId;
    //어떤 시간표인지
    private Timetable timetable;
    //최종 점수 -> 추천 시간표에 영향을 줌
    private int totalScore;
    //점수 구성 내역 - nullsafe _ 방어적 복사패턴
    private List<ScoreDetail> scoreDetails;

    public RecommendationResult() {
        this.scoreDetails = new ArrayList<>();
    }

    public RecommendationResult(String recommendationId, Timetable timetable, int totalScore, List<ScoreDetail> scoreDetails) {
        this.recommendationId = recommendationId;
        this.timetable = timetable;
        this.totalScore = totalScore;
        setScoreDetails(scoreDetails);
    }

    public String getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(String recommendationId) {
        this.recommendationId = recommendationId;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public List<ScoreDetail> getScoreDetails() {
        return new ArrayList<>(scoreDetails);
    }

    public void setScoreDetails(List<ScoreDetail> scoreDetails) {
        this.scoreDetails = scoreDetails == null ? new ArrayList<>() : new ArrayList<>(scoreDetails);
    }

    @Override
    public String toString() {
        return "RecommendationResult{" +
                "recommendationId='" + recommendationId + '\'' +
                ", timetable=" + timetable +
                ", totalScore=" + totalScore +
                ", scoreDetails=" + scoreDetails +
                '}';
    }
}

