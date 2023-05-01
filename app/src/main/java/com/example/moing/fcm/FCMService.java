package com.example.moing.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class FCMService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token){
        // 기기 마다 다른 refreshed token - 서버에 전송
        Log.d("FCM Log", "Refreshed token: "+token);
    }
}