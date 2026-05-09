package com.syu.smarttimetable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SmartTimetableApp extends Application {

    private ToneGenerator toneGenerator;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            // 볼륨 35: 작고 가볍게, STREAM_SYSTEM으로 시스템 효과음 채널 사용
            toneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM, 35);
        } catch (Exception ignored) {}

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                applyClickSound(activity.getWindow().getDecorView());
            }

            @Override public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle b) {}
            @Override public void onActivityStarted(@NonNull Activity activity) {}
            @Override public void onActivityPaused(@NonNull Activity activity) {}
            @Override public void onActivityStopped(@NonNull Activity activity) {}
            @Override public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle b) {}
            @Override public void onActivityDestroyed(@NonNull Activity activity) {}
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void applyClickSound(View view) {
        if (view.isClickable() && !Boolean.TRUE.equals(view.getTag(R.id.tag_sound_applied))) {
            view.setTag(R.id.tag_sound_applied, Boolean.TRUE);
            // 안드로이드 기본 터치 효과음 끄기 → 커스텀 틱으로 통일
            view.setSoundEffectsEnabled(false);
            view.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP,
                            HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                    playLightTick();
                }
                return false;
            });
        }

        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                applyClickSound(group.getChildAt(i));
            }
        }
    }

    private void playLightTick() {
        if (toneGenerator == null) return;
        // TONE_CDMA_KEYPAD_VOLUME_KEY_LITE: 짧고 가벼운 고음 틱, 30ms만 재생
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE, 30);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (toneGenerator != null) {
            toneGenerator.release();
        }
    }
}
