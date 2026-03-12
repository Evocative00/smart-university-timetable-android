package com.syu.smarttimetable.model;

public class UserPreference {

    private int targetCredit;
    private boolean preferMorning;
    private boolean preferNoFreeTime;
    private boolean preferMajor;
    private String preferredFreeDay;

    public UserPreference() {
    }

    public UserPreference(int targetCredit, boolean preferMorning, boolean preferNoFreeTime,
                          boolean preferMajor, String preferredFreeDay) {
        this.targetCredit = targetCredit;
        this.preferMorning = preferMorning;
        this.preferNoFreeTime = preferNoFreeTime;
        this.preferMajor = preferMajor;
        this.preferredFreeDay = preferredFreeDay;
    }

    public int getTargetCredit() {
        return targetCredit;
    }

    public void setTargetCredit(int targetCredit) {
        this.targetCredit = targetCredit;
    }

    public boolean isPreferMorning() {
        return preferMorning;
    }

    public void setPreferMorning(boolean preferMorning) {
        this.preferMorning = preferMorning;
    }

    public boolean isPreferNoFreeTime() {
        return preferNoFreeTime;
    }

    public void setPreferNoFreeTime(boolean preferNoFreeTime) {
        this.preferNoFreeTime = preferNoFreeTime;
    }

    public boolean isPreferMajor() {
        return preferMajor;
    }

    public void setPreferMajor(boolean preferMajor) {
        this.preferMajor = preferMajor;
    }

    public String getPreferredFreeDay() {
        return preferredFreeDay;
    }

    public void setPreferredFreeDay(String preferredFreeDay) {
        this.preferredFreeDay = preferredFreeDay;
    }
}