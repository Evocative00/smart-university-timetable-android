package com.syu.smarttimetable.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageStorageRepository {

    private ImageStorageRepository() {}

    public static Uri savePng(Context context, Bitmap bitmap) throws IOException {
        String fileName = "timetable_"
                + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date())
                + ".png";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return savePngApi29(context, bitmap, fileName);
        } else {
            return savePngLegacy(context, bitmap, fileName);
        }
    }

    public static Uri getShareableUri(Context context, Bitmap bitmap) throws IOException {
        File cacheDir = new File(context.getCacheDir(), "timetable_share");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        File file = new File(cacheDir, "timetable_share.png");
        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        return FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".fileprovider",
                file
        );
    }

    private static Uri savePngApi29(Context context, Bitmap bitmap, String fileName) throws IOException {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + "/SmartTimetable");

        Uri uri = context.getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (uri == null) {
            throw new IOException("MediaStore insert failed");
        }

        try (OutputStream out = context.getContentResolver().openOutputStream(uri)) {
            if (out == null) throw new IOException("OutputStream is null");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        return uri;
    }

    private static Uri savePngLegacy(Context context, Bitmap bitmap, String fileName) throws IOException {
        File dir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "SmartTimetable"
        );
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to create directory");
        }
        File file = new File(dir, fileName);
        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()}, null, null);
        return Uri.fromFile(file);
    }
}
