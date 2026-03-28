package com.syu.smarttimetable.domain.recommendation.constraints;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.ArrayList;
import java.util.List;

public class HardConstraintValidator {

    private final List<RecommendationConstraint> constraints;

    public HardConstraintValidator() {
        constraints = new ArrayList<>();
        constraints.add(new CreditRangeConstraint());
        constraints.add(new RequiredLectureConstraint());
        constraints.add(new NoTimeConflictConstraint());
    }

    public ValidationResult validate(List<Lecture> timetable, RecommendationRequest request) {
        List<String> errors = new ArrayList<>();

        if (timetable == null) {
            errors.add("시간표가 비어 있습니다.");
            return new ValidationResult(false, errors);
        }

        for (RecommendationConstraint constraint : constraints) {
            if (!constraint.isValid(timetable, request)) {
                errors.add(constraint.getErrorMessage());
            }
        }

        return new ValidationResult(errors.isEmpty(), errors);
    }

    public static class ValidationResult {
        private final boolean valid;
        private final List<String> errors;

        public ValidationResult(boolean valid, List<String> errors) {
            this.valid = valid;
            this.errors = errors;
        }

        public boolean isValid() {
            return valid;
        }

        public List<String> getErrors() {
            return errors;
        }
    }
}