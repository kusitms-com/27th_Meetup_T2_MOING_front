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
import com.example.moing.Request.ChangeJwtRequest;
import com.example.moing.Request.LoginRequest;
import com.example.moing.Response.ChangeJwtResponse;
import com.example.moing.Response.CheckAdditionalInfo;
import com.example.moing.Response.LoginResponse;
import com.example.moing.retrofit.ChangeJwt;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClient;
import com.example.moing.retrofit.RetrofitClientJwt;
import com.kakao.sdk.user.UserApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private RetrofitAPI retrofitAPI, apiService;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "Token";
    private static final String JWT_ACCESS_TOKEN = "JWT_access_token";
    private static final String JWT_REFRESH_TOKEN = "JWT_refresh_token";
    private static final String USER_ID = "user_id";

    private static final String PROCESS = "process";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView kakaoBtn = findViewById(R.id.kakaoBtn);
        kakaoBtn.setOnClickListener(loginClickListener);

        retrofitAPI = RetrofitClient.getApiService();

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

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
                LoginStart(oAuthToken.getAccessToken());
            }
            return null;
        });
    }

    public void accountLogin() {
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰): " + oAuthToken.getAccessToken());
                LoginStart(oAuthToken.getAccessToken());
            }
            return null;
        });
    }

    private void saveTokens(String accessToken, String refreshToken, String process, Long userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String strUserId = String.valueOf(userId);
        editor.putString(JWT_ACCESS_TOKEN, "Bearer " + accessToken);
        editor.putString(JWT_REFRESH_TOKEN, refreshToken);
        editor.putString(PROCESS, process);
        editor.putLong(USER_ID, userId);

        Log.d("MakeTeam_access_token", "Access Token: " + accessToken);
        Log.d("MakeTeam_refresh", "Refresh Token: " + refreshToken);
        Log.d("MakeTeam_process", "Process Token: " + process);

        editor.apply();
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
                    if (loginResponse != null) {
                        String msg = loginResponse.getMessage();
                        if (msg.equals("카카오 로그인을 했습니다")) {
                            LoginResponse.Data data = loginResponse.getData();
                            if (data != null) {
                                String access = data.getAccessToken();
                                String refresh = data.getRefreshToken();
                                String process = data.getProcess();
                                Long userId = data.getUserId();

                                // 토큰 값 저장
                                saveTokens(access, refresh, process, userId);

                                if (process.equals("로그인이 완료되었습니다")) {
                                    // 홈 화면(MainActivity)로 이동
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    // 회원가입 추가 정보 입력 화면(RegisterInputNameActivity)로 이동
                                    Intent intent = new Intent(LoginActivity.this, RegisterInputNameActivity.class);
                                    startActivity(intent);
                                }
                            }
                            else {
                                Log.d("LoginStart", "토큰 얻기 실패" + response.message() + response.code());
                            }
                        }
                        /** 만료된 토큰 처리 **/
                        else if (msg.equals("만료된 토큰입니다.")) {
                            Log.d("LoginActivity", "만료된 토큰입니다...");

                            /** 토큰 변환 처리 **/
                            ChangeJwt.updateJwtToken(LoginActivity.this);

                            /** API 통신 **/
                            sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                            String access_token = sharedPreferences.getString(JWT_ACCESS_TOKEN, null); // 액세스 토큰 검색
                            apiService = RetrofitClientJwt.getApiService(access_token);
                            Call<CheckAdditionalInfo> call2 = retrofitAPI.checkAdditionalInfo(access_token);
                            call2.enqueue(new Callback<CheckAdditionalInfo>() {
                                @Override
                                public void onResponse(Call<CheckAdditionalInfo> call, Response<CheckAdditionalInfo> response) {
                                    CheckAdditionalInfo info = response.body();
                                    String msg = info.getMessage();
                                    if(msg.equals("추가정보를 입력했습니다")) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(LoginActivity.this, RegisterInputNameActivity.class);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<CheckAdditionalInfo> call, Throwable t) {

                                }
                            });




                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

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
