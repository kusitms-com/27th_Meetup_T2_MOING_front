package com.example.moing.fcm;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moing.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/** FCM 기능들을 처리하는 클래스 **/
public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "FCMService";

    // Refreshed Token 이 생성 또는 update 될 때 호출되는 메소드
    @Override
    public void onNewToken(@NonNull String token){
        // 기기 마다 다른 refreshed token - 서버에 전송
        Log.d(TAG, "Refreshed token: "+token);

    }

    // FCM 서버로 부터 푸시 알림을 받았을 때 호출되는 메소드
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification() != null){
            Log.d(TAG, "Message Notification Body:" + remoteMessage.getNotification().getBody());
        }
    }
}