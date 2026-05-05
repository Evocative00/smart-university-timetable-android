package com.syu.smarttimetable.data.model;

import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.data.model.enums.FreeTimePreference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SoftConstraint implements Serializable {

    private boolean skipped;
    private List<DayOfWeek> preferredFreeDays;
    private FreeTimePreference freeTimePreference;
    private boolean keepLunch12To13Free;
    private boolean avoidGapOver3Hours;
    private List<String> preferredProfessors;
    private boolean considerTravelTime;
    private List<LectureTime> blockedTimes;

    public SoftConstraint() {
        this.skipped = false;
        this.preferredFreeDays = new ArrayList<>();
        this.freeTimePreference = FreeTimePreference.NONE;
        this.keepLunch12To13Free = false;
        this.avoidGapOver3Hours = false;
        this.preferredProfessors = new ArrayList<>();
        this.considerTravelTime = false;
        this.blockedTimes = new ArrayList<>();
    }

    public SoftConstraint(boolean skipped,
                          List<DayOfWeek> preferredFreeDays,
                          FreeTimePreference freeTimePreference,
                          boolean keepLunch12To13Free,
                          boolean avoidGapOver3Hours,
                          List<String> preferredProfessors,
                          boolean considerTravelTime) {
        this(skipped, preferredFreeDays, freeTimePreference, keepLunch12To13Free,
                avoidGapOver3Hours, preferredProfessors, considerTravelTime, new ArrayList<>());
    }

    public SoftConstraint(boolean skipped,
                          List<DayOfWeek> preferredFreeDays,
                          FreeTimePreference freeTimePreference,
                          boolean keepLunch12To13Free,
                          boolean avoidGapOver3Hours,
                          List<String> preferredProfessors,
                          boolean considerTravelTime,
                          List<LectureTime> blockedTimes) {
        this.skipped = skipped;
        this.preferredFreeDays = preferredFreeDays != null ? preferredFreeDays : new ArrayList<>();
        this.freeTimePreference = freeTimePreference != null ? freeTimePreference : FreeTimePreference.NONE;
        this.keepLunch12To13Free = keepLunch12To13Free;
        this.avoidGapOver3Hours = avoidGapOver3Hours;
        this.preferredProfessors = preferredProfessors != null ? preferredProfessors : new ArrayList<>();
        this.considerTravelTime = considerTravelTime;
        this.blockedTimes = blockedTimes != null ? blockedTimes : new ArrayList<>();
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    public List<DayOfWeek> getPreferredFreeDays() {
        return preferredFreeDays;
    }

    public void setPreferredFreeDays(List<DayOfWeek> preferredFreeDays) {
        this.preferredFreeDays = preferredFreeDays;
    }

    public FreeTimePreference getFreeTimePreference() {
        return freeTimePreference;
    }

    public void setFreeTimePreference(FreeTimePreference freeTimePreference) {
        this.freeTimePreference = freeTimePreference;
    }

    public boolean isKeepLunch12To13Free() {
        return keepLunch12To13Free;
    }

    public void setKeepLunch12To13Free(boolean keepLunch12To13Free) {
        this.keepLunch12To13Free = keepLunch12To13Free;
    }

    public boolean isAvoidGapOver3Hours() {
        return avoidGapOver3Hours;
    }

    public void setAvoidGapOver3Hours(boolean avoidGapOver3Hours) {
        this.avoidGapOver3Hours = avoidGapOver3Hours;
    }

    public List<String> getPreferredProfessors() {
        return preferredProfessors;
    }

    public void setPreferredProfessors(List<String> preferredProfessors) {
        this.preferredProfessors = preferredProfessors;
    }

    public boolean isConsiderTravelTime() {
        return considerTravelTime;
    }

    public void setConsiderTravelTime(boolean considerTravelTime) {
        this.considerTravelTime = considerTravelTime;
    }

    public List<LectureTime> getBlockedTimes() {
        return blockedTimes;
    }

    public void setBlockedTimes(List<LectureTime> blockedTimes) {
        this.blockedTimes = blockedTimes != null ? blockedTimes : new ArrayList<>();
    }
}