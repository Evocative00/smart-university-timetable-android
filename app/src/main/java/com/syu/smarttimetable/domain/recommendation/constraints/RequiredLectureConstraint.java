package com.syu.smarttimetable.domain.recommendation.constraints;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.domain.recommendation.RecommendationUserInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 고정 과목 검사 클래스
// 사용자가 반드시 포함해달라고 선택한 과목들이 시간표에 모두 들어있는지 판단함
public class RequiredLectureConstraint implements RecommendationConstraint {

    @Override
    public boolean isValid(List<Lecture> timetable, RecommendationUserInfo userInfo) {
        return containsAllFixedLectures(timetable, userInfo.getFixedLectureKeys());
    }

    @Override
    public String getErrorMessage() {
        return "고정 과목이 누락되었습니다.";
    }

    // 고정 과목 포함 검사
    public boolean containsAllFixedLectures(List<Lecture> timetable, Set<String> fixedLectureKeys) {

        // 고정 과목이 없으면 검사할 필요 없음
        if (fixedLectureKeys == null || fixedLectureKeys.isEmpty()) {
            return true;
        }

        // 시간표에 들어있는 강의들을 key 형태로 저장. 객체 비교보다는 문자열 key로 변환해서 비교
        Set<String> included = new HashSet<String>();

        for (Lecture lecture : timetable) {
            included.add(buildLectureKey(lecture));
        }

        // fixedLectureKeys가 전부 포함되어 있는지 확인
        return included.containsAll(fixedLectureKeys);
    }

    // 강의 식별용 key 생성
    public static String buildLectureKey(Lecture lecture) {

        // 과목코드 + 과목명 + 교수로 고유하게 식별
        return lecture.getCourseCode() + "|"
                + lecture.getCourseName() + "|"
                + lecture.getProfessor();
    }
}