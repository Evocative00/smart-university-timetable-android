package com.syu.smarttimetable.domain.recommendation.constraints;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.domain.recommendation.RecommendationUserInfo;

import java.util.List;

// 추천 제약 조건 인터페이스
// 모든 제약 조건 클래스는 이 인터페이스를 구현해서 같은 방식으로 검사하게 만듦
public interface RecommendationConstraint {

    boolean isValid(List<Lecture> timetable, RecommendationUserInfo userInfo);     // timetable: 추천된 시간표 (강의 리스트), userInfo: 사용자가 입력한 조건들

    // 실패 시 보여줄 메시지
    String getErrorMessage();
}