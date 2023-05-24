package com.example.moing.mypage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moing.R;
import com.example.moing.Request.AlarmRequest;
import com.example.moing.Response.AlarmResponse;
import com.example.moing.Response.AlarmSettingResponse;
import com.example.moing.retrofit.ChangeJwt;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClientJwt;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageAlarmActivity extends AppCompatActivity {
    private static final String TAG = "MyPageAlarmActivity";
    private static final String PREF_NAME = "Token";
    private static final String JWT_ACCESS_TOKEN = "JWT_access_token";
    private SharedPreferences sharedPreferences;
    private String jwtAccessToken;
    private ImageButton btnBack;
    private Button btnDone;
    private RadioGroup rgNew;
    private RadioGroup rgRemind;
    private RadioGroup rgFire;
    private RadioButton rbNewOn;
    private RadioButton rbNewOff;
    private RadioButton rbRemindOn;
    private RadioButton rbRemindOff;
    private RadioButton rbFireOn;
    private RadioButton rbFireOff;
    private boolean isNew;
    private boolean isRemind;
    private boolean isFire;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_alarm);

        // JWT Access Token 을 가져오기 위한 SharedPreferences Token
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        jwtAccessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null);

        // 뒤로가기 버튼
        btnBack = findViewById(R.id.mypage_alarm_btn_back);
        // 완료 버튼
        btnDone = findViewById(R.id.mypage_alarm_btn_done);
        // 신규 업로드 알림
        rgNew = findViewById(R.id.mypage_alarm_rg_new);
        rbNewOn = findViewById(R.id.mypage_alarm_rb_new_on);
        rbNewOff = findViewById(R.id.mypage_alarm_rb_new_off);
        // 미션 리마인드 알림
        rgRemind = findViewById(R.id.mypage_alarm_rg_remind);
        rbRemindOn = findViewById(R.id.mypage_alarm_rb_remind_on);
        rbRemindOff = findViewById(R.id.mypage_alarm_rb_remind_off);
        // 불 던지기 알림
        rgFire = findViewById(R.id.mypage_alarm_rg_fire);
        rbFireOn = findViewById(R.id.mypage_alarm_rb_fire_on);
        rbFireOff = findViewById(R.id.mypage_alarm_rb_fire_off);

        // 뒤로가기 버튼 클릭 리스너 - 종료
        btnBack.setOnClickListener(v -> finish());
        // 완료 버튼 클릭 리스너 - 종료
        btnDone.setOnClickListener(v -> finish());

        // 사용자의 초기 알림 설정 상태 적용
        getAlarmSetting();

    }

    // 신규업로드 알림 체크 변화 리스너
    RadioGroup.OnCheckedChangeListener onNewCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if(checkedId == R.id.mypage_alarm_rb_new_on)
                isNew = true;

            if(checkedId == R.id.mypage_alarm_rb_new_off)
                isNew = false;

            // 알림 설정 정보 전달
            putAlarmNew();
        }
    };
    // 리마인드 알림 체크 변화 리스너
    RadioGroup.OnCheckedChangeListener onRemindCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if(checkedId == R.id.mypage_alarm_rb_remind_on)
                isRemind = true;

            if(checkedId == R.id.mypage_alarm_rb_remind_off)
                isRemind = false;

            // 알림 설정 정보 전달
            putAlarmRemind();
        }
    };
    // 불던지기 알림 체크 변화 리스너
    RadioGroup.OnCheckedChangeListener onFireCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if(checkedId == R.id.mypage_alarm_rb_fire_on)
                isFire = true;

            if(checkedId == R.id.mypage_alarm_rb_fire_off)
                isFire = false;

            // 알림 설정 정보 전달
            putAlarmFire();
        }
    };


    // 사용자의 초기 알림 설정 상태를 가져와 적용하는 메소드
    private void getAlarmSetting(){

        RetrofitAPI apiService = RetrofitClientJwt.getApiService(jwtAccessToken);
        Call<AlarmSettingResponse> call = apiService.getAlarmSetting(jwtAccessToken);
        call.enqueue(new Callback<AlarmSettingResponse>() {
            @Override
            public void onResponse(@NonNull Call<AlarmSettingResponse> call, @NonNull Response<AlarmSettingResponse> response) {
                // 연결 성공
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d(TAG,response.body().toString());

                        // 신규업로드 알림 기본 설정
                        isNew = response.body().getData().isNewUpload();
                        if(isNew)
                            rbNewOn.setChecked(true);
                        else
                            rbNewOff.setChecked(true);

                        // 미션 리마인드 알림 기본 설정
                        isRemind = response.body().getData().isRemind();
                        if(isRemind)
                            rbRemindOn.setChecked(true);
                        else
                            rbRemindOff.setChecked(true);

                        // 불 던지기 알림 기본 설정
                        isFire = response.body().getData().isFire();
                        if(isFire)
                            rbFireOn.setChecked(true);
                        else
                            rbFireOff.setChecked(true);

                        // 신규업로드 알림 체크 변화 리스너
                        rgNew.setOnCheckedChangeListener(onNewCheckedChangeListener);
                        // 리마인드 알림 체크 변화 리스너
                        rgRemind.setOnCheckedChangeListener(onRemindCheckedChangeListener);
                        // 불던지기 알림 체크 변화 리스너
                        rgFire.setOnCheckedChangeListener(onFireCheckedChangeListener);

                    }
                } else if (response.message().equals("만료된 토큰입니다.")) {
                    Log.d(TAG, response.message());
                    // 토큰 재발급 후 다시 호출
                    ChangeJwt.updateJwtToken(MyPageAlarmActivity.this);
                    getAlarmSetting();
                }
            }
            @Override
            public void onFailure(@NonNull Call<AlarmSettingResponse> call, @NonNull Throwable t) {
                // 응답 실패
                Log.d(TAG, "알림 설정 가져오기 실패");
            }
        });
    }

    private void putAlarmNew(){

        RetrofitAPI apiService = RetrofitClientJwt.getApiService(jwtAccessToken);
        Call<AlarmResponse> call = apiService.putAlarmNew(jwtAccessToken,new AlarmRequest(isNew));
        call.enqueue(new Callback<AlarmResponse>() {
            @Override
            public void onResponse(@NonNull Call<AlarmResponse> call, @NonNull Response<AlarmResponse> response) {
                // 연결 성공
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d(TAG, "신규미션 알림 설정 성공");
                    }
                } else if (response.message().equals("만료된 토큰입니다.")) {
                    Log.d(TAG, response.message());
                    // 토큰 재발급 후 다시 호출
                    ChangeJwt.updateJwtToken(MyPageAlarmActivity.this);
                    putAlarmNew();
                }
            }
            @Override
            public void onFailure(@NonNull Call<AlarmResponse> call, @NonNull Throwable t) {
                // 응답 실패
                Log.d(TAG, "신규미션 알림 설정 실패");
            }
        });
    }

    private void putAlarmRemind(){

        RetrofitAPI apiService = RetrofitClientJwt.getApiService(jwtAccessToken);
        Call<AlarmResponse> call = apiService.putAlarmRemind(jwtAccessToken, new AlarmRequest(isRemind));
        call.enqueue(new Callback<AlarmResponse>() {
            @Override
            public void onResponse(@NonNull Call<AlarmResponse> call, @NonNull Response<AlarmResponse> response) {
                // 연결 성공
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d(TAG, "리마인드 알림 설정 성공");
                    }
                } else if (response.message().equals("만료된 토큰입니다.")) {
                    Log.d(TAG, response.message());
                    // 토큰 재발급 후 다시 호출
                    ChangeJwt.updateJwtToken(MyPageAlarmActivity.this);
                    putAlarmRemind();
                }
            }
            @Override
            public void onFailure(@NonNull Call<AlarmResponse> call, @NonNull Throwable t) {
                // 응답 실패
                Log.d(TAG, "리마인드 알림 설정 실패");
            }
        });
    }

    private void putAlarmFire(){

        RetrofitAPI apiService = RetrofitClientJwt.getApiService(jwtAccessToken);
        Call<AlarmResponse> call = apiService.putAlarmFire(jwtAccessToken, new AlarmRequest(isFire));
        call.enqueue(new Callback<AlarmResponse>() {
            @Override
            public void onResponse(@NonNull Call<AlarmResponse> call, @NonNull Response<AlarmResponse> response) {
                // 연결 성공
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d(TAG, "불던지기 알림 설정 성공");
                    }
                } else if (response.message().equals("만료된 토큰입니다.")) {
                    Log.d(TAG, response.message());
                    // 토큰 재발급 후 다시 호출
                    ChangeJwt.updateJwtToken(MyPageAlarmActivity.this);
                    putAlarmFire();
                }
            }
            @Override
            public void onFailure(@NonNull Call<AlarmResponse> call, @NonNull Throwable t) {
                // 응답 실패
                Log.d(TAG, "불던지기 알림 설정 실패");
            }
        });
    }

}