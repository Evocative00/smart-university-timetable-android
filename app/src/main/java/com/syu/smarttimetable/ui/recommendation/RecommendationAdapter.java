package com.syu.smarttimetable.ui.recommendation;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.data.model.enums.FreeTimePreference;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class RecommendationAdapter {

    private static final int START_HOUR = 9;
    private static final int DEFAULT_END_HOUR = 19;

    private RecommendationAdapter() {
    }

    public static void renderPreferenceChips(Context context,
                                             LinearLayout container,
                                             RecommendationRequest request) {
        container.removeAllViews();

        if (request == null || request.getSoftConstraint() == null) {
            addChip(context, container, "기본 추천");
            return;
        }

        if (request.getSoftConstraint().isSkipped()) {
            addChip(context, container, "선호 조건 없음");
            return;
        }

        if (request.getSoftConstraint().getPreferredFreeDays() != null
                && !request.getSoftConstraint().getPreferredFreeDays().isEmpty()) {
            StringBuilder builder = new StringBuilder("공강 선호: ");
            for (int i = 0; i < request.getSoftConstraint().getPreferredFreeDays().size(); i++) {
                builder.append(formatDayShort(request.getSoftConstraint().getPreferredFreeDays().get(i)));
                if (i < request.getSoftConstraint().getPreferredFreeDays().size() - 1) {
                    builder.append(", ");
                }
            }
            addChip(context, container, builder.toString());
        }

        FreeTimePreference freeTimePreference = request.getSoftConstraint().getFreeTimePreference();
        if (freeTimePreference == FreeTimePreference.MORNING) {
            addChip(context, container, "오전 공강 선호");
        } else if (freeTimePreference == FreeTimePreference.AFTERNOON) {
            addChip(context, container, "오후 공강 선호");
        }

        if (request.getSoftConstraint().isKeepLunch12To13Free()) {
            addChip(context, container, "점심시간 확보");
        }

        if (request.getSoftConstraint().isAvoidGapOver3Hours()) {
            addChip(context, container, "긴 공강 회피");
        }

        if (request.getSoftConstraint().isConsiderTravelTime()) {
            addChip(context, container, "이동시간 고려");
        }

        if (request.getSoftConstraint().getPreferredProfessors() != null
                && !request.getSoftConstraint().getPreferredProfessors().isEmpty()) {
            addChip(context, container, "선호 교수 반영");
        }

        if (container.getChildCount() == 0) {
            addChip(context, container, "기본 추천");
        }
    }

    public static void renderLectureList(Context context,
                                         LinearLayout container,
                                         Timetable timetable) {
        container.removeAllViews();

        List<Lecture> lectures = new ArrayList<>(timetable.getLecturesReadOnly());
        lectures.sort(Comparator.comparing(Lecture::getCourseName));

        LayoutInflater inflater = LayoutInflater.from(context);

        for (Lecture lecture : lectures) {
            View item = inflater.inflate(
                    R.layout.item_recommendation,
                    container,
                    false
            );

            TextView tvCourseName = item.findViewById(R.id.tv_course_name);
            TextView tvProfessor = item.findViewById(R.id.tv_professor);
            TextView tvMeta = item.findViewById(R.id.tv_meta);
            TextView tvTime = item.findViewById(R.id.tv_time);

            tvCourseName.setText(lecture.getCourseName());
            tvProfessor.setText(lecture.getProfessor());
            tvMeta.setText(buildMetaText(lecture));
            tvTime.setText(buildTimeText(lecture));

            container.addView(item);
        }
    }

    public static void renderTimetableGrid(Context context,
                                           TableLayout tableLayout,
                                           Timetable timetable) {
        tableLayout.removeAllViews();

        addHeaderRow(context, tableLayout);

        int endHour = calculateEndHour(timetable);

        for (int hour = START_HOUR; hour < endHour; hour++) {
            TableRow row = new TableRow(context);

            TextView timeCell = buildCell(context, formatHourRange(hour), true, false);
            row.addView(timeCell);

            DayOfWeek[] days = {
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY
            };

            for (DayOfWeek day : days) {
                Lecture matchedLecture = findLectureForSlot(timetable, day, hour);
                TextView dayCell;

                if (matchedLecture != null) {
                    String label = matchedLecture.getCourseName() + "\n" + matchedLecture.getProfessor();
                    dayCell = buildCell(context, label, false, true);
                } else {
                    dayCell = buildCell(context, "", false, false);
                }

                row.addView(dayCell);
            }

            tableLayout.addView(row);
        }
    }

    private static void addHeaderRow(Context context, TableLayout tableLayout) {
        TableRow headerRow = new TableRow(context);

        headerRow.addView(buildHeaderCell(context, "시간"));
        headerRow.addView(buildHeaderCell(context, "월"));
        headerRow.addView(buildHeaderCell(context, "화"));
        headerRow.addView(buildHeaderCell(context, "수"));
        headerRow.addView(buildHeaderCell(context, "목"));
        headerRow.addView(buildHeaderCell(context, "금"));

        tableLayout.addView(headerRow);
    }

    private static TextView buildHeaderCell(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextSize(13);
        textView.setPadding(16, 14, 16, 14);
        textView.setBackgroundResource(R.drawable.bg_timetable_header_cell);
        textView.setTextColor(ContextCompat.getColor(context, R.color.smart_text_primary));

        TableRow.LayoutParams params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        textView.setLayoutParams(params);
        return textView;
    }

    private static TextView buildCell(Context context, String text, boolean isTimeColumn, boolean occupied) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(isTimeColumn ? 12 : 11);
        textView.setPadding(10, 14, 10, 14);
        textView.setMinLines(2);
        textView.setMaxLines(3);

        if (isTimeColumn) {
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setBackgroundResource(R.drawable.bg_timetable_side_cell);
            textView.setTextColor(ContextCompat.getColor(context, R.color.smart_text_primary));
        } else if (occupied) {
            textView.setBackgroundResource(R.drawable.bg_timetable_filled_cell);
            textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            textView.setBackgroundResource(R.drawable.bg_timetable_empty_cell);
            textView.setTextColor(ContextCompat.getColor(context, R.color.smart_text_secondary));
        }

        TableRow.LayoutParams params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        textView.setLayoutParams(params);
        return textView;
    }

    private static Lecture findLectureForSlot(Timetable timetable, DayOfWeek day, int hour) {
        int slotStart = hour * 60;
        int slotEnd = (hour + 1) * 60;

        try {
            for (Lecture lecture : timetable.getLecturesReadOnly()) {
                if (lecture == null || lecture.getTimes() == null || lecture.getTimes().isEmpty()) {
                    continue;
                }

                for (LectureTime time : lecture.getTimes()) {
                    if (time == null) {
                        continue;
                    }

                    boolean sameDay = time.getDay() == day;
                    boolean overlap = time.getStartTime() < slotEnd && time.getEndTime() > slotStart;

                    if (sameDay && overlap) {
                        return lecture;
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }


    private static int calculateEndHour(Timetable timetable) {
        int maxEndHour = DEFAULT_END_HOUR;

        if (timetable == null || timetable.getLecturesReadOnly() == null) {
            return maxEndHour;
        }

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime time : lecture.getTimes()) {
                if (time == null) {
                    continue;
                }

                int endHour = (time.getEndTime() + 59) / 60;
                if (endHour > maxEndHour) {
                    maxEndHour = endHour;
                }
            }
        }

        return maxEndHour;
    }

    private static String buildMetaText(Lecture lecture) {
        return lecture.getCredits() + "학점 · " + lecture.getCourseCode() + " · " + lecture.getClassroom();
    }

    private static String buildTimeText(Lecture lecture) {
        if (lecture.getTimes() == null || lecture.getTimes().isEmpty()) {
            return "시간 정보 없음";
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < lecture.getTimes().size(); i++) {
            LectureTime time = lecture.getTimes().get(i);

            builder.append(formatDayShort(time.getDay()))
                    .append(" ")
                    .append(formatMinutes(time.getStartTime()))
                    .append("~")
                    .append(formatMinutes(time.getEndTime()));

            if (i < lecture.getTimes().size() - 1) {
                builder.append("  ·  ");
            }
        }

        return builder.toString();
    }

    private static void addChip(Context context, LinearLayout container, String text) {
        TextView chip = new TextView(context);
        chip.setText(text);
        chip.setTextSize(12);
        chip.setPadding(24, 14, 24, 14);
        chip.setBackgroundResource(R.drawable.bg_preference_chip);
        chip.setTextColor(ContextCompat.getColor(context, R.color.smart_primary_dark));

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 16, 16);
        chip.setLayoutParams(params);

        container.addView(chip);
    }

    private static String formatHourRange(int hour) {
        return String.format(Locale.getDefault(), "%02d:00\n~ %02d:00", hour, hour + 1);
    }

    private static String formatMinutes(int totalMinutes) {
        int hour = totalMinutes / 60;
        int minute = totalMinutes % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
    }

    private static String formatDayShort(DayOfWeek day) {
        switch (day) {
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
                return "?";
        }
    }
}