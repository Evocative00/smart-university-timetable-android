package com.syu.smarttimetable.data.source.local;

import com.syu.smarttimetable.constants.LectureCategory;
import com.syu.smarttimetable.model.Lecture;

import java.util.ArrayList;
import java.util.List;

public class DummyLectureDataSource {

    public List<Lecture> getLectures() {
        List<Lecture> lectures = new ArrayList<>();

        lectures.add(new Lecture("모바일프로그래밍", "김관우", 3,
                "컴퓨터공학부", 3, LectureCategory.MAJOR, "수", 11, 14));

        lectures.add(new Lecture("기계학습", "왕수현", 3,
                "컴퓨터공학부", 4, LectureCategory.MAJOR, "화", 9, 12));

        lectures.add(new Lecture("글쓰기", "박교수", 2,
                "공통", 1, LectureCategory.LIBERAL, "수", 9, 11));

        return lectures;
    }
}