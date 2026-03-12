package com.syu.smarttimetable.model;

public class User {

    private String uid;
    private String email;
    private String department;
    private int grade;

    public User() {
    }

    public User(String uid, String email, String department, int grade) {
        this.uid = uid;
        this.email = email;
        this.department = department;
        this.grade = grade;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}