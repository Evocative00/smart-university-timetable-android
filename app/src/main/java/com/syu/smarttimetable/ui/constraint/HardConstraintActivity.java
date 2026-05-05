package com.syu.smarttimetable.ui.constraint;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.HardConstraint;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.repository.LectureRepository;
import com.syu.smarttimetable.data.model.enums.CourseCategory;
import com.syu.smarttimetable.domain.recommendation.constraints.RequiredLectureConstraint;

import java.util.ArrayList;
import java.util.List;

public class HardConstraintActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCredits;
    private AutoCompleteTextView autoCategory;
    private AutoCompleteTextView autoLecture;
    private Button btnAddLecture;
    private Button btnNext;
    private TextView tvSelectedLectures;

    private LectureRepository lectureRepository;
    private final List<Lecture> allLectures = new ArrayList<>();
    private final List<Lecture> filteredLectures = new ArrayList<>();
    private final List<String> fixedLectureKeys = new ArrayList<>();
    private final List<String> selectedLectureDisplayTexts = new ArrayList<>();

    private String selectedCategory = "전공";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_constraint);

        bindViews();

        lectureRepository = new LectureRepository();

        List<Lecture> lectures = lectureRepository.getAllLectures();

        if (lectures == null) {
            Toast.makeText(this, "강의 데이터 없음", Toast.LENGTH_LONG).show();
            return;
        }

        for (Lecture lecture : lectures) {
            if (lecture != null) {
                allLectures.add(lecture);
            }
        }

        setupCreditDropdown();
        setupCategoryDropdown();
        setupLectureDropdown(selectedCategory);
        setupButtons();
        updateSelectedLectureText();
    }

    private void bindViews() {
        autoCredits = findViewById(R.id.auto_credits);
        autoCategory = findViewById(R.id.auto_category);
        autoLecture = findViewById(R.id.auto_lecture);
        btnAddLecture = findViewById(R.id.btn_add_lecture);
        btnNext = findViewById(R.id.btn_next);
        tvSelectedLectures = findViewById(R.id.tv_selected_lectures);
    }

    private void setupCreditDropdown() {
        String[] creditOptions = {"15", "16", "17", "18", "19", "20", "21"};

        ArrayAdapter<String> creditAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                creditOptions
        );

        autoCredits.setAdapter(creditAdapter);
        autoCredits.setOnClickListener(v -> autoCredits.showDropDown());
    }

    private void setupCategoryDropdown() {
        String[] categoryOptions = {"전공", "교양"};

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                categoryOptions
        );

        autoCategory.setAdapter(categoryAdapter);
        autoCategory.setText(selectedCategory, false);
        autoCategory.setOnClickListener(v -> autoCategory.showDropDown());

        autoCategory.setOnItemClickListener((parent, view, position, id) -> {
            selectedCategory = (String) parent.getItemAtPosition(position);
            setupLectureDropdown(selectedCategory);
        });
    }

    private void setupLectureDropdown(String categoryLabel) {
        filteredLectures.clear();

        CourseCategory targetCategory = "전공".equals(categoryLabel)
                ? CourseCategory.MAJOR
                : CourseCategory.GENERAL;

        List<String> lectureDisplayList = new ArrayList<>();

        for (Lecture lecture : allLectures) {
            if (lecture != null && lecture.getCategory() == targetCategory) {
                filteredLectures.add(lecture);
                lectureDisplayList.add(buildLectureDisplayText(lecture));
            }
        }

        ArrayAdapter<String> lectureAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                lectureDisplayList
        );

        autoLecture.setText("", false);
        autoLecture.setAdapter(lectureAdapter);
        autoLecture.setOnClickListener(v -> autoLecture.showDropDown());
    }

    private void setupButtons() {
        btnAddLecture.setOnClickListener(v -> addFixedLecture());
        btnNext.setOnClickListener(v -> submitHardConstraint());
    }

    private void addFixedLecture() {
        String selectedLectureText = getText(autoLecture);

        if (TextUtils.isEmpty(selectedLectureText)) {
            Toast.makeText(this, "고정할 과목을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Lecture selectedLecture = findLectureByDisplayText(selectedLectureText);
        if (selectedLecture == null) {
            Toast.makeText(this, "선택한 과목 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        String lectureKey = RequiredLectureConstraint.buildLectureKey(selectedLecture);

        if (fixedLectureKeys.contains(lectureKey)) {
            Toast.makeText(this, "이미 추가된 과목입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        fixedLectureKeys.add(lectureKey);
        selectedLectureDisplayTexts.add(buildLectureDisplayText(selectedLecture));

        updateSelectedLectureText();
        autoLecture.setText("", false);

        Toast.makeText(this, "고정 과목이 추가되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private void submitHardConstraint() {
        String creditText = getText(autoCredits);

        if (TextUtils.isEmpty(creditText)) {
            Toast.makeText(this, "희망 학점을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        int targetCredits;
        try {
            targetCredits = Integer.parseInt(creditText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "학점 값이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        HardConstraint hardConstraint = new HardConstraint(
                targetCredits,
                new ArrayList<>(fixedLectureKeys)
        );

        Toast.makeText(this, "하드제약 저장 완료", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, SoftConstraintActivity.class);
        intent.putExtra("hardConstraint", hardConstraint);
        startActivity(intent);
        finish();
    }

    private void updateSelectedLectureText() {
        if (selectedLectureDisplayTexts.isEmpty()) {
            tvSelectedLectures.setText("선택된 고정 과목 없음");
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("선택된 고정 과목\n");

        for (int i = 0; i < selectedLectureDisplayTexts.size(); i++) {
            builder.append(i + 1)
                    .append(". ")
                    .append(selectedLectureDisplayTexts.get(i))
                    .append("\n");
        }

        tvSelectedLectures.setText(builder.toString().trim());
    }

    private Lecture findLectureByDisplayText(String displayText) {
        for (Lecture lecture : filteredLectures) {
            if (buildLectureDisplayText(lecture).equals(displayText)) {
                return lecture;
            }
        }
        return null;
    }

    private String buildLectureDisplayText(Lecture lecture) {
        return lecture.getCourseName()
                + " / "
                + lecture.getProfessor()
                + " / "
                + lecture.getCourseCode();
    }

    private String getText(TextView view) {
        return view.getText() == null ? "" : view.getText().toString().trim();
    }
}