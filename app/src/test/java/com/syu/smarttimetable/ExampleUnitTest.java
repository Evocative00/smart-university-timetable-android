package com.syu.smarttimetable;

import org.junit.Test;
import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.enums.*;

import java.util.ArrayList;
import java.util.List;

public class ExampleUnitTest {
    @Test
    public void testTimetableSerialization() {
        List<LectureTime> times = new ArrayList<>();
        times.add(new LectureTime(DayOfWeek.MONDAY, 540, 600));
        
        Lecture lecture = new Lecture(
            "CS101",
            "Intro to CS",
            "Dr. Smith",
            3,
            Department.COMPUTER_SCIENCE,
            MajorType.NONE,
            1,
            ClassParity.ALL,
            "Room 101",
            CourseCategory.MAJOR,
            RequirementType.REQUIRED,
            GeneralArea.NONE,
            times
        );
        
        Timetable timetable = new Timetable();
        timetable.addLecture(lecture);
        
        Gson gson = new Gson();
        String json = gson.toJson(timetable);
        System.out.println("Serialized JSON: " + json);
        
        Timetable deserialized = gson.fromJson(json, Timetable.class);
        assertNotNull(deserialized);
        assertEquals(1, deserialized.getLectures().size());
        assertEquals("Intro to CS", deserialized.getLectures().get(0).getCourseName());
    }
}