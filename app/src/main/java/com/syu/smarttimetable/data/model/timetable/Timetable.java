package com.syu.smarttimetable.data.model.timetable;

import data.model.course.LectureSection;
import java.util.ArrayList;
import java.util.List;

public class Timetable {
    // Room 확장 시: selectedSections는 중간 매핑 테이블로 분리하면 관리가 편합니다.
    //시간표 식별자
    //title 사용자에게 보여줄 이름 (시간표 이름으로 생각하면 됨 (27-1 시간표 진,진진, 최종본, 진짜최종본 등 )
    //selectedSections 최종 선택된 분반 목록
    // totalCredits 총 학점
    private String timetableId;
    private String title;
    private List<LectureSection> selectedSections;
    private int totalCredits;

    public Timetable() {
        this.selectedSections = new ArrayList<>();
    }


    public Timetable(String timetableId, String title, List<LectureSection> selectedSections, int totalCredits) {
        this.timetableId = timetableId;
        this.title = title;
        setSelectedSections(selectedSections);
        this.totalCredits = totalCredits;
    }

    public String getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(String timetableId) {
        this.timetableId = timetableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LectureSection> getSelectedSections() {
        return new ArrayList<>(selectedSections);
    }

    //selectedSections를 new ArrayList<>()로 기본 초기화 → null 안전
    //getter에서 new ArrayList<>(selectedSections) 반환 → 내부 리스트 보호(방어적 복사)
    //setter도 복사해서 저장 → 외부 참조 변경으로 내부 상태 깨지는 것 방지

    public void setSelectedSections(List<LectureSection> selectedSections) {
        this.selectedSections = selectedSections == null ? new ArrayList<>() : new ArrayList<>(selectedSections);
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "timetableId='" + timetableId + '\'' +
                ", title='" + title + '\'' +
                ", selectedSections=" + selectedSections +
                ", totalCredits=" + totalCredits +
                '}';
    }
}


