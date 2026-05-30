package com.syu.smarttimetable.domain.recommendation.constraints;

import android.util.Log;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

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
    }

    public ValidationResult validate(List<Lecture> timetable, RecommendationRequest request) {
        List<String> errors = new ArrayList<>();

        if (timetable == null) {
            errors.add("시간표가 비어 있습니다.");
            return new ValidationResult(false, errors);
        }

        // 방어적 충돌 검사: 고정 강의의 과목 코드가 이수 완료 목록에도 존재하는 경우
        if (request != null
                && request.getFixedLectureKeys() != null
                && request.getCompletedCourseCodes() != null) {
            for (Lecture lecture : timetable) {
                if (lecture == null) continue;

                String lectureKey = RequiredLectureConstraint.buildLectureKey(lecture);
                if (request.getFixedLectureKeys().contains(lectureKey)
                        && request.getCompletedCourseCodes().contains(lecture.getCourseCode())) {
                    String warning = "고정 과목 '" + lecture.getCourseName()
                            + "'이(가) 이수 완료 목록에도 포함되어 있습니다.";
                    errors.add(warning);
                    Log.w(TAG, "Fixed-Completed conflict: " + lectureKey);
                }
            }
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