package com.syu.smarttimetable.data.model;

import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private List<Lecture> lectures = new ArrayList<>();

    public Timetable() {}

    public Timetable(List<Lecture> lectures) {
        this.lectures = new ArrayList<>(lectures);
    }
    
    public void addLecture(Lecture lecture) {
        this.lectures.add(lecture);
    }

    public void removeLecture(Lecture lecture) {
        this.lectures.remove(lecture);
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public boolean hasConflict(Lecture newLecture) {
        for (Lecture existing : lectures) {
            for (LectureTime existingTime : existing.getTimes()) {
                for (LectureTime newTime : newLecture.getTimes()) {
                    if (existingTime.getDay() == newTime.getDay()) {
                        if (!(newTime.getEndTime() <= existingTime.getStartTime() || 
                              newTime.getStartTime() >= existingTime.getEndTime())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public int getTotalCredits() {
        int total = 0;
        for (Lecture lecture : lectures) {
            total += lecture.getCredits();
        }
        return total;
    }
}
