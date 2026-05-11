package com.syu.smarttimetable.domain.recommendation;

import android.util.Log;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.SoftConstraint;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.data.model.enums.CourseCategory;
import com.syu.smarttimetable.domain.recommendation.constraints.HardConstraintValidator;
import com.syu.smarttimetable.domain.recommendation.constraints.RequiredLectureConstraint;
import com.syu.smarttimetable.domain.timetable.TimetableGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecommendationEngine {

    private static final String TAG = "RecommendationEngine";
    private static final int MAX_RECOMMENDATIONS = 10;

    private final TakenLectureFilter takenLectureFilter;
    private final TimetableGenerator timetableGenerator;
    private final HardConstraintValidator hardConstraintValidator;
    private final ScoreCalculator scoreCalculator;

    public RecommendationEngine() {
        this(
                new TakenLectureFilter(),
                new TimetableGenerator(),
                new HardConstraintValidator(),
                new ScoreCalculator()
        );
    }

    public RecommendationEngine(TakenLectureFilter takenLectureFilter,
                                TimetableGenerator timetableGenerator,
                                HardConstraintValidator hardConstraintValidator,
                                ScoreCalculator scoreCalculator) {
        this.takenLectureFilter = takenLectureFilter;
        this.timetableGenerator = timetableGenerator;
        this.hardConstraintValidator = hardConstraintValidator;
        this.scoreCalculator = scoreCalculator;
    }

    public List<TimetableScoreTuple> recommend(List<Lecture> allLectures, RecommendationRequest request) {
        List<TimetableScoreTuple> results = new ArrayList<>();

        if (allLectures == null || allLectures.isEmpty() || request == null) {
            Log.e(TAG, "Invalid input: allLectures="
                    + (allLectures == null ? "null" : allLectures.size())
                    + ", request="
                    + (request == null ? "null" : "ok"));
            return results;
        }

        Log.d(TAG, "Starting recommendation with " + allLectures.size() + " lectures");

        List<Lecture> filteredLectures = takenLectureFilter.filterCompletedLectures(
                allLectures,
                request.getCompletedCourseCodes()
        );

        Log.d(TAG, "After filtering completed lectures: " + filteredLectures.size() + " lectures");

        List<Timetable> candidates = timetableGenerator.generateCandidates(filteredLectures, request);

        Log.d(TAG, "Generated " + candidates.size() + " candidate timetables");

        Set<String> seenTimetableSignatures = new HashSet<>();

        for (Timetable candidate : candidates) {
            HardConstraintValidator.ValidationResult validationResult =
                    hardConstraintValidator.validate(candidate.getLecturesReadOnly(), request);

            if (!validationResult.isValid()) {
                Log.d(TAG, "Candidate rejected: " + validationResult.getErrors());
                continue;
            }

            String signature = buildTimetableSignature(candidate);
            if (!seenTimetableSignatures.add(signature)) {
                Log.d(TAG, "Duplicate candidate skipped: " + signature);
                continue;
            }

            int score = scoreCalculator.calculateScore(candidate, request);
            results.add(new TimetableScoreTuple(candidate, score));
        }

        Log.d(TAG, "Valid recommendations after filtering: " + results.size());

        // 정렬: 학년 맞춤 전공 과목 개수 기준 → 점수 기준
        results.sort((first, second) -> {
            int gradeMatchedCountFirst = countGradeMatchedMajors(first.getTimetable(), request);
            int gradeMatchedCountSecond = countGradeMatchedMajors(second.getTimetable(), request);

            // 학년 맞춤 전공 과목이 많은 것을 우선
            int countCompare = Integer.compare(gradeMatchedCountSecond, gradeMatchedCountFirst);
            if (countCompare != 0) {
                return countCompare;
            }

            // 학년 맞춤 전공 과목 개수가 같으면 점수로 비교
            int scoreCompare = Integer.compare(second.getScore(), first.getScore());
            if (scoreCompare != 0) {
                return scoreCompare;
            }

            // 점수도 같으면 총 학점으로 비교
            return Integer.compare(
                    second.getTimetable().getTotalCredits(),
                    first.getTimetable().getTotalCredits()
            );
        });

        results = prioritizeFreeDayPreferences(results, request);

        if (results.size() > MAX_RECOMMENDATIONS) {
            Log.d(TAG, "Returning top " + MAX_RECOMMENDATIONS + " recommendations");
            return new ArrayList<>(results.subList(0, MAX_RECOMMENDATIONS));
        }

        Log.d(TAG, "Returning " + results.size() + " recommendations");
        return results;
    }

    private List<TimetableScoreTuple> prioritizeFreeDayPreferences(List<TimetableScoreTuple> results,
                                                                   RecommendationRequest request) {
        if (results == null || results.isEmpty() || request == null) {
            return results;
        }

        SoftConstraint softConstraint = request.getSoftConstraint();
        if (softConstraint == null
                || softConstraint.isSkipped()
                || softConstraint.getPreferredFreeDays() == null
                || softConstraint.getPreferredFreeDays().isEmpty()) {
            return results;
        }

        List<TimetableScoreTuple> freeDaySatisfied = new ArrayList<>();
        List<TimetableScoreTuple> freeDayViolated = new ArrayList<>();

        for (TimetableScoreTuple tuple : results) {
            if (tuple == null || tuple.getTimetable() == null) {
                continue;
            }

            if (containsAnyPreferredFreeDayLecture(tuple.getTimetable(), softConstraint.getPreferredFreeDays())) {
                freeDayViolated.add(tuple);
            } else {
                freeDaySatisfied.add(tuple);
            }
        }

        List<TimetableScoreTuple> prioritized = new ArrayList<>(results.size());
        prioritized.addAll(freeDaySatisfied);
        prioritized.addAll(freeDayViolated);
        return prioritized;
    }

    private boolean containsAnyPreferredFreeDayLecture(Timetable timetable, List<DayOfWeek> preferredFreeDays) {
        if (timetable == null || preferredFreeDays == null || preferredFreeDays.isEmpty()) {
            return false;
        }

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime time : lecture.getTimes()) {
                if (time != null && time.getDay() != null && preferredFreeDays.contains(time.getDay())) {
                    return true;
                }
            }
        }

        return false;
    }

    private int countGradeMatchedMajors(Timetable timetable, RecommendationRequest request) {
        if (request == null || request.getUserGrade() <= 0) {
            return 0;
        }

        int count = 0;
        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture != null 
                    && lecture.getCategory() == CourseCategory.MAJOR
                    && lecture.getGrade() == request.getUserGrade()) {
                count++;
            }
        }
        return count;
    }

    private String buildTimetableSignature(Timetable timetable) {
        List<String> lectureKeys = new ArrayList<>();

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture != null) {
                lectureKeys.add(RequiredLectureConstraint.buildLectureKey(lecture));
            }
        }

        lectureKeys.sort(String::compareTo);
        return String.join("||", lectureKeys);
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