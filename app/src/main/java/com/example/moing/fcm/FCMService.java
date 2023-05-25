package com.example.moing.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.moing.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "FCMService";
    private static final String PREF_NAME = "Token";
    private static final String FCM_TOKEN = "FCM_token";
    private static final String CHANNEL_ID = "FCM_channel";

    /** FCM Token이 생성/업데이트 되었을 때 호출되는 메소드**/
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "fcmToken: " + token);

        // 전달받은 FCM Token을 SharedPreference를 통해 저장
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FCM_TOKEN, token);
        editor.apply();
    }

    /** 알림을 전달받았을 때 호출되는 메소드**/
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();  // 알림 제목
            String message = remoteMessage.getNotification().getBody(); // 알림 메세지
            showNotification(title, message);  // 알림 호출하는 메소드
        }
    }

    /** 알림을 호출하는 메소드 **/
    private void showNotification(String title, String message) {
        // 알림 관리자 생성
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 버전 확인 - 안드로이드 Oreo (API 레벨 26) 이상인 경우에만 알림 채널을 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "FCM Channel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("FCM Channel Description");      // 채널에 대한 설명
            notificationManager.createNotificationChannel(channel); // 알림 관리자에 알림 채널 설정
        }

        // NotificationCompat.Builder를 통해 알림 구성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)  // 헤드업 알림 설정
                .setSmallIcon(R.drawable.ic_moings) // 아이콘
                .setContentTitle(title)             // 제목
                .setContentText(message)            // 내용
                .setDefaults(Notification.DEFAULT_SOUND| Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true);               // 알림 클릭시 삭제 - On

        // 알림 표시
        notificationManager.notify(0, builder.build());
    }
}