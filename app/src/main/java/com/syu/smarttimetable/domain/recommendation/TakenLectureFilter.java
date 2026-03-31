package com.syu.smarttimetable.domain.recommendation;
import com.syu.smarttimetable.data.model.Lecture;
import java.util.ArrayList;
import java.util.List;
public class TakenLectureFilter {
    public List<Lecture> filterCompletedLectures(List<Lecture> allLectures, List<String> completedCourseCodes) {
        List<Lecture> filteredList = new ArrayList<>();
        for (Lecture lecture : allLectures) {
            if (completedCourseCodes == null || !completedCourseCodes.contains(lecture.getCourseCode())) {
                filteredList.add(lecture);
            }
        }
        return filteredList;
    }
}
