package com.syu.smarttimetable.ui.calendar;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.AcademicSchedule;
import com.syu.smarttimetable.data.source.local.AcademicScheduleDummyData;

import java.util.List;

public class CalendarFragment extends Fragment {

    private LinearLayout scheduleListContainer;
    private TextView scheduleCountText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scheduleListContainer = view.findViewById(R.id.layout_academic_schedule_list);
        scheduleCountText = view.findViewById(R.id.tv_schedule_count);
        renderAcademicSchedules();
    }

    private void renderAcademicSchedules() {
        scheduleListContainer.removeAllViews();
        List<AcademicSchedule> schedules = AcademicScheduleDummyData.getSchedules();
        if (scheduleCountText != null) {
            scheduleCountText.setText(schedules.size() + "개");
        }
        for (int i = 0; i < schedules.size(); i++) {
            scheduleListContainer.addView(createTimelineItem(schedules.get(i), i == schedules.size() - 1));
        }
    }

    private View createTimelineItem(AcademicSchedule schedule, boolean isLastItem) {
        LinearLayout row = new LinearLayout(requireContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.TOP);
        row.setPadding(0, dp(14), 0, isLastItem ? 0 : dp(2));

        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        row.setLayoutParams(rowParams);

        LinearLayout dateColumn = new LinearLayout(requireContext());
        dateColumn.setOrientation(LinearLayout.VERTICAL);
        dateColumn.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams dateColumnParams = new LinearLayout.LayoutParams(dp(58), LinearLayout.LayoutParams.MATCH_PARENT);
        dateColumn.setLayoutParams(dateColumnParams);

        LinearLayout dateChip = new LinearLayout(requireContext());
        dateChip.setOrientation(LinearLayout.VERTICAL);
        dateChip.setGravity(Gravity.CENTER);
        dateChip.setBackgroundResource(R.drawable.bg_calendar_date_chip);
        LinearLayout.LayoutParams dateChipParams = new LinearLayout.LayoutParams(dp(54), dp(54));
        dateChip.setLayoutParams(dateChipParams);

        TextView monthView = new TextView(requireContext());
        monthView.setText(getMonthText(schedule.getDate()));
        monthView.setGravity(Gravity.CENTER);
        monthView.setTextColor(ContextCompat.getColor(requireContext(), R.color.smart_primary));
        monthView.setTextSize(11);
        monthView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        dateChip.addView(monthView);

        TextView dayView = new TextView(requireContext());
        dayView.setText(getDayText(schedule.getDate()));
        dayView.setGravity(Gravity.CENTER);
        dayView.setTextColor(ContextCompat.getColor(requireContext(), R.color.smart_text_primary));
        dayView.setTextSize(16);
        dayView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        dateChip.addView(dayView);
        dateColumn.addView(dateChip);

        if (!isLastItem) {
            View line = new View(requireContext());
            line.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.smart_border));
            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(dp(1), dp(34));
            lineParams.setMargins(0, dp(6), 0, 0);
            line.setLayoutParams(lineParams);
            dateColumn.addView(line);
        }

        row.addView(dateColumn);

        LinearLayout content = new LinearLayout(requireContext());
        content.setOrientation(LinearLayout.VERTICAL);
        content.setBackgroundResource(R.drawable.bg_calendar_item_surface);
        content.setPadding(dp(14), dp(13), dp(14), dp(13));
        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        contentParams.setMargins(dp(10), 0, 0, 0);
        content.setLayoutParams(contentParams);

        LinearLayout header = new LinearLayout(requireContext());
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);

        TextView categoryView = new TextView(requireContext());
        categoryView.setText(schedule.getCategory());
        categoryView.setTextColor(ContextCompat.getColor(requireContext(), R.color.smart_primary));
        categoryView.setTextSize(12);
        categoryView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        categoryView.setBackgroundResource(R.drawable.bg_pill_primary_soft);
        categoryView.setPadding(dp(10), dp(4), dp(10), dp(4));
        header.addView(categoryView);

        TextView dateView = new TextView(requireContext());
        dateView.setText(schedule.getDate());
        dateView.setTextColor(ContextCompat.getColor(requireContext(), R.color.smart_text_secondary));
        dateView.setTextSize(12);
        LinearLayout.LayoutParams dateParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        dateParams.setMargins(dp(10), 0, 0, 0);
        dateView.setLayoutParams(dateParams);
        header.addView(dateView);
        content.addView(header);

        TextView titleView = new TextView(requireContext());
        titleView.setText(schedule.getTitle());
        titleView.setTextColor(ContextCompat.getColor(requireContext(), R.color.smart_text_primary));
        titleView.setTextSize(16);
        titleView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        titleParams.setMargins(0, dp(8), 0, 0);
        titleView.setLayoutParams(titleParams);
        content.addView(titleView);

        TextView descriptionView = new TextView(requireContext());
        descriptionView.setText(schedule.getDescription());
        descriptionView.setTextColor(ContextCompat.getColor(requireContext(), R.color.smart_text_secondary));
        descriptionView.setTextSize(13);
        descriptionView.setLineSpacing(dp(2), 1.0f);
        LinearLayout.LayoutParams descParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        descParams.setMargins(0, dp(5), 0, 0);
        descriptionView.setLayoutParams(descParams);
        content.addView(descriptionView);

        row.addView(content);
        return row;
    }

    private String getMonthText(String date) {
        if (date == null || date.length() < 7) {
            return "일정";
        }
        String month = date.substring(5, 7);
        if (month.startsWith("0")) {
            month = month.substring(1);
        }
        return month + "월";
    }

    private String getDayText(String date) {
        if (date == null || date.length() < 10) {
            return "--";
        }
        return date.substring(8, 10);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public void onDestroyView() {
        scheduleListContainer = null;
        scheduleCountText = null;
        super.onDestroyView();
    }
}
