package com.syu.smarttimetable.data.model.enums;

public enum FreeTimePreference {
    NONE,

    // 오전 공강 선호 = 오전을 비우고 싶음 = 수업은 오후 쪽 선호
    MORNING,

    // 오후 공강 선호 = 오후를 비우고 싶음 = 수업은 오전 쪽 선호
    AFTERNOON
}