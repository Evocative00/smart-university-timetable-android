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
            // 충돌하는 요일이 발견되었을 때 (RecommendationEngine.java 87번째 줄 부근)
            if (!conflicts.isEmpty()) {
                Log.d(TAG, "Fixed lecture vs preferred free day conflict: " + conflicts + ". Removing conflicted days from preference.");

                // 강제로 빈 리스트를 반환하지 않고, 충돌하는 요일만 선호 공강 리스트에서 제거 (양보)
                request.getSoftConstraint().getPreferredFreeDays().removeAll(conflicts);

                // 그대로 추천 프로세스 계속 진행
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
        int rejectedByHardConstraint = 0;
        int rejectedByDuplicate = 0;

        for (Timetable candidate : candidates) {
            if (candidate == null) {
                continue;
            }

            HardConstraintValidator.ValidationResult validationResult =
                    hardConstraintValidator.validate(candidate.getLecturesReadOnly(), request);

            if (!validationResult.isValid()) {
                rejectedByHardConstraint++;
                Log.d(TAG, "Candidate rejected by hard constraint: " + validationResult.getErrors());
                continue;
            }

            // Enforce rule: for the five general areas, at most one lecture per area is allowed in a recommendation
            if (hasExcessGeneralAreaLectures(candidate)) {
                Log.d(TAG, "Candidate rejected: exceeds per-area general lecture limit");
                continue;
            }

            // Preferred free days is now handled as a soft constraint (score-based penalty)
            // in PreferFreeDayRule, not as a hard constraint. This allows timetables with
            // lectures on preferred free days to be generated if necessary, but with lower scores.

            String signature = buildTimetableSignature(candidate);

            if (!seenTimetableSignatures.add(signature)) {
                rejectedByDuplicate++;
                Log.d(TAG, "Duplicate candidate skipped: " + signature);
                continue;
            }

            int score = scoreCalculator.calculateScore(candidate, request);
            results.add(new TimetableScoreTuple(candidate, score));
        }

        Log.d(TAG, "Valid recommendations after filtering: " + results.size());
        Log.d(TAG, "Rejected by hard constraint: " + rejectedByHardConstraint + ", by duplicate: " + rejectedByDuplicate);

        results.sort((first, second) -> {
            int firstFreeDayViolations = countPreferredFreeDayViolations(first.getTimetable(), request);
            int secondFreeDayViolations = countPreferredFreeDayViolations(second.getTimetable(), request);

            // 1) 선호 공강 요일 만족도를 최우선으로 비교 (공강을 완벽히 만족하는 것이 최고 우선)
            // 공강이 설정되지 않으면 이 값은 0이므로, 차이가 있을 때만 우선순위에 영향
            int freeDayViolationCompare = Integer.compare(firstFreeDayViolations, secondFreeDayViolations);
            if (freeDayViolationCompare != 0) {
                Log.d(TAG, "Sorting by free day: first violations=" + firstFreeDayViolations
                        + ", second violations=" + secondFreeDayViolations);
                return freeDayViolationCompare;
            }

            // 공강이 같으면, 점수로 비교 (점수가 공강 만족 여부를 반영)
            int scoreCompare = Integer.compare(second.getScore(), first.getScore());
            if (scoreCompare != 0) {
                return scoreCompare;
            }

            int firstGradeMajorCount = countGradeMatchedMajors(first.getTimetable(), request);
            int secondGradeMajorCount = countGradeMatchedMajors(second.getTimetable(), request);

            int gradeMajorCompare = Integer.compare(secondGradeMajorCount, firstGradeMajorCount);
            if (gradeMajorCompare != 0) {
                return gradeMajorCompare;
            }


            return Integer.compare(
                    second.getTimetable().getTotalCredits(),
                    first.getTimetable().getTotalCredits()
            );
        });


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
            if (lectureDays.contains(preferredFreeDay)) {
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