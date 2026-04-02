package com.syu.smarttimetable.domain.recommendation;

import com.syu.smarttimetable.data.model.SoftConstraint;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RecommendationRequest implements Serializable {

    private final int minCredits;
    private final int maxCredits;
    private final Set<String> fixedLectureKeys;
    private final Set<String> completedCourseCodes;
    private final SoftConstraint softConstraint;

    public RecommendationRequest(int desiredCredits,
                                 Set<String> fixedLectureKeys,
                                 Set<String> completedCourseCodes) {
        this(desiredCredits, desiredCredits, fixedLectureKeys, completedCourseCodes, new SoftConstraint());
    }

    public RecommendationRequest(int desiredCredits,
                                 Set<String> fixedLectureKeys,
                                 Set<String> completedCourseCodes,
                                 SoftConstraint softConstraint) {
        this(desiredCredits, desiredCredits, fixedLectureKeys, completedCourseCodes, softConstraint);
    }

    public RecommendationRequest(int minCredits,
                                 int maxCredits,
                                 Set<String> fixedLectureKeys,
                                 Set<String> completedCourseCodes) {
        this(minCredits, maxCredits, fixedLectureKeys, completedCourseCodes, new SoftConstraint());
    }

    public RecommendationRequest(int minCredits,
                                 int maxCredits,
                                 Set<String> fixedLectureKeys,
                                 Set<String> completedCourseCodes,
                                 SoftConstraint softConstraint) {
        this.minCredits = minCredits;
        this.maxCredits = maxCredits;
        this.fixedLectureKeys = fixedLectureKeys != null
                ? new HashSet<>(fixedLectureKeys)
                : new HashSet<>();
        this.completedCourseCodes = completedCourseCodes != null
                ? new HashSet<>(completedCourseCodes)
                : new HashSet<>();
        this.softConstraint = softConstraint != null ? softConstraint : new SoftConstraint();
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

    public SoftConstraint getSoftConstraint() {
        return softConstraint;
    }
}