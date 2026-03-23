package com.syu.smarttimetable.data.model;

import com.syu.smarttimetable.data.model.enums.DayOfWeek;

import java.util.List;

public class UserPreference {

    private int preferredGrade;
    private String preferredMajor;
    private List<DayOfWeek> preferredDays;

    public UserPreference() {
    }

    public UserPreference(int preferredGrade, String preferredMajor, List<DayOfWeek> preferredDays) {
        this.preferredGrade = preferredGrade;
        this.preferredMajor = preferredMajor;
        this.preferredDays = preferredDays;
    }

    public int getPreferredGrade() {
        return preferredGrade;
    }

    public void setPreferredGrade(int preferredGrade) {
        this.preferredGrade = preferredGrade;
    }

    public String getPreferredMajor() {
        return preferredMajor;
    }

    public void setPreferredMajor(String preferredMajor) {
        this.preferredMajor = preferredMajor;
    }

    public List<DayOfWeek> getPreferredDays() {
        return preferredDays;
    }

    public void setPreferredDays(List<DayOfWeek> preferredDays) {
        this.preferredDays = preferredDays;
    }
}