package com.syu.smarttimetable.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.AcademicSchedule;
import com.syu.smarttimetable.data.source.local.AcademicScheduleDummyData;

public class CalendarFragment extends Fragment {

    private LinearLayout scheduleListContainer;

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
        renderAcademicSchedules();
    }

    private void renderAcademicSchedules() {
        scheduleListContainer.removeAllViews();
        for (AcademicSchedule schedule : AcademicScheduleDummyData.getSchedules()) {
            scheduleListContainer.addView(createScheduleCard(schedule));
        }
    }

    private View createScheduleCard(AcademicSchedule schedule) {
        MaterialCardView cardView = new MaterialCardView(requireContext());
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 0, 0, dp(10));
        cardView.setLayoutParams(cardParams);
        cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.smart_surface_alt));
        cardView.setRadius(dp(16));
        cardView.setCardElevation(0f);
        cardView.setStrokeWidth(dp(1));
        cardView.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.smart_border));

        LinearLayout content = new LinearLayout(requireContext());
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(16), dp(14), dp(16), dp(14));

        TextView categoryView = new TextView(requireContext());
        categoryView.setText(schedule.getCategory());
        categoryView.setTextColor(ContextCompat.getColor(requireContext(), R.color.smart_primary));
        categoryView.setTextSize(12);
        categoryView.setTypeface(categoryView.getTypeface(), android.graphics.Typeface.BOLD);
        content.addView(categoryView);

        TextView titleView = new TextView(requireContext());
        titleView.setText(schedule.getTitle());
        titleView.setTextColor(ContextCompat.getColor(requireContext(), R.color.smart_text_primary));
        titleView.setTextSize(17);
        titleView.setTypeface(titleView.getTypeface(), android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        titleParams.setMargins(0, dp(4), 0, 0);
        titleView.setLayoutParams(titleParams);
        content.addView(titleView);

        TextView dateView = new TextView(requireContext());
        dateView.setText(schedule.getDate());
        dateView.setTextColor(ContextCompat.getColor(requireContext(), R.color.smart_text_secondary));
        dateView.setTextSize(14);
        LinearLayout.LayoutParams dateParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        dateParams.setMargins(0, dp(4), 0, 0);
        dateView.setLayoutParams(dateParams);
        content.addView(dateView);

        TextView descriptionView = new TextView(requireContext());
        descriptionView.setText(schedule.getDescription());
        descriptionView.setTextColor(ContextCompat.getColor(requireContext(), R.color.smart_text_secondary));
        descriptionView.setTextSize(13);
        descriptionView.setLineSpacing(dp(2), 1.0f);
        LinearLayout.LayoutParams descParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        descParams.setMargins(0, dp(8), 0, 0);
        descriptionView.setLayoutParams(descParams);
        content.addView(descriptionView);

        cardView.addView(content);
        return cardView;
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public void onDestroyView() {
        scheduleListContainer = null;
        super.onDestroyView();
    }
}
