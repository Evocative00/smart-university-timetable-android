package com.syu.smarttimetable.domain.recommendation;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.domain.timetable.TimetableGenerator;
import com.syu.smarttimetable.domain.recommendation.constraints.HardConstraintValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecommendationEngine {
    
    private final TakenLectureFilter takenLectureFilter;
    private final TimetableGenerator timetableGenerator;
    private final HardConstraintValidator hardConstraintValidator;
    private final ScoreCalculator scoreCalculator;

    public RecommendationEngine() {
        this.takenLectureFilter = new TakenLectureFilter();
        this.timetableGenerator = new TimetableGenerator();
        this.hardConstraintValidator = new HardConstraintValidator();
        this.scoreCalculator = new ScoreCalculator();
    }

    public List<TimetableScoreTuple> recommend(List<Lecture> allLectures, RecommendationRequest request) {
        // 1. 이미 들은 강의 제외
        List<Lecture> filteredLectures = takenLectureFilter.filterCompletedLectures(allLectures, request.getCompletedCourseCodes());
        
        // 2. 조합 가능한 시간표 후보 생성
        List<Timetable> candidates = timetableGenerator.generateCandidateTimetables(filteredLectures, request);
        
        List<TimetableScoreTuple> validTimetables = new ArrayList<>();
        
        // 3. 하드제약 검사 및 소프트제약 점수화
        for (Timetable candidate : candidates) {
            HardConstraintValidator.ValidationResult isValid = hardConstraintValidator.validate(candidate.getLectures(), request);
            if (isValid.isValid()) {
                int score = scoreCalculator.calculateScore(candidate, request);
                validTimetables.add(new TimetableScoreTuple(candidate, score));
            }
        }
        
        // 4. 높은 점수 순으로 정렬
        Collections.sort(validTimetables, new Comparator<TimetableScoreTuple>() {
            @Override
            public int compare(TimetableScoreTuple o1, TimetableScoreTuple o2) {
                return Integer.compare(o2.getScore(), o1.getScore());
            }
        });
        
        // 5. 상위 추천 결과 반환 (최대 10개)
        int limit = Math.min(10, validTimetables.size());
        return validTimetables.subList(0, limit);
    }

    public static class TimetableScoreTuple {
        private final Timetable timetable;
        private final int score;

        public TimetableScoreTuple(Timetable timetable, int score) {
            this.timetable = timetable;
            this.score = score;
        }

        public Timetable getTimetable() {
            return timetable;
        }

        public int getScore() {
            return score;
        }
    }
}
