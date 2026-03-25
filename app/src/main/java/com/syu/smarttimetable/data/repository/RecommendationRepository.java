package com.syu.smarttimetable.data.repository;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.UserPreference;

import java.util.ArrayList;
import java.util.List;

public class RecommendationRepository {

    private final LectureRepository lectureRepository;

    public RecommendationRepository() {
        this.lectureRepository = new LectureRepository();
    }

    public List<Lecture> recommend(UserPreference preference) {
        List<Lecture> allLectures = lectureRepository.getAllLectures();
        List<Lecture> result = new ArrayList<>();

        for (Lecture lecture : allLectures) {
            if (preference.getPreferredGrade() != 0 &&
                    lecture.getGrade() != preference.getPreferredGrade()) {
                continue;
            }

            if (preference.getPreferredMajor() != null &&
                    !preference.getPreferredMajor().isEmpty() &&
                    !lecture.getMajor().name().equals(preference.getPreferredMajor())) {
                continue;
            }

            result.add(lecture);
        }

        return result;
    }
}