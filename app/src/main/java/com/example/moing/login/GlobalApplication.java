package com.example.moing.login;

import android.app.Application;
import android.util.Log;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    private static GlobalApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 네이티브 앱 키로 초기화
        KakaoSdk.init(this, "877d84c9c8b609abd803b36914a8fbeb");
        String hash = KakaoSdk.INSTANCE.getKeyHash();
        Log.d("kakaoHashKey", hash);
    }
}
