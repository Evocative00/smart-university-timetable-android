package com.syu.smarttimetable.data.model.common;

// 강의시간 + 사용자 공강 선호에 사용할 요일 열거
// enum 을 사용해서 == 연산자로 비교 단순하게 만들기
// 주말이 포함된 이유는 시각적으로 편안하고 주차의 스케줄을 전체적으로 짤 수 있게 Ex) 아르바이트
public enum Weekday {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

