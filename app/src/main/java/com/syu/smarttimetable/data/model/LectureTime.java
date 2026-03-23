package com.syu.smarttimetable.data.model;

import com.syu.smarttimetable.data.model.enums.DayOfWeek;

public class LectureTime { // 강의의 시간만 따로 담당하는 클래스

    // 요일
    private DayOfWeek day;
    // 시작 시간 (분 단위, 예: 09:00 → 540)
    private int startTime;
    // 종료 시간 (분 단위, 예: 10:00 → 600)
    private int endTime;
    // 시작, 종료시간들이 분 단위인 이유는 비교할 때 더 쉽게 하기 위함

    // 생성자: LectureTime 객체 생성할 때 값 세팅
    public LectureTime(DayOfWeek day, int startTime, int endTime) {
        this.day = day;               	// 요일 초기화
        this.startTime = startTime;   	// 시작 시간 초기화
        this.endTime = endTime;	 // 종료 시간 초기화
    }

    // 반환
    public DayOfWeek getDay() { return day; }
    public int getStartTime() { return startTime; }
    public int getEndTime() { return endTime; }
}