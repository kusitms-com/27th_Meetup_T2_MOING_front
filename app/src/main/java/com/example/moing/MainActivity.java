package com.example.moing;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.moing.Response.TeamListResponse;
import com.example.moing.retrofit.ChangeJwt;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClientJwt;
import com.example.moing.team.TeamAdapter;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String PREF_NAME = "Token";
    private static final String JWT_ACCESS_TOKEN = "JWT_access_token";
    private List<TeamListResponse.Team> teamList;
    private ViewPager2 viewpager;
    private DotsIndicator dotsIndicator;
    private TextView tvCurTeam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 현재 소모임 수
        tvCurTeam = findViewById(R.id.main_tv_cur_team);
        // 소모임 목록 ViewPager
        viewpager = findViewById(R.id.main_vp_team_list);
        // DotIndicator 설정
        dotsIndicator = findViewById(R.id.main_dots_indicator);
        // 소모임 리스트 get
        setTeamList();

    }

        private void setTeamList(){
            // Token 을 가져오기 위한 SharedPreferences Token
            SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            String jwtAccessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null);

            RetrofitAPI apiService = RetrofitClientJwt.getApiService(jwtAccessToken);
            Call<TeamListResponse> call = apiService.getTeamList(jwtAccessToken);
            call.enqueue(new Callback<TeamListResponse>() {
                @Override
                public void onResponse(@NonNull Call<TeamListResponse> call, @NonNull Response<TeamListResponse> response) {
                    // 연결 성공
                    if(response.isSuccessful()) {
                        if (response.body() != null) {
                            // 모임 리스트 가져오기
                            teamList = response.body().getData().getTeamBlocks();

                            // 진행 중인 모임 표시
                            String curTeamText = "진행 중 모임 "+teamList.size();
                            tvCurTeam.setText(curTeamText);

                            // adapter 설정
                            TeamAdapter teamAdapter = new TeamAdapter(teamList,getApplicationContext());
                            viewpager.setAdapter(teamAdapter);

                            // DotIndicator 설정
                            dotsIndicator.setViewPager2(viewpager);
                        }
                    }
                    else if (response.message().equals("만료된 토큰입니다.")) {
                        // 토큰 재발급 후 다시 호출
                        ChangeJwt.updateJwtToken(MainActivity.this);
                        setTeamList();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<TeamListResponse> call, @NonNull Throwable t) {
                    // 응답 실패
                    Log.d(TAG, "getTeamList Fail");
                }
            });
        }
}