package com.syu.smarttimetable.data.source.local;

import com.syu.smarttimetable.constants.LectureCategory;
import com.syu.smarttimetable.model.Lecture;

import java.util.ArrayList;
import java.util.List;

public class DummyLectureDataSource {

    public List<Lecture> getLectures() {
        List<Lecture> lectures = new ArrayList<>();

        lectures.add(new Lecture("모바일프로그래밍", "김교수", 3,
                "컴퓨터공학과", 3, LectureCategory.MAJOR, "월", 10, 12));

        lectures.add(new Lecture("소프트웨어공학", "이교수", 3,
                "컴퓨터공학과", 3, LectureCategory.MAJOR, "화", 13, 15));

        lectures.add(new Lecture("글쓰기", "박교수", 2,
                "공통", 1, LectureCategory.LIBERAL, "수", 9, 11));

        return lectures;
    }
}