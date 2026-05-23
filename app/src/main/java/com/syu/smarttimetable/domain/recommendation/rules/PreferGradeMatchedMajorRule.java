package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.CourseCategory;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

/**
 * 사용자 학년과 일치하는 전공 과목에 높은 점수를 부여하는 규칙.
 *
 * 점수 체계:
 * - 학년 일치 전공: 과목당 200점 (최대 5과목 = 1000점이지만 실제로는 4-5개 과목이 상한)
 * - 다른 학년 전공: 과목당 30점
 * - 교양 과목: 과목당 20점
 * - 학년 일치 전공 5개 이상 시 보너스: 50점
 *
 * 주의:
 * 현재 앱은 학년별 채플을 fixedLectureKeys에 자동 추가한다.
 * 따라서 fixedLectureKeys가 비어 있지 않다고 해서 이 규칙을 비활성화하면 안 된다.
 */
public class PreferGradeMatchedMajorRule implements RecommendationRule {

    private static final int GRADE_MATCHED_MAJOR_SCORE = 100;   // 감소: 200 -> 100
    private static final int OTHER_MAJOR_SCORE = 15;            // 감소: 30 -> 15
    private static final int GENERAL_SCORE = 10;                // 감소: 20 -> 10
    private static final int ENOUGH_GRADE_MAJOR_BONUS = 30;     // 감소: 50 -> 30

    @Override
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        if (timetable == null || request == null || request.getUserGrade() <= 0) {
            return 0;
        }

        int score = 0;
        int gradeMatchedMajorCount = 0;

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null) {
                continue;
            }

            if (lecture.getCategory() == CourseCategory.MAJOR) {
                if (lecture.getGrade() == request.getUserGrade()) {
                    score += GRADE_MATCHED_MAJOR_SCORE;
                    gradeMatchedMajorCount++;
                } else {
                    score += OTHER_MAJOR_SCORE;
                }
            } else if (lecture.getCategory() == CourseCategory.GENERAL) {
                score += GENERAL_SCORE;
            }
        }

        if (gradeMatchedMajorCount >= 5) {
            score += ENOUGH_GRADE_MAJOR_BONUS;
        }

        return score;
    }
}