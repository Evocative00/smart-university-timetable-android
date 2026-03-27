package com.syu.smarttimetable.domain.recommendation.constraints;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.domain.recommendation.RecommendationUserInfo;

import java.util.ArrayList;
import java.util.List;

// 하드 제약 검사 클래스
// 각각의 세부 하드 제약들을 한 번에 모아서 검사함 (추천 X, 검증 O)
public class HardConstraint {

    // 하드 제약 목록
    private final List<RecommendationConstraint> constraints;

    public HardConstraint() {
        constraints = new ArrayList<RecommendationConstraint>();

        // 검사할 하드 제약들을 등록
        constraints.add(new CreditRangeConstraint());
        constraints.add(new RequiredLectureConstraint());
        constraints.add(new NoTimeConflictConstraint());
        constraints.add(new BlockedTimeConstraint());
    }

    public ValidationResult validate(List<Lecture> timetable, RecommendationUserInfo userInfo) {

        // 에러 메시지들을 모아둘 리스트
        List<String> errors = new ArrayList<String>();

        // 시간표 자체가 null이면 바로 실패
        if (timetable == null) {
            errors.add("시간표가 비어 있습니다.");
            return new ValidationResult(false, errors);
        }

        // 등록된 모든 하드 제약을 순서대로 검사
        for (RecommendationConstraint constraint : constraints) {
            if (!constraint.isValid(timetable, userInfo)) {
                errors.add(constraint.getErrorMessage());
            }
        }

        // 에러가 하나도 없으면 valid = true
        return new ValidationResult(errors.isEmpty(), errors);
    }

    // 결과 클래스
    public static class ValidationResult {

        private final boolean valid; // 통과 여부
        private final List<String> errors; // 실패 이유들

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