package com.syu.smarttimetable.ui.constraint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.syu.smarttimetable.R;
import com.syu.smarttimetable.common.utils.ConstraintStateManager;
import com.syu.smarttimetable.data.model.HardConstraint;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.SoftConstraint;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.data.model.enums.FreeTimePreference;
import com.syu.smarttimetable.data.repository.LectureRepository;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;
import com.syu.smarttimetable.domain.recommendation.constraints.RequiredLectureConstraint;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SoftConstraintActivity extends AppCompatActivity {

    private HardConstraint hardConstraint;
    private int userGrade = 0;
    private String studentId = "";
    private LectureRepository lectureRepository;

    private CheckBox checkboxMonday;
    private CheckBox checkboxTuesday;
    private CheckBox checkboxWednesday;
    private CheckBox checkboxThursday;
    private CheckBox checkboxFriday;
    private CheckBox checkboxNoneDay;
    private RadioGroup radioGroupFreeTime;
    private CheckBox checkboxLunch;
    private CheckBox checkboxAvoidLongGap;
    private Button buttonSkip;
    private Button buttonRecommend;
    private Button buttonResetSoft;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_constraint);

        hardConstraint = (HardConstraint) getIntent().getSerializableExtra("hardConstraint");
        userGrade = getIntent().getIntExtra("userGrade", 0);
        studentId = getIntent().getStringExtra("studentId");

        if (studentId == null) {
            studentId = "";
        }

        lectureRepository = new LectureRepository();

        bindViews();
        setupButtons();
        restoreSavedState();
    }

    private void bindViews() {
        checkboxMonday = findViewById(R.id.checkboxMonday);
        checkboxTuesday = findViewById(R.id.checkboxTuesday);
        checkboxWednesday = findViewById(R.id.checkboxWednesday);
        checkboxThursday = findViewById(R.id.checkboxThursday);
        checkboxFriday = findViewById(R.id.checkboxFriday);
        checkboxNoneDay = findViewById(R.id.checkboxNoneDay);
        radioGroupFreeTime = findViewById(R.id.radioGroupFreeTime);
        checkboxLunch = findViewById(R.id.checkboxLunch);
        checkboxAvoidLongGap = findViewById(R.id.checkboxAvoidLongGap);
        buttonSkip = findViewById(R.id.buttonSkip);
        buttonRecommend = findViewById(R.id.buttonRecommend);
        buttonResetSoft = findViewById(R.id.btn_reset_soft);
        btnBack = findViewById(R.id.btn_back);
    }

    private void setupButtons() {
        btnBack.setOnClickListener(v -> onBackPressed());
        buttonResetSoft.setOnClickListener(v -> showResetConfirmDialog());

        buttonSkip.setOnClickListener(v -> {
            SoftConstraint softConstraint = new SoftConstraint();
            softConstraint.setSkipped(true);
            saveSoftConstraintState(softConstraint);
            navigateToMain(softConstraint);
        });

        buttonRecommend.setOnClickListener(v -> {
            SoftConstraint softConstraint = collectConstraintFromUi();
            saveSoftConstraintState(softConstraint);
            navigateToMain(softConstraint);
        });

        setupNoneDayMutualExclusion();
    }

    private void setupNoneDayMutualExclusion() {
        CheckBox[] dayCheckboxes = {checkboxMonday, checkboxTuesday, checkboxWednesday,
                checkboxThursday, checkboxFriday};

        checkboxNoneDay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                for (CheckBox cb : dayCheckboxes) {
                    cb.setChecked(false);
                }
            }
        });

        for (CheckBox cb : dayCheckboxes) {
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    checkboxNoneDay.setChecked(false);
                }
            });
        }
    }

    private void showResetConfirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_reset_title)
                .setMessage(R.string.dialog_reset_message)
                .setPositiveButton(R.string.dialog_reset_confirm, (dialog, which) -> {
                    ConstraintStateManager.clearSoftConstraintState(this);
                    resetSoftConstraintUi();
                    Toast.makeText(this, "선호 조건이 초기화되었습니다.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.dialog_reset_cancel, null)
                .show();
    }

    private void resetSoftConstraintUi() {
        checkboxMonday.setChecked(false);
        checkboxTuesday.setChecked(false);
        checkboxWednesday.setChecked(false);
        checkboxThursday.setChecked(false);
        checkboxFriday.setChecked(false);
        checkboxNoneDay.setChecked(false);
        radioGroupFreeTime.check(R.id.radioNone);
        checkboxLunch.setChecked(false);
        checkboxAvoidLongGap.setChecked(false);
    }

    private void restoreSavedState() {
        resetSoftConstraintUi();

        List<String> savedDays = ConstraintStateManager.getSavedPreferredDays(this);
        checkboxMonday.setChecked(savedDays.contains(DayOfWeek.MONDAY.name()));
        checkboxTuesday.setChecked(savedDays.contains(DayOfWeek.TUESDAY.name()));
        checkboxWednesday.setChecked(savedDays.contains(DayOfWeek.WEDNESDAY.name()));
        checkboxThursday.setChecked(savedDays.contains(DayOfWeek.THURSDAY.name()));
        checkboxFriday.setChecked(savedDays.contains(DayOfWeek.FRIDAY.name()));

        String savedFreeTime = ConstraintStateManager.getSavedFreeTime(this);
        if (FreeTimePreference.MORNING.name().equals(savedFreeTime)) {
            radioGroupFreeTime.check(R.id.radioMorning);
        } else if (FreeTimePreference.AFTERNOON.name().equals(savedFreeTime)) {
            radioGroupFreeTime.check(R.id.radioAfternoon);
        }

        checkboxLunch.setChecked(ConstraintStateManager.getSavedLunch(this));
        checkboxAvoidLongGap.setChecked(ConstraintStateManager.getSavedAvoidGap(this));
    }

    private SoftConstraint collectConstraintFromUi() {
        List<DayOfWeek> preferredDays = new ArrayList<>();

        if (!checkboxNoneDay.isChecked()) {
            if (checkboxMonday.isChecked()) preferredDays.add(DayOfWeek.MONDAY);
            if (checkboxTuesday.isChecked()) preferredDays.add(DayOfWeek.TUESDAY);
            if (checkboxWednesday.isChecked()) preferredDays.add(DayOfWeek.WEDNESDAY);
            if (checkboxThursday.isChecked()) preferredDays.add(DayOfWeek.THURSDAY);
            if (checkboxFriday.isChecked()) preferredDays.add(DayOfWeek.FRIDAY);
        }

        FreeTimePreference freeTimePreference;
        int checkedId = radioGroupFreeTime.getCheckedRadioButtonId();

        if (checkedId == R.id.radioMorning) {
            freeTimePreference = FreeTimePreference.MORNING;
        } else if (checkedId == R.id.radioAfternoon) {
            freeTimePreference = FreeTimePreference.AFTERNOON;
        } else {
            freeTimePreference = FreeTimePreference.NONE;
        }

        return new SoftConstraint(
                false,
                preferredDays,
                freeTimePreference,
                checkboxLunch.isChecked(),
                checkboxAvoidLongGap.isChecked(),
                new ArrayList<>(),
                false
        );
    }

    private void saveSoftConstraintState(SoftConstraint softConstraint) {
        if (softConstraint == null) {
            return;
        }

        List<String> preferredDays = new ArrayList<>();
        if (softConstraint.getPreferredFreeDays() != null) {
            for (DayOfWeek day : softConstraint.getPreferredFreeDays()) {
                if (day != null) {
                    preferredDays.add(day.name());
                }
            }
        }

        String freeTime = softConstraint.getFreeTimePreference() == null
                ? FreeTimePreference.NONE.name()
                : softConstraint.getFreeTimePreference().name();

        ConstraintStateManager.saveSoftConstraintState(
                this,
                preferredDays,
                freeTime,
                softConstraint.isKeepLunch12To13Free(),
                softConstraint.isAvoidGapOver3Hours(),
                "",
                false
        );
    }

    @Override
    public void onBackPressed() {
        saveSoftConstraintState(collectConstraintFromUi());
        super.onBackPressed();
    }

    private void navigateToMain(SoftConstraint softConstraint) {
        int targetCredits = hardConstraint != null ? hardConstraint.getTargetCredits() : 0;
        int minCredits = Math.max(0, targetCredits - 1);
        int maxCredits = targetCredits + 1;

        HashSet<String> fixedLectureKeySet = new HashSet<>();

        if (hardConstraint != null && hardConstraint.getFixedLectureKeys() != null) {
            fixedLectureKeySet.addAll(hardConstraint.getFixedLectureKeys());
        }

        addRequiredChapelIfNeeded(fixedLectureKeySet);

        HashSet<String> completedCourseCodes = new HashSet<>();

        if (hardConstraint != null && hardConstraint.getCompletedCourseCodes() != null) {
            completedCourseCodes.addAll(hardConstraint.getCompletedCourseCodes());
        }

        RecommendationRequest recommendationRequest = new RecommendationRequest(
                minCredits,
                maxCredits,
                fixedLectureKeySet,
                completedCourseCodes,
                softConstraint,
                userGrade,
                studentId
        );

        Intent intent = new Intent(this, com.syu.smarttimetable.ui.main.MainNavigationActivity.class);
        intent.putExtra("recommendationRequest", recommendationRequest);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void addRequiredChapelIfNeeded(HashSet<String> fixedLectureKeySet) {
        if (fixedLectureKeySet == null || userGrade <= 0 || lectureRepository == null) {
            return;
        }

        List<Lecture> lectures = lectureRepository.getAllLectures();

        if (lectures == null || lectures.isEmpty()) {
            return;
        }

        for (Lecture lecture : lectures) {
            if (lecture == null) {
                continue;
            }

            String courseName = lecture.getCourseName();

            if (courseName == null) {
                continue;
            }

            boolean isChapel = isChapelCourse(courseName);

            if (isChapel && lecture.getGrade() == userGrade) {
                fixedLectureKeySet.add(RequiredLectureConstraint.buildLectureKey(lecture));
                return;
            }
        }
    }

    private boolean isChapelCourse(String courseName) {
        if (courseName == null) {
            return false;
        }

        String normalized = courseName.trim().toLowerCase();
        return normalized.startsWith("채플") || normalized.contains("chapel");
    }

}
