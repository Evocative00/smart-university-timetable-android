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

                new Lecture(  //홀짝 나중에 구분
                        "733",                          // 과목 코드
                        "소프트웨어 원리",                   // 과목명
                        "신인수",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "노작교육실습장",      // 강의실
                        CourseCategory.MAJOR,           // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 540, 720) // 9시~12시
                        )
                ),

                new Lecture(  //홀짝 나중에 구분
                        "734",                          // 과목 코드
                        "소프트웨어 원리",                   // 과목명
                        "신인수",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,           // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 720, 900) // 12시~15시
                        )
                ),

                new Lecture(  //홀짝 나중에 구분
                        "1878",                          // 과목 코드
                        "인생설계와 진로 I",              // 과목명
                        "정수목",                        // 교수
                        1,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관403호(컴과실습실)",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 1080, 1140) // 18시~19시
                        )
                ),

                new Lecture(  //홀짝 나중에 구분
                        "1879",                          // 과목 코드
                        "인생설계와 진로 I",              // 과목명
                        "정수목",                        // 교수
                        1,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관404호(컴과실습실)",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 1020, 1080) // 17시~18시
                        )
                ),

                new Lecture(  //홀짝 나중에 구분
                        "723",                          // 과목 코드
                        "인생설계와 진로 I",              // 과목명
                        "김병국",                        // 교수
                        1,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관410호(컴과실습실)",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 1020, 1080) // 17시~18시
                        )
                ),

                new Lecture(  //홀짝 나중에 구분
                        "722",                          // 과목 코드
                        "인생설계와 진로 I",              // 과목명
                        "김병국",                        // 교수
                        1,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관404호(컴과실습실)",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 1080, 1140) // 18시~19시
                        )
                ),

                new Lecture(  //홀짝 나중에 구분
                        "754",                          // 과목 코드
                        "인성과사회",              // 과목명
                        "김동혜",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "사무엘관109호강의실",      // 강의실
                        CourseCategory.GENERAL,           // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 780, 900) // 13시~15시
                        )
                ),

                new Lecture(  //홀짝 나중에 구분
                        "753",                          // 과목 코드
                        "인성과사회",              // 과목명
                        "김동혜",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
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

                new Lecture(  // 홀짝 구분 나중에
                        "1759",                          // 과목 코드
                        "AI를 위한 미적분학",              // 과목명
                        "이한청",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 720, 900) // 12시~15시
                        )
                ),

                new Lecture(  // 홀짝 구분 나중에
                        "1758",                          // 과목 코드
                        "AI를 위한 미적분학",              // 과목명
                        "이한청",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        1,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관403호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720) // 9시~12시
                        )
                ),

                // 2학년 과목 시작
                new Lecture(  // 홀짝 구분 나중에
                        "749",                          // 과목 코드
                        "객체지향프로그래밍 I",              // 과목명
                        "안영아",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 540, 720) // 9시~12시
                        )
                ),

                new Lecture(  // 홀짝 구분 나중에
                        "748",                          // 과목 코드
                        "객체지향프로그래밍 I",              // 과목명
                        "최희식",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 540, 660), // 9시~11시
                                new LectureTime(DayOfWeek.MONDAY, 720, 780)  // 12시~13시
                        )
                ),

                new Lecture(  // 홀짝 구분 나중에
                        "737",                          // 과목 코드
                        "객체지향프로그래밍응용",              // 과목명
                        "최희식",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,             // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 780, 900)  // 13시~15시
                        )
                ),

                new Lecture(  // 홀짝 구분 나중에
                        "738",                          // 과목 코드
                        "객체지향프로그래밍응용",              // 과목명
                        "안영아",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,                 // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관409호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,           // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 720, 840)  // 12시~14시
                        )
                ),

                new Lecture(  // 홀짝 구분 나중에
                        "1721",                          // 과목 코드
                        "디지털 논리회로",              // 과목명
                        "정수목",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관404호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 600, 780)  // 10시~13시
                        )
                ),

                new Lecture(  // 홀짝 구분 나중에
                        "774",                          // 과목 코드
                        "디지털 논리회로",              // 과목명
                        "정수목",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관404호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.FRIDAY, 600, 780)  // 10시~13시
                        )
                ),

                new Lecture(  // 홀짝 구분 나중에
                        "742",                          // 과목 코드
                        "생활과 윤리",              // 과목명
                        "김동혜",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,      // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "요한관323호(中)강의실",      // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 720, 840)  // 12시~14시
                        )
                ),

                new Lecture(  // 홀짝 구분 나중에
                        "743",                          // 과목 코드
                        "생활과 윤리",              // 과목명
                        "김동혜",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,      // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "에스라관113호강의실",      // 강의실
                        CourseCategory.GENERAL,          // 전공, 교양
                        RequirementType.REQUIRED,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.MONDAY, 900, 1020)  // 15시~17시
                        )
                ),

                new Lecture(  // 홀짝 구분 나중에
                        "719",                          // 과목 코드
                        "선형대수학",              // 과목명
                        "권윤기",                        // 교수
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
                                new LectureTime(DayOfWeek.WEDNESDAY, 840, 900),  // 14시~15시
                                new LectureTime(DayOfWeek.FRIDAY, 960, 1080)   // 16시~18시
                        )
                ),

                new Lecture(  // 홀짝 구분 나중에
                        "718",                          // 과목 코드
                        "선형대수학",              // 과목명
                        "권윤기",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
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

                new Lecture( // 홀짝 구분 나중에
                        "752",                          // 과목 코드
                        "컴퓨터프로그래밍",              // 과목명
                        "이현주",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관410호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 840, 1020)   // 14시~17시
                        )
                ),

                new Lecture( // 홀짝 구분 나중에
                        "751",                          // 과목 코드
                        "컴퓨터프로그래밍",              // 과목명
                        "이현주",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관410호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 600, 780)   // 10시~13시
                        )
                ),

                new Lecture( // 홀짝 구분 나중에
                        "740",                          // 과목 코드
                        "컴퓨터프로그래밍응용",              // 과목명
                        "김병국",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관403호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 900, 1020)   // 15시~17시
                        )
                ),

                new Lecture( // 홀짝 구분 나중에
                        "739",                          // 과목 코드
                        "컴퓨터프로그래밍응용",              // 과목명
                        "김병국",                        // 교수
                        2,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.NONE,               // 세부 전공 여부
                        2,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
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

                new Lecture( //홀짝 구분 나중에
                        "776",                          // 과목 코드
                        "시스템프로그래밍",              // 과목명
                        "홍성옥",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관410호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 840, 1020)   // 14시~17시
                        )
                ),

                new Lecture( //홀짝 구분 나중에
                        "775",                          // 과목 코드
                        "시스템프로그래밍",              // 과목명
                        "홍성옥",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관411호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 600, 780)   // 10시~13시
                        )
                ),

                new Lecture( //홀짝 구분 나중에
                        "769",                          // 과목 코드
                        "운영체제",                     // 과목명
                        "공준익",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관404호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.THURSDAY, 1080, 1140),   // 18시~19시
                                new LectureTime(DayOfWeek.FRIDAY, 900, 1020)       // 15시~17시
                        )
                ),

                new Lecture( //홀짝 구분 나중에
                        "770",                          // 과목 코드
                        "운영체제",                     // 과목명
                        "공준익",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
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

                new Lecture( // 홀짝 구분 나중에
                        "766",                          // 과목 코드
                        "컴퓨터네트워크",               // 과목명
                        "조양현",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관411호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.WEDNESDAY, 660, 840)     // 11시~14시
                        )
                ),

                new Lecture( // 홀짝 구분 나중에
                        "767",                          // 과목 코드
                        "컴퓨터네트워크",               // 과목명
                        "조양현",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.COMPUTERSCIENCE,      // 세부 전공 여부
                        3,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
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
                new Lecture( // 홀짝 구분 나중에
                        "762",                          // 과목 코드
                        "기계학습",                     // 과목명
                        "왕수현",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.SOFTWARE,              // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
                        "제1실습관411호(컴과실습실)",      // 강의실
                        CourseCategory.MAJOR,          // 전공, 교양
                        RequirementType.OPTIONAL,       // 필수, 선택
                        GeneralArea.NONE,               // 교양이면 영역선택, 아니면 NONE
                        java.util.Arrays.asList(
                                new LectureTime(DayOfWeek.TUESDAY, 720, 900)     // 12시~15시
                        )
                ),

                new Lecture( // 홀짝 구분 나중에
                        "763",                          // 과목 코드
                        "기계학습",                     // 과목명
                        "왕수현",                        // 교수
                        3,                              // 학점
                        Department.COMPUTER_SCIENCE,    // 학과
                        MajorType.SOFTWARE,              // 세부 전공 여부
                        4,                              // 학년
                        ClassParity.ALL,                // 홀 / 짝
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
                        "1653",                          // 과목 코드
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
                )
                //AI리터러시와 문제해결(구, 컴퓨팅사고력) 부터 하기..



        );
    }
}