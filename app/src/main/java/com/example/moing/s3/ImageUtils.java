package com.example.moing.s3;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;
import java.util.UUID;

/** 갤러리에서 선택한 이미지 파일을 처리하는 클래스 **/
public class ImageUtils {

    // 이미지 파일의 Uri -> 절대경로 반환
    public static String getAbsolutePathFromUri(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                }
            }
        } else {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    String path = cursor.getString(column_index);
                    cursor.close();
                    return path;
                }
                cursor.close();
            }
        }
        return null;
    }

    // 이미지 파일의 절대경로 -> 고유한 파일명 반환
    public static String generateUniqueFileName(String filePath) {
        File file = new File(filePath);
        String originalFileName = file.getName();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + fileExtension;
    }
}
