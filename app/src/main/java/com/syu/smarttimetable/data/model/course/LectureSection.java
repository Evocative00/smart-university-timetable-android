package com.syu.smarttimetable.data.model.course;

import data.model.common.ClassParity;

import java.util.ArrayList;
import java.util.List;

public class LectureSection {
    // Room 확장 시: courseId는 Course와 연결되는 foreign key 후보입니다.
    //분반 식별자
    private String sectionId;
    //어떤 course 소속인지 연결 키
    private String courseId;
    //분반 번호 01 -홀수, 02 - 짝수
    private String sectionNumber;
    //담당 교수 이름
    private String instructorName;
    //반 홀 짝 구분
    private ClassParity classParity;
    //분반의 수업 시간
    private List<MeetingTime> meetingTimes;

    //홀 짝 구분을 한 뒤에 분반 수업 시간 구분
    public LectureSection() {
        this.classParity = ClassParity.ALL;
        this.meetingTimes = new ArrayList<>();
    }

    //sectionId: 절대 중복 없는 내부 키로 사용
    //sectionNumber: 화면/사용자 선택용 값
    public LectureSection(String sectionId, String courseId, String sectionNumber, String instructorName,
                          ClassParity classParity, List<MeetingTime> meetingTimes) {
        this.sectionId = sectionId;
        this.courseId = courseId;
        this.sectionNumber = sectionNumber;
        this.instructorName = instructorName;
        this.classParity = classParity == null ? ClassParity.ALL : classParity;
        setMeetingTimes(meetingTimes);
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(String sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public ClassParity getClassParity() {
        return classParity;
    }

    public void setClassParity(ClassParity classParity) {
        this.classParity = classParity == null ? ClassParity.ALL : classParity;
    }


    public List<MeetingTime> getMeetingTimes() {
        return new ArrayList<>(meetingTimes);
    }

    // setMeetingTimes 메서드에서 null 체크 후 빈 리스트로 초기화하여 안정성 확보
    // getMeetingTimes는 내부 리스트를 그대로 주지 않고 newArrayList<>로 반환
    public void setMeetingTimes(List<MeetingTime> meetingTimes) {
        this.meetingTimes = meetingTimes == null ? new ArrayList<>() : new ArrayList<>(meetingTimes);
    }

    @Override
    public String toString() {
        return "LectureSection{" +
                "sectionId='" + sectionId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", sectionNumber='" + sectionNumber + '\'' +
                ", instructorName='" + instructorName + '\'' +
                ", classParity=" + classParity +
                ", meetingTimes=" + meetingTimes +
                '}';
    }
}

