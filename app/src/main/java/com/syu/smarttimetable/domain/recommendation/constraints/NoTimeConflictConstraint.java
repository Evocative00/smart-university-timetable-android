package com.syu.smarttimetable.domain.recommendation.constraints;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.List;

public class NoTimeConflictConstraint implements RecommendationConstraint {

    @Override
    public boolean isValid(List<Lecture> timetable, RecommendationRequest request) {
        return !hasLectureTimeConflict(timetable);
    }

    @Override
    public String getErrorMessage() {
        return "강의 시간이 충돌했습니다.";
    }

    public boolean hasLectureTimeConflict(List<Lecture> timetable) {
        if (timetable == null || timetable.isEmpty()) {
            return false;
        }

        for (int i = 0; i < timetable.size(); i++) {
            for (int j = i + 1; j < timetable.size(); j++) {
                if (isLectureConflict(timetable.get(i), timetable.get(j))) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isLectureConflict(Lecture first, Lecture second) {
        if (first == null || second == null) {
            return false;
        }

        // 시간 정보가 없는 강의는 충돌 불가
        if (first.getTimes() == null || first.getTimes().isEmpty() ||
            second.getTimes() == null || second.getTimes().isEmpty()) {
            return false;
        }

        for (LectureTime firstTime : first.getTimes()) {
            for (LectureTime secondTime : second.getTimes()) {
                if (firstTime != null && secondTime != null && isTimeOverlap(firstTime, secondTime)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isTimeOverlap(LectureTime first, LectureTime second) {
        return first.getDay() == second.getDay()
                && first.getStartTime() < second.getEndTime()
                && first.getEndTime() > second.getStartTime();
    }
}