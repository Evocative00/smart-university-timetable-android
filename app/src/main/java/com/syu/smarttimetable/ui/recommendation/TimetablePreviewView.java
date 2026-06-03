package com.syu.smarttimetable.ui.recommendation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class TimetablePreviewView extends View {

    private static final int DEFAULT_START_HOUR = 9;
    private static final int DEFAULT_END_HOUR = 18;
    private static final DayOfWeek[] WEEKDAYS = {
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
    };
    private static final String[] WEEKDAY_LABELS = {"월", "화", "수", "목", "금"};
    private static final int[] LECTURE_COLORS = {
            Color.rgb(83, 75, 204),
            Color.rgb(61, 101, 196),
            Color.rgb(48, 139, 135),
            Color.rgb(123, 94, 188),
            Color.rgb(173, 88, 122),
            Color.rgb(173, 105, 61),
            Color.rgb(65, 125, 92),
            Color.rgb(82, 101, 116),
            Color.rgb(73, 126, 164),
            Color.rgb(144, 91, 164),
            Color.rgb(70, 142, 109),
            Color.rgb(65, 104, 153)
    };

    private final Paint cellPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint blockPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private final List<LectureBlock> lectureBlocks = new ArrayList<>();
    private final Map<String, Integer> lectureColorMap = new HashMap<>();

    private Timetable timetable;
    private RecommendationAdapter.OnLectureClickListener lectureClickListener;

    private int startHour = DEFAULT_START_HOUR;
    private int endHour = DEFAULT_END_HOUR;

    private int surfaceColor;
    private int emptyCellColor;
    private int headerCellColor;
    private int borderColor;
    private int primaryTextColor;
    private int secondaryTextColor;

    private float contentPadding;
    private float headerHeight;
    private float timeColumnWidth;
    private float gap;
    private float rowHeight;
    private float cornerRadius;

    public TimetablePreviewView(Context context) {
        super(context);
        init();
    }

    public TimetablePreviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimetablePreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setClickable(true);
        surfaceColor = ContextCompat.getColor(getContext(), R.color.smart_surface_card);
        emptyCellColor = ContextCompat.getColor(getContext(), R.color.smart_grid_empty);
        headerCellColor = ContextCompat.getColor(getContext(), R.color.smart_grid_header);
        borderColor = ContextCompat.getColor(getContext(), R.color.smart_border);
        primaryTextColor = ContextCompat.getColor(getContext(), R.color.smart_text_primary);
        secondaryTextColor = ContextCompat.getColor(getContext(), R.color.smart_text_secondary);

        contentPadding = dp(4);
        headerHeight = dp(28);
        timeColumnWidth = dp(38);
        gap = dp(2);
        rowHeight = dp(32);
        cornerRadius = dp(10);

        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(dp(1));
        strokePaint.setColor(borderColor);

        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(dp(1));
        gridPaint.setColor(adjustAlpha(borderColor, 0.52f));

        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(primaryTextColor);
    }

    public void setTimetable(Timetable timetable,
                             int startHour,
                             int endHour,
                             RecommendationAdapter.OnLectureClickListener lectureClickListener) {
        this.timetable = timetable;
        this.startHour = Math.max(0, startHour);
        this.endHour = Math.max(this.startHour + 1, endHour);
        this.lectureClickListener = lectureClickListener;
        rebuildLectureColorMap();
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredHeight = Math.round(contentPadding * 2
                + headerHeight
                + gap
                + getHourCount() * rowHeight
                + Math.max(0, getHourCount() - 1) * gap);
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = resolveSize(desiredHeight, heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        lectureBlocks.clear();

        float left = contentPadding;
        float top = contentPadding;
        float right = getWidth() - contentPadding;
        float width = right - left;

        if (width <= 0) {
            return;
        }

        float dayWidth = (width - timeColumnWidth - gap * WEEKDAYS.length) / WEEKDAYS.length;
        if (dayWidth <= 0) {
            return;
        }

        drawHeaders(canvas, left, top, dayWidth);
        drawGrid(canvas, left, top + headerHeight + gap, dayWidth);
        drawLectureBlocks(canvas, left, top + headerHeight + gap, dayWidth);
    }

    private void drawHeaders(Canvas canvas, float left, float top, float dayWidth) {
        drawCell(canvas, left, top, timeColumnWidth, headerHeight, headerCellColor, "", true);

        for (int i = 0; i < WEEKDAY_LABELS.length; i++) {
            float x = left + timeColumnWidth + gap + i * (dayWidth + gap);
            drawCell(canvas, x, top, dayWidth, headerHeight, headerCellColor, WEEKDAY_LABELS[i], true);
        }
    }

    private void drawGrid(Canvas canvas, float left, float top, float dayWidth) {
        drawBodyGrid(canvas, left, top, dayWidth);

        for (int hour = startHour; hour < endHour; hour++) {
            int row = hour - startHour;
            float y = top + row * (rowHeight + gap);
            drawTimeCell(canvas, left, y, timeColumnWidth, rowHeight,
                    String.format(Locale.getDefault(), "%02d", hour));
        }
    }

    private void drawBodyGrid(Canvas canvas, float left, float top, float dayWidth) {
        float bodyLeft = left + timeColumnWidth + gap;
        float bodyRight = bodyLeft
                + WEEKDAYS.length * dayWidth
                + Math.max(0, WEEKDAYS.length - 1) * gap;
        float bodyBottom = top
                + getHourCount() * rowHeight
                + Math.max(0, getHourCount() - 1) * gap;

        RectF bodyRect = new RectF(bodyLeft, top, bodyRight, bodyBottom);
        cellPaint.setStyle(Paint.Style.FILL);
        cellPaint.setColor(adjustAlpha(emptyCellColor, 0.20f));
        canvas.drawRoundRect(bodyRect, cornerRadius, cornerRadius, cellPaint);

        gridPaint.setColor(adjustAlpha(borderColor, 0.48f));
        for (int day = 0; day <= WEEKDAYS.length; day++) {
            float x = bodyLeft + day * (dayWidth + gap) - gap / 2f;
            if (day == 0) {
                x = bodyLeft;
            } else if (day == WEEKDAYS.length) {
                x = bodyRight;
            }
            canvas.drawLine(x, top + dp(2), x, bodyBottom - dp(2), gridPaint);
        }

        for (int hour = 0; hour <= getHourCount(); hour++) {
            float y = top + hour * (rowHeight + gap) - gap / 2f;
            if (hour == 0) {
                y = top;
            } else if (hour == getHourCount()) {
                y = bodyBottom;
            }
            canvas.drawLine(bodyLeft + dp(2), y, bodyRight - dp(2), y, gridPaint);
        }
    }

    private void drawLectureBlocks(Canvas canvas, float left, float bodyTop, float dayWidth) {
        if (timetable == null || timetable.getLecturesReadOnly() == null) {
            return;
        }

        int visibleStart = startHour * 60;
        int visibleEnd = endHour * 60;
        float hourSlot = rowHeight + gap;

        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            for (LectureTime time : lecture.getTimes()) {
                int dayIndex = getDayIndex(time == null ? null : time.getDay());
                if (dayIndex < 0 || time == null) {
                    continue;
                }

                int clippedStart = Math.max(time.getStartTime(), visibleStart);
                int clippedEnd = Math.min(time.getEndTime(), visibleEnd);
                if (clippedEnd <= clippedStart) {
                    continue;
                }

                float x = left + timeColumnWidth + gap + dayIndex * (dayWidth + gap);
                float y = bodyTop + ((clippedStart - visibleStart) / 60f) * hourSlot;
                float height = ((clippedEnd - clippedStart) / 60f) * hourSlot - gap;
                height = Math.max(height, dp(27));

                RectF rect = new RectF(x + dp(1), y + dp(1), x + dayWidth - dp(1), y + height - dp(1));
                drawLectureBlock(canvas, rect, lecture);
                lectureBlocks.add(new LectureBlock(rect, lecture));
            }
        }
    }

    private void drawLectureBlock(Canvas canvas, RectF rect, Lecture lecture) {
        blockPaint.setStyle(Paint.Style.FILL);
        blockPaint.setColor(getLectureColor(lecture));
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, blockPaint);

        strokePaint.setColor(adjustAlpha(Color.BLACK, 0.12f));
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, strokePaint);
        strokePaint.setColor(borderColor);

        textPaint.setColor(Color.WHITE);
        textPaint.setFakeBoldText(true);
        textPaint.setTextSize(sp(9));
        textPaint.setTextAlign(Paint.Align.CENTER);

        float textWidth = Math.max(1f, rect.width() - dp(8));
        String title = lecture.getCourseName() == null ? "" : lecture.getCourseName().trim();
        List<String> lines = buildBlockLines(title, textWidth, rect.height() >= dp(48) ? 2 : 1);
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        float lineHeight = metrics.descent - metrics.ascent;
        float totalTextHeight = lineHeight * lines.size();
        float baseline = rect.centerY() - totalTextHeight / 2f - metrics.ascent;

        for (String line : lines) {
            canvas.drawText(line, rect.centerX(), baseline, textPaint);
            baseline += lineHeight;
        }

        textPaint.setFakeBoldText(false);
    }

    private void drawTimeCell(Canvas canvas,
                              float left,
                              float top,
                              float width,
                              float height,
                              String text) {
        RectF rect = new RectF(left, top + dp(1), left + width, top + height - dp(1));
        cellPaint.setStyle(Paint.Style.FILL);
        cellPaint.setColor(adjustAlpha(headerCellColor, 0.72f));
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, cellPaint);

        textPaint.setColor(primaryTextColor);
        textPaint.setFakeBoldText(true);
        textPaint.setTextSize(sp(9));
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        float baseline = rect.centerY() - (metrics.ascent + metrics.descent) / 2f;
        canvas.drawText(text, rect.centerX(), baseline, textPaint);
        textPaint.setFakeBoldText(false);
    }

    private void drawCell(Canvas canvas,
                          float left,
                          float top,
                          float width,
                          float height,
                          int fillColor,
                          String text,
                          boolean strongText) {
        RectF rect = new RectF(left, top, left + width, top + height);
        cellPaint.setStyle(Paint.Style.FILL);
        cellPaint.setColor(fillColor);
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, cellPaint);

        strokePaint.setColor(borderColor);
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, strokePaint);

        if (text == null || text.isEmpty()) {
            return;
        }

        textPaint.setColor(strongText ? primaryTextColor : secondaryTextColor);
        textPaint.setFakeBoldText(strongText);
        textPaint.setTextSize(strongText ? sp(10) : sp(9));
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        float baseline = rect.centerY() - (metrics.ascent + metrics.descent) / 2f;
        canvas.drawText(text, rect.centerX(), baseline, textPaint);
        textPaint.setFakeBoldText(false);
    }

    private List<String> buildBlockLines(String title, float width, int maxLines) {
        List<String> lines = new ArrayList<>();
        if (title.isEmpty()) {
            lines.add("");
            return lines;
        }

        String remaining = title;
        for (int i = 0; i < maxLines && !remaining.isEmpty(); i++) {
            if (i == maxLines - 1) {
                CharSequence ellipsized = TextUtils.ellipsize(remaining, textPaint, width, TextUtils.TruncateAt.END);
                lines.add(ellipsized.toString());
                break;
            }

            int count = textPaint.breakText(remaining, true, width, null);
            if (count <= 0 || count >= remaining.length()) {
                lines.add(remaining);
                break;
            }

            lines.add(remaining.substring(0, count));
            remaining = remaining.substring(count).trim();
        }

        return lines;
    }

    private void rebuildLectureColorMap() {
        lectureColorMap.clear();
        if (timetable == null || timetable.getLecturesReadOnly() == null) {
            return;
        }

        Set<Integer> usedIndexes = new HashSet<>();
        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            String key = getLectureColorKey(lecture);
            if (key.isEmpty() || lectureColorMap.containsKey(key)) {
                continue;
            }

            int preferredIndex = positiveHash(key) % LECTURE_COLORS.length;
            int selectedIndex = preferredIndex;
            for (int offset = 0; offset < LECTURE_COLORS.length; offset++) {
                int candidate = (preferredIndex + offset) % LECTURE_COLORS.length;
                if (!usedIndexes.contains(candidate)) {
                    selectedIndex = candidate;
                    break;
                }
            }

            usedIndexes.add(selectedIndex);
            lectureColorMap.put(key, LECTURE_COLORS[selectedIndex]);
        }
    }

    private int getLectureColor(Lecture lecture) {
        String key = getLectureColorKey(lecture);
        Integer color = lectureColorMap.get(key);
        if (color != null) {
            return color;
        }

        int index = positiveHash(key) % LECTURE_COLORS.length;
        return LECTURE_COLORS[index];
    }

    private String getLectureColorKey(Lecture lecture) {
        if (lecture == null) {
            return "";
        }

        String courseName = lecture.getCourseName();
        if (courseName != null && !courseName.trim().isEmpty()) {
            return courseName.trim().toLowerCase(Locale.KOREA);
        }

        String courseCode = lecture.getCourseCode();
        return courseCode == null ? "" : courseCode.trim().toLowerCase(Locale.KOREA);
    }

    private int positiveHash(String key) {
        return (key == null ? 0 : key.hashCode()) & 0x7fffffff;
    }

    private int getDayIndex(DayOfWeek day) {
        if (day == null) {
            return -1;
        }

        for (int i = 0; i < WEEKDAYS.length; i++) {
            if (WEEKDAYS[i] == day) {
                return i;
            }
        }
        return -1;
    }

    private int getHourCount() {
        return Math.max(1, endHour - startHour);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            for (int i = lectureBlocks.size() - 1; i >= 0; i--) {
                LectureBlock block = lectureBlocks.get(i);
                if (block.rect.contains(event.getX(), event.getY())) {
                    performClick();
                    if (lectureClickListener != null) {
                        lectureClickListener.onLectureClick(block.lecture);
                    }
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    private float dp(float value) {
        return value * getResources().getDisplayMetrics().density;
    }

    private float sp(float value) {
        return value * getResources().getDisplayMetrics().scaledDensity;
    }

    private static class LectureBlock {
        final RectF rect;
        final Lecture lecture;

        LectureBlock(RectF rect, Lecture lecture) {
            this.rect = rect;
            this.lecture = lecture;
        }
    }
}
