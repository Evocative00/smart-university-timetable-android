package com.syu.smarttimetable.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Timetable implements Serializable {

    private final List<Lecture> lectures = new ArrayList<>();

    public Timetable() {
    }

    public Timetable(List<Lecture> lectures) {
        if (lectures != null) {
            this.lectures.addAll(lectures);
        }
    }

    public void addLecture(Lecture lecture) {
        if (lecture != null) {
            lectures.add(lecture);
        }
    }

    public void removeLecture(Lecture lecture) {
        lectures.remove(lecture);
    }

    public List<Lecture> getLectures() {
        return new ArrayList<>(lectures);
    }

    public List<Lecture> getLecturesReadOnly() {
        return Collections.unmodifiableList(lectures);
    }

    public int getTotalCredits() {
        int total = 0;
        for (Lecture lecture : lectures) {
            if (lecture != null) {
                total += lecture.getCredits();
            }
        }
        return total;
    }

    public boolean hasConflict(Lecture newLecture) {
        if (newLecture == null || newLecture.getTimes() == null) {
            return false;
        }

        for (Lecture existing : lectures) {
            if (existing == null || existing.getTimes() == null) {
                continue;
            }

            for (LectureTime existingTime : existing.getTimes()) {
                for (LectureTime newTime : newLecture.getTimes()) {
                    if (isOverlap(existingTime, newTime)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean isOverlap(LectureTime first, LectureTime second) {
        if (first == null || second == null) {
            return false;
        }

        return first.getDay() == second.getDay()
                && first.getStartTime() < second.getEndTime()
                && first.getEndTime() > second.getStartTime();
    }
}