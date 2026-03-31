package com.syu.smarttimetable.domain.recommendation;
import java.util.List;
public class RecommendationRequest {
    private int minCredits;
    private int maxCredits;
    private List<String> fixedLectureKeys;
    private List<String> completedCourseCodes;
    public RecommendationRequest(int minCredits, int maxCredits, List<String> fixedLectureKeys, List<String> completedCourseCodes) {
        this.minCredits = minCredits;
        this.maxCredits = maxCredits;
        this.fixedLectureKeys = fixedLectureKeys;
        this.completedCourseCodes = completedCourseCodes;
    }
    public int getMinCredits() { return minCredits; }
    public int getMaxCredits() { return maxCredits; }
    public List<String> getFixedLectureKeys() { return fixedLectureKeys; }
    public List<String> getCompletedCourseCodes() { return completedCourseCodes; }
}
