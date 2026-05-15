package com.syu.smarttimetable.data.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String email;
    private String name;
    private int grade;
    private String department;
    private String majorDetail;
    private String studentId;

    public User() {
    }

    public User(String userId, String email, String name, int grade,
                String department, String majorDetail, String studentId) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.grade = grade;
        this.department = department;
        this.majorDetail = majorDetail;
        this.studentId = studentId;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajorDetail() {
        return majorDetail;
    }

    public void setMajorDetail(String majorDetail) {
        this.majorDetail = majorDetail;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}