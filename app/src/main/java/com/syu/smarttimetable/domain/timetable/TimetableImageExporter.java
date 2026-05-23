package com.syu.smarttimetable.domain.timetable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class TimetableImageExporter {

    private TimetableImageExporter() {
    }

    public static Bitmap captureView(View view) {
        if (view == null || view.getWidth() <= 0 || view.getHeight() <= 0) {
            throw new IllegalArgumentException("캡처할 View가 준비되지 않았습니다.");
        }

        Bitmap bitmap = Bitmap.createBitmap(
                view.getWidth(),
                view.getHeight(),
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        view.draw(canvas);

        return bitmap;
    }
}