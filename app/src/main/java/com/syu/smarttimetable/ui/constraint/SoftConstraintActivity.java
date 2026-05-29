package com.syu.smarttimetable.ui.constraint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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
    private ChipGroup chipGroupFreeTime;
    private CheckBox checkboxLunch;
    private CheckBox checkboxAvoidLongGap;
    private EditText editPreferredProfessors;
    private CheckBox checkboxTravelTime;
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
        chipGroupFreeTime = findViewById(R.id.chipGroupFreeTime);
        checkboxLunch = findViewById(R.id.checkboxLunch);
        checkboxAvoidLongGap = findViewById(R.id.checkboxAvoidLongGap);
        editPreferredProfessors = findViewById(R.id.editPreferredProfessors);
        checkboxTravelTime = findViewById(R.id.checkboxTravelTime);
        buttonSkip = findViewById(R.id.buttonSkip);
        buttonRecommend = findViewById(R.id.buttonRecommend);
        buttonResetSoft = findViewById(R.id.btn_reset_soft);
        btnBack = findViewById(R.id.btn_back);
    }

    private void setupButtons() {
        btnBack.setOnClickListener(v -> onBackPressed());
        buttonResetSoft.setOnClickListener(v -> {

            new MaterialAlertDialogBuilder(SoftConstraintActivity.this)
                    .setTitle("선택 조건 초기화")
                    .setMessage("정말 선택 조건을 초기화하시겠습니까?")
                    .setPositiveButton("초기화", (dialog, which) -> {
                        resetSoftConstraintState();
                    })
                    .setNegativeButton("취소", null)
                    .show();
        });

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
    }

    private void resetSoftConstraintState() {
        ConstraintStateManager.clearSoftConstraintState(this);
        resetSoftConstraintUi();
        Toast.makeText(this, "선택 조건이 초기화되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private void resetSoftConstraintUi() {
        checkboxMonday.setChecked(false);
        checkboxTuesday.setChecked(false);
        checkboxWednesday.setChecked(false);
        checkboxThursday.setChecked(false);
        checkboxFriday.setChecked(false);
        chipGroupFreeTime.check(R.id.chipNone);
        checkboxLunch.setChecked(false);
        checkboxAvoidLongGap.setChecked(false);
        editPreferredProfessors.setText("");
        checkboxTravelTime.setChecked(false);
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
            chipGroupFreeTime.check(R.id.chipMorning);
        } else if (FreeTimePreference.AFTERNOON.name().equals(savedFreeTime)) {
            chipGroupFreeTime.check(R.id.chipAfternoon);
        }

        checkboxLunch.setChecked(ConstraintStateManager.getSavedLunch(this));
        checkboxAvoidLongGap.setChecked(ConstraintStateManager.getSavedAvoidGap(this));
        editPreferredProfessors.setText(ConstraintStateManager.getSavedProfessors(this));
        checkboxTravelTime.setChecked(ConstraintStateManager.getSavedTravelTime(this));
    }

    private SoftConstraint collectConstraintFromUi() {
        List<DayOfWeek> preferredDays = new ArrayList<>();

        if (checkboxMonday.isChecked()) {
            preferredDays.add(DayOfWeek.MONDAY);
        }

        if (checkboxTuesday.isChecked()) {
            preferredDays.add(DayOfWeek.TUESDAY);
        }

        if (checkboxWednesday.isChecked()) {
            preferredDays.add(DayOfWeek.WEDNESDAY);
        }

        if (checkboxThursday.isChecked()) {
            preferredDays.add(DayOfWeek.THURSDAY);
        }

        if (checkboxFriday.isChecked()) {
            preferredDays.add(DayOfWeek.FRIDAY);
        }

        FreeTimePreference freeTimePreference;
        int checkedId = chipGroupFreeTime.getCheckedChipId();

        if (checkedId == R.id.chipMorning) {
            freeTimePreference = FreeTimePreference.MORNING;
        } else if (checkedId == R.id.chipAfternoon) {
            freeTimePreference = FreeTimePreference.AFTERNOON;
        } else {
            freeTimePreference = FreeTimePreference.NONE;
        }

        List<String> professors = parseProfessorInput(editPreferredProfessors.getText().toString());

        return new SoftConstraint(
                false,
                preferredDays,
                freeTimePreference,
                checkboxLunch.isChecked(),
                checkboxAvoidLongGap.isChecked(),
                professors,
                checkboxTravelTime.isChecked()
        );
    }

    private List<String> parseProfessorInput(String input) {
        List<String> result = new ArrayList<>();

        if (input == null || input.trim().isEmpty()) {
            return result;
        }

        String[] split = input.split(",");

        for (String name : split) {
            String trimmed = name.trim();

            if (!trimmed.isEmpty()) {
                result.add(trimmed);
            }
        }

        return result;
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

        String professors = joinProfessors(softConstraint.getPreferredProfessors());

        ConstraintStateManager.saveSoftConstraintState(
                this,
                preferredDays,
                freeTime,
                softConstraint.isKeepLunch12To13Free(),
                softConstraint.isAvoidGapOver3Hours(),
                professors,
                softConstraint.isConsiderTravelTime()
        );
    }

    private String joinProfessors(List<String> professors) {
        if (professors == null || professors.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (String professor : professors) {
            if (professor == null || professor.trim().isEmpty()) {
                continue;
            }

            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(professor.trim());
        }

        return builder.toString();
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