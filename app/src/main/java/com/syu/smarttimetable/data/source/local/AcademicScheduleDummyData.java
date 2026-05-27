package com.syu.smarttimetable.data.source.local;

import com.syu.smarttimetable.data.model.AcademicSchedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AcademicScheduleDummyData {

    private AcademicScheduleDummyData() {
        // Utility class
    }

    public static List<AcademicSchedule> getSchedules() {
        List<AcademicSchedule> schedules = new ArrayList<>();
        schedules.add(new AcademicSchedule(
                "2026.03.02",
                "1학기 개강",
                "학사",
                "학기 시작일입니다. 수강 신청 내역과 강의실을 미리 확인하세요."
        ));
        schedules.add(new AcademicSchedule(
                "2026.03.03 ~ 2026.03.07",
                "수강정정 기간",
                "수강",
                "수강 과목을 변경하거나 정정할 수 있는 기간입니다."
        ));
        schedules.add(new AcademicSchedule(
                "2026.04.20 ~ 2026.04.25",
                "중간고사 기간",
                "시험",
                "과목별 시험 일정은 담당 교수 공지를 함께 확인하세요."
        ));
        schedules.add(new AcademicSchedule(
                "2026.06.08 ~ 2026.06.13",
                "기말고사 기간",
                "시험",
                "기말 평가가 진행되는 기간입니다."
        ));
        schedules.add(new AcademicSchedule(
                "2026.06.22",
                "하계방학 시작",
                "방학",
                "학기 종료 후 방학이 시작됩니다."
        ));
        return Collections.unmodifiableList(schedules);
    }
}
