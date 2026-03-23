package com.syu.smarttimetable.ui.main;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.UserPreference;
import com.syu.smarttimetable.data.repository.RecommendationRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RecommendationTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("MainActivityTest", "MainActivity onCreate 실행됨");

        testRecommendation();
    }

    private void testRecommendation() {
        Log.e("RecommendationTest", "testRecommendation 시작");

        RecommendationRepository repository = new RecommendationRepository();

        UserPreference preference = new UserPreference(
                0,
                "",
                null
        );

        List<Lecture> lectures = repository.recommend(preference);

        Log.e("RecommendationTest", "추천 결과 개수: " + lectures.size());

        for (Lecture lecture : lectures) {
            Log.e("RecommendationTest", "과목명: " + lecture.getCourseName());
        }
    }
}