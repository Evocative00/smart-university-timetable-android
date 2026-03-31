package com.syu.smarttimetable.ui.recommendation;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.view.Gravity;
import android.graphics.Color;
import android.util.TypedValue;
import androidx.appcompat.app.AppCompatActivity;

import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.data.source.local.DummyLectureDataSource;
import com.syu.smarttimetable.domain.recommendation.RecommendationEngine;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.ArrayList;
import java.util.List;

public class RecommendationActivity extends AppCompatActivity {

    private LinearLayout resultContainer;
    private Button btnRun;

    private final String[] colors = {
        "#FFCDD2", "#F8BBD0", "#E1BEE7", "#D1C4E9", "#C5CAE9",
        "#BBDEFB", "#B3E5FC", "#B2EBF2", "#B2DFDB", "#B0E0E6"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        resultContainer = findViewById(R.id.ll_result_container);
        btnRun = findViewById(R.id.btn_run_recommendation);

        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runRecommendation();
            }
        });
    }

    private void runRecommendation() {
        resultContainer.removeAllViews();
        TextView tvGenerating = new TextView(this);
        tvGenerating.setText("생성 중...");
        resultContainer.addView(tvGenerating);

        // 1. 전체 강의 목록 가져오기 (Dummy)
        List<Lecture> allLectures = DummyLectureDataSource.getLectures();

        // 2. 추천 엔진 초기화
        RecommendationEngine engine = new RecommendationEngine();

        // 3. 테스트용 추천 요청 (15~18 학점, 고정 과목 및 기수강 지정 없음)
        RecommendationRequest request = new RecommendationRequest(15, 18, new ArrayList<>(), new ArrayList<>());

        // 4. 추천 실행
        List<RecommendationEngine.TimetableScoreTuple> results = engine.recommend(allLectures, request);

        // 5. 결과 출력 (UI 적용)
        displayResults(results);
    }

    private void displayResults(List<RecommendationEngine.TimetableScoreTuple> results) {
        resultContainer.removeAllViews();
        
        if (results.isEmpty()) {
            TextView emptyView = new TextView(this);
            emptyView.setText("조건에 맞는 시간표를 찾을 수 없습니다.");
            resultContainer.addView(emptyView);
            return;
        }

        TextView summaryView = new TextView(this);
        summaryView.setText("총 " + results.size() + "개의 후보가 생성되었습니다.\n");
        summaryView.setTextSize(16);
        resultContainer.addView(summaryView);

        int rank = 1;
        for (RecommendationEngine.TimetableScoreTuple tuple : results) {
            // Header
            TextView headerView = new TextView(this);
            headerView.setText("===== [" + rank + "순위] (점수: " + tuple.getScore() + ") =====\n총 학점: " + tuple.getTimetable().getTotalCredits());
            headerView.setTextSize(16);
            headerView.setPadding(0, 48, 0, 16);
            resultContainer.addView(headerView);

            // Timetable Grid
            View tableView = createTimetableView(tuple.getTimetable().getLectures());
            resultContainer.addView(tableView);
            rank++;
        }
    }

    private View createTimetableView(List<Lecture> lectures) {
        LinearLayout tableContainer = new LinearLayout(this);
        tableContainer.setOrientation(LinearLayout.VERTICAL);
        tableContainer.setBackgroundColor(Color.LTGRAY);
        tableContainer.setPadding(dpToPx(1), dpToPx(1), dpToPx(1), dpToPx(1));

        // Header Row (Time, Mon, Tue, Wed, Thu, Fri)
        LinearLayout headerRow = new LinearLayout(this);
        headerRow.setOrientation(LinearLayout.HORIZONTAL);
        headerRow.setBackgroundColor(Color.WHITE);
        String[] days = {"", "월", "화", "수", "목", "금"};
        for (int i = 0; i < 6; i++) {
            TextView tv = new TextView(this);
            tv.setText(days[i]);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(dpToPx(4), dpToPx(8), dpToPx(4), dpToPx(8));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            if (i == 0) lp.weight = 0.6f;
            tv.setLayoutParams(lp);
            headerRow.addView(tv);
        }
        tableContainer.addView(headerRow);

        // Separator line
        View headerLine = new View(this);
        headerLine.setBackgroundColor(Color.LTGRAY);
        tableContainer.addView(headerLine, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(1)));

        // Content Row
        LinearLayout contentRow = new LinearLayout(this);
        contentRow.setOrientation(LinearLayout.HORIZONTAL);
        contentRow.setBackgroundColor(Color.WHITE);

        int startHour = 9;
        int hours = 13; // 9:00 to 22:00
        int pxPerHour = dpToPx(50); // height per hour

        // Time Column
        FrameLayout timeCol = new FrameLayout(this);
        LinearLayout.LayoutParams timeLp = new LinearLayout.LayoutParams(0, hours * pxPerHour, 0.6f);
        timeCol.setLayoutParams(timeLp);
        for (int i = 0; i < hours; i++) {
            TextView tv = new TextView(this);
            tv.setText(String.format("%02d", startHour + i));
            tv.setTextSize(12);
            tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = i * pxPerHour;
            tv.setLayoutParams(lp);
            timeCol.addView(tv);

            View line = new View(this);
            line.setBackgroundColor(Color.parseColor("#E0E0E0"));
            FrameLayout.LayoutParams lineLp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(1));
            lineLp.topMargin = i * pxPerHour;
            line.setLayoutParams(lineLp);
            timeCol.addView(line);
        }
        contentRow.addView(timeCol);

        // Days setup
        DayOfWeek[] dayEnums = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
        FrameLayout[] dayCols = new FrameLayout[5];

        for (int i = 0; i < 5; i++) {
            View vLine = new View(this);
            vLine.setBackgroundColor(Color.LTGRAY);
            contentRow.addView(vLine, new LinearLayout.LayoutParams(dpToPx(1), ViewGroup.LayoutParams.MATCH_PARENT));

            dayCols[i] = new FrameLayout(this);
            LinearLayout.LayoutParams contentLp = new LinearLayout.LayoutParams(0, hours * pxPerHour, 1);
            dayCols[i].setLayoutParams(contentLp);
            contentRow.addView(dayCols[i]);

            for (int j = 0; j < hours; j++) {
                View line = new View(this);
                line.setBackgroundColor(Color.parseColor("#F5F5F5"));
                FrameLayout.LayoutParams lineLp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(1));
                lineLp.topMargin = j * pxPerHour;
                line.setLayoutParams(lineLp);
                dayCols[i].addView(line);
            }
        }

        // Add Lectures to grid
        int colorIdx = 0;
        for (Lecture lecture : lectures) {
            String color = colors[colorIdx % colors.length];
            colorIdx++;

            List<LectureTime> times = lecture.getTimes();
            if (times == null) continue;

            for (LectureTime time : times) {
                int dayIdx = -1;
                for (int i = 0; i < dayEnums.length; i++) {
                    if (dayEnums[i] == time.getDay()) {
                        dayIdx = i;
                        break;
                    }
                }
                if (dayIdx == -1) continue;

                int startMin = time.getStartTime() - (startHour * 60);
                int endMin = time.getEndTime() - (startHour * 60);
                if (startMin < 0) startMin = 0;

                int topMargin = (int) (startMin * (pxPerHour / 60.0));
                int height = (int) ((endMin - startMin) * (pxPerHour / 60.0));

                TextView cell = new TextView(this);
                cell.setText(lecture.getCourseName() + "\n" + lecture.getClassroom());
                cell.setTextSize(10);
                cell.setBackgroundColor(Color.parseColor(color));
                cell.setTextColor(Color.DKGRAY);
                cell.setPadding(dpToPx(2), dpToPx(2), dpToPx(2), dpToPx(2));
                cell.setGravity(Gravity.CENTER);

                FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        height
                );
                param.topMargin = topMargin;
                param.leftMargin = dpToPx(1);
                param.rightMargin = dpToPx(1);
                cell.setLayoutParams(param);

                dayCols[dayIdx].addView(cell);
            }
        }

        tableContainer.addView(contentRow);
        return tableContainer;
    }

    private int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
