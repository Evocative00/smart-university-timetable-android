package com.syu.smarttimetable.domain.recommendation.constraints;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.List;

public class BlockedTimeConstraint implements RecommendationConstraint {

    @Override
    public boolean isValid(List<Lecture> timetable, RecommendationRequest request) {
        if (request == null || request.getSoftConstraint() == null) {
            return true;
        }

        List<LectureTime> blockedTimes = request.getSoftConstraint().getBlockedTimes();
        if (blockedTimes == null || blockedTimes.isEmpty()) {
            return true;
        }

        return !hasConflictWithBlockedTimes(timetable, blockedTimes);
    }

    @Override
    public String getErrorMessage() {
        return "차단된 시간대와 강의 시간이 충돌했습니다.";
    }

    public boolean hasConflictWithBlockedTimes(List<Lecture> timetable, List<LectureTime> blockedTimes) {
        if (timetable == null || timetable.isEmpty() || blockedTimes == null || blockedTimes.isEmpty()) {
            return false;
        }

        for (Lecture lecture : timetable) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime lectureTime : lecture.getTimes()) {
                if (lectureTime == null) {
                    continue;
                }

                for (LectureTime blockedTime : blockedTimes) {
                    if (blockedTime == null) {
                        continue;
                    }

                    if (isTimeOverlap(lectureTime, blockedTime)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean isTimeOverlap(LectureTime lectureTime, LectureTime blockedTime) {
        return lectureTime.getDay() == blockedTime.getDay()
                && lectureTime.getStartTime() < blockedTime.getEndTime()
                && lectureTime.getEndTime() > blockedTime.getStartTime();
    }
}
