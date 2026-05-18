package com.syu.smarttimetable.domain.recommendation;

import android.util.Log;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.SoftConstraint;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.CourseCategory;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
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

    public RecommendationResult recommend(List<Lecture> allLectures, RecommendationRequest request) {
        List<TimetableScoreTuple> results = new ArrayList<>();

        if (allLectures == null || allLectures.isEmpty() || request == null) {
            Log.e(TAG, "Invalid input: allLectures="
                    + (allLectures == null ? "null" : allLectures.size())
                    + ", request="
                    + (request == null ? "null" : "ok"));
            return new RecommendationResult(results, new ArrayList<>());
        }

        Log.d(TAG, "Starting recommendation with " + allLectures.size() + " lectures");

        // Pre-check: detect conflict between fixed (hard) lectures' days and soft-constraint preferred free days
        SoftConstraint softConstraint = request.getSoftConstraint();
        if (softConstraint != null && softConstraint.getPreferredFreeDays() != null
                && !softConstraint.getPreferredFreeDays().isEmpty()
                && request.getFixedLectureKeys() != null && !request.getFixedLectureKeys().isEmpty()) {
            Set<DayOfWeek> fixedLectureDays = new HashSet<>();

            for (Lecture lecture : allLectures) {
                if (lecture == null) continue;
                String key = RequiredLectureConstraint.buildLectureKey(lecture);
                if (!request.getFixedLectureKeys().contains(key)) continue;
                if (lecture.getTimes() == null) continue;
                for (LectureTime time : lecture.getTimes()) {
                    if (time != null && time.getDay() != null) {
                        fixedLectureDays.add(time.getDay());
                    }
                }
            }

            // find intersection
            List<DayOfWeek> conflicts = new ArrayList<>();
            for (DayOfWeek preferred : softConstraint.getPreferredFreeDays()) {
                if (fixedLectureDays.contains(preferred)) {
                    conflicts.add(preferred);
                }
            }

            if (!conflicts.isEmpty()) {
                Log.d(TAG, "Recommendation aborted due to fixed lecture vs preferred free day conflict: " + conflicts);
                return new RecommendationResult(results, conflicts);
            }
        }

        List<Lecture> filteredLectures = takenLectureFilter.filterCompletedLectures(
                allLectures,
                request.getCompletedCourseCodes()
        );

        Log.d(TAG, "After filtering completed lectures: " + filteredLectures.size() + " lectures");

        List<Timetable> candidates = timetableGenerator.generateCandidates(filteredLectures, request);

        Log.d(TAG, "Generated " + candidates.size() + " candidate timetables");

        Set<String> seenTimetableSignatures = new HashSet<>();

        for (Timetable candidate : candidates) {
            if (candidate == null) {
                continue;
            }

            HardConstraintValidator.ValidationResult validationResult =
                    hardConstraintValidator.validate(candidate.getLecturesReadOnly(), request);

            if (!validationResult.isValid()) {
                Log.d(TAG, "Candidate rejected: " + validationResult.getErrors());
                continue;
            }

            // Enforce rule: for the five general areas, at most one lecture per area is allowed in a recommendation
            if (hasExcessGeneralAreaLectures(candidate)) {
                Log.d(TAG, "Candidate rejected: exceeds per-area general lecture limit");
                continue;
            }

            // Enforce preferred free days: if soft constraint requests free days, any timetable containing
            // a lecture that has any timeslot on those days must be rejected entirely.
            // reuse existing softConstraint variable from pre-check
            if (softConstraint != null
                    && !softConstraint.isSkipped()
                    && softConstraint.getPreferredFreeDays() != null
                    && !softConstraint.getPreferredFreeDays().isEmpty()) {
                if (containsAnyPreferredFreeDayLecture(candidate, softConstraint.getPreferredFreeDays())) {
                    Log.d(TAG, "Candidate rejected: contains lecture on preferred free day(s)");
                    continue;
                }
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

        results.sort((first, second) -> {
            int firstGradeMajorCount = countGradeMatchedMajors(first.getTimetable(), request);
            int secondGradeMajorCount = countGradeMatchedMajors(second.getTimetable(), request);

            int gradeMajorCompare = Integer.compare(secondGradeMajorCount, firstGradeMajorCount);
            if (gradeMajorCompare != 0) {
                return gradeMajorCompare;
            }

            int scoreCompare = Integer.compare(second.getScore(), first.getScore());
            if (scoreCompare != 0) {
                return scoreCompare;
            }

            return Integer.compare(
                    second.getTimetable().getTotalCredits(),
                    first.getTimetable().getTotalCredits()
            );
        });

        results = prioritizeFreeDayPreferences(results, request);

        if (results.size() > MAX_RECOMMENDATIONS) {
            Log.d(TAG, "Returning top " + MAX_RECOMMENDATIONS + " recommendations");
            return new RecommendationResult(new ArrayList<>(results.subList(0, MAX_RECOMMENDATIONS)), new ArrayList<>());
        }

        Log.d(TAG, "Returning " + results.size() + " recommendations");
        return new RecommendationResult(results, new ArrayList<>());
    }

    public static class RecommendationResult {
        private final List<TimetableScoreTuple> recommendations;
        private final List<DayOfWeek> conflictDays;

        public RecommendationResult(List<TimetableScoreTuple> recommendations, List<DayOfWeek> conflictDays) {
            this.recommendations = recommendations != null ? recommendations : new ArrayList<>();
            this.conflictDays = conflictDays != null ? conflictDays : new ArrayList<>();
        }

        public List<TimetableScoreTuple> getRecommendations() {
            return recommendations;
        }

        public List<DayOfWeek> getConflictDays() {
            return conflictDays;
        }
    }

    private boolean hasExcessGeneralAreaLectures(Timetable timetable) {
        if (timetable == null || timetable.getLecturesReadOnly() == null) {
            return false;
        }

        int humanities = 0;
        int natural = 0;
        int social = 0;
        int digital = 0;
        int character = 0;

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null) continue;
            if (lecture.getCategory() != CourseCategory.GENERAL) continue;

            switch (lecture.getGeneralArea()) {
                case HUMANITIES_ART:
                    humanities++;
                    break;
                case NATURAL_SCIENCE:
                    natural++;
                    break;
                case SOCIAL_SCIENCE:
                    social++;
                    break;
                case DIGITAL_LITERACY:
                    digital++;
                    break;
                case CHARACTER_EDUCATION:
                    character++;
                    break;
                default:
                    break;
            }

            if (humanities > 1 || natural > 1 || social > 1 || digital > 1 || character > 1) {
                return true;
            }
        }

        return false;
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
                if (time != null
                        && time.getDay() != null
                        && preferredFreeDays.contains(time.getDay())) {
                    return true;
                }
            }
        }

        return false;
    }

    private int countGradeMatchedMajors(Timetable timetable, RecommendationRequest request) {
        if (timetable == null || request == null || request.getUserGrade() <= 0) {
            return 0;
        }

        int count = 0;

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null) {
                continue;
            }

            if (lecture.getCategory() == CourseCategory.MAJOR
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