package com.syu.smarttimetable.domain.recommendation;

import com.syu.smarttimetable.data.model.LectureTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 사용자 입력값 묶음 클래스
// 추천 시간표 생성과 검증에 필요한 사용자 조건들을 담음
public class RecommendationUserInfo {

    private final int desiredCredits; // 사용자가 원하는 총 학점(18~21)
    private final List<LectureTime> blockedTimes; // 절대 안 되는 시간
    private final Set<String> fixedLectureKeys; // 반드시 포함해야 하는 고정 과목, 왜 key 형식이냐면 더 쉽게 하기 위함
    private final Set<String> completedCourseCodes; // 이미 수강한 과목 코드

    public RecommendationUserInfo(int desiredCredits,
                                  List<LectureTime> blockedTimes,
                                  Set<String> fixedLectureKeys,
                                  Set<String> completedCourseCodes) {
        this.desiredCredits = desiredCredits;
        this.blockedTimes = blockedTimes != null
                ? new ArrayList<LectureTime>(blockedTimes)  // null이 아니면 복사해서 저장
                : new ArrayList<LectureTime>();     // null이면 빈 리스트 생성
        this.fixedLectureKeys = fixedLectureKeys != null
                ? new HashSet<String>(fixedLectureKeys)  // null이 아니면 복사해서 저장
                : new HashSet<String>();            // null이면 빈 리스트 생성
        this.completedCourseCodes = completedCourseCodes != null
                ? new HashSet<String>(completedCourseCodes) // null이 아니면 복사해서 저장
                : new HashSet<String>();            // null이면 빈 리스트 생성
    }

    public int getDesiredCredits() {    //희망 학점 반환
        return desiredCredits;
    }

    public List<LectureTime> getBlockedTimes() {    // 불가능한 시간 반환
        return new ArrayList<LectureTime>(blockedTimes);
    }

    public Set<String> getFixedLectureKeys() {  // 고정 과목 반환
        return new HashSet<String>(fixedLectureKeys);
    }

    public Set<String> getCompletedCourseCodes() {  // 이미 들은 과목 반환
        return new HashSet<String>(completedCourseCodes);
    }
}