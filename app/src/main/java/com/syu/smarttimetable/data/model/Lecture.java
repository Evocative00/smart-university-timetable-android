package com.syu.smarttimetable.data.model;

import com.syu.smarttimetable.data.model.enums.ClassParity;
import com.syu.smarttimetable.data.model.enums.CourseCategory;
import com.syu.smarttimetable.data.model.enums.Department;
import com.syu.smarttimetable.data.model.enums.GeneralArea;
import com.syu.smarttimetable.data.model.enums.MajorType;
import com.syu.smarttimetable.data.model.enums.RequirementType;

import java.io.Serializable;
import java.util.List;
public class Lecture implements Serializable {   // 강의 하나를 나타내는 클래스

    private String courseCode; 	  // 과목 코드
    private String courseName;   	// 과목명
    private String professor;   		// 교수명
    private int credits;        		// 학점
    private Department department; 	  // 학과 (예: 컴퓨터공학과)
    private MajorType major;   	  // 세부 전공 (컴공, 소프트웨어, NONE)
    private int grade;         		 // 학년 (0~4, 무관이면 0)
    private ClassParity classParity;	// 홀수/짝수/전체 - ODD EVEN ALL
    private String classroom;  		// 강의실 정보
    private CourseCategory category;   // 전공 / 교양 / 선택 - (MAJOR, GENERAL)
    private RequirementType requirement; // 필수 / 선택 - (REQUIRED, OPTIONAL)
    private GeneralArea generalArea;
    /*교양 세부 영역 - SOCIAL_SCIENCE, NATURAL_SCIENCE, DIGITAL_LITERACY, HUMANITIES_ART, CHARACTER_EDUCATION, NONE */

    //시간 정보
    private List<LectureTime> times; // 여러 시간(요일별)을 저장

    // 생성자: Lecture 객체 생성 시 값 세팅
    public Lecture(String courseCode,
                   String courseName,
                   String professor,
                   int credits,
                   Department department,
                   MajorType major,
                   int grade,
                   ClassParity classParity,
                   String classroom,
                   CourseCategory category,
                   RequirementType requirement,
                   GeneralArea generalArea,
                   List<LectureTime> times) {

        this.courseCode = courseCode;     // 과목 코드 설정
        this.courseName = courseName;     // 과목명 설정
        this.professor = professor;       // 교수명 설정
        this.credits = credits;           // 학점 설정
        this.department = department;     // 학과 설정
        this.major = major;               // 세부 전공 설정
        this.grade = grade;               // 학년 설정
        this.classParity = classParity;   // 홀수/짝수 설정
        this.classroom = classroom;       // 강의실 설정
        this.category = category;         // 전공/교양/선택 설정
        this.requirement = requirement;   // 필수/선택 설정

        // 교양(GENERAL)이 아닌 경우 → generalArea는 의미 없으므로 NONE으로 강제함
        if (category != CourseCategory.GENERAL) {
            this.generalArea = GeneralArea.NONE;
        } else {
            this.generalArea = generalArea; // 교양이라면 정상적으로 저장
        }

        this.times = times; // 시간 리스트 설정 (여러 요일 가능)
    }

    //Getter 메서드
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public String getProfessor() { return professor; }
    public int getCredits() { return credits; }
    public Department getDepartment() { return department; }
    public MajorType getMajor() { return major; }
    public int getGrade() { return grade; }
    public ClassParity getClassParity() { return classParity; }
    public String getClassroom() { return classroom; }
    public CourseCategory getCategory() { return category; }
    public RequirementType getRequirement() { return requirement; }
    public GeneralArea getGeneralArea() { return generalArea; }
    public List<LectureTime> getTimes() { return times; }
}