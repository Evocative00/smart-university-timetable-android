package com.syu.smarttimetable.data.model.course;

import data.model.common.Weekday;

public class MeetingTime {
    // Room 확장 시: Timetable 조회를 위해 @Embedded 또는 별도 시간 테이블 분리를 고려할 수 있습니다.
    //Weekend enum - Monday to Sunday
    private Weekday dayOfWeek;
    // 시작 교시
    private int startPeriod;
    // 종료 교시
    private int endPeriod;

    public MeetingTime() {
    }

    public MeetingTime(Weekday dayOfWeek, int startPeriod, int endPeriod) {
        this.dayOfWeek = dayOfWeek;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
    }

    public Weekday getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Weekday dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(int startPeriod) {
        this.startPeriod = startPeriod;
    }

    public int getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(int endPeriod) {
        this.endPeriod = endPeriod;
    }

    @Override
    public String toString() {
        return "MeetingTime{" +
                "dayOfWeek=" + dayOfWeek +
                ", startPeriod=" + startPeriod +
                ", endPeriod=" + endPeriod +
                '}';
    }
}

