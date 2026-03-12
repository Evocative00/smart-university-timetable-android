package com.syu.smarttimetable.data.repository;

import com.syu.smarttimetable.data.source.local.DummyLectureDataSource;
import com.syu.smarttimetable.model.Lecture;

import java.util.List;

public class LectureRepository {

    private final DummyLectureDataSource dummyLectureDataSource;

    public LectureRepository() {
        dummyLectureDataSource = new DummyLectureDataSource();
    }

    public List<Lecture> getLectures() {
        return dummyLectureDataSource.getLectures();
    }
}