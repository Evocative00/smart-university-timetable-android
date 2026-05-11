package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.CourseCategory;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

/**
 * 사용자 학년과 일치하는 전공 과목에 높은 점수를 부여하는 규칙
 * 하드제약이 없을 때 학년별 전공 과목을 우선 추천
 */
public class PreferGradeMatchedMajorRule implements RecommendationRule {

    private static final int GRADE_MATCHED_MAJOR_SCORE = 200;  // 학년 맞춤 전공 과목: 200점 (매우 높음)
    private static final int OTHER_MAJOR_SCORE = 30;           // 다른 학년 전공 과목: 30점
    private static final int GENERAL_SCORE = 20;               // 교양 과목: 20점

    @Override
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        if (request == null || request.getUserGrade() <= 0) {
            return 0;
        }

        // 하드제약이 있는 경우는 이 규칙을 적용하지 않음
        if (request.getFixedLectureKeys() != null && !request.getFixedLectureKeys().isEmpty()) {
            return 0;
        }

        int score = 0;
        int gradeMatchedMajorCount = 0;

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null) {
                continue;
            }

            if (lecture.getCategory() == CourseCategory.MAJOR) {
                // 전공 과목
                if (lecture.getGrade() == request.getUserGrade()) {
                    // 학년이 일치하는 전공 과목
                    score += GRADE_MATCHED_MAJOR_SCORE;
                    gradeMatchedMajorCount++;
                } else if (lecture.getGrade() > 0) {
                    // 다른 학년의 전공 과목
                    score += OTHER_MAJOR_SCORE;
                } else {
                    // 학년 정보가 없는 전공 과목
                    score += OTHER_MAJOR_SCORE;
                }
            } else if (lecture.getCategory() == CourseCategory.GENERAL) {
                // 교양 과목
                score += GENERAL_SCORE;
            }
        }

        // 추가 보너스: 적어도 5개의 학년 맞춤 전공 과목이 있으면 보너스
        if (gradeMatchedMajorCount >= 5) {
            score += 50;
        }

        return score;
    }
}

