package com.syu.smarttimetable.domain.recommendation;

import com.syu.smarttimetable.data.model.Lecture;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// 강의 필터 클래스
// 추천 전에 이미 들은 과목을 제거하는 역할
public class LectureFilter {

    // 이미 들은 과목 제거
    public static List<Lecture> filterCompletedLectures(List<Lecture> allLectures,
                                                        Set<String> completedCourseCodes) {

        List<Lecture> result = new ArrayList<Lecture>();

        // 전체 강의가 없으면 빈 리스트 반환
        if (allLectures == null || allLectures.isEmpty()) {
            return result;
        }

        // 모든 강의를 하나씩 확인
        for (Lecture lecture : allLectures) {

            // 이미 들은 과목이면 추천에서 제외
            if (completedCourseCodes != null
                    && completedCourseCodes.contains(lecture.getCourseCode())) {
                continue;
            }

            // 아직 듣지 않은 과목이면 결과에 추가
            result.add(lecture);
        }

        return result;
    }
}