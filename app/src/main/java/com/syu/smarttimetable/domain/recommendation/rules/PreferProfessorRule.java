package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.List;

public class PreferProfessorRule implements RecommendationRule {

    // 선호 교수 점수 (과목당 최대 한 번만 계산)
    private static final int PREFERRED_PROFESSOR_BONUS = 15;  // 선호 교수 과목당 +15점

    @Override
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        if (request == null
                || request.getSoftConstraint() == null
                || request.getSoftConstraint().isSkipped()
                || request.getSoftConstraint().getPreferredProfessors() == null
                || request.getSoftConstraint().getPreferredProfessors().isEmpty()) {
            return 0;
        }

        int score = 0;
        List<String> preferredProfessors = request.getSoftConstraint().getPreferredProfessors();

        // 각 과목별로 최대 1번만 계산 (여러 교수 매칭되어도 중복 불가)
        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getProfessor() == null) {
                continue;
            }

            boolean matchFound = false;

            for (String preferredProfessor : preferredProfessors) {
                if (preferredProfessor != null
                        && !preferredProfessor.trim().isEmpty()
                        && lecture.getProfessor().contains(preferredProfessor.trim())) {
                    score += PREFERRED_PROFESSOR_BONUS;
                    matchFound = true;
                    break;  // 첫 매칭만 인정 (중복 불가)
                }
            }
        }

        return score;
    }
}