package com.example.moing.mission;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.moing.R;
import com.example.moing.Response.MissionStatusListResponse;
import com.example.moing.retrofit.ChangeJwt;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClientJwt;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MissionStatusActivity extends AppCompatActivity implements Serializable {
    private static final String TAG = "MissionStatusActivity";
    private static final String PREF_NAME = "Token";
    private static final String JWT_ACCESS_TOKEN = "JWT_access_token";
    private TextView tvMissionTitle;
    private TextView tvRemainDay;
    private TextView tvCompleteUser;
    private ImageView ivStatus;
    private Fragment fragmentDone;
    private Fragment fragmentUnDone;
    private TabLayout tabs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_status);

//        // teamId 값 받아오기
//        long teamId = getIntent().getLongExtra("teamId", 0);
//        // missionId 값 받아오기
//        long missionId = getIntent().getLongExtra("teamId", 0);

        // 내 인증 상태 확인 컴포넌트 - 진행 상태에 따라 다르게 나옴
        ivStatus = findViewById(R.id.mission_status_iv_status); // 나의 인증 상태
        tvMissionTitle = findViewById(R.id.mission_status_tv_mission_title); // 미션 제목
        tvRemainDay = findViewById(R.id.mission_status_tv_remain_day); // 남은 시간
        tvCompleteUser = findViewById(R.id.mission_status_tv_complete_user); // 인증 완료 인원

        // 뒤로가기 - 클릭 시 종료
        ImageButton btnBack = findViewById(R.id.mission_status_btn_back);
        btnBack.setOnClickListener(view -> finish());

        // TabLayout - 인증 완료 / 인증 미완료
        tabs = findViewById(R.id.mission_status_tl);

        // 더미 - 삭제 예정
        long teamId = 1;
        long missionId = 6;

        // 미션 목록 리스트 불러오고 설정
        getMissionStatusList(teamId,missionId);
    }

    private void getMissionStatusList(long teamId, long missionId) {
        // Token 을 가져오기 위한 SharedPreferences Token
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String jwtAccessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null);
        Log.d(TAG, jwtAccessToken);
        RetrofitAPI apiService = RetrofitClientJwt.getApiService(jwtAccessToken);

        // 미션 현황 리스트를 가져옴
        Call<MissionStatusListResponse> call = apiService.getMissionStatusList(jwtAccessToken,teamId, missionId);
        call.enqueue(new Callback<MissionStatusListResponse>() {
            @Override
            public void onResponse(@NonNull Call<MissionStatusListResponse> call, @NonNull Response<MissionStatusListResponse> response) {
                // 연결 성공
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // 완료한 인원 목록
                        List<MissionStatusListResponse.UserMission> doneList = response.body().getData().getCompleteList();

                        // 미완료한 인원 목록, 불을 맞은 인원 목록
                        List<MissionStatusListResponse.UserMission> undoneList = response.body().getData().getIncompleteList();
                        List<Integer> fireList = response.body().getData().getFireUserMissionList();

                        // 미션 상태에 따른 컴포넌트 상단 바 설정
                        String myStatus = response.body().getData().getMyStatus();

                        // 미션 제목 설정
                        String missionTitle = response.body().getData().getTitle();

                        // 미션 남은 시간 설정
                        String remainDay = "남은 시간 D-"+response.body().getData().getRemainDay();

                        // 미션 인증 완료 인원 설정
                        long totalNum = doneList.size() + undoneList.size();   // 총 인원
                        long doneNum = doneList.size();
                        String completeUser = "인증완료 인원 " + doneNum + "/" + totalNum;

                        // UI 업데이트 작업을 예약하기 위해 핸들러를 사용
                        new Handler(Looper.getMainLooper()).post(() -> {

                            // 완료한 인원 목록
                            Bundle doneBundle = new Bundle();
                            doneBundle.putSerializable("doneList", new ArrayList<>(doneList));   // 완료한 인원 목록 저장

                            // 인증 완료 Fragment
                            fragmentDone = new MissionStatusDoneFragment();
                            fragmentDone.setArguments(doneBundle);  // 완료 프래그먼트에 전달

                            // 미완료한 인원 목록, 불을 맞은 인원 목록
                            Bundle undoneBundle = new Bundle();
                            undoneBundle.putSerializable("undoneList", new ArrayList<>(undoneList)); // 미완료한 인원 목록 저장
                            undoneBundle.putSerializable("fireList", new ArrayList<>(fireList));    // 불을 맞은 인원 목록 저장

                            // 인증 미완료 Fragment
                            fragmentUnDone = new MissionStatusUndoneFragment();
                            fragmentUnDone.setArguments(undoneBundle); // 미완료 프래그먼트에 전달

                            // FrameLayout 기본 Fragment - 인증 완료, hide(인증 미완료)
                            getSupportFragmentManager().beginTransaction()
                                    .add(R.id.mission_status_fl, fragmentDone)
                                    .add(R.id.mission_status_fl, fragmentUnDone)
                                    .hide(fragmentUnDone)
                                    .commit();

                            // TabLayout 설정
                            tabs.addTab(tabs.newTab().setText("인증완료"));
                            tabs.addTab(tabs.newTab().setText("인증미완료"));
                            tabs.addOnTabSelectedListener(onTabSelectedListener);

                            // 미션 상태에 따른 상단바 설정
                            setMyStatus(myStatus, ivStatus, tvMissionTitle);

                            // 미션 제목 설정
                            tvMissionTitle.setText(missionTitle);

                            // 미션 남은 시간 설정
                            tvRemainDay.setText(remainDay);

                            // 미션 인증 완료 인원 설정
                            tvCompleteUser.setText(completeUser);
                        });
                    }
                } else if (response.message().equals("만료된 토큰입니다.")) {
                    Log.d(TAG, "만료된 토큰입니다.");
                    // 토큰 재발급 후 다시 호출
                    ChangeJwt.updateJwtToken(MissionStatusActivity.this);
                    getMissionStatusList(teamId, missionId);
                }
                else if (response.message().equals("접근이 거부되었습니다.")) {
                    Log.d(TAG, "접근이 거부되었습니다.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MissionStatusListResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "getMissionStatusList Fail");
            }
        });
    }

    TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            // 선택한 Fragment 로 교체
            int position = tab.getPosition();
            Fragment showFragment = null;
            Fragment hideFragment = null;
            switch (position) {
                case 0:
                    showFragment = fragmentDone;
                    hideFragment = fragmentUnDone;
                    break;
                case 1:
                    showFragment = fragmentUnDone;
                    hideFragment = fragmentDone;
                    break;
            }

            if (showFragment != null && hideFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .show(showFragment)
                        .hide(hideFragment)
                        .commit();
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {}

        @Override
        public void onTabReselected(TabLayout.Tab tab) {}
    };

    // 유저의 인증 현황에 따른 상위 컴포넌트
    private void setMyStatus(String myStatus, ImageView ivStatus, TextView tvMissionTitle){
        // 인증 완료
        if(myStatus.equals("COMPLETE"))
        {
            ivStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_mission_status_done));
            tvMissionTitle.setPaintFlags(tvMissionTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        // 인증 건너뛰기
        else if(myStatus.equals("PENDING")){
            ivStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_mission_status_pending));
            tvMissionTitle.setPaintFlags(tvMissionTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        // 인증 미완료
        else{
            ivStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_mission_status_undone));
            tvMissionTitle.setPaintFlags(tvMissionTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }
}