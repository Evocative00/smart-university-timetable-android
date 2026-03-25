package com.syu.smarttimetable.data.model.course;

import data.model.common.CourseCategory;

    /* course 패키지 내에서 클래스 간의 연결 관계
        Course (과목 마스터 정보) 1 - N LectureSection (개설 분반 정보) 1 - N MeetingTime (분반의 개별 시간 정보)
        과목 단위 필터링(전공/교양)
        분반 단위 선택(교수/반)
        시간 블록 단위 충돌 검사 를 각자 책임 분리해서 처리 가능하게 설계
     */

//course 과목 자체(코드, 이름, 학점, 분류)
//LectureSection 과목의 실제 개설 분반 (교수, 분반 구분 홀 짝, 시간 )
//MeetingTime 한 분반의 개별 시간 블럭(요일 + 시작교시 + 종료교시)
public class Course {
    // Room 확장 시: courseId를 @PrimaryKey로 두고 개설 분반은 별도 Entity로 분리하기 좋습니다.
    private String courseId;
    private String courseCode;
    private String courseName;
    private CourseCategory category;
    private int credits;

    public Course() {
    }

    // 과목 마스터 정보
    public Course(String courseId, String courseCode, String courseName, CourseCategory category, int credits) {
        //시스텝 내부 식별자 (DB 안에서 쓰이는 고유 키) - 절대 중복 없는 값이고 안정적으로 유지해야되는 값임
        this.courseId = courseId;
        //수강편람, 사용자와 학교에 보이는 공식 과목 코드
        this.courseCode = courseCode;
        // 과목명
        this.courseName = courseName;
        //과목 카테고리 ( 전공, 교양 )
        this.category = category;
        // 학점
        this.credits = credits;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public CourseCategory getCategory() {
        return category;
    }

    public void setCategory(CourseCategory category) {
        this.category = category;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", category=" + category +
                ", credits=" + credits +
                '}';
    }
}