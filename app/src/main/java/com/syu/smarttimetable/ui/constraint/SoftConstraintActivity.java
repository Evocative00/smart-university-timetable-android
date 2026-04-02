package com.syu.smarttimetable.ui.constraint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.HardConstraint;
import com.syu.smarttimetable.data.model.SoftConstraint;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.data.model.enums.FreeTimePreference;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;
import com.syu.smarttimetable.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SoftConstraintActivity extends AppCompatActivity {

    private HardConstraint hardConstraint;

    private CheckBox checkboxMonday;
    private CheckBox checkboxTuesday;
    private CheckBox checkboxWednesday;
    private CheckBox checkboxThursday;
    private CheckBox checkboxFriday;
    private RadioGroup radioGroupFreeTime;
    private CheckBox checkboxLunch;
    private CheckBox checkboxAvoidLongGap;
    private EditText editPreferredProfessors;
    private CheckBox checkboxTravelTime;
    private Button buttonSkip;
    private Button buttonRecommend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_constraint);

        hardConstraint = (HardConstraint) getIntent().getSerializableExtra("hardConstraint");

        bindViews();
        setupButtons();
    }

    private void bindViews() {
        checkboxMonday = findViewById(R.id.checkboxMonday);
        checkboxTuesday = findViewById(R.id.checkboxTuesday);
        checkboxWednesday = findViewById(R.id.checkboxWednesday);
        checkboxThursday = findViewById(R.id.checkboxThursday);
        checkboxFriday = findViewById(R.id.checkboxFriday);
        radioGroupFreeTime = findViewById(R.id.radioGroupFreeTime);
        checkboxLunch = findViewById(R.id.checkboxLunch);
        checkboxAvoidLongGap = findViewById(R.id.checkboxAvoidLongGap);
        editPreferredProfessors = findViewById(R.id.editPreferredProfessors);
        checkboxTravelTime = findViewById(R.id.checkboxTravelTime);
        buttonSkip = findViewById(R.id.buttonSkip);
        buttonRecommend = findViewById(R.id.buttonRecommend);
    }

    private void setupButtons() {
        buttonSkip.setOnClickListener(v -> {
            SoftConstraint softConstraint = new SoftConstraint();
            softConstraint.setSkipped(true);
            navigateToMain(softConstraint);
        });

        buttonRecommend.setOnClickListener(v -> {
            SoftConstraint softConstraint = collectConstraintFromUi();
            navigateToMain(softConstraint);
        });
    }

    private SoftConstraint collectConstraintFromUi() {
        List<DayOfWeek> preferredDays = new ArrayList<>();

        if (checkboxMonday.isChecked()) preferredDays.add(DayOfWeek.MONDAY);
        if (checkboxTuesday.isChecked()) preferredDays.add(DayOfWeek.TUESDAY);
        if (checkboxWednesday.isChecked()) preferredDays.add(DayOfWeek.WEDNESDAY);
        if (checkboxThursday.isChecked()) preferredDays.add(DayOfWeek.THURSDAY);
        if (checkboxFriday.isChecked()) preferredDays.add(DayOfWeek.FRIDAY);

        FreeTimePreference freeTimePreference;
        int checkedId = radioGroupFreeTime.getCheckedRadioButtonId();
        if (checkedId == R.id.radioMorning) {
            freeTimePreference = FreeTimePreference.MORNING;
        } else if (checkedId == R.id.radioAfternoon) {
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

    private void navigateToMain(SoftConstraint softConstraint) {
        int targetCredits = hardConstraint != null ? hardConstraint.getTargetCredits() : 0;

        Set<String> fixedLectureKeySet = new HashSet<>();
        if (hardConstraint != null && hardConstraint.getFixedLectureKeys() != null) {
            fixedLectureKeySet.addAll(hardConstraint.getFixedLectureKeys());
        }

        RecommendationRequest recommendationRequest = new RecommendationRequest(
                targetCredits,
                fixedLectureKeySet,
                new HashSet<>(),
                softConstraint
        );

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("hardConstraint", hardConstraint);
        intent.putExtra("softConstraint", softConstraint);
        intent.putExtra("recommendationRequest", recommendationRequest);
        startActivity(intent);
        finish();
    }
}