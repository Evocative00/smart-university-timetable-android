package com.syu.smarttimetable.data.repository;

import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.source.local.DummyLectureDataSource;

import java.util.List;

public class LectureRepository {

    public List<Lecture> getAllLectures() {
        return DummyLectureDataSource.getLectures();
    }
}