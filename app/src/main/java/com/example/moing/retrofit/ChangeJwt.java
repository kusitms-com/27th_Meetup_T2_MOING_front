package com.example.moing.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.moing.Request.ChangeJwtRequest;
import com.example.moing.Response.ChangeJwtResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Jwt 토큰이 만료되었을 때 사용 **/
/** Context를 넣어줘야 함. 예를 들어 MainActivity 라면 MainActivity.this 를 넣어주면 됨. **/
public class ChangeJwt {
    public static void updateJwtToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("JWT Token", Context.MODE_PRIVATE);

        String refresh_token = sharedPreferences.getString("refresh_token", null); // 액세스 토큰 검색
        String strId = sharedPreferences.getString("userId", null); // 액세스 토큰 검색
        String token = sharedPreferences.getString("jwtToken", null);
        Long userId = Long.parseLong(strId);

        RetrofitAPI ApiService = RetrofitClientJwt.getApiService(token);

        ChangeJwtRequest request = new ChangeJwtRequest(refresh_token, userId);
        Call<ChangeJwtResponse> call = ApiService.changeJwt(request);
        call.enqueue(new Callback<ChangeJwtResponse>() {
            @Override
            public void onResponse(Call<ChangeJwtResponse> call, Response<ChangeJwtResponse> response) {
                if(response.isSuccessful()) {
                    ChangeJwtResponse jwtResponse = response.body();
                    ChangeJwtResponse.Data data = jwtResponse.getData();
                    String accessToken = data.getAccessToken();
                    String refreshToken = data.getRefreshToken();
                    /** 저장 **/
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("jwtToken"); // 기존 값 삭제
                    editor.putString("jwtToken", "Bearer " + accessToken); // 수정된 값 삽입
                    editor.remove("refresh_token"); // 기존 값 삭제
                    editor.putString("refresh_token", refreshToken); // 수정된 값 삽입
                    editor.apply(); // 변경사항 저장
                }
            }

            @Override
            public void onFailure(Call<ChangeJwtResponse> call, Throwable t) {
                Log.d("LOGINACTIVITY", t.getMessage());
            }
        });
    }
}
