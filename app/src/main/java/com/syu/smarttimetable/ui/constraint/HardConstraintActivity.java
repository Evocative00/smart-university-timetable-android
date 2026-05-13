package com.syu.smarttimetable.ui.constraint;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.HardConstraint;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.enums.CourseCategory;
import com.syu.smarttimetable.data.repository.LectureRepository;
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
    private int userGrade = 0;
    private String studentId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_constraint);

        bindViews();

        lectureRepository = new LectureRepository();
        userGrade = getIntent().getIntExtra("userGrade", 0);
        studentId = getIntent().getStringExtra("studentId");

        if (studentId == null) {
            studentId = "";
        }

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

        for (Lecture lecture : allLectures) {
            if (lecture != null && lecture.getCategory() == targetCategory) {
                filteredLectures.add(lecture);
            }
        }

        autoLecture.setText("", false);
        autoLecture.setFocusable(false);
        autoLecture.setFocusableInTouchMode(false);
        autoLecture.setOnClickListener(v -> showLectureSearchDialog());
    }

    private void showLectureSearchDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_lecture_search, null);

        EditText etSearch = dialogView.findViewById(R.id.et_search);
        ListView lvLectures = dialogView.findViewById(R.id.lv_lectures);

        etSearch.setHint("강의명 / 교수명 / 과목코드로 검색");

        List<String> allDisplayList = new ArrayList<>();
        for (Lecture lecture : filteredLectures) {
            allDisplayList.add(buildLectureDisplayText(lecture));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>(allDisplayList)
        );

        lvLectures.setAdapter(adapter);

        androidx.appcompat.app.AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                .setTitle("고정 과목 검색")
                .setView(dialogView)
                .setNegativeButton("닫기", null)
                .create();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s == null ? "" : s.toString().toLowerCase().trim();

                adapter.clear();

                for (String item : allDisplayList) {
                    if (item.toLowerCase().contains(query)) {
                        adapter.add(item);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        lvLectures.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            autoLecture.setText(selected, false);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void setupButtons() {
        btnAddLecture.setOnClickListener(v -> addFixedLecture());
        btnNext.setOnClickListener(v -> submitHardConstraint());

        tvSelectedLectures.setOnClickListener(v -> showRemoveFixedLectureDialog());
    }

    private void showRemoveFixedLectureDialog() {
        if (selectedLectureDisplayTexts.isEmpty()) {
            Toast.makeText(this, "삭제할 고정 과목이 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] items = selectedLectureDisplayTexts.toArray(new String[0]);

        new MaterialAlertDialogBuilder(this)
                .setTitle("삭제할 고정 과목 선택")
                .setItems(items, (dialog, which) -> {
                    if (which >= 0 && which < selectedLectureDisplayTexts.size()) {
                        selectedLectureDisplayTexts.remove(which);
                        fixedLectureKeys.remove(which);
                        updateSelectedLectureText();
                        Toast.makeText(this, "고정 과목이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("닫기", null)
                .show();
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
        intent.putExtra("userGrade", userGrade);
        intent.putExtra("studentId", studentId);
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
                + buildLectureTimeText(lecture)
                + " / "
                + lecture.getClassroom()
                + " / "
                + lecture.getCourseCode();
    }

    private String buildLectureTimeText(Lecture lecture) {
        if (lecture == null || lecture.getTimes() == null || lecture.getTimes().isEmpty()) {
            return "시간 미정";
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < lecture.getTimes().size(); i++) {
            LectureTime time = lecture.getTimes().get(i);

            if (time == null) {
                continue;
            }

            if (builder.length() > 0) {
                builder.append(", ");
            }

            builder.append(convertDayToKorean(time))
                    .append(" ")
                    .append(formatMinutes(time.getStartTime()))
                    .append("~")
                    .append(formatMinutes(time.getEndTime()));
        }

        if (builder.length() == 0) {
            return "시간 미정";
        }

        return builder.toString();
    }

    private String convertDayToKorean(LectureTime time) {
        if (time == null || time.getDay() == null) {
            return "";
        }

        switch (time.getDay()) {
            case MONDAY:
                return "월";
            case TUESDAY:
                return "화";
            case WEDNESDAY:
                return "수";
            case THURSDAY:
                return "목";
            case FRIDAY:
                return "금";
            case SATURDAY:
                return "토";
            case SUNDAY:
                return "일";
            default:
                return "";
        }
    }

    private String formatMinutes(int minutes) {
        int hour = minutes / 60;
        int minute = minutes % 60;

        return String.format("%02d:%02d", hour, minute);
    }

    private String getText(TextView view) {
        return view.getText() == null ? "" : view.getText().toString().trim();
    }
}