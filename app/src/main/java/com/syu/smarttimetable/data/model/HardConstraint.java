package com.syu.smarttimetable.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HardConstraint implements Serializable {

    private int targetCredits;
    private List<String> fixedLectureKeys;

    public HardConstraint() {
        this.fixedLectureKeys = new ArrayList<>();
    }

    public HardConstraint(int targetCredits, List<String> fixedLectureKeys) {
        this.targetCredits = targetCredits;
        this.fixedLectureKeys = fixedLectureKeys != null ? fixedLectureKeys : new ArrayList<>();
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
        this.fixedLectureKeys = fixedLectureKeys;
    }
}