package com.syu.smarttimetable.domain.recommendation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RecommendationRequest {

    // min/max를 둘 다 가지고 가서 나중에 범위 허용하기 쉽게 설계
    private final int minCredits;
    private final int maxCredits;
    private final Set<String> fixedLectureKeys;
    private final Set<String> completedCourseCodes;

    /**
     * 희망 학점을 정확히 하나만 받을 때 사용하는 생성자
     * min=max로 저장해서, 나중에 범위 허용으로 바꾸기 쉽게 해둠
     */
    public RecommendationRequest(int desiredCredits,
                                 Set<String> fixedLectureKeys,
                                 Set<String> completedCourseCodes) {
        this(desiredCredits, desiredCredits, fixedLectureKeys, completedCourseCodes);
    }

    /**
     * 범위형 학점 제약용 생성자
     */
    public RecommendationRequest(int minCredits,
                                 int maxCredits,
                                 Set<String> fixedLectureKeys,
                                 Set<String> completedCourseCodes) {
        this.minCredits = minCredits;
        this.maxCredits = maxCredits;
        this.fixedLectureKeys = fixedLectureKeys != null
                ? new HashSet<>(fixedLectureKeys)
                : new HashSet<>();
        this.completedCourseCodes = completedCourseCodes != null
                ? new HashSet<>(completedCourseCodes)
                : new HashSet<>();
    }

    public int getMinCredits() {
        return minCredits;
    }

    public int getMaxCredits() {
        return maxCredits;
    }

    public Set<String> getFixedLectureKeys() {
        return Collections.unmodifiableSet(fixedLectureKeys);
    }

    public Set<String> getCompletedCourseCodes() {
        return Collections.unmodifiableSet(completedCourseCodes);
    }
}