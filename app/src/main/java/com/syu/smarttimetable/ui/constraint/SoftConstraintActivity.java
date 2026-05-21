package com.syu.smarttimetable.ui.constraint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
import com.syu.smarttimetable.ui.main.MainActivity;

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
    private RadioGroup radioGroupFreeTime;
    private CheckBox checkboxLunch;
    private CheckBox checkboxAvoidLongGap;
    private EditText editPreferredProfessors;
    private CheckBox checkboxTravelTime;
    private Button buttonSkip;
    private Button buttonRecommend;
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
        setupBack();
        setupButtons();
        // 저장된 상태 복원
        restoreSavedState();

        Button btnResetSoft = findViewById(R.id.btn_reset_soft);

        btnResetSoft.setOnClickListener(v -> {

            ConstraintStateManager.clearSoftConstraintState(SoftConstraintActivity.this);

            Toast.makeText(SoftConstraintActivity.this,
                    "초기화 실행됨",
                    Toast.LENGTH_LONG).show();

            finish();

            startActivity(getIntent());
        });
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
        btnBack = findViewById(R.id.btn_back);
    }

    private void setupBack() {
        btnBack.setOnClickListener(v -> onBackPressed());
    }

    private void setupButtons() {
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

    // 소프트 제약 상태 저장

    private void saveSoftConstraintState(SoftConstraint softConstraint) {
        if (softConstraint == null) {
            return;
        }
        
        // 선호 요일을 String List로 변환
        List<String> preferredDaysStr = new ArrayList<>();
        if (softConstraint.getPreferredFreeDays() != null) {
            for (DayOfWeek day : softConstraint.getPreferredFreeDays()) {
                preferredDaysStr.add(day.name());
            }
        }
        
        // FreeTimePreference를 String으로 변환
        String freeTimeStr = softConstraint.getFreeTimePreference() != null ? 
                softConstraint.getFreeTimePreference().name() : "";
        
        // 교수명들을 쉼표로 구분된 문자열로 변환
        String professorsStr = "";
        if (softConstraint.getPreferredProfessors() != null && !softConstraint.getPreferredProfessors().isEmpty()) {
            professorsStr = String.join(",", softConstraint.getPreferredProfessors());
        }
        
        ConstraintStateManager.saveSoftConstraintState(
                this,
                preferredDaysStr,
                freeTimeStr,
                softConstraint.isKeepLunch12To13Free(),
                softConstraint.isAvoidGapOver3Hours(),
                professorsStr,
                softConstraint.isConsiderTravelTime()
        );
    }

    // 저장된 소프트 제약 상태 복원

    private void restoreSavedState() {

        // 기본 상태 초기화 (이거 없으면 초기화 안 됨)
        checkboxMonday.setChecked(false);
        checkboxTuesday.setChecked(false);
        checkboxWednesday.setChecked(false);
        checkboxThursday.setChecked(false);
        checkboxFriday.setChecked(false);
        radioGroupFreeTime.check(R.id.radioNone);
        checkboxLunch.setChecked(false);
        checkboxAvoidLongGap.setChecked(false);
        editPreferredProfessors.setText("");
        checkboxTravelTime.setChecked(false);

        // 선호 요일 복원
        List<String> savedDays = ConstraintStateManager.getSavedPreferredDays(this);
        if (savedDays != null && !savedDays.isEmpty()) {
            if (savedDays.contains(DayOfWeek.MONDAY.name())) checkboxMonday.setChecked(true);
            if (savedDays.contains(DayOfWeek.TUESDAY.name())) checkboxTuesday.setChecked(true);
            if (savedDays.contains(DayOfWeek.WEDNESDAY.name())) checkboxWednesday.setChecked(true);
            if (savedDays.contains(DayOfWeek.THURSDAY.name())) checkboxThursday.setChecked(true);
            if (savedDays.contains(DayOfWeek.FRIDAY.name())) checkboxFriday.setChecked(true);
        }
        
        // 자유시간 선호도 복원
        String savedFreeTime = ConstraintStateManager.getSavedFreeTime(this);
        if (!savedFreeTime.isEmpty()) {
            if (savedFreeTime.equals(FreeTimePreference.MORNING.name())) {
                radioGroupFreeTime.check(R.id.radioMorning);
            } else if (savedFreeTime.equals(FreeTimePreference.AFTERNOON.name())) {
                radioGroupFreeTime.check(R.id.radioAfternoon);
            }
        }
        
        // 점심 시간 복원
        checkboxLunch.setChecked(ConstraintStateManager.getSavedLunch(this));
        
        // 긴 공강 피하기 복원
        checkboxAvoidLongGap.setChecked(ConstraintStateManager.getSavedAvoidGap(this));
        
        // 교수명 복원
        String savedProfessors = ConstraintStateManager.getSavedProfessors(this);
        if (!savedProfessors.isEmpty()) {
            editPreferredProfessors.setText(savedProfessors);
        }
        
        // 통학시간 고려 복원
        checkboxTravelTime.setChecked(ConstraintStateManager.getSavedTravelTime(this));
    }

    @Override
    public void onBackPressed() {
        // 뒤로가기 시 현재 상태 저장
        SoftConstraint softConstraint = collectConstraintFromUi();
        saveSoftConstraintState(softConstraint);
        super.onBackPressed();
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
