package com.syu.smarttimetable.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HardConstraint implements Serializable {

    private int targetCredits;
    private List<String> fixedLectureKeys;
    private List<String> completedCourseCodes;

    public HardConstraint() {
        this.fixedLectureKeys = new ArrayList<>();
        this.completedCourseCodes = new ArrayList<>();
    }

    public HardConstraint(int targetCredits, List<String> fixedLectureKeys) {
        this(targetCredits, fixedLectureKeys, new ArrayList<>());
    }

    public HardConstraint(int targetCredits,
                          List<String> fixedLectureKeys,
                          List<String> completedCourseCodes) {
        this.targetCredits = targetCredits;
        this.fixedLectureKeys = fixedLectureKeys != null ? fixedLectureKeys : new ArrayList<>();
        this.completedCourseCodes = completedCourseCodes != null ? completedCourseCodes : new ArrayList<>();
    }

    public int getTargetCredits() {
        return targetCredits;
    }

    public void setTargetCredits(int targetCredits) {
        this.targetCredits = targetCredits;
    }

    public List<String> getFixedLectureKeys() {
        return fixedLectureKeys;
    }

    public void setFixedLectureKeys(List<String> fixedLectureKeys) {
        this.fixedLectureKeys = fixedLectureKeys != null ? fixedLectureKeys : new ArrayList<>();
    }

    public List<String> getCompletedCourseCodes() {
        return completedCourseCodes;
    }

    public void setCompletedCourseCodes(List<String> completedCourseCodes) {
        this.completedCourseCodes = completedCourseCodes != null ? completedCourseCodes : new ArrayList<>();
    }
}