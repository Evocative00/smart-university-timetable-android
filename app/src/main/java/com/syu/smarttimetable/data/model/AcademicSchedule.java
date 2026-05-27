package com.syu.smarttimetable.data.model;

public class AcademicSchedule {

    private final String date;
    private final String title;
    private final String category;
    private final String description;

    public AcademicSchedule(String date, String title, String category, String description) {
        this.date = date;
        this.title = title;
        this.category = category;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
