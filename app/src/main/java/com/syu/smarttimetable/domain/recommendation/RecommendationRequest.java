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
    private final int userGrade;
    private final String studentId;

    public RecommendationRequest(int desiredCredits,
                                 Set<String> fixedLectureKeys,
                                 Set<String> completedCourseCodes) {
        this(desiredCredits, desiredCredits, fixedLectureKeys, completedCourseCodes, new SoftConstraint(), 0, "");
    }

    public RecommendationRequest(int desiredCredits,
                                 Set<String> fixedLectureKeys,
                                 Set<String> completedCourseCodes,
                                 SoftConstraint softConstraint) {
        this(desiredCredits, desiredCredits, fixedLectureKeys, completedCourseCodes, softConstraint, 0, "");
    }

    public RecommendationRequest(int minCredits,
                                 int maxCredits,
                                 Set<String> fixedLectureKeys,
                                 Set<String> completedCourseCodes) {
        this(minCredits, maxCredits, fixedLectureKeys, completedCourseCodes, new SoftConstraint(), 0, "");
    }

    public RecommendationRequest(int minCredits,
                                 int maxCredits,
                                 Set<String> fixedLectureKeys,
                                 Set<String> completedCourseCodes,
                                 SoftConstraint softConstraint) {
        this(minCredits, maxCredits, fixedLectureKeys, completedCourseCodes, softConstraint, 0, "");
    }

    public RecommendationRequest(int minCredits,
                                 int maxCredits,
                                 Set<String> fixedLectureKeys,
                                 Set<String> completedCourseCodes,
                                 SoftConstraint softConstraint,
                                 int userGrade) {
        this(minCredits, maxCredits, fixedLectureKeys, completedCourseCodes, softConstraint, userGrade, "");
    }

    public RecommendationRequest(int minCredits,
                                 int maxCredits,
                                 Set<String> fixedLectureKeys,
                                 Set<String> completedCourseCodes,
                                 SoftConstraint softConstraint,
                                 int userGrade,
                                 String studentId) {
        this.minCredits = minCredits;
        this.maxCredits = maxCredits;
        this.fixedLectureKeys = fixedLectureKeys != null
                ? new HashSet<>(fixedLectureKeys)
                : new HashSet<>();
        this.completedCourseCodes = completedCourseCodes != null
                ? new HashSet<>(completedCourseCodes)
                : new HashSet<>();
        this.softConstraint = softConstraint != null ? softConstraint : new SoftConstraint();
        this.userGrade = userGrade;
        this.studentId = studentId != null ? studentId : "";
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

    public int getUserGrade() {
        return userGrade;
    }

    public String getStudentId() {
        return studentId;
    }
}