package com.syu.smarttimetable.domain.recommendation.constraints;

import android.util.Log;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import com.syu.smarttimetable.domain.recommendation.constraints.PreferredFreeDayConstraint;

import java.util.ArrayList;
import java.util.List;

public class HardConstraintValidator {

    private static final String TAG = "HardConstraintValidator";

    private final List<RecommendationConstraint> constraints;

    public HardConstraintValidator() {
        constraints = new ArrayList<>();
        constraints.add(new CreditRangeConstraint());
        constraints.add(new RequiredLectureConstraint());
        constraints.add(new NoTimeConflictConstraint());
        constraints.add(new BlockedTimeConstraint());
        // 공강은 하드 제약이 아니라 소프트 제약으로 관리하여, 필수 과목과의 충돌 방지
        // constraints.add(new PreferredFreeDayConstraint());
    }

    public ValidationResult validate(List<Lecture> timetable, RecommendationRequest request) {
        List<String> errors = new ArrayList<>();

        if (timetable == null) {
            errors.add("시간표가 비어 있습니다.");
            return new ValidationResult(false, errors);
        }

        int totalCredits = 0;
        for (Lecture lecture : timetable) {
            if (lecture != null) {
                totalCredits += lecture.getCredits();
            }
        }
        Log.d(TAG, "Validating timetable with " + timetable.size() + " lectures, " + totalCredits + " credits");

        for (RecommendationConstraint constraint : constraints) {
            if (!constraint.isValid(timetable, request)) {
                String error = constraint.getErrorMessage();
                errors.add(error);
                Log.d(TAG, "Constraint failed: " + error);
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