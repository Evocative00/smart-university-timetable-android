package com.syu.smarttimetable.domain.timetable;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.ArrayList;
import java.util.List;

public class TimetableGenerator {

    private static final int MAX_RESULTS = 100; // Cap to avoid OOM or slow execution

    public List<Timetable> generateCandidateTimetables(List<Lecture> filteredLectures, RecommendationRequest request) {
        List<Timetable> results = new ArrayList<>();
        
        // Find fixed lectures and non-fixed lectures
        List<Lecture> fixedLectures = new ArrayList<>();
        List<Lecture> poolLectures = new ArrayList<>();
        
        for (Lecture lecture : filteredLectures) {
            if (request.getFixedLectureKeys() != null && request.getFixedLectureKeys().contains(lecture.getCourseCode())) {
                fixedLectures.add(lecture);
            } else {
                poolLectures.add(lecture);
            }
        }
        
        Timetable current = new Timetable();
        int currentCredits = 0;
        
        // Add fixed lectures
        boolean conflictInFixed = false;
        for (Lecture fixed : fixedLectures) {
            if (!current.hasConflict(fixed)) {
                current.addLecture(fixed);
                currentCredits += fixed.getCredits();
            } else {
                conflictInFixed = true;
                break;
            }
        }
        
        if (conflictInFixed || currentCredits > request.getMaxCredits()) {
            return results; // Return empty if fixed lectures themselves have conflict
        }
        
        backtrack(poolLectures, 0, current, request, results);
        
        return results;
    }
    
    private void backtrack(List<Lecture> pool, int index, Timetable current, RecommendationRequest request, List<Timetable> results) {
        if (results.size() >= MAX_RESULTS) return;
        
        int credits = current.getTotalCredits();
        if (credits >= request.getMinCredits() && credits <= request.getMaxCredits()) {
            results.add(new Timetable(current.getLectures()));
        }
        
        if (credits >= request.getMaxCredits()) {
            return;
        }
        
        for (int i = index; i < pool.size(); i++) {
            Lecture nextLecture = pool.get(i);
            
            if (current.hasConflict(nextLecture)) continue;
            if (credits + nextLecture.getCredits() > request.getMaxCredits()) continue;
            
            current.addLecture(nextLecture);
            backtrack(pool, i + 1, current, request, results);
            current.removeLecture(nextLecture);
        }
    }
}
