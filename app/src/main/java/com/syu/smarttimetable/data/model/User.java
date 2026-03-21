package com.syu.smarttimetable.data.model;

public class User {
    private String userId;
    private String email;
    private String name;
    private int grade;
    private String major;

    public User() {
    }

    public User(String userId, String email, String name, int grade, String major) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.grade = grade;
        this.major = major;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}