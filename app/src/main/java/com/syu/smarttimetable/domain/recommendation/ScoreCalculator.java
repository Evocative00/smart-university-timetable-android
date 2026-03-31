package com.syu.smarttimetable.domain.recommendation;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

public class ScoreCalculator {
    public int calculateScore(Timetable timetable, RecommendationRequest request) {
        int score = 0;
        
        // 1. 공강 확보 점수: 월/금 수업은 감점, 화/수/목 수업은 가점
        for (Lecture lecture : timetable.getLectures()) {
            for (LectureTime time : lecture.getTimes()) {
                switch(time.getDay()) {
                    case MONDAY:
                    case FRIDAY:
                        score -= 20;
                        break;
                    case TUESDAY:
                    case WEDNESDAY:
                    case THURSDAY:
                        score += 50;
                        break;
                }
            }
        }
        
        // 2. 점심시간 비우기: (12:00 ~ 13:00 또는 13:00 ~ 14:00)
        // 12:00=720분, 13:00=780분, 14:00=840분
        for (int dayIndex = 1; dayIndex <= 5; dayIndex++) {
            boolean hasLunchBreak = hasEmptySlot(timetable, dayIndex, 720, 780) || hasEmptySlot(timetable, dayIndex, 780, 840);
            if (hasLunchBreak) {
                score += 20;
            } else {
                score -= 10;
            }
        }
        
        // 3. 오전/오후 선호 처리 (가정: 오전 선호)
        for (Lecture lecture : timetable.getLectures()) {
            for (LectureTime time : lecture.getTimes()) {
                if (time.getStartTime() < 720) { // 12시 이전
                    score += 20;
                }
            }
        }
        
        return score;
    }
    
    // helper method to check if a specific time range is free in the schedule for that day
    private boolean hasEmptySlot(Timetable timetable, int dayIndex, int startRange, int endRange) {
        for (Lecture lecture : timetable.getLectures()) {
            for (LectureTime time : lecture.getTimes()) {
                if (time.getDay().ordinal() == dayIndex - 1) { // 0=Mon, 1=Tue...
                    if (!(time.getEndTime() <= startRange || time.getStartTime() >= endRange)) {
                        return false; // there is a class in this range
                    }
                }
            }
        }
        return true;
    }
}
