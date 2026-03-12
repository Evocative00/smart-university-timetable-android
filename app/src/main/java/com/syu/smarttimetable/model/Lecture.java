package com.syu.smarttimetable.model;

public class Lecture {

    private String courseName;
    private String professor;
    private int credit;
    private String department;
    private int grade;
    private String category;
    private String day;
    private int startTime;
    private int endTime;

    public Lecture() {
    }

    public Lecture(String courseName, String professor, int credit, String department,
                   int grade, String category, String day, int startTime, int endTime) {
        this.courseName = courseName;
        this.professor = professor;
        this.credit = credit;
        this.department = department;
        this.grade = grade;
        this.category = category;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}