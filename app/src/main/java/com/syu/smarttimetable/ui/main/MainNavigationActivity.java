package com.syu.smarttimetable.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.common.utils.ProfilePhotoManager;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;
import com.syu.smarttimetable.ui.calendar.CalendarFragment;
import com.syu.smarttimetable.ui.constraint.ConstraintFragment;
import com.syu.smarttimetable.ui.profile.ProfileFragment;
import com.syu.smarttimetable.ui.school.SchoolHomeFragment;
import com.syu.smarttimetable.ui.timetable.TimetableFragment;

import java.io.File;

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
        updateProfileIcon();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateProfileIcon();
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

    public void updateProfileIcon() {
        android.view.MenuItem profileItem = bottomNavigation.getMenu().findItem(R.id.nav_profile);
        if (profileItem == null) return;

        String path = ProfilePhotoManager.getPhotoPath(this);
        if (path == null) {
            profileItem.setIcon(R.drawable.ic_nav_profile);
            return;
        }

        File file = new File(path);
        if (!file.exists()) {
            profileItem.setIcon(R.drawable.ic_nav_profile);
            return;
        }

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap == null) {
            profileItem.setIcon(R.drawable.ic_nav_profile);
            return;
        }

        int size = (int) (36 * getResources().getDisplayMetrics().density);
        Drawable circular = createCircularDrawable(bitmap, size);
        profileItem.setIcon(circular);

        // Prevent Android framework-level tinting from distorting the selected photo.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            profileItem.setIconTintList(null);
        }
    }

    private Drawable createCircularDrawable(Bitmap source, int size) {
        Bitmap scaled = Bitmap.createScaledBitmap(source, size, size, true);
        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(scaled, 0, 0, paint);
        return new BitmapDrawable(getResources(), output);
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
