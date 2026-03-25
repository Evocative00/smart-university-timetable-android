package com.syu.smarttimetable.data.model.timetable;
public class ScoreDetail {
    // Room 확장 시: RecommendationResult와 1:N 관계로 분리 저장하기 좋습니다.
    //추천 점수의 근거 한 줄 표현

    //평가 기준 이름("MORNING_PREFERENCE", "EVENING_PENALTY", "CONFLICT_PENALTY" 등)
    private String criterion;
    //해당 평가 기준으로 얻은 점수 ( +n 점, -n 점 등 )
    private int score;
    // 사람에게 보여줄 설명 ("오전 수업 2개 포함으로 +n점입니다.")
    private String description;

    public ScoreDetail() {
    }

    public ScoreDetail(String criterion, int score, String description) {
        this.criterion = criterion;
        this.score = score;
        this.description = description;
    }

    public String getCriterion() {
        return criterion;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ScoreDetail{" +
                "criterion='" + criterion + '\'' +
                ", score=" + score +
                ", description='" + description + '\'' +
                '}';
    }
}

