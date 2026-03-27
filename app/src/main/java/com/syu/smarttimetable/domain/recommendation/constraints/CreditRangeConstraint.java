package com.syu.smarttimetable.domain.recommendation.constraints;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.domain.recommendation.RecommendationUserInfo;

import java.util.List;

// 학점 검사 클래스
// 이 시간표의 총 학점이 사용자가 원하는 희망 학점과 일치하는지 판단함
public class CreditRangeConstraint implements RecommendationConstraint {

    @Override
    public boolean isValid(List<Lecture> timetable, RecommendationUserInfo userInfo) {
        return isCreditMatched(timetable, userInfo.getDesiredCredits());
    }

    @Override
    public String getErrorMessage() {
        return "희망 학점이 불일치합니다.";
    }

    // 학점 검사
    public boolean isCreditMatched(List<Lecture> timetable, int desiredCredits) {
        int totalCredits = 0;

        // 모든 강의의 학점을 더함
        for (Lecture lecture : timetable) {
            totalCredits += lecture.getCredits();
        }

        // 합이 원하는 학점과 같으면 true
        return totalCredits == desiredCredits;
    }
}