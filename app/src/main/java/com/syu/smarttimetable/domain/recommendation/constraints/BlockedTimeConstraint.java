package com.syu.smarttimetable.domain.recommendation.constraints;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.domain.recommendation.RecommendationUserInfo;

import java.util.List;

// 불가능 시간 검사 클래스
// 사용자가 설정한 절대 안 되는 시간(알바, 근로 등)과 강의 시간이 겹치는지 판단함
public class BlockedTimeConstraint implements RecommendationConstraint {

    @Override
    public boolean isValid(List<Lecture> timetable, RecommendationUserInfo userInfo) {
        return !hasBlockedTimeConflict(timetable, userInfo.getBlockedTimes());
    }

    @Override
    public String getErrorMessage() {
        return "불가능한 시간을 포함합니다.";
    }

    // 불가능 시간 충돌 검사
    public boolean hasBlockedTimeConflict(List<Lecture> timetable, List<LectureTime> blockedTimes) {

        // 막아놓은 시간(절대 안 되는 시간 설정)이 없으면 검사 필요 없음
        if (blockedTimes == null || blockedTimes.isEmpty()) {
            return false;
        }

        // 모든 강의에 대해 검사
        for (Lecture lecture : timetable) {

            // 강의의 시간들 (월/수 이런 거 여러 개 가능)
            for (LectureTime lectureTime : lecture.getTimes()) {

                // 막아놓은 시간들과 비교
                for (LectureTime blocked : blockedTimes) {

                    // 겹치면 바로 true
                    if (isTimeOverlap(lectureTime, blocked)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // 시간 겹침 판단
    private boolean isTimeOverlap(LectureTime a, LectureTime b) {

        return a.getDay() == b.getDay() // 같은 요일이어야 하고
                && a.getStartTime() < b.getEndTime() // 시작이 상대 끝보다 빠르고
                && a.getEndTime() > b.getStartTime(); // 끝이 상대 시작보다 늦으면 겹치는 것
    }
}