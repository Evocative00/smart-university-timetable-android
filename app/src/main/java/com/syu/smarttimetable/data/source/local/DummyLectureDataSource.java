package com.syu.smarttimetable.data.source.local;

import com.syu.smarttimetable.data.model.enums.ClassParity;
import com.syu.smarttimetable.data.model.enums.CourseCategory;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.data.model.enums.Department;
import com.syu.smarttimetable.data.model.enums.GeneralArea;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.enums.MajorType;
import com.syu.smarttimetable.data.model.enums.RequirementType;

import java.util.List;

public class DummyLectureDataSource {

    public static List<Lecture> getLectures() {

        return java.util.Arrays.asList(
                // 컴퓨터공학부 1학년 과목 시작
                new Lecture(
                        "747",                          // 과목 코드
                        "글로컬 영어 I",                   // 과목명
                        "정보 없음",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "미지정",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 720) // 9시~12시
                        )
                ),

                new Lecture(
                        "755",                          // 과목 코드
                        "노작교육(그린교육)",                   // 과목명
                        "남상용",                        // 교수
                        1,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 780, 840) // 13시~14시
                        )
                ),

                new Lecture(
                        "733",                          // 과목 코드
                        "소프트웨어 원리",                   // 과목명
                        "신인수",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "노작교육실습장",      // 강의실
                        CourseCategory.MAJOR,           // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720) // 9시~12시
                        )
                ),

                new Lecture(
                        "734",                          // 과목 코드
                        "소프트웨어 원리",                   // 과목명
                        "신인수",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,           // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 720, 900) // 12시~15시
                        )
                ),

                new Lecture(
                        "1878",                          // 과목 코드
                        "인생설계와 진로 I",              // 과목명
                        "정수목",                        // 교수
                        1,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관403호(컴과실습실)",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 1080, 1140) // 18시~19시
                        )
                ),

                new Lecture(
                        "1879",                          // 과목 코드
                        "인생설계와 진로 I",              // 과목명
                        "정수목",                        // 교수
                        1,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관404호(컴과실습실)",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 1020, 1080) // 17시~18시
                        )
                ),

                new Lecture(
                        "723",                          // 과목 코드
                        "인생설계와 진로 I",              // 과목명
                        "김병국",                        // 교수
                        1,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관410호(컴과실습실)",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 1020, 1080) // 17시~18시
                        )
                ),

                new Lecture(
                        "722",                          // 과목 코드
                        "인생설계와 진로 I",              // 과목명
                        "김병국",                        // 교수
                        1,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관404호(컴과실습실)",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 1080, 1140) // 18시~19시
                        )
                ),

                new Lecture(
                        "754",                          // 과목 코드
                        "인성과사회",              // 과목명
                        "김동혜",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "사무엘관109호강의실",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 900) // 13시~15시
                        )
                ),

                new Lecture(
                        "753",                          // 과목 코드
                        "인성과사회",              // 과목명
                        "김동혜",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "바울관207호강의실",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 600, 720) // 10시~12시
                        )
                ),

                new Lecture(
                        "726",                          // 과목 코드
                        "채플",              // 과목명
                        "김동혜",                        // 교수
                        0,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "신학관208호(PBL소형)강의실",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 960, 1020) // 16시~17시
                        )
                ),

                new Lecture(
                        "1759",                          // 과목 코드
                        "AI를 위한 미적분학",              // 과목명
                        "이한청",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 720, 900) // 12시~15시
                        )
                ),

                new Lecture(
                        "1758",                          // 과목 코드
                        "AI를 위한 미적분학",              // 과목명
                        "이한청",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관403호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720) // 9시~12시
                        )
                ),

                // 2학년 과목 시작
                new Lecture(
                        "749",                          // 과목 코드
                        "객체지향프로그래밍 I",              // 과목명
                        "안영아",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720) // 9시~12시
                        )
                ),

                new Lecture(
                        "748",                          // 과목 코드
                        "객체지향프로그래밍 I",              // 과목명
                        "최희식",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 660), // 9시~11시
                                new LectureTime(DayOfWeek.MONDAY, 720, 780)  // 12시~13시
                        )
                ),

                new Lecture(
                        "737",                          // 과목 코드
                        "객체지향프로그래밍응용",              // 과목명
                        "최희식",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 780, 900)  // 13시~15시
                        )
                ),

                new Lecture(
                        "738",                          // 과목 코드
                        "객체지향프로그래밍응용",              // 과목명
                        "안영아",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,                 // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,           // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 720, 840)  // 12시~14시
                        )
                ),

                new Lecture(
                        "1756",                          // 과목 코드
                        "디지털 논리회로",              // 과목명
                        "정수목",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관404호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 600, 780)  // 10시~13시
                        )
                ),

                new Lecture(
                        "1757",                          // 과목 코드
                        "디지털 논리회로",              // 과목명
                        "정수목",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관404호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.FRIDAY, 600, 780)  // 10시~13시
                        )
                ),

                new Lecture(
                        "742",                          // 과목 코드
                        "생활과 윤리",              // 과목명
                        "김동혜",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,      // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "요한관323호(中)강의실",      // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 720, 840)  // 12시~14시
                        )
                ),

                new Lecture(
                        "743",                          // 과목 코드
                        "생활과 윤리",              // 과목명
                        "김동혜",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,      // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "에스라관113호강의실",      // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 900, 1020)  // 15시~17시
                        )
                ),

                new Lecture(
                        "719",                          // 과목 코드
                        "선형대수학",              // 과목명
                        "권윤기",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관403호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 840, 900),  // 14시~15시
                                new LectureTime(DayOfWeek.FRIDAY, 960, 1080)   // 16시~18시
                        )
                ),

                new Lecture(
                        "718",                          // 과목 코드
                        "선형대수학",              // 과목명
                        "권윤기",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관403호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,           // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 840),  // 13시~14시
                                new LectureTime(DayOfWeek.FRIDAY, 840, 960)   // 14시~16시
                        )
                ),

                new Lecture(
                        "761",                          // 과목 코드
                        "오픈소스SW이해와활용",              // 과목명
                        "신인수",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.SOFTWARE,               // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관403호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.FRIDAY, 900, 1020)   // 15시~17시
                        )
                ),

                new Lecture(
                        "720",                          // 과목 코드
                        "창의적 공학 설계",              // 과목명
                        "김기락",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관403호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 600, 780)   // 10시~13시
                        )
                ),

                new Lecture(
                        "736",                          // 과목 코드
                        "채플",              // 과목명
                        "김동혜",                        // 교수
                        0,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "대강당(메인)",                  // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 660, 720)   // 11시~12시
                        )
                ),

                new Lecture(
                        "752",                          // 과목 코드
                        "컴퓨터프로그래밍",              // 과목명
                        "이현주",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관410호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 840, 1020)   // 14시~17시
                        )
                ),

                new Lecture(
                        "751",                          // 과목 코드
                        "컴퓨터프로그래밍",              // 과목명
                        "이현주",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관410호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 600, 780)   // 10시~13시
                        )
                ),

                new Lecture(
                        "740",                          // 과목 코드
                        "컴퓨터프로그래밍응용",              // 과목명
                        "김병국",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관403호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 900, 1020)   // 15시~17시
                        )
                ),

                new Lecture(
                        "739",                          // 과목 코드
                        "컴퓨터프로그래밍응용",              // 과목명
                        "김병국",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관403호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 720, 840)   // 12시~14시
                        )
                ),

                // 3학년 과목 시작
                new Lecture(
                        "760",                          // 과목 코드
                        "데이터베이스",                   // 과목명
                        "김성완",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.SOFTWARE,             // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관404호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,           // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 840, 900),   // 14시~15시
                                new LectureTime(DayOfWeek.WEDNESDAY, 900, 1020)   // 15시~17시
                        )
                ),

                new Lecture(
                        "759",                          // 과목 코드
                        "데이터베이스",                   // 과목명
                        "김성완",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.SOFTWARE,             // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관404호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,           // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 900, 1020),   // 15시~17시
                                new LectureTime(DayOfWeek.WEDNESDAY, 840, 900)   // 14시~15시
                        )
                ),

                new Lecture(
                        "773",                          // 과목 코드
                        "멀티미디어",              // 과목명
                        "정수목",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,               // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관404호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 600, 780)   // 10시~13시
                        )
                ),

                new Lecture(
                        "765",                          // 과목 코드
                        "모바일 프로그래밍(캡스톤디자인)",  // 과목명
                        "김관우",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.SOFTWARE,               // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관410호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 900, 1080)   // 15시~18시
                        )
                ),

                new Lecture(
                        "764",                          // 과목 코드
                        "모바일 프로그래밍(캡스톤디자인)",  // 과목명
                        "김관우",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.SOFTWARE,               // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관410호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 660, 840)   // 11시~14시
                        )
                ),

                new Lecture(
                        "776",                          // 과목 코드
                        "시스템프로그래밍",              // 과목명
                        "홍성옥",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관410호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 840, 1020)   // 14시~17시
                        )
                ),

                new Lecture(
                        "775",                          // 과목 코드
                        "시스템프로그래밍",              // 과목명
                        "홍성옥",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관411호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 600, 780)   // 10시~13시
                        )
                ),

                new Lecture(
                        "769",                          // 과목 코드
                        "운영체제",                     // 과목명
                        "공준익",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관404호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 1080, 1140),   // 18시~19시
                                new LectureTime(DayOfWeek.FRIDAY, 900, 1020)       // 15시~17시
                        )
                ),

                new Lecture(
                        "770",                          // 과목 코드
                        "운영체제",                     // 과목명
                        "공준익",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관404호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 1080, 1140),   // 18시~19시
                                new LectureTime(DayOfWeek.FRIDAY, 780, 900)       // 13시~15시
                        )
                ),

                new Lecture(
                        "745",                          // 과목 코드
                        "채플",                       // 과목명
                        "김동혜",                        // 교수
                        0,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "대강당(메인)",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 600, 660) // 10시~11시
                        )
                ),

                new Lecture(
                        "730",                          // 과목 코드
                        "컴퓨터 알고리즘",               // 과목명
                        "노현아",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관403호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 540, 720)     // 9시~12시
                        )
                ),

                new Lecture(
                        "731",                          // 과목 코드
                        "컴퓨터 알고리즘",               // 과목명
                        "노현아",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관403호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 780, 960)     // 13시~16시
                        )
                ),

                new Lecture(
                        "766",                          // 과목 코드
                        "컴퓨터네트워크",               // 과목명
                        "조양현",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관411호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 660, 840)     // 11시~14시
                        )
                ),

                new Lecture(
                        "767",                          // 과목 코드
                        "컴퓨터네트워크",               // 과목명
                        "조양현",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관411호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 900, 1080)     // 15시~18시
                        )
                ),

                new Lecture(
                        "758",                          // 과목 코드
                        "프로그래밍언어론",               // 과목명
                        "김관우",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.SOFTWARE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관411호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.FRIDAY, 540, 720)     // 9시~12시
                        )
                ),

                new Lecture(
                        "1841",                          // 과목 코드
                        "AI를 위한 클라우드 컴퓨팅",      // 과목명
                        "조충희",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관410호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 780, 960)     // 13시~16시
                        )
                ),

                new Lecture(
                        "1840",                          // 과목 코드
                        "AI를 위한 클라우드 컴퓨팅",      // 과목명
                        "조충희",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관410호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 720)     // 9시~12시
                        )
                ),

                // 4학년 과목 시작
                new Lecture(
                        "762",                          // 과목 코드
                        "기계학습",                     // 과목명
                        "왕수현",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.SOFTWARE,              // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.EVEN,                // 홀 / 짝
                        "제1실습관411호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 720, 900)     // 12시~15시
                        )
                ),

                new Lecture(
                        "763",                          // 과목 코드
                        "기계학습",                     // 과목명
                        "왕수현",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.SOFTWARE,              // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ODD,                // 홀 / 짝
                        "제1실습관411호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720)     // 9시~12시
                        )
                ),

                new Lecture(
                        "768",                          // 과목 코드
                        "네트워크 엔지니어링",           // 과목명
                        "공준익",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.FRIDAY, 540, 720)     // 9시~12시
                        )
                ),

                new Lecture(
                        "1766",                          // 과목 코드
                        "모바일 네트워크",           // 과목명
                        "왕수현",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 900, 1080)     // 15시~18시
                        )
                ),

                new Lecture(
                        "750",                          // 과목 코드
                        "산학협력 캡스톤디자인Ⅱ",       // 과목명
                        "김성완",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,      // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "미지정",                      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 1080, 1260)     // 18시~21시
                        )
                ),

                new Lecture(
                        "756",                          // 과목 코드
                        "소프트웨어 디자인패턴",         // 과목명
                        "김기락",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.SOFTWARE,      // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관411호(컴과실습실)",    // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 840, 1020)     // 14시~17시
                        )
                ),

                new Lecture(
                        "1872",                          // 과목 코드
                        "인턴십 III",                    // 과목명
                        "취업정보",                        // 교수
                        12,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,                 // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "정보 없음",                    // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList()                       // 시간 자체가 없음
                ),

                new Lecture(
                        "732",                          // 과목 코드
                        "종합시험",                    // 과목명
                        "김병국",                        // 교수
                        0,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,                // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "정보 없음",                    // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList()                       // 시간 자체가 없음
                ),

                new Lecture(
                        "735",                          // 과목 코드
                        "채플(온라인)",                    // 과목명
                        "김동혜",                        // 교수
                        0,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,                // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "정보 없음",                    // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList()                       // 시간 자체가 없음
                ),

                new Lecture(
                        "1767",                          // 과목 코드
                        "AI 임베디드 시스템",           // 과목명
                        "김병국",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관403호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 660, 840)     // 11시~14시
                        )
                ),

                new Lecture(
                        "1762",                          // 과목 코드
                        "AI를 위한 빅데이터 처리",           // 과목명
                        "최희식",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.SOFTWARE,      // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 960, 1140)     // 16시~19시
                        )
                ),

                new Lecture(
                        "744",                          // 과목 코드
                        "ICT인턴십Ⅰ",                    // 과목명
                        "김병국",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,                // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "정보 없음",                    // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList()                       // 시간 자체가 없음
                ),

                new Lecture(
                        "741",                          // 과목 코드
                        "ICT인턴십Ⅱ",                    // 과목명
                        "김병국",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,                // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "정보 없음",                    // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList()                       // 시간 자체가 없음
                ),

                new Lecture(
                        "727",                          // 과목 코드
                        "ICT인턴십Ⅲ",                    // 과목명
                        "김병국",                        // 교수
                        6,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,                // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "정보 없음",                    // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList()                       // 시간 자체가 없음
                ),

                new Lecture(
                        "746",                          // 과목 코드
                        "ICT인턴십Ⅳ",                    // 과목명
                        "김병국",                        // 교수
                        12,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,                // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "정보 없음",                    // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList()                       // 시간 자체가 없음
                ),

                // 컴퓨터공학부 시간표 끝!!
                //교양 시작
                new Lecture(
                        "937",                          // 과목 코드
                        "지역사회공헌",                    // 과목명
                        "이병희",                        // 교수
                        1,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "정보 없음",                    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList()                       // 시간 자체가 없음
                ),

                new Lecture(
                        "1701",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "임준서",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관210호(PBL소형)강의실",  // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 600, 780)     // 10시~13시
                        )
                ),

                new Lecture(
                        "1702",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "임준서",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관210호(PBL소형)강의실",  // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 840, 1020)     // 14시~17시
                        )
                ),

                new Lecture(
                        "1703",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "전지은",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "신학관202호강의실(PBL)",       // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720)     // 9시~12시
                        )
                ),

                new Lecture(
                        "1112",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "한금윤",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관110호강의실",       // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 780, 960)     // 13시~16시
                        )
                ),

                new Lecture(
                        "1687",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "한금윤",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관406호(PBL 전용 Lab. 中)강의실", // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 780, 960)     // 13시~16시
                        )
                ),

                new Lecture(
                        "1688",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "한금윤",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관406호(PBL 전용 Lab. 中)강의실", // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 960)     // 13시~16시
                        )
                ),

                new Lecture(
                        "1689",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "한금윤",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관406호(PBL 전용 Lab. 中)강의실", // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 780, 960)     // 13시~16시
                        )
                ),

                new Lecture(
                        "1690",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "최가형",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관406호(PBL 전용 Lab. 中)강의실", // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 600, 780)     // 10시~13시
                        )
                ),

                new Lecture(
                        "1691",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "최가형",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관402호(PBL)강의실",     // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 600, 780)     // 10시~13시
                        )
                ),

                new Lecture(
                        "1692",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "유승현",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관110호강의실",     // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 600, 780)     // 10시~13시
                        )
                ),

                new Lecture(
                        "1693",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "유승현",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관110호강의실",     // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 960)     // 13시~16시
                        )
                ),

                new Lecture(
                        "1696",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "김정헌",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관306호(中)강의실(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 600, 780)     // 10시~13시
                        )
                ),

                new Lecture(
                        "1697",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "김정헌",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관406호(PBL 전용 Lab. 中)강의실",// 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 840, 1020)     // 14시~17시
                        )
                ),

                new Lecture(
                        "1698",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "이관호",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관402호(PBL)강의실",      // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 660, 840)     // 11시~14시
                        )
                ),

                new Lecture(
                        "1694",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "박성준",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관126호(小)강의실",      // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 960)     // 13시~16시
                        )
                ),

                new Lecture(
                        "1695",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "박성준",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "신학관208호(PBL소형)강의실",     // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 600, 780)     // 10시~13시
                        )
                ),

                new Lecture(
                        "1699",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "권도경",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관201호강의실",          // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720)     // 9시~12시
                        )
                ),

                new Lecture(
                        "1700",                          // 과목 코드
                        "사고와 표현",                    // 과목명
                        "권도경",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관223호(中)(교필토익전용)강의실",  // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 900, 1080)    // 15시~18시
                        )
                ),

                new Lecture(
                        "1024",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "정보 없음",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "미지정",                       // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1645",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "정보 없음",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "미지정",                       // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1674",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "AndrewCho",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관205호(小)",                       // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1673",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "AndrewCho",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관205호(小)",                       // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1672",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "AndrewCho",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관205호(小)",                       // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1671",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "AndrewCho",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관205호(小)",                       // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1023",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "AndrewCho",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관126호(小)강의실",           // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 1080, 1260)    // 18시~21시
                        )
                ),

                new Lecture(
                        "1686",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "YBM시사",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관223호(中)(교필토익전용)강의실", // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1685",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "YBM시사",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관223호(中)(교필토익전용)강의실", // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1684",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "YBM시사",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관223호(中)(교필토익전용)강의실", // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1683",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "YBM시사",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관223호(中)(교필토익전용)강의실", // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1682",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "YBM시사",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관223호(中)(교필토익전용)강의실", // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1646",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "정보 없음",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "미지정",                     // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1647",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "정보 없음",                        // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "미지정",                     // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1663",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "ChristopherWright",                  // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관203호(小)",                   // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1664",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "ChristopherWright",                  // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관203호(小)",                   // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1665",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "ChristopherWright",                  // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관203호(小)",                   // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1666",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "ChristopherWright",                  // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관203호(小)",                   // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1651",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "백정혜",                  // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "백주년311호강의실",                   // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1652",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "백정혜",                  // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "백주년311호강의실",                   // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1653",                          // 과목 코드ㅎ
                        "글로컬 영어 I",                    // 과목명
                        "백정혜",                  // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "백주년311호강의실",                   // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1654",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "백정혜",                  // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "백주년311호강의실",                   // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1655",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "LagundinoWilliamChester",         // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관201호(小)강의실",          // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1656",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "LagundinoWilliamChester",         // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관201호(小)강의실",          // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1657",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "LagundinoWilliamChester",         // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관201호(小)강의실",          // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1658",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "LagundinoWilliamChester",         // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관201호(小)강의실",          // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1667",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "로즈마리신",         // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관204호(小)",          // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1668",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "로즈마리신",         // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관204호(小)",          // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1669",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "로즈마리신",         // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관204호(小)",          // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1670",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "로즈마리신",         // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관204호(小)",          // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1659",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "Ian Robert J.Aujero",         // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관202호(小)강의실",          // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1660",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "Ian Robert J.Aujero",         // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관202호(小)강의실",          // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1661",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "Ian Robert J.Aujero",         // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관202호(小)강의실",          // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1662",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "Ian Robert J.Aujero",         // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관202호(小)강의실",          // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1675",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "YBM시사",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관221호(中)(교필토익전용)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1676",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "YBM시사",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관221호(中)(교필토익전용)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1677",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "YBM시사",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관221호(中)(교필토익전용)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1678",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "YBM시사",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관221호(中)(교필토익전용)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1679",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "YBM시사",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관222호(中)(교필토익전용)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1680",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "YBM시사",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관222호(中)(교필토익전용)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1681",                          // 과목 코드
                        "글로컬 영어 I",                    // 과목명
                        "YBM시사",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관222호(中)(교필토익전용)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1044",                          // 과목 코드
                        "AI리터러시와 문제해결(구, 컴퓨팅사고력)",                    // 과목명
                        "이새봄",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "온라인",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 1200,  1380)    // 20시~23시
                        )
                ),

                new Lecture(
                        "1638",                          // 과목 코드
                        "AI리터러시와 문제해결(구, 컴퓨팅사고력)",                    // 과목명
                        "김성완",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "온라인",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 1200,  1380)    // 20시~23시
                        )
                ),

                new Lecture(
                        "1637",                          // 과목 코드
                        "AI리터러시와 문제해결(구, 컴퓨팅사고력)",                    // 과목명
                        "이동학",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "온라인",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 1200,  1380)    // 20시~23시
                        )
                ),

                new Lecture(
                        "878",                          // 과목 코드
                        "건강과운동",                    // 과목명
                        "박형길",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관301호(中)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "1100",                          // 과목 코드
                        "결혼과 가족관계",                    // 과목명
                        "허상민",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "신학관103호강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 960)    // 13시~16시
                        )
                ),

                new Lecture(
                        "810",                          // 과목 코드
                        "교육고전과 인성(구, 교육고전의이해)",                    // 과목명
                        "명지원",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관321호(中)강의실(첨단강의실)",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "951",                          // 과목 코드
                        "시민윤리(구, 그리스도인 생활윤리)",                    // 과목명
                        "백승기",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관423호(中)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 840, 1020)    // 14시~17시
                        )
                ),

                new Lecture(
                        "823",                          // 과목 코드
                        "교양인 화법과 태도",                    // 과목명
                        "김명희",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관402호(PBL)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 780, 960)    // 13시~16시
                        )
                ),

                new Lecture(
                        "829",                          // 과목 코드
                        "긍정심리학",                    // 과목명
                        "김나미",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관221호(中)(교필토익전용)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 900, 1080)    // 15시~18시
                        )
                ),

                new Lecture(
                        "1109",                          // 과목 코드
                        "기업가정신과 창업입문(구,기업가정신과 창업)",                    // 과목명
                        "이동학",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관201호강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 960)    // 13시~16시
                        )
                ),

                new Lecture(
                        "889",                          // 과목 코드
                        "초급 스페인어(구, 기초스페인어)",                    // 과목명
                        "김선이",                   // 교수
                        2,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관201호강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 660)    // 9시~11시
                        )
                ),

                new Lecture(
                        "839",                          // 과목 코드
                        "초급프랑스어Ⅰ(구, 기초프랑스어)",                    // 과목명
                        "KIM JINSOO JASON",                   // 교수
                        2,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관222호(中)(교필토익전용)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 780, 900)    // 13시~15시
                        )
                ),

                new Lecture(
                        "1089",                          // 과목 코드
                        "인간과 행동과학(구, 뇌와 인간행동의 이해)",                    // 과목명
                        "강병용",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관322호(中)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 900, 1080)    // 15시~18시
                        )
                ),

                new Lecture(
                        "869",                          // 과목 코드
                        "대인관계 심리학",                    // 과목명
                        "김비",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관221호(中)(교필토익전용)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 900, 1080)    // 15시~18시
                        )
                ),

                new Lecture(
                        "975",                          // 과목 코드
                        "대학수학",                    // 과목명
                        "윤미",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관201호강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 720, 900)    // 12시~15시
                        )
                ),

                new Lecture(
                        "925",                          // 과목 코드
                        "도덕적 딜레마와 정의로운 삶",                    // 과목명
                        "음영철",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "바울관203호(PBL)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.HUMANITIES_ART,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 780, 960)    // 13시~16시
                        )
                ),

                new Lecture(
                        "1838",                          // 과목 코드
                        "동물자원과학개론",                    // 과목명
                        "정훈",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관321호(中)강의실(첨단강의실)",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 600, 780)    // 10시~13시
                        )
                ),

                new Lecture(
                        "920",                          // 과목 코드
                        "명인과의 토크 콘서트",                    // 과목명
                        "음영철",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "바울관203호(PBL)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.HUMANITIES_ART,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 600, 780)    // 10시~13시
                        )
                ),

                new Lecture(
                        "854",                          // 과목 코드
                        "미술의 이해",                    // 과목명
                        "하임성",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관201호강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.HUMANITIES_ART,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 900, 1080)    // 15시~18시
                        )
                ),

                new Lecture(
                        "1742",                          // 과목 코드
                        "배구",                    // 과목명
                        "유재홍",                   // 교수
                        2,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "체육관203호(주경기장)",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 660, 780)    // 11시~13시
                        )
                ),

                new Lecture(
                        "1082",                          // 과목 코드
                        "북한사회와주민생활",                    // 과목명
                        "한승대",                   // 교수
                        2,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관201호강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 600, 720)    // 10시~12시
                        )
                ),

                new Lecture(
                        "832",                          // 과목 코드
                        "사고조사기법",                    // 과목명
                        "김훈",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관304호(大)",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 960, 1140)    // 16시~19시
                        )
                ),

                new Lecture(
                        "981",                          // 과목 코드
                        "생활교양한문",                    // 과목명
                        "명지원",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관321호(中)강의실(첨단강의실)",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.HUMANITIES_ART,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 780, 960)    // 13시~16시
                        )
                ),

                new Lecture(
                        "993",                          // 과목 코드
                        "생활속의 화학",                    // 과목명
                        "고정원",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관209호강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 840, 1020)    // 14시~17시
                        )
                ),

                new Lecture(
                        "996",                          // 과목 코드
                        "세계문화와 종교(구,세계의 종교)",                    // 과목명
                        "최수동",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "신학관103호강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 840, 1020)    // 14시~17시
                        )
                ),

                new Lecture(
                        "877",                          // 과목 코드
                        "소그룹 리더십 I",                    // 과목명
                        "곽주화",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "신학관208호(PBL소형)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 1140, 1320)    // 19시~22시
                        )
                ),

                new Lecture(
                        "953",                          // 과목 코드
                        "수영초급",                    // 과목명
                        "문종주",                   // 교수
                        2,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "체육관102호(수영장)",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 780, 900)    // 13시~15시
                        )
                ),

                new Lecture(
                        "842",                          // 과목 코드
                        "스쿼시",                    // 과목명
                        "김영미",                   // 교수
                        2,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "체육관 206호(스쿼시장)",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.FRIDAY, 660, 780)    // 11시~13시
                        )
                ),

                new Lecture(
                        "822",                          // 과목 코드
                        "라틴문화예술의 이해(구, 스페인문화와 언어)",                    // 과목명
                        "김선이",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관201호강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.HUMANITIES_ART,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 660, 840)    // 11시~14시
                        )
                ),

                new Lecture(
                        "941",                          // 과목 코드
                        "신체자세와동작요법",                    // 과목명
                        "홍선미",                   // 교수
                        2,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "시온관115호",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 840, 960)    // 14시~16시
                        )
                ),

                new Lecture(
                        "865",                          // 과목 코드
                        "영작문",                    // 과목명
                        "로즈마리신",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관222호(中)(교필토익전용)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.HUMANITIES_ART,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 900, 1080)    // 15시~18시
                        )
                ),

                new Lecture(
                        "999",                          // 과목 코드
                        "예비부모교육",                    // 과목명
                        "김나미",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관422호(中)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 900, 1080)    // 15시~18시
                        )
                ),

                new Lecture(
                        "943",                          // 과목 코드
                        "대학한국어I(구, 외국학생용 한국어교육 I)",                    // 과목명
                        "허은혜",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관201호(小)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 720, 900)    // 12시~15시
                        )
                ),

                new Lecture(
                        "1020",                          // 과목 코드
                        "응급처치 및 CPR",                    // 과목명
                        "이재구",                   // 교수
                        2,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관302호(中)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 900, 1020)    // 15시~17시
                        )
                ),

                new Lecture(
                        "966",                          // 과목 코드
                        "감성 디자인을 위한 AI와 일러스트",                    // 과목명
                        "이임정",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관308호(전산원관리)컴퓨터강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.HUMANITIES_ART,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720)    // 9시~12시
                        )
                ),

                new Lecture(
                        "964",                          // 과목 코드
                        "일본문화와 언어",                    // 과목명
                        "김정헌",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관402호(PBL)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.HUMANITIES_ART,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 600, 780)    // 10시~13시
                        )
                ),

                new Lecture(
                        "1041",                          // 과목 코드
                        "기후재난과 대비",                    // 과목명
                        "권기욱",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관109호강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 600, 780)    // 10시~13시
                        )
                ),

                new Lecture(
                        "851",                          // 과목 코드
                        "중국문화와 언어",                    // 과목명
                        "권기욱",                   // 교수
                        3,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "바울관413호식음료실습실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.HUMANITIES_ART,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 780, 960)    // 13시~16시
                        )
                ),

                new Lecture(
                        "1067",                          // 과목 코드
                        "중국어 기초회화",                    // 과목명
                        "안병삼",                   // 교수
                        2,                              // 학점
                        Department.GENERAL,            // 학과. 교양은 GENERAL
                        MajorType.NONE,                // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "다니엘관302호(中)강의실",    // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,        // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 900)    // 13시~15 시
                        )
                ),

                new Lecture(
                        "1059",                          // 과목 코드
                        "지구의 기원(구, 창조론과 과학)",   // 과목명
                        "최준태",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과. 교양은 GENERAL
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "신학관201호강의실",         // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION, // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.THURSDAY, 780, 960) // 목5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1092",                          // 과목 코드
                        "채식과 건강(구, 채식과 건강II)",   // 과목명
                        "김현정",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관402호(PBL)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION, // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.WEDNESDAY, 840, 1020) // 수6~8 → 840 ~ 1020
                        )
                ),

                new Lecture(
                        "821",                          // 과목 코드
                        "농구(교양)",                     // 과목명
                        "문종주",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "체육관203호(주경기장)",      // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.MONDAY, 600, 720) // 월2~3 → 600 ~ 720
                        )
                ),

                new Lecture(
                        "1709",                          // 과목 코드
                        "취업정보분석과 입사전략",      // 과목명
                        "취업정보",                 // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관401호(PBL)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.WEDNESDAY, 600, 720) // 수2~3 → 600 ~ 720
                        )
                ),

                new Lecture(
                        "1047",                          // 과목 코드
                        "취업정보분석과 입사전략",      // 과목명
                        "취업정보",                 // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관422호(中)강의실",      // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 900) // 수5~6 → 780 ~ 900
                        )
                ),

                new Lecture(
                        "1052",                          // 과목 코드
                        "토익I (Total Toeic Care I)-초급", // 과목명
                        "온라인강의교수",           // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "정보 없음",               // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList()   // 시간 없음
                ),

                new Lecture(
                        "894",                          // 과목 코드
                        "토익II (Total Toeic Care II)-중급", // 과목명
                        "온라인강의교수",           // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "정보 없음",               // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList()   // 시간 없음
                ),

                new Lecture(
                        "1099",                          // 과목 코드
                        "평생교육론",               // 과목명
                        "박완성",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관223호(中)(교필토익전용)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.TUESDAY, 780, 960) // 화5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1061",                          // 과목 코드
                        "AI와 포토디자인",          // 과목명
                        "이임정",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "제1실습관308호(전산원관리)컴퓨터강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720) // 화1~3 → 540 ~ 720
                        )
                ),

                new Lecture(
                        "1060",                          // 과목 코드
                        "프랑스 문화(구, 프랑스 문화와 언어)", // 과목명
                        "KIM JINSOO JASON",         // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관222호(中)(교필토익전용)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.THURSDAY, 900, 1080) // 목7~9 → 900 ~ 1080
                        )
                ),

                new Lecture(
                        "1009",                          // 과목 코드
                        "현대의 시민생활과 법",        // 과목명
                        "김형태",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과. 교양은 GENERAL
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "바울관203호(PBL)강의실",     // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.TUESDAY, 600, 780) // 화2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "867",                          // 과목 코드
                        "현대 중국의 이해(구, 현대 중국사회의 이해)", // 과목명
                        "이정현",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관223호(中)(교필토익전용)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.MONDAY, 780, 960) // 월5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "856",                          // 과목 코드
                        "영미소설의 이해",            // 과목명
                        "김용성",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "사무엘관209호강의실",        // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.TUESDAY, 780, 960) // 화5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1032",                          // 과목 코드
                        "글로벌 문화와 비즈니스 매너",   // 과목명
                        "김명희",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관402호(PBL)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.THURSDAY, 840, 1020) // 목6~8 → 840 ~ 1020
                        )
                ),

                new Lecture(
                        "916",                          // 과목 코드
                        "합창과 인성(구, 교회합창)",    // 과목명
                        "조대명",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "제2과학관102호(세미나실)",    // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION, // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.WEDNESDAY, 1080, 1260) // 수10~12 → 1080 ~ 1260
                        )
                ),

                new Lecture(
                        "1710",                          // 과목 코드
                        "합창과 인성(구, 교회합창)",    // 과목명
                        "조대명",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "제2과학관102호(세미나실)",    // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION, // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.WEDNESDAY, 1080, 1260) // 수10~12 → 1080 ~ 1260
                        )
                ),

                new Lecture(
                        "1037",                          // 과목 코드
                        "스피치와 프리젠테이션",       // 과목명
                        "취업정보",                 // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과. 교양은 GENERAL
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관221호(中)(교필토익전용)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역 (일반선택)
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.MONDAY, 780, 900) // 월5~6 → 780 ~ 900
                        )
                ),

                new Lecture(
                        "1707",                          // 과목 코드
                        "스피치와 프리젠테이션",       // 과목명
                        "취업정보",                 // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관221호(中)(교필토익전용)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.MONDAY, 900, 1020) // 월7~8 → 900 ~ 1020
                        )
                ),

                new Lecture(
                        "1017",                          // 과목 코드
                        "건강원리와 실제",            // 과목명
                        "유재현",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "체육관203호(주경기장)",      // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION, // 교양 영역 (인성교양)
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.FRIDAY, 540, 720) // 금1~3 → 540 ~ 720
                        )
                ),

                new Lecture(
                        "1643",                          // 과목 코드
                        "건강원리와 실제",            // 과목명
                        "유재현",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "체육관203호(주경기장)",      // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION, // 교양 영역 (인성교양)
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.FRIDAY, 540, 720) // 금1~3 → 540 ~ 720
                        )
                ),

                new Lecture(
                        "1081",                          // 과목 코드
                        "글로컬리더십_창의적 문제해결", // 과목명
                        "이병희",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관402호(PBL)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역 (인문예술)
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.FRIDAY, 780, 960) // 금5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "855",                          // 과목 코드
                        "언어의 세계와 소통",         // 과목명
                        "김정헌",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관403호(PBL)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역 (사회과학)
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.WEDNESDAY, 600, 780) // 수2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "1639",                          // 과목 코드
                        "NCS 전략적 커리어개발의 이해(구,NCS직업기초능력의 이해)", // 과목명
                        "취업정보",                 // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과. 교양은 GENERAL
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관223호(中)(교필토익전용)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역 (일반선택)
                        java.util.Arrays.asList(    // 시간 리스트
                                new LectureTime(DayOfWeek.THURSDAY, 780, 900) // 목5~6 → 780 ~ 900
                        )
                ),

                new Lecture(
                        "1640",
                        "NCS 전략적 커리어개발의 이해(구,NCS직업기초능력의 이해)",
                        "취업정보",
                        2,
                        Department.GENERAL,
                        MajorType.NONE,
                        1,
                        ClassParity.ALL,
                        "백주년311호강의실",
                        CourseCategory.GENERAL,
                        RequirementType.OPTIONAL,
                        GeneralArea.NONE,
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 780, 900) // 화5~6 → 780 ~ 900
                        )
                ),

                new Lecture(
                        "1039",
                        "논어 다시읽기",
                        "Zheng Chengzhe",
                        3,
                        Department.GENERAL,
                        MajorType.NONE,
                        1,
                        ClassParity.ALL,
                        "요한관421호(中)강의실",
                        CourseCategory.GENERAL,
                        RequirementType.OPTIONAL,
                        GeneralArea.HUMANITIES_ART,
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 840, 1020) // 목6~8 → 840 ~ 1020
                        )
                ),

                new Lecture(
                        "1016",
                        "다문화와 사회통합(구, 다문화의 이해)",
                        "김명희",
                        3,
                        Department.GENERAL,
                        MajorType.NONE,
                        1,
                        ClassParity.ALL,
                        "다니엘관403호(PBL)강의실",
                        CourseCategory.GENERAL,
                        RequirementType.OPTIONAL,
                        GeneralArea.CHARACTER_EDUCATION,
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 840, 1020) // 수6~8 → 840 ~ 1020
                        )
                ),

                new Lecture(
                        "1050",
                        "글로벌 문화콘텐츠 창업 성공전략",
                        "임채휘",
                        3,
                        Department.GENERAL,
                        MajorType.NONE,
                        1,
                        ClassParity.ALL,
                        "바울관203호(PBL)강의실",
                        CourseCategory.GENERAL,
                        RequirementType.OPTIONAL,
                        GeneralArea.HUMANITIES_ART,
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 720, 900) // 월4~6 → 720 ~ 900
                        )
                ),

                new Lecture(
                        "950",
                        "스포츠와 음악(구, 스포츠음악의 이해)",
                        "박승순",
                        3,
                        Department.GENERAL,
                        MajorType.NONE,
                        1,
                        ClassParity.ALL,
                        "체육관 108(세미나실)",
                        CourseCategory.GENERAL,
                        RequirementType.OPTIONAL,
                        GeneralArea.HUMANITIES_ART,
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 720, 900) // 화4~6 → 720 ~ 900
                        )
                ),

                new Lecture(
                        "808",
                        "전인건강과 교양인의 삶(구,전인적 건강과 신앙)",
                        "이현재",
                        3,
                        Department.GENERAL,
                        MajorType.NONE,
                        1,
                        ClassParity.ALL,
                        "요한관422호(中)강의실",
                        CourseCategory.GENERAL,
                        RequirementType.OPTIONAL,
                        GeneralArea.NATURAL_SCIENCE,
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 780, 960) // 월5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1644",
                        "경전의 지혜(구,중국고전과 성서)",
                        "Zheng Chengzhe",
                        3,
                        Department.GENERAL,
                        MajorType.NONE,
                        1,
                        ClassParity.ALL,
                        "요한관322호(中)강의실",
                        CourseCategory.GENERAL,
                        RequirementType.OPTIONAL,
                        GeneralArea.CHARACTER_EDUCATION,
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 960) // 수5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1062",
                        "채용시장 이해와 직무·직업 탐색(구, 여성비전과 미래직업)",
                        "취업정보",
                        2,
                        Department.GENERAL,
                        MajorType.NONE,
                        1,
                        ClassParity.ALL,
                        "다니엘관403호(PBL)강의실",
                        CourseCategory.GENERAL,
                        RequirementType.OPTIONAL,
                        GeneralArea.NONE,
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 900, 1020) // 목7~8 → 900 ~ 1020
                        )
                ),

                new Lecture(
                        "934",
                        "알기쉬운 미적분학",
                        "윤미",
                        3,
                        Department.GENERAL,
                        MajorType.NONE,
                        1,
                        ClassParity.ALL,
                        "사무엘관109호강의실",
                        CourseCategory.GENERAL,
                        RequirementType.OPTIONAL,
                        GeneralArea.NATURAL_SCIENCE,
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720) // 화1~3 → 540 ~ 720
                        )
                ),

                new Lecture(
                        "1021",
                        "라이프스타일과 건강관리I",
                        "이금선",
                        2,
                        Department.GENERAL,
                        MajorType.NONE,
                        1,
                        ClassParity.ALL,
                        "온라인(가상공간)",
                        CourseCategory.GENERAL,
                        RequirementType.OPTIONAL,
                        GeneralArea.NONE,
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 900, 1020) // 수7~8 → 900 ~ 1020
                        )
                ),

                new Lecture(
                        "988",
                        "매직테니스",
                        "임지헌",
                        2,
                        Department.GENERAL,
                        MajorType.NONE,
                        1,
                        ClassParity.ALL,
                        "체육관203호(주경기장)",
                        CourseCategory.GENERAL,
                        RequirementType.OPTIONAL,
                        GeneralArea.NONE,
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 900) // 수5~6 → 780 ~ 900
                        )
                ),

                new Lecture(
                        "933",                          // 과목 코드
                        "인간과 곤충",               // 과목명
                        "김동건",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관321호(中)강의실(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 720) // 월1~3 → 540 ~ 720
                        )
                ),

                new Lecture(
                        "922",                          // 과목 코드
                        "중국언어와 예술",           // 과목명
                        "이정현",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관401호(PBL)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 780, 960) // 화5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "965",                          // 과목 코드
                        "음악교육과 나눔실천",       // 과목명
                        "한송이",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관306호강의실",       // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 600, 780) // 수2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "912",                          // 과목 코드
                        "Reading Glocal News-자연과학", // 과목명
                        "AndrewCho",                // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관301호(中)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 900, 1080) // 수7~9 → 900 ~ 1080
                        )
                ),

                new Lecture(
                        "1641",                          // 과목 코드
                        "Reading Glocal News-문화예술", // 과목명
                        "ChristopherWright",        // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관401호(PBL)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 900, 1080) // 월7~9 → 900 ~ 1080
                        )
                ),

                new Lecture(
                        "1056",                          // 과목 코드
                        "타자성의 철학",             // 과목명
                        "노동욱",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관107호(자유전공학부 전용)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 600, 780) // 화2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "1066",                          // 과목 코드
                        "영화읽기와 감정수업",       // 과목명
                        "한금윤",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "사무엘관110호강의실",       // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 600, 780) // 월2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "1031",                          // 과목 코드
                        "문학과 종교적 상상력",       // 과목명
                        "김용성",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "사무엘관209호강의실",       // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 780, 960) // 월5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "968",                          // 과목 코드
                        "사고조사이론",             // 과목명
                        "김훈",                     // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관405호(大)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 900, 1080) // 목7~9 → 900 ~ 1080
                        )
                ),

                new Lecture(
                        "914",                          // 과목 코드
                        "축구",                     // 과목명
                        "민새누",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "대운동장",                 // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 780, 900) // 목5~6 → 780 ~ 900
                        )
                ),

                new Lecture(
                        "1088",                          // 과목 코드
                        "데이터 비즈니스 활용",       // 과목명
                        "김미영",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관304호(大)",         // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 780, 960) // 목5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1042",                          // 과목 코드
                        "디자인 씽킹과 창업",         // 과목명
                        "임채휘",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "바울관203호(PBL)강의실",    // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 900, 1080) // 월7~9 → 900 ~ 1080
                        )
                ),

                new Lecture(
                        "840",                          // 과목 코드
                        "고전음악과 인간의 삶",       // 과목명
                        "박신희",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관321호(中)강의실(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 960, 1140) // 목8~10 → 960 ~ 1140
                        )
                ),

                new Lecture(
                        "1107",                          // 과목 코드
                        "베스트셀러로 철학하기",       // 과목명
                        "이관호",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관402호(PBL)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 840, 1020) // 월6~8 → 840 ~ 1020
                        )
                ),

                new Lecture(
                        "1096",                          // 과목 코드
                        "4차산업과 인공지능",         // 과목명
                        "최광진",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "사무엘관201호강의실",       // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.DIGITAL_LITERACY, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 600, 780) // 목2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "1000",                          // 과목 코드
                        "기초생물학",               // 과목명
                        "강병용",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관221호(中)(교필토익전용)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 900, 1080) // 수7~9 → 900 ~ 1080
                        )
                ),

                new Lecture(
                        "1079",                          // 과목 코드
                        "종교음악의 이해",           // 과목명
                        "한송이",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관401호(PBL)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 600, 780) // 화2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "1007",                          // 과목 코드
                        "도시과학과 리빙랩 아이디어 설계", // 과목명
                        "박은수",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "바울관305호(유비실-인공지능융합)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 720, 900) // 월4~6 → 720 ~ 900
                        )
                ),

                new Lecture(
                        "924",                          // 과목 코드
                        "조직행동과 사회",           // 과목명
                        "김도영",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관306호(中)강의실(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 960) // 수5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1837",                          // 과목 코드
                        "인간관계 심리학",           // 과목명
                        "정성진",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "신학관202호강의실(PBL)",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 720, 900) // 월4~6 → 720 ~ 900
                        )
                ),

                new Lecture(
                        "956",                          // 과목 코드
                        "창업기초1",               // 과목명
                        "강동균",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관406호(PBL 전용 Lab. 中)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 600, 780) // 목2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "892",                          // 과목 코드
                        "서비스 경영실무(SMAT)",     // 과목명
                        "취업정보",                 // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관403호(PBL)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 600, 780) // 화2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "976",                          // 과목 코드
                        "예술과 과학기술",           // 과목명
                        "이미희",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관422호(中)강의실",     // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 600, 780) // 목2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "1083",                          // 과목 코드
                        "인문학적 사고로 바라본 중국이야기", // 과목명
                        "안병삼",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관202호(小)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 780, 960) // 화5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1008",                          // 과목 코드
                        "숨 쉬는 자연 속 공감 디자인", // 과목명
                        "이임정",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관321호(中)강의실(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 780, 960) // 화5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "838",                          // 과목 코드
                        "클래식 음악과 여행",         // 과목명
                        "Alexander Park",           // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "사무엘관107호강의실",       // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 900, 1080) // 수7~9 → 900 ~ 1080
                        )
                ),

                new Lecture(
                        "843",                          // 과목 코드
                        "세계 식문화의 이해",         // 과목명
                        "김민주",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관209호강의실(교직과목)(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 600, 780) // 화2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "1642",                          // 과목 코드
                        "SW중심의 미래사회",         // 과목명
                        "이동학",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "온라인(가상공간)",           // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.DIGITAL_LITERACY, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 1200, 1380) // 목12~14 → 1200 ~ 1380
                        )
                ),

                new Lecture(
                        "986",                          // 과목 코드
                        "SW중심의 미래사회",         // 과목명
                        "김성완",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "온라인(가상공간)",           // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.DIGITAL_LITERACY, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 1200, 1380) // 목12~14 → 1200 ~ 1380
                        )
                ),

                new Lecture(
                        "845",                          // 과목 코드
                        "영화속의 법률이야기",         // 과목명
                        "김형태",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "국제교육관222호(中)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 840, 1020) // 화6~8 → 840 ~ 1020
                        )
                ),

                new Lecture(
                        "1015",                          // 과목 코드
                        "약사 및 의료종사자를 위한 일반법률 상식", // 과목명
                        "Lee Jeeny Jeeyoun",        // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관209호강의실(교직과목)(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 600, 780) // 월2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "907",                          // 과목 코드
                        "교회음악과 현악교수법 I",     // 과목명
                        "한송이",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관114호(大)강의실",     // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 720, 900) // 월4~6 → 720 ~ 900
                        )
                ),

                new Lecture(
                        "1004",                          // 과목 코드
                        "미디어로 보는 일본",         // 과목명
                        "김정헌",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관401호(PBL)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 600, 780) // 목2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "977",                          // 과목 코드
                        "영화속 음악 산책",           // 과목명
                        "Alexander Park",           // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "신학관203호강의실",         // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 900, 1080) // 목7~9 → 900 ~ 1080
                        )
                ),

                new Lecture(
                        "813",                          // 과목 코드
                        "대학 한국어 읽기",           // 과목명
                        "김선미",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "사무엘관210호(PBL소형)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720) // 수1~3 → 540 ~ 720
                        )
                ),

                new Lecture(
                        "1102",                          // 과목 코드
                        "소프트웨어 이해와 문제해결",   // 과목명
                        "하상현",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관213호SW전용실습실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.DIGITAL_LITERACY, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 780, 960) // 목5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1069",                          // 과목 코드
                        "웹 프로그래밍의 기초와 이해",   // 과목명
                        "홍경표",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관213호SW전용실습실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.DIGITAL_LITERACY, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 780, 960) // 화5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "844",                          // 과목 코드
                        "교회음악과 현악교수법 II",    // 과목명
                        "한송이",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관114호(大)강의실",     // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 900, 1080) // 월7~9 → 900 ~ 1080
                        )
                ),

                new Lecture(
                        "1080",                          // 과목 코드
                        "소비자와 시장경제",         // 과목명
                        "신동진",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관209호강의실(교직과목)(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 780, 960) // 월5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "876",                          // 과목 코드
                        "범죄와 법",               // 과목명
                        "Lee Jeeny Jeeyoun",        // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관306호(中)강의실(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 840, 1020) // 월6~8 → 840 ~ 1020
                        )
                ),

                new Lecture(
                        "1040",                          // 과목 코드
                        "생활속 물리",               // 과목명
                        "김의석",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관321호(中)강의실(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 900, 1080) // 월7~9 → 900 ~ 1080
                        )
                ),

                new Lecture(
                        "1104",                          // 과목 코드
                        "한류 문화 속 전통무용",       // 과목명
                        "이미희",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "사무엘관202호강의실",       // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 600, 780) // 수2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "830",                          // 과목 코드
                        "대학 한국어 쓰기",           // 과목명
                        "김선미",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "사무엘관210호(PBL소형)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 720, 900) // 수4~6 → 720 ~ 900
                        )
                ),

                new Lecture(
                        "998",                          // 과목 코드
                        "언어와 예술",             // 과목명
                        "정재신",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관202호(小)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 900, 1080) // 월7~9 → 900 ~ 1080
                        )
                ),

                new Lecture(
                        "1063",                          // 과목 코드
                        "음식과 생활윤리",           // 과목명
                        "김현정",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관402호(PBL)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 600, 780) // 수2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "932",                          // 과목 코드
                        "나노융합과 미래",           // 과목명
                        "고원배",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관323호(中)강의실",     // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 780, 960) // 화5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1046",                        // 과목 코드
                        "Global Traveling",         // 과목명
                        "Ian Robert J.Aujero",      // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관401호(PBL)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 900, 1080) // 수7~9 → 900 ~ 1080
                        )
                ),

                new Lecture(
                        "858",                          // 과목 코드
                        "바이러스의 세계와 생명공학",   // 과목명
                        "이양진",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관306호(中)강의실(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720) // 수1~3 → 540 ~ 720
                        )
                ),

                new Lecture(
                        "879",                          // 과목 코드
                        "빅데이터 이해와 활용",       // 과목명
                        "이임정",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관105호(컴퓨터실습실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.DIGITAL_LITERACY, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 780, 960) // 월5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "871",                          // 과목 코드
                        "미래직업과 신직업 탐색",     // 과목명
                        "취업정보",                 // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관401호(PBL)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 900) // 수5~6 → 780 ~ 900
                        )
                ),

                new Lecture(
                        "1091",                          // 과목 코드
                        "과거, 현재, 미래의 법률 논쟁", // 과목명
                        "Lee Jeeny Jeeyoun",        // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "신학관208호(PBL소형)강의실", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.FRIDAY, 600, 780) // 금2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "891",                          // 과목 코드
                        "국제사이버법의 이해",       // 과목명
                        "Lee Jeeny Jeeyoun",        // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관209호강의실(교직과목)(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 840, 1020) // 수6~8 → 840 ~ 1020
                        )
                ),

                new Lecture(
                        "886",                          // 과목 코드
                        "스케이트 초급",             // 과목명
                        "김수연",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "요한관422호(中)강의실",     // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.FRIDAY, 780, 900) // 금5~6 → 780 ~ 900
                        )
                ),

                new Lecture(
                        "952",                          // 과목 코드
                        "윈드서핑 초급",             // 과목명
                        "유재현",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관401호(PBL)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 1080, 1200) // 화10~11 → 1080 ~ 1200
                        )
                ),

                new Lecture(
                        "819",                          // 과목 코드
                        "인문-디지털 가상현실의 문자적 경험", // 과목명
                        "최원재",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "신학관201호강의실",         // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.DIGITAL_LITERACY, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 600, 780) // 월2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "1048",                          // 과목 코드
                        "문학적 상상력의 3D 구현",     // 과목명
                        "최원재",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "신학관201호강의실",         // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.DIGITAL_LITERACY, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 780, 960) // 월5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1038",                          // 과목 코드
                        "탄소중립과 미래기술",       // 과목명
                        "권기욱",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "사무엘관110호강의실",       // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 840, 1020) // 화6~8 → 840 ~ 1020
                        )
                ),

                new Lecture(
                        "1741",                          // 과목 코드
                        "문화와 사회, 그리고 인간(문사인(文社人))", // 과목명
                        "백숭기",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "신학관103호강의실",         // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.CHARACTER_EDUCATION, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 780, 960) // 목5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1745",                          // 과목 코드
                        "재미와 상식으로 살펴보는 인류와 질병의 뒷이야기", // 과목명
                        "이현재",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관305호강의실(PBL보건관리학)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 780, 960) // 화5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1740",                          // 과목 코드
                        "녹색도시와 인간생활",       // 과목명
                        "권기욱",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "사무엘관109호강의실",       // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 840, 1020) // 월6~8 → 840 ~ 1020
                        )
                ),

                new Lecture(
                        "1705",                          // 과목 코드
                        "배드민턴",                 // 과목명
                        "방은혜",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "체육관203호(주경기장)",     // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 660, 780) // 수3~4 → 660 ~ 780
                        )
                ),

                new Lecture(
                        "1706",                          // 과목 코드
                        "배드민턴",                 // 과목명
                        "이재구",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "체육관203호(주경기장)",     // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 780, 900) // 월5~6 → 780 ~ 900
                        )
                ),

                new Lecture(
                        "1820",                          // 과목 코드
                        "영상과 신체의 융합",       // 과목명
                        "홍선미",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "신학관201호강의실",         // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 960) // 수5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1821",                          // 과목 코드
                        "처음 만나는 중국어",       // 과목명
                        "박민수",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "바울관413호식음료실습실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.HUMANITIES_ART, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 840, 1020) // 수6~8 → 840 ~ 1020
                        )
                ),

                new Lecture(
                        "1822",                          // 과목 코드
                        "AI 웰니스 융합 프로젝트",   // 과목명
                        "조은영",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "다니엘관301호(中)강의실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.SOCIAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 600, 780) // 수2~4 → 600 ~ 780
                        )
                ),

                new Lecture(
                        "1826",                          // 과목 코드
                        "AI 코딩 첫걸음",           // 과목명
                        "심순이",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관213호SW전용실습실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.DIGITAL_LITERACY, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.FRIDAY, 780, 960) // 금5~7 → 780 ~ 960
                        )
                ),

                new Lecture(
                        "1827",                          // 과목 코드
                        "AI와 함께하는 데이터 분석",   // 과목명
                        "하상현",                   // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관213호SW전용실습실",   // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.DIGITAL_LITERACY, // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 960, 1140) // 목8~10 → 960 ~ 1140
                        )
                ),

                new Lecture(
                        "1829",                          // 과목 코드
                        "호신술",                   // 과목명
                        "고재면",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "체육관 110호(무도관)",      // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 960, 1080) // 수8~9 → 960 ~ 1080
                        )
                ),

                new Lecture(
                        "1824",                          // 과목 코드
                        "세상속의 화학: 물질, 에너지, 환경", // 과목명
                        "온라인강의교수",             // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "정보 없음",                 // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList()   // 시간 리스트
                ),

                new Lecture(
                        "1825",                          // 과목 코드
                        "통계적학습 및 인공지능",     // 과목명
                        "온라인강의교수",             // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "정보 없음",                 // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.DIGITAL_LITERACY, // 교양 영역
                        java.util.Arrays.asList()   // 시간 리스트
                ),

                new Lecture(
                        "1823",                          // 과목 코드
                        "현실속의 수학",             // 과목명
                        "온라인강의교수",             // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "정보 없음",                 // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NATURAL_SCIENCE, // 교양 영역
                        java.util.Arrays.asList()   // 시간 리스트
                ),

                new Lecture(
                        "834",                          // 과목 코드
                        "교육과정 및 교육평가",       // 과목명
                        "명지원",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관209호강의실(교직과목)(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.REQUIRED,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 840, 960) // 화6~7 → 840 ~ 960
                        )
                ),

                new Lecture(
                        "825",                          // 과목 코드
                        "교육방법 및 교육공학",       // 과목명
                        "박완성",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관209호강의실(교직과목)(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.REQUIRED,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 660) // 수1~2 → 540 ~ 660
                        )
                ),

                new Lecture(
                        "898",                          // 과목 코드
                        "교육봉사활동",             // 과목명
                        "박완성",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관209호강의실(교직과목)(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.REQUIRED,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 960, 1080) // 목8~9 → 960 ~ 1080
                        )
                ),

                new Lecture(
                        "841",                          // 과목 코드
                        "교육철학 및 교육사",         // 과목명
                        "명지원",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관209호강의실(교직과목)(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.REQUIRED,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 960, 1080) // 화8~9 → 960 ~ 1080
                        )
                ),

                new Lecture(
                        "1013",                          // 과목 코드
                        "교직실무",                 // 과목명
                        "박완성",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관209호강의실(교직과목)(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.REQUIRED,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 960, 1080) // 월8~9 → 960 ~ 1080
                        )
                ),

                new Lecture(
                        "949",                          // 과목 코드
                        "특수교육학개론",           // 과목명
                        "심성용",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관209호강의실(교직과목)(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.REQUIRED,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 660, 780) // 목3~4 → 660 ~ 780
                        )
                ),

                new Lecture(
                        "1737",                          // 과목 코드
                        "학교현장실습(교육실습)",     // 과목명
                        "박완성",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관209호강의실(교직과목)(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.REQUIRED,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 1080, 1200) // 월10~11 → 1080 ~ 1200
                        )
                ),

                new Lecture(
                        "1738",                          // 과목 코드
                        "디지털교육",               // 과목명
                        "최순남",                   // 교수
                        2,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "에스라관209호강의실(교직과목)(첨단강의실)", // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.REQUIRED,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 540, 660) // 목1~2 → 540 ~ 660
                        )
                ),

                new Lecture(
                        "850",                          // 과목 코드
                        "채플셀(Chapel Cell)",      // 과목명
                        "교목처",                   // 교수
                        0,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        2,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "정보 없음",                 // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.REQUIRED,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList()   // 시간 리스트
                ),

                new Lecture(
                        "908",                          // 과목 코드
                        "(KLEC)대학한국어 말하기Ⅰ",   // 과목명
                        "국제교육원",               // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        8,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "정보 없음",                 // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList()   // 시간 리스트
                ),

                new Lecture(
                        "1043",                          // 과목 코드
                        "(KLEC)대학한국어 읽기Ⅰ",     // 과목명
                        "국제교육원",               // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        8,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "정보 없음",                 // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList()   // 시간 리스트
                ),

                new Lecture(
                        "962",                          // 과목 코드
                        "(KLEC)대학한국어 쓰기Ⅰ",     // 과목명
                        "국제교육원",               // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        8,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "정보 없음",                 // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList()   // 시간 리스트
                ),

                new Lecture(
                        "1880",                          // 과목 코드
                        "(KLEC)대학한국어 읽기Ⅱ",     // 과목명
                        "국제교육원",               // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        8,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "정보 없음",                 // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList()   // 시간 리스트
                ),

                new Lecture(
                        "1881",                          // 과목 코드
                        "(KLEC)대학한국어 말하기Ⅱ",   // 과목명
                        "국제교육원",               // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        8,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "정보 없음",                 // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList()   // 시간 리스트
                ),

                new Lecture(
                        "1882",                          // 과목 코드
                        "(KLEC)대학한국어 쓰기Ⅱ",     // 과목명
                        "국제교육원",               // 교수
                        3,                          // 학점
                        Department.GENERAL,         // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        8,                          // 학년
                        ClassParity.ALL,            // 홀 / 짝
                        "정보 없음",                 // 강의실
                        CourseCategory.GENERAL,     // 전공, 교양
                        RequirementType.OPTIONAL,   // 필수, 선택
                        GeneralArea.NONE,           // 교양 영역
                        java.util.Arrays.asList()   // 시간 리스트
                )

        );
    }
}












