package com.example.moing.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moing.MainActivity;
import com.example.moing.R;
import com.example.moing.Request.LoginRequest;
import com.example.moing.Response.LoginResponse;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClient;
import com.kakao.sdk.user.UserApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private RetrofitAPI retrofitAPI;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "JWT Token";
    private static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String REFRESH_TOKEN_KEY = "refresh_token";
    private static final String PROCESS_TOKEN_KEY = "process_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView kakaoBtn = findViewById(R.id.kakaoBtn);
        kakaoBtn.setOnClickListener(loginClickListener);

        retrofitAPI = RetrofitClient.getApiService();

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Check if user is already logged in
        /* if (isUserLoggedIn()) {
            navigateToNextActivity();
        } */
    }

    /* private boolean isUserLoggedIn() {
        String accessToken = getAccessToken();
        return accessToken != null && !accessToken.isEmpty();
    } */

    /* private void navigateToNextActivity(String accessToken, String refreshToken, String process) {
        Intent intent = new Intent(LoginActivity.this, RegisterInputNameActivity.class);
        intent.putExtra("access_token", accessToken);
        intent.putExtra("refresh_token", refreshToken);
        intent.putExtra("process_token", process);
        startActivity(intent);
        finish();
    } */

    View.OnClickListener loginClickListener = view -> {
        if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
            login();
        } else {
            accountLogin();
        }
    };

    public void login() {
        String TAG = "login()";
        UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else {
                Log.i(TAG, "로그인 성공(토큰): " + oAuthToken.getAccessToken());
                saveTokens(oAuthToken.getAccessToken(), null, null);
                LoginStart(oAuthToken.getAccessToken());
            }
            return null;
        });
    }

    public void accountLogin() {
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰): " + oAuthToken.getAccessToken());
                saveTokens(oAuthToken.getAccessToken(), null, null);
                LoginStart(oAuthToken.getAccessToken());
            }
            return null;
        });
    }

    private void saveTokens(String accessToken, String refreshToken, String process) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN_KEY, accessToken);
        editor.putString(REFRESH_TOKEN_KEY, refreshToken);
        editor.putString(PROCESS_TOKEN_KEY, process);

        Log.d("Token", "Access Token: " + accessToken);
        Log.d("Token", "Refresh Token: " + refreshToken);
        Log.d("Token", "Process Token: " + process);

        editor.apply();
    }


    /* private String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null);
    }

    private String getRefreshToken() {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null);
    }

    private String getProcessToken() {
        return sharedPreferences.getString(PROCESS_TOKEN_KEY, null);
    } */

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
                    if (loginResponse != null) {
                        LoginResponse.Data data = loginResponse.getData();
                        if (data != null) {
                            String access = data.getAccessToken();
                            String refresh = data.getRefreshToken();
                            String process = data.getProcess();

                            // 토큰 값 저장
                            saveTokens(access, refresh, process);

                            if (process.equals("로그인이 완료되었습니다")) {
                                // 홈 화면(MainActivity)로 이동
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // 회원가입 추가 정보 입력 화면(RegisterInputNameActivity)로 이동
                                Intent intent = new Intent(LoginActivity.this, RegisterInputNameActivity.class);
                                startActivity(intent);
                            }

                            /*
                            Intent intent = new Intent(LoginActivity.this, RegisterInputNameActivity.class);
                            startActivity(intent);
                            */

                        } else {
                            Log.d("LoginStart", "토큰 얻기 실패" + response.message() + response.code());
                        }
                    }
                } else {
                    Log.d("LoginStart", "요청 실패" + response.message() + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("LoginStart", "요청 실패" + t.getMessage());
            }
        });
    }
}
