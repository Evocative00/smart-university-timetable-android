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

        List<DayOfWeek> conflictDays = findFixedLectureFreeDayConflicts(allLectures, request);
        RecommendationRequest effectiveRequest = createRequestWithoutConflictedFreeDays(request, conflictDays);

        if (!conflictDays.isEmpty()) {
            Log.d(TAG, "Fixed lecture conflicts with preferred free days: "
                    + conflictDays
                    + ". The conflicted days will be ignored for recommendation scoring/generation.");
        }

        List<Lecture> filteredLectures = takenLectureFilter.filterCompletedLectures(
                allLectures,
                effectiveRequest.getCompletedCourseCodes()
        );

        Log.d(TAG, "After filtering completed lectures: " + filteredLectures.size() + " lectures");

        List<Timetable> candidates = timetableGenerator.generateCandidates(filteredLectures, effectiveRequest);

        Log.d(TAG, "Generated " + candidates.size() + " candidate timetables");

        Set<String> seenTimetableSignatures = new HashSet<>();
        int rejectedByHardConstraint = 0;
        int rejectedByDuplicate = 0;

        for (Timetable candidate : candidates) {
            if (candidate == null) {
                continue;
            }

            HardConstraintValidator.ValidationResult validationResult =
                    hardConstraintValidator.validate(candidate.getLecturesReadOnly(), effectiveRequest);

            if (!validationResult.isValid()) {
                rejectedByHardConstraint++;
                Log.d(TAG, "Candidate rejected by hard constraint: " + validationResult.getErrors());
                continue;
            }

            // 삼육대 교양 영역 특성 반영: 인문예술/자연과학/사회과학/디지털/인성교육은 영역별 1개까지만 추천한다.
            if (hasExcessGeneralAreaLectures(candidate)) {
                Log.d(TAG, "Candidate rejected: exceeds per-area general lecture limit");
                continue;
            }

            String signature = buildTimetableSignature(candidate);

            if (!seenTimetableSignatures.add(signature)) {
                rejectedByDuplicate++;
                Log.d(TAG, "Duplicate candidate skipped: " + signature);
                continue;
            }

            int score = scoreCalculator.calculateScore(candidate, effectiveRequest);
            results.add(new TimetableScoreTuple(candidate, score));
        }

        Log.d(TAG, "Valid recommendations after filtering: " + results.size());
        Log.d(TAG, "Rejected by hard constraint: " + rejectedByHardConstraint + ", by duplicate: " + rejectedByDuplicate);

        results.sort((first, second) -> {
            int firstFreeDayViolations = countPreferredFreeDayViolations(first.getTimetable(), effectiveRequest);
            int secondFreeDayViolations = countPreferredFreeDayViolations(second.getTimetable(), effectiveRequest);

            int freeDayViolationCompare = Integer.compare(firstFreeDayViolations, secondFreeDayViolations);
            if (freeDayViolationCompare != 0) {
                return freeDayViolationCompare;
            }

            int scoreCompare = Integer.compare(second.getScore(), first.getScore());
            if (scoreCompare != 0) {
                return scoreCompare;
            }

            int firstGradeMajorCount = countGradeMatchedMajors(first.getTimetable(), effectiveRequest);
            int secondGradeMajorCount = countGradeMatchedMajors(second.getTimetable(), effectiveRequest);

            int gradeMajorCompare = Integer.compare(secondGradeMajorCount, firstGradeMajorCount);
            if (gradeMajorCompare != 0) {
                return gradeMajorCompare;
            }

            return Integer.compare(
                    second.getTimetable().getTotalCredits(),
                    first.getTimetable().getTotalCredits()
            );
        });

        List<TimetableScoreTuple> limitedResults = results;
        if (results.size() > MAX_RECOMMENDATIONS) {
            Log.d(TAG, "Returning top " + MAX_RECOMMENDATIONS + " recommendations");
            limitedResults = new ArrayList<>(results.subList(0, MAX_RECOMMENDATIONS));
        } else {
            Log.d(TAG, "Returning " + results.size() + " recommendations");
        }

        return new RecommendationResult(limitedResults, conflictDays);
    }

    private List<DayOfWeek> findFixedLectureFreeDayConflicts(List<Lecture> allLectures,
                                                             RecommendationRequest request) {
        List<DayOfWeek> conflicts = new ArrayList<>();

        if (allLectures == null
                || request == null
                || request.getSoftConstraint() == null
                || request.getSoftConstraint().isSkipped()
                || request.getSoftConstraint().getPreferredFreeDays() == null
                || request.getSoftConstraint().getPreferredFreeDays().isEmpty()
                || request.getFixedLectureKeys() == null
                || request.getFixedLectureKeys().isEmpty()) {
            return conflicts;
        }

        Set<DayOfWeek> fixedLectureDays = new HashSet<>();

        for (Lecture lecture : allLectures) {
            if (lecture == null) {
                continue;
            }

            String key = RequiredLectureConstraint.buildLectureKey(lecture);
            if (!request.getFixedLectureKeys().contains(key)) {
                continue;
            }

            if (lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime time : lecture.getTimes()) {
                if (time != null && time.getDay() != null) {
                    fixedLectureDays.add(time.getDay());
                }
            }
        }

        for (DayOfWeek preferred : request.getSoftConstraint().getPreferredFreeDays()) {
            if (preferred != null && fixedLectureDays.contains(preferred) && !conflicts.contains(preferred)) {
                conflicts.add(preferred);
            }
        }

        return conflicts;
    }

    private RecommendationRequest createRequestWithoutConflictedFreeDays(RecommendationRequest original,
                                                                         List<DayOfWeek> conflictDays) {
        if (original == null || conflictDays == null || conflictDays.isEmpty()) {
            return original;
        }

        SoftConstraint originalSoft = original.getSoftConstraint();
        if (originalSoft == null) {
            return original;
        }

        List<DayOfWeek> adjustedFreeDays = new ArrayList<>();
        if (originalSoft.getPreferredFreeDays() != null) {
            for (DayOfWeek day : originalSoft.getPreferredFreeDays()) {
                if (day != null && !conflictDays.contains(day)) {
                    adjustedFreeDays.add(day);
                }
            }
        }

        SoftConstraint adjustedSoft = new SoftConstraint(
                originalSoft.isSkipped(),
                adjustedFreeDays,
                originalSoft.getFreeTimePreference(),
                originalSoft.isKeepLunch12To13Free(),
                originalSoft.isAvoidGapOver3Hours(),
                originalSoft.getPreferredProfessors() == null
                        ? new ArrayList<>()
                        : new ArrayList<>(originalSoft.getPreferredProfessors()),
                originalSoft.isConsiderTravelTime(),
                originalSoft.getBlockedTimes() == null
                        ? new ArrayList<>()
                        : new ArrayList<>(originalSoft.getBlockedTimes())
        );

        return new RecommendationRequest(
                original.getMinCredits(),
                original.getMaxCredits(),
                original.getFixedLectureKeys(),
                original.getCompletedCourseCodes(),
                adjustedSoft,
                original.getUserGrade(),
                original.getStudentId()
        );
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
            if (lecture.getGeneralArea() == null) continue;

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

    private int countPreferredFreeDayViolations(Timetable timetable, RecommendationRequest request) {
        if (timetable == null
                || request == null
                || request.getSoftConstraint() == null
                || request.getSoftConstraint().isSkipped()
                || request.getSoftConstraint().getPreferredFreeDays() == null
                || request.getSoftConstraint().getPreferredFreeDays().isEmpty()) {
            return 0;
        }

        Set<DayOfWeek> lectureDays = new HashSet<>();

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime time : lecture.getTimes()) {
                if (time != null && time.getDay() != null) {
                    lectureDays.add(time.getDay());
                }
            }
        }

        int violations = 0;
        for (DayOfWeek preferredFreeDay : request.getSoftConstraint().getPreferredFreeDays()) {
            if (preferredFreeDay != null && lectureDays.contains(preferredFreeDay)) {
                violations++;
            }
        }

        return violations;
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