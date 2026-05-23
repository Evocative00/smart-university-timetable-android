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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.common.utils.ConstraintStateManager;
import com.syu.smarttimetable.data.model.HardConstraint;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.enums.CourseCategory;
import com.syu.smarttimetable.data.repository.LectureRepository;
import com.syu.smarttimetable.domain.recommendation.constraints.RequiredLectureConstraint;

import com.syu.smarttimetable.data.model.enums.GeneralArea;
import com.syu.smarttimetable.data.model.enums.RequirementType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class HardConstraintActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCredits;
    private AutoCompleteTextView autoCategory;
    private AutoCompleteTextView autoLecture;
    private Button btnAddLecture;
    private Button btnNext;
    private Button btnReset;
    private ImageButton btnBack;
    private TextView tvSelectedLectures;

    private MaterialButton btnExcludedGrade1;
    private MaterialButton btnExcludedGrade2;
    private MaterialButton btnExcludedGrade3;
    private MaterialButton btnExcludedGrade4;
    private LinearLayout layoutExcludedMajorChips;
    private TextView tvSelectedExcludedLectures;

    private MaterialButton btnExcludedRequiredGeneral;
    private MaterialButton btnExcludedOptional;
    private MaterialButton btnSelectAllCurrentCategory;

    // Grouped general-area UI
    private MaterialButton btnExcludedGeneralGroup;
    private LinearLayout layoutGeneralAreaGroup;
    private CheckBox cbExcludedHumanitiesArt;
    private CheckBox cbExcludedNaturalScience;
    private CheckBox cbExcludedSocialScience;
    private CheckBox cbExcludedDigitalLiteracy;
    private CheckBox cbExcludedCharacter;

    private ExcludedTabType currentExcludedTabType = ExcludedTabType.MAJOR_GRADE_1;

    private enum ExcludedTabType {
        MAJOR_GRADE_1,
        MAJOR_GRADE_2,
        MAJOR_GRADE_3,
        MAJOR_GRADE_4,
        REQUIRED_GENERAL,
        HUMANITIES_ART,
        NATURAL_SCIENCE,
        SOCIAL_SCIENCE,
        DIGITAL_LITERACY,
        CHARACTER_EDUCATION,
        OPTIONAL
    }

    private LectureRepository lectureRepository;
    private final List<Lecture> allLectures = new ArrayList<>();
    private final List<Lecture> filteredLectures = new ArrayList<>();

    private final List<String> fixedLectureKeys = new ArrayList<>();
    private final List<String> selectedLectureDisplayTexts = new ArrayList<>();

    private final Set<String> excludedCourseNames = new LinkedHashSet<>();
    private final List<String> selectedExcludedDisplayTexts = new ArrayList<>();

    private String selectedCategory = "전공";
    private int userGrade = 0;
    private String studentId = "";
    private int selectedExcludedGrade = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_constraint);

        bindViews();

        btnBack.setOnClickListener(v -> onBackPressed());
        btnReset.setOnClickListener(v -> resetHardConstraintState());

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
        setupExcludedCourseUi();

        restoreSavedState();
        updateSelectedLectureText();
        updateSelectedExcludedText();
    }

    private void bindViews() {
        autoCredits = findViewById(R.id.auto_credits);
        autoCategory = findViewById(R.id.auto_category);
        autoLecture = findViewById(R.id.auto_lecture);
        btnAddLecture = findViewById(R.id.btn_add_lecture);
        btnNext = findViewById(R.id.btn_next);
        btnReset = findViewById(R.id.btn_reset);
        btnBack = findViewById(R.id.btn_back);
        tvSelectedLectures = findViewById(R.id.tv_selected_lectures);

        btnExcludedGrade1 = findViewById(R.id.btn_excluded_grade_1);
        btnExcludedGrade2 = findViewById(R.id.btn_excluded_grade_2);
        btnExcludedGrade3 = findViewById(R.id.btn_excluded_grade_3);
        btnExcludedGrade4 = findViewById(R.id.btn_excluded_grade_4);
        layoutExcludedMajorChips = findViewById(R.id.layout_excluded_major_chips);
        tvSelectedExcludedLectures = findViewById(R.id.tv_selected_excluded_lectures);

        btnExcludedRequiredGeneral = findViewById(R.id.btn_excluded_required_general);
        btnExcludedOptional = findViewById(R.id.btn_excluded_optional);
        btnSelectAllCurrentCategory = findViewById(R.id.btn_select_all_current_category);

        btnExcludedGeneralGroup = findViewById(R.id.btn_excluded_general_group);
        layoutGeneralAreaGroup = findViewById(R.id.layout_general_area_group);
        cbExcludedHumanitiesArt = findViewById(R.id.cb_excluded_humanities_art);
        cbExcludedNaturalScience = findViewById(R.id.cb_excluded_natural_science);
        cbExcludedSocialScience = findViewById(R.id.cb_excluded_social_science);
        cbExcludedDigitalLiteracy = findViewById(R.id.cb_excluded_digital_literacy);
        cbExcludedCharacter = findViewById(R.id.cb_excluded_character);
    }

    private void restoreSavedState() {
        String savedCredits = ConstraintStateManager.getSavedTargetCredits(this);
        if (!TextUtils.isEmpty(savedCredits)) {
            autoCredits.setText(savedCredits, false);
        }

        List<String> savedFixedLectures = ConstraintStateManager.getSavedFixedLectures(this);
        for (String lectureKey : savedFixedLectures) {
            if (TextUtils.isEmpty(lectureKey) || fixedLectureKeys.contains(lectureKey)) {
                continue;
            }

            fixedLectureKeys.add(lectureKey);
            Lecture lecture = findLectureByKey(lectureKey);
            selectedLectureDisplayTexts.add(
                    lecture != null ? buildLectureDisplayText(lecture) : lectureKey
            );
        }

        Set<String> savedExcludedCourses = ConstraintStateManager.getSavedExcludedCourses(this);
        for (String excludedCourseName : savedExcludedCourses) {
            if (TextUtils.isEmpty(excludedCourseName) || excludedCourseNames.contains(excludedCourseName)) {
                continue;
            }

            excludedCourseNames.add(excludedCourseName);
            selectedExcludedDisplayTexts.add(findCourseDisplayName(excludedCourseName));
        }

        refreshCurrentExcludedChipList();
    }

    private void resetHardConstraintState() {
        ConstraintStateManager.clearHardConstraintState(this);

        autoCredits.setText("", false);
        autoLecture.setText("", false);
        fixedLectureKeys.clear();
        selectedLectureDisplayTexts.clear();
        excludedCourseNames.clear();
        selectedExcludedDisplayTexts.clear();

        cbExcludedHumanitiesArt.setChecked(false);
        cbExcludedNaturalScience.setChecked(false);
        cbExcludedSocialScience.setChecked(false);
        cbExcludedDigitalLiteracy.setChecked(false);
        cbExcludedCharacter.setChecked(false);

        updateSelectedLectureText();
        updateSelectedExcludedText();
        refreshCurrentExcludedChipList();

        Toast.makeText(this, "하드 제약이 초기화되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private Lecture findLectureByKey(String lectureKey) {
        if (TextUtils.isEmpty(lectureKey)) {
            return null;
        }

        for (Lecture lecture : allLectures) {
            if (lecture != null && lectureKey.equals(RequiredLectureConstraint.buildLectureKey(lecture))) {
                return lecture;
            }
        }

        return null;
    }

    private String findCourseDisplayName(String normalizedCourseName) {
        if (TextUtils.isEmpty(normalizedCourseName)) {
            return "";
        }

        for (Lecture lecture : allLectures) {
            if (lecture == null || TextUtils.isEmpty(lecture.getCourseName())) {
                continue;
            }

            if (normalizedCourseName.equals(normalizeCourseName(lecture.getCourseName()))) {
                return lecture.getCourseName().trim();
            }
        }

        return normalizedCourseName;
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
        tvSelectedExcludedLectures.setOnClickListener(v -> showRemoveExcludedCourseDialog());
        btnSelectAllCurrentCategory.setOnClickListener(v -> selectAllCurrentExcludedCategory());
    }

    private void setupExcludedCourseUi() {
        btnExcludedGrade1.setOnClickListener(v -> showExcludedMajorChips(1));
        btnExcludedGrade2.setOnClickListener(v -> showExcludedMajorChips(2));
        btnExcludedGrade3.setOnClickListener(v -> showExcludedMajorChips(3));
        btnExcludedGrade4.setOnClickListener(v -> showExcludedMajorChips(4));

        btnExcludedRequiredGeneral.setOnClickListener(v -> showExcludedGeneralChips(ExcludedTabType.REQUIRED_GENERAL));
        btnExcludedOptional.setOnClickListener(v -> showExcludedGeneralChips(ExcludedTabType.OPTIONAL));

        // Toggle group visibility
        btnExcludedGeneralGroup.setOnClickListener(v -> {
            if (layoutGeneralAreaGroup.getVisibility() == View.VISIBLE) {
                layoutGeneralAreaGroup.setVisibility(View.GONE);
            } else {
                layoutGeneralAreaGroup.setVisibility(View.VISIBLE);
            }
        });

        // Checkboxes: selecting an area will add all courses in that area to excluded list; unchecking removes them
        cbExcludedHumanitiesArt.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) addAllFromGeneralArea(ExcludedTabType.HUMANITIES_ART);
            else removeAllFromGeneralArea(ExcludedTabType.HUMANITIES_ART);
            updateSelectedExcludedText();
            refreshCurrentExcludedChipList();
        });

        cbExcludedNaturalScience.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) addAllFromGeneralArea(ExcludedTabType.NATURAL_SCIENCE);
            else removeAllFromGeneralArea(ExcludedTabType.NATURAL_SCIENCE);
            updateSelectedExcludedText();
            refreshCurrentExcludedChipList();
        });

        cbExcludedSocialScience.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) addAllFromGeneralArea(ExcludedTabType.SOCIAL_SCIENCE);
            else removeAllFromGeneralArea(ExcludedTabType.SOCIAL_SCIENCE);
            updateSelectedExcludedText();
            refreshCurrentExcludedChipList();
        });

        cbExcludedDigitalLiteracy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) addAllFromGeneralArea(ExcludedTabType.DIGITAL_LITERACY);
            else removeAllFromGeneralArea(ExcludedTabType.DIGITAL_LITERACY);
            updateSelectedExcludedText();
            refreshCurrentExcludedChipList();
        });

        cbExcludedCharacter.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) addAllFromGeneralArea(ExcludedTabType.CHARACTER_EDUCATION);
            else removeAllFromGeneralArea(ExcludedTabType.CHARACTER_EDUCATION);
            updateSelectedExcludedText();
            refreshCurrentExcludedChipList();
        });

        int initialGrade = userGrade > 0 ? userGrade : 1;
        showExcludedMajorChips(initialGrade);
    }

    private void addAllFromGeneralArea(ExcludedTabType tabType) {
        for (Lecture lecture : allLectures) {
            if (lecture == null) continue;
            if (!isLectureInGeneralTab(lecture, tabType)) continue;
            String courseName = lecture.getCourseName();
            if (TextUtils.isEmpty(courseName)) continue;
            addExcludedCourseByName(courseName);
        }
    }

    private void removeAllFromGeneralArea(ExcludedTabType tabType) {
        for (Lecture lecture : allLectures) {
            if (lecture == null) continue;
            if (!isLectureInGeneralTab(lecture, tabType)) continue;
            String courseName = lecture.getCourseName();
            if (TextUtils.isEmpty(courseName)) continue;
            String normalized = normalizeCourseName(courseName);
            if (excludedCourseNames.contains(normalized)) {
                excludedCourseNames.remove(normalized);
                removeExcludedDisplayText(normalized);
            }
        }
    }

    private void showExcludedMajorChips(int grade) {
        selectedExcludedGrade = grade;

        if (grade == 1) {
            currentExcludedTabType = ExcludedTabType.MAJOR_GRADE_1;
        } else if (grade == 2) {
            currentExcludedTabType = ExcludedTabType.MAJOR_GRADE_2;
        } else if (grade == 3) {
            currentExcludedTabType = ExcludedTabType.MAJOR_GRADE_3;
        } else {
            currentExcludedTabType = ExcludedTabType.MAJOR_GRADE_4;
        }

        layoutExcludedMajorChips.removeAllViews();

        List<String> uniqueCourseNames = new ArrayList<>();
        Set<String> seenNames = new HashSet<>();

        for (Lecture lecture : allLectures) {
            if (lecture == null) {
                continue;
            }

            if (lecture.getCategory() != CourseCategory.MAJOR) {
                continue;
            }

            if (lecture.getGrade() != grade) {
                continue;
            }

            String courseName = lecture.getCourseName();

            if (TextUtils.isEmpty(courseName)) {
                continue;
            }

            String normalizedName = normalizeCourseName(courseName);

            if (seenNames.add(normalizedName)) {
                uniqueCourseNames.add(courseName.trim());
            }
        }

        if (uniqueCourseNames.isEmpty()) {
            TextView emptyText = new TextView(this);
            emptyText.setText(grade + "학년 전공 과목이 없습니다.");
            emptyText.setTextSize(14);
            emptyText.setPadding(0, 8, 0, 8);
            layoutExcludedMajorChips.addView(emptyText);
            return;
        }

        for (String courseName : uniqueCourseNames) {
            MaterialButton chipButton = new MaterialButton(this);
            chipButton.setAllCaps(false);

            String normalizedName = normalizeCourseName(courseName);
            boolean selected = excludedCourseNames.contains(normalizedName);

            chipButton.setText(selected ? "✓ " + courseName : courseName);
            chipButton.setMinHeight(dpToPx(42));
            chipButton.setTextSize(13);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, dpToPx(8));
            chipButton.setLayoutParams(params);

            chipButton.setOnClickListener(v -> {
                toggleExcludedCourse(courseName);
                showExcludedMajorChips(selectedExcludedGrade);
            });

            layoutExcludedMajorChips.addView(chipButton);
        }
    }

    private void showExcludedGeneralChips(ExcludedTabType tabType) {
        currentExcludedTabType = tabType;
        layoutExcludedMajorChips.removeAllViews();

        List<String> uniqueCourseNames = new ArrayList<>();
        Set<String> seenNames = new HashSet<>();

        for (Lecture lecture : allLectures) {
            if (!isLectureInGeneralTab(lecture, tabType)) {
                continue;
            }

            String courseName = lecture.getCourseName();

            if (TextUtils.isEmpty(courseName)) {
                continue;
            }

            String normalizedName = normalizeCourseName(courseName);

            if (seenNames.add(normalizedName)) {
                uniqueCourseNames.add(courseName.trim());
            }
        }

        if (uniqueCourseNames.isEmpty()) {
            TextView emptyText = new TextView(this);
            emptyText.setText(getExcludedTabLabel(tabType) + " 과목이 없습니다.");
            emptyText.setTextSize(14);
            emptyText.setPadding(0, 8, 0, 8);
            layoutExcludedMajorChips.addView(emptyText);
            return;
        }

        for (String courseName : uniqueCourseNames) {
            MaterialButton chipButton = new MaterialButton(this);
            chipButton.setAllCaps(false);

            String normalizedName = normalizeCourseName(courseName);
            boolean selected = excludedCourseNames.contains(normalizedName);

            chipButton.setText(selected ? "✓ " + courseName : courseName);
            chipButton.setMinHeight(dpToPx(42));
            chipButton.setTextSize(13);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, dpToPx(8));
            chipButton.setLayoutParams(params);

            chipButton.setOnClickListener(v -> {
                toggleExcludedCourse(courseName);
                showExcludedGeneralChips(currentExcludedTabType);
            });

            layoutExcludedMajorChips.addView(chipButton);
        }
    }

    private boolean isLectureInGeneralTab(Lecture lecture, ExcludedTabType tabType) {
        if (lecture == null || lecture.getCategory() != CourseCategory.GENERAL) {
            return false;
        }

        switch (tabType) {
            case REQUIRED_GENERAL:
                return lecture.getRequirementType() == RequirementType.REQUIRED;

            case HUMANITIES_ART:
                return lecture.getGeneralArea() == GeneralArea.HUMANITIES_ART;

            case NATURAL_SCIENCE:
                return lecture.getGeneralArea() == GeneralArea.NATURAL_SCIENCE;

            case SOCIAL_SCIENCE:
                return lecture.getGeneralArea() == GeneralArea.SOCIAL_SCIENCE;

            case DIGITAL_LITERACY:
                return lecture.getGeneralArea() == GeneralArea.DIGITAL_LITERACY;

            case CHARACTER_EDUCATION:
                return lecture.getGeneralArea() == GeneralArea.CHARACTER_EDUCATION;

            case OPTIONAL:
                return lecture.getRequirementType() == RequirementType.OPTIONAL
                        && lecture.getGeneralArea() == GeneralArea.NONE;

            default:
                return false;
        }
    }

    private void selectAllCurrentExcludedCategory() {
        int addedCount = 0;

        for (Lecture lecture : allLectures) {
            if (lecture == null) {
                continue;
            }

            boolean matched;

            switch (currentExcludedTabType) {
                case MAJOR_GRADE_1:
                    matched = lecture.getCategory() == CourseCategory.MAJOR && lecture.getGrade() == 1;
                    break;

                case MAJOR_GRADE_2:
                    matched = lecture.getCategory() == CourseCategory.MAJOR && lecture.getGrade() == 2;
                    break;

                case MAJOR_GRADE_3:
                    matched = lecture.getCategory() == CourseCategory.MAJOR && lecture.getGrade() == 3;
                    break;

                case MAJOR_GRADE_4:
                    matched = lecture.getCategory() == CourseCategory.MAJOR && lecture.getGrade() == 4;
                    break;

                default:
                    matched = isLectureInGeneralTab(lecture, currentExcludedTabType);
                    break;
            }

            if (!matched) {
                continue;
            }

            String courseName = lecture.getCourseName();

            if (TextUtils.isEmpty(courseName)) {
                continue;
            }

            if (addExcludedCourseByName(courseName)) {
                addedCount++;
            }
        }

        updateSelectedExcludedText();
        refreshCurrentExcludedChipList();

        if (addedCount > 0) {
            Toast.makeText(this, "현재 카테고리 과목을 전체 선택했습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "이미 모두 선택되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshCurrentExcludedChipList() {
        switch (currentExcludedTabType) {
            case MAJOR_GRADE_1:
                showExcludedMajorChips(1);
                break;

            case MAJOR_GRADE_2:
                showExcludedMajorChips(2);
                break;

            case MAJOR_GRADE_3:
                showExcludedMajorChips(3);
                break;

            case MAJOR_GRADE_4:
                showExcludedMajorChips(4);
                break;

            default:
                showExcludedGeneralChips(currentExcludedTabType);
                break;
        }
    }
    private String getExcludedTabLabel(ExcludedTabType tabType) {
        switch (tabType) {
            case MAJOR_GRADE_1:
                return "1학년 전공";

            case MAJOR_GRADE_2:
                return "2학년 전공";

            case MAJOR_GRADE_3:
                return "3학년 전공";

            case MAJOR_GRADE_4:
                return "4학년 전공";

            case REQUIRED_GENERAL:
                return "필수교양";

            case HUMANITIES_ART:
                return "인문예술영역";

            case NATURAL_SCIENCE:
                return "자연과학영역";

            case SOCIAL_SCIENCE:
                return "사회과학영역";

            case DIGITAL_LITERACY:
                return "디지털리터러시영역";

            case CHARACTER_EDUCATION:
                return "인성교육영역";

            case OPTIONAL:
                return "일반선택영역";

            default:
                return "카테고리";
        }
    }

    private boolean addExcludedCourseByName(String courseName) {
        if (TextUtils.isEmpty(courseName)) {
            return false;
        }

        String normalizedName = normalizeCourseName(courseName);

        if (excludedCourseNames.contains(normalizedName)) {
            return false;
        }

        excludedCourseNames.add(normalizedName);
        selectedExcludedDisplayTexts.add(courseName.trim());
        return true;
    }

    private void toggleExcludedCourse(String courseName) {
        if (TextUtils.isEmpty(courseName)) {
            return;
        }

        String normalizedName = normalizeCourseName(courseName);

        if (excludedCourseNames.contains(normalizedName)) {
            excludedCourseNames.remove(normalizedName);
            removeExcludedDisplayText(normalizedName);
        } else {
            addExcludedCourseByName(courseName);
        }

        updateSelectedExcludedText();
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

    private void showRemoveExcludedCourseDialog() {
        if (selectedExcludedDisplayTexts.isEmpty()) {
            Toast.makeText(this, "삭제할 제외 과목이 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] items = selectedExcludedDisplayTexts.toArray(new String[0]);

        new MaterialAlertDialogBuilder(this)
                .setTitle("삭제할 이수/제외 과목 선택")
                .setItems(items, (dialog, which) -> {
                    if (which >= 0 && which < selectedExcludedDisplayTexts.size()) {
                        String removedDisplayName = selectedExcludedDisplayTexts.remove(which);
                        excludedCourseNames.remove(normalizeCourseName(removedDisplayName));
                        updateSelectedExcludedText();
                        showExcludedMajorChips(selectedExcludedGrade);
                        Toast.makeText(this, "제외 과목이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
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

        List<String> completedCourseCodes = buildCompletedCourseCodes();

        HardConstraint hardConstraint = new HardConstraint(
                targetCredits,
                new ArrayList<>(fixedLectureKeys),
                completedCourseCodes
        );

        saveCurrentHardConstraintState(creditText);

        Toast.makeText(this, "하드제약 저장 완료", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, SoftConstraintActivity.class);
        intent.putExtra("hardConstraint", hardConstraint);
        intent.putExtra("userGrade", userGrade);
        intent.putExtra("studentId", studentId);
        startActivity(intent);
    }

    private void saveCurrentHardConstraintState(String creditText) {
        ConstraintStateManager.saveHardConstraintState(
                this,
                creditText,
                new ArrayList<>(fixedLectureKeys),
                new LinkedHashSet<>(excludedCourseNames)
        );
    }

    @Override
    public void onBackPressed() {
        saveCurrentHardConstraintState(getText(autoCredits));
        super.onBackPressed();
    }

    private List<String> buildCompletedCourseCodes() {
        Set<String> completedCodes = new LinkedHashSet<>();

        for (Lecture lecture : allLectures) {
            if (lecture == null) {
                continue;
            }

            String courseName = lecture.getCourseName();

            if (TextUtils.isEmpty(courseName)) {
                continue;
            }

            String normalizedLectureName = normalizeCourseName(courseName);

            if (excludedCourseNames.contains(normalizedLectureName)) {
                completedCodes.add(lecture.getCourseCode());
            }
        }

        return new ArrayList<>(completedCodes);
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

    private void updateSelectedExcludedText() {
        if (selectedExcludedDisplayTexts.isEmpty()) {
            tvSelectedExcludedLectures.setText("선택된 이수/제외 과목 없음");
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("선택된 이수/제외 과목\n");

        for (int i = 0; i < selectedExcludedDisplayTexts.size(); i++) {
            builder.append(i + 1)
                    .append(". ")
                    .append(selectedExcludedDisplayTexts.get(i))
                    .append("\n");
        }

        tvSelectedExcludedLectures.setText(builder.toString().trim());
    }

    private void removeExcludedDisplayText(String normalizedName) {
        for (int i = selectedExcludedDisplayTexts.size() - 1; i >= 0; i--) {
            if (normalizeCourseName(selectedExcludedDisplayTexts.get(i)).equals(normalizedName)) {
                selectedExcludedDisplayTexts.remove(i);
            }
        }
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

    private String normalizeCourseName(String courseName) {
        if (courseName == null) {
            return "";
        }

        return courseName.trim().replaceAll("\\s+", " ");
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    private String getText(TextView view) {
        return view.getText() == null ? "" : view.getText().toString().trim();
    }
}