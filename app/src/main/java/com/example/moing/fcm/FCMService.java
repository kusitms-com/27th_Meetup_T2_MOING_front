package com.example.moing.fcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/** FCM 기능들을 처리하는 클래스 **/
public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "FCMService";
    private static final String PREF_NAME = "Token";
    private static final String FCM_TOKEN = "FCM_token";

    // Refreshed Token 이 생성 또는 update 될 때 호출되는 메소드
    @Override
    public void onNewToken(@NonNull String token) {
        // 기기 마다 다른 FCM Token - 서버에 전송
        Log.d(TAG, "fcmToken: " + token);

        // SharedPreference 객체 생성
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // FCM Token 저장
        editor.putString(FCM_TOKEN, token);
        editor.apply();

    }

    // FCM 서버로 부터 푸시 알림을 받았을 때 호출되는 메소드
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Title:" + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "Message Notification Body:" + remoteMessage.getNotification().getBody());
        }
    }
}