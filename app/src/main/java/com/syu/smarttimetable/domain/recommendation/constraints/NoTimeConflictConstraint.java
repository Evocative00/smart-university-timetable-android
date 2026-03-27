package com.syu.smarttimetable.domain.recommendation.constraints;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.domain.recommendation.RecommendationUserInfo;

import java.util.List;

// 강의 시간 충돌 검사 클래스
// 시간표 안에 들어있는 강의들끼리 시간이 서로 겹치는지 판단함
public class NoTimeConflictConstraint implements RecommendationConstraint {

    @Override
    public boolean isValid(List<Lecture> timetable, RecommendationUserInfo userInfo) {
        return !hasLectureTimeConflict(timetable);
    }

    @Override
    public String getErrorMessage() {
        return "강의 시간이 충돌했습니다.";
    }

    // 강의 시간이 충돌하는지 검사
    public boolean hasLectureTimeConflict(List<Lecture> timetable) {

        // 모든 강의 쌍을 비교
        for (int i = 0; i < timetable.size(); i++) {
            for (int j = i + 1; j < timetable.size(); j++) {

                // 두 강의가 겹치면 바로 true
                if (isLectureConflict(timetable.get(i), timetable.get(j))) {
                    return true;
                }
            }
        }

        return false; // 충돌 없음
    }

    // 강의 vs 강의 충돌 검사
    private boolean isLectureConflict(Lecture a, Lecture b) {

        // a 강의의 모든 시간 vs b 강의의 모든 시간 비교
        for (LectureTime ta : a.getTimes()) {
            for (LectureTime tb : b.getTimes()) {

                if (isTimeOverlap(ta, tb)) {
                    return true;
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