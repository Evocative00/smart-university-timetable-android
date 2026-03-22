package com.syu.smarttimetable.data.model.enums;

public enum MajorType {
    COMPUTERSCIENCE, // 컴퓨터공학부
    SOFTWARE,  // 컴퓨터공학부
    // ALL, // 모든 세부전공이 들을 수 있음 fix : 26.03.18 department로 범위가 정해져 있어서 헷갈릴 뿐 실효성 없어서 삭제
    NONE // 특정 세부전공에 속하지 X -> 공통 기초 과목, 교양, 학과 전체 공통 전
}