package com.example.moing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.moing.retrofit.LoginRequest;
import com.example.moing.retrofit.LoginResponse;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClient;
import com.kakao.sdk.user.UserApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private RetrofitAPI retrofitAPI;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView kakaoBtn = (ImageView) findViewById(R.id.kakaoBtn);
        kakaoBtn.setOnClickListener(loginClickListener);
        //retrofit 이용
        retrofitAPI = RetrofitClient.getApiService();
    }

    View.OnClickListener loginClickListener = view -> {
        // 로그인 기능 구현
        if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
            login();
        }
        else {
            accountLogin();
        }
    };

    public void login() {
        String TAG = "login()";
        UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else
            {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                LoginStart(oAuthToken.getAccessToken());
            }
            return null;
        });
    }

    public void accountLogin(){
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken);
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                accessToken = oAuthToken.getAccessToken();
               LoginStart(oAuthToken.getAccessToken());
            }
            return null;
        });
    }

    private void LoginStart(String accessToken) {
        String TAG = "LoginStart";
        Log.d(TAG, accessToken);
        LoginRequest loginRequest = new LoginRequest(accessToken);
        Call<LoginResponse> call = retrofitAPI.kakaoLogin(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    String access = loginResponse.getAccessToken();
                    String refresh = loginResponse.getRefreshToken();
                    String process = loginResponse.getProcess();
                }
                else {
                    Log.d("LoginStart", "토큰 얻기 실패" + response.message() + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}