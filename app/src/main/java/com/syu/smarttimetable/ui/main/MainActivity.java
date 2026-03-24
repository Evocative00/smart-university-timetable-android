package com.syu.smarttimetable.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.UserPreference;
import com.syu.smarttimetable.data.repository.RecommendationRepository;
import java.util.List;

// 앱의 메인 화면을 담당하는 클래스
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 화면이 처음 생성될 때 실행되는 부분
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // activity_main.xml 레이아웃 파일을 화면에 표시

        // ---------------------------------------------------------
        // 1. 학과 설정 영역
        // ---------------------------------------------------------
        // XML에서 만든 학과 입력창(AutoCompleteTextView)을 자바의 변수와 연결
        AutoCompleteTextView autoDept = findViewById(R.id.auto_department);

        // strings.xml에 저장해둔 학과 목록 배열 가져오기
        String[] departments = getResources().getStringArray(R.array.department_array);

        // 목록 데이터를 어떤 모양(simple_dropdown_item_1line)으로 보여줄지 결정하는 어댑터를 만들기
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, departments);

        // 입력창에 학과 목록 어댑터를 연결
        autoDept.setAdapter(adapter);

        // ---------------------------------------------------------
        // 2. 학년 설정 영역
        // ---------------------------------------------------------
        // 학년 선택창을 연결하고 목록 데이터를 가져와 어댑터로 연결
        AutoCompleteTextView gradeView = findViewById(R.id.auto_grade);
        String[] grades = getResources().getStringArray(R.array.grade_array);
        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, grades);
        gradeView.setAdapter(gradeAdapter);

        // 학년 칸을 클릭했을 때 바로 선택 목록이 아래로 펼쳐지도록 설정
        gradeView.setOnClickListener(v -> gradeView.showDropDown());

        // ---------------------------------------------------------
        // 3. 학번 및 세부전공 UI 부품 연결
        // ---------------------------------------------------------
        // 학번 입력창과 세부전공을 감싸는 레이아웃, 그리고 세부전공 입력창을 연결
        TextInputEditText studentIdEditText = findViewById(R.id.et_student_id);
        TextInputLayout layoutDetail = findViewById(R.id.layout_major_detail);
        AutoCompleteTextView autoDetail = findViewById(R.id.auto_major_detail);

        // ---------------------------------------------------------
        // 4. 학과 선택에 따른 세부전공 노출 로직
        // ---------------------------------------------------------
        // 학과 목록 중 하나를 클릭했을 때 실행되는 리스너
        autoDept.setOnItemClickListener((parent, view, position, id) -> {
            // 사용자가 선택한 학과 이름을 가져오기
            String selectedDept = (String) parent.getItemAtPosition(position);
            String[] details = null;

            // 선택한 학과에 따라 보여줄 세부전공 목록을 switch문으로 작성함.
            switch (selectedDept) {
                case "컴퓨터공학부":
                    details = new String[]{"컴퓨터공학 전공", "소프트웨어 전공"};
                    break;
                case "인공지능융합학부":
                    details = new String[]{"인공지능공학 전공", "지능형반도체 전공", "경영정보시스템 전공"};
                    break;
                case "아트앤디자인학과":
                    details = new String[]{"미술 전공", "디자인 전공"};
                    break;
                case "음악학과":
                    details = new String[]{"성악", "피아노", "관현악", "작곡"};
                    break;
                case "항공관광외국어학부":
                    details = new String[]{"관광경영 전공", "동양어문화 전공"};
                    break;
                case "체육학과":
                    details = new String[]{"축구 전공", "배구 전공","테니스 전공","배드민턴 전공","수영 전공","실용무용 전공"};
                    break;
                case "화학생명과학과":
                    details = new String[]{"화학 전공", "생명과학 전공"};
                    break;
                default:
                    // 세부전공이 없는 학과는 그냥 넘어가기
                    break;
            }

            // 세부전공 목록이 있는 학과일때
            if (details != null) {
                // 세부전공 입력창(상자)을 화면에 보이게함
                layoutDetail.setVisibility(View.VISIBLE);

                // 해당 학과에 맞는 세부전공 목록 어댑터를 새로 만들어 연결
                ArrayAdapter<String> detailAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_dropdown_item_1line, details);
                autoDetail.setAdapter(detailAdapter);

                // 클릭 시 세부전공 목록이 바로 보이게하기
                autoDetail.setOnClickListener(v -> autoDetail.showDropDown());
            } else {
                // 세부전공이 없는 학과라면 입력창을 아예 숨깁니다. [cite: 23, 24]
                layoutDetail.setVisibility(View.GONE);
            }
        });

        // 추천 시스템 작동 여부를 확인하기 위한 테스트 함수를 호출
        testRecommendation();
    }

    // 과목 추천 기능이 잘 작동하는지 로그캣(Logcat)에 출력해 보는 함수입니다. [cite: 25]
    private void testRecommendation() {
        RecommendationRepository repository = new RecommendationRepository();

        // 사용자의 가상 선호도 데이터를 만듭니다. [cite: 26]
        UserPreference preference = new UserPreference(0, "", null);

        // 선호도에 따른 추천 과목 리스트를 가져옵니다. [cite: 26]
        List<Lecture> lectures = repository.recommend(preference);

        // 가져온 과목 이름들을 로그캣창에 하나씩 출력합니다. [cite: 27, 28]
        for (Lecture lecture : lectures) {
            Log.e("RecommendationTest", "과목명: " + lecture.getCourseName());
        }
    }
}