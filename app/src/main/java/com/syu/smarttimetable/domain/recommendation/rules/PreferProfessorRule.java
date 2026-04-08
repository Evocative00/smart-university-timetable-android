package com.syu.smarttimetable.domain.recommendation.rules;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.List;

public class PreferProfessorRule implements RecommendationRule {

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

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getProfessor() == null) {
                continue;
            }

            for (String preferredProfessor : preferredProfessors) {
                if (preferredProfessor != null
                        && !preferredProfessor.trim().isEmpty()
                        && lecture.getProfessor().contains(preferredProfessor.trim())) {
                    score += 25;
                    break;
                }
            }
        }

        return score;
    }
}