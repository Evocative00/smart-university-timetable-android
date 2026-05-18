package com.syu.smarttimetable.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;
import com.syu.smarttimetable.ui.calendar.CalendarFragment;
import com.syu.smarttimetable.ui.constraint.ConstraintFragment;
import com.syu.smarttimetable.ui.profile.ProfileFragment;
import com.syu.smarttimetable.ui.school.SchoolHomeFragment;
import com.syu.smarttimetable.ui.timetable.TimetableFragment;

public class MainNavigationActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private RecommendationRequest lastRecommendationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);

        bottomNavigation = findViewById(R.id.bottom_navigation);

        if (getIntent() != null) {
            lastRecommendationRequest =
                    (RecommendationRequest) getIntent().getSerializableExtra("recommendationRequest");
        }

        if (savedInstanceState == null) {
            loadFragment(new TimetableFragment());
            bottomNavigation.setSelectedItemId(R.id.nav_timetable);
        }

        setupNavListener();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent != null) {
            RecommendationRequest req =
                    (RecommendationRequest) intent.getSerializableExtra("recommendationRequest");
            if (req != null) {
                lastRecommendationRequest = req;
            }
        }
        // 이미 선택된 탭이면 리스너가 발동 안 되므로 직접 fragment 교체
        loadFragment(new TimetableFragment());
        bottomNavigation.setOnItemSelectedListener(null);
        bottomNavigation.setSelectedItemId(R.id.nav_timetable);
        setupNavListener();
    }

    private void setupNavListener() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_timetable) {
                loadFragment(new TimetableFragment());
                return true;
            } else if (id == R.id.nav_calendar) {
                loadFragment(new CalendarFragment());
                return true;
            } else if (id == R.id.nav_school) {
                loadFragment(new SchoolHomeFragment());
                return true;
            } else if (id == R.id.nav_constraint) {
                loadFragment(new ConstraintFragment());
                return true;
            } else if (id == R.id.nav_profile) {
                loadFragment(new ProfileFragment());
                return true;
            }
            return false;
        });
    }

    public RecommendationRequest getLastRecommendationRequest() {
        return lastRecommendationRequest;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
