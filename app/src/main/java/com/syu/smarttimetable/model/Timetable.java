package com.syu.smarttimetable.model;

import java.util.List;

public class Timetable {

    private List<Lecture> lectures;
    private int totalCredits;
    private int score;

    public Timetable() {
    }

    public Timetable(List<Lecture> lectures, int totalCredits, int score) {
        this.lectures = lectures;
        this.totalCredits = totalCredits;
        this.score = score;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}