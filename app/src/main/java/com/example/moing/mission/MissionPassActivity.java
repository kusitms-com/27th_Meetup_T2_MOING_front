package com.example.moing.mission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moing.R;
import com.example.moing.Response.MissionSkipResponse;
import com.example.moing.retrofit.ChangeJwt;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClientJwt;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MissionPassActivity extends AppCompatActivity {
    private static final String TAG = "MissionPassActivity";

    Button close, complete;
    TextView reason, cnt;
    EditText why;

    private RetrofitAPI apiService;
    private static final String PREF_NAME = "Token";
    private static final String JWT_ACCESS_TOKEN = "JWT_access_token";
    private SharedPreferences sharedPreferences;

    private Long teamId, missionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_pass);

        // Token을 사용할 SharedPreference
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        /** Intent로 teamId, missionId 값 받아와야 한다. **/

        close = findViewById(R.id.btn_close);
        complete = findViewById(R.id.btn_complete);
        reason = findViewById(R.id.tv2);
        cnt = findViewById(R.id.tv_count);
        why = findViewById(R.id.et_reason);

        //Focus, TextWatcher 등록
        setFoucsChanged(why, reason,cnt);
        setTextWatcher(why, cnt);


        // 클릭 리스너
        close.setOnClickListener(closeClickListener);
        complete.setOnClickListener(completeClickListener);
        complete.setClickable(false);
    }

    // 뒤로가기 버튼 눌렀을 때
    View.OnClickListener closeClickListener = v -> {
        // 메인 액티비티로 다시 돌아갈 때 입력필드의 입력 값을 되돌려준다.
        Intent intent = new Intent();
        intent.putExtra("value", "0");
        setResult(RESULT_OK, intent);
        finish();
    };

    // 업로드하기 버튼 눌렀을 때
    View.OnClickListener completeClickListener = v -> {
        /** API 호출 **/
        //skipMission();
    };

    /** 입력 시 값 변경 메서드 **/
    private void setTextWatcher(EditText et, TextView tv) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = "(" + s.length() + "/" + 50 + ")";
                tv.setText(result);

                if(s.length() > 0) {
                    complete.setClickable(true);
                    complete.setBackgroundColor(ContextCompat.getColor(MissionPassActivity.this, R.color.secondary_grey_black_1));
                    complete.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondary_grey_black_12));
                }
                else {
                    complete.setClickable(false);
                    complete.setBackgroundColor(ContextCompat.getColor(MissionPassActivity.this, R.color.secondary_grey_black_12));
                    complete.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondary_grey_black_10));
                }

            }
        });
    }

    private void setFoucsChanged(EditText et, TextView tv, TextView tv2) {
        et.setOnFocusChangeListener((view, isFocused)-> {
            if(isFocused) {
                reason.setTextColor(ContextCompat.getColor(this,R.color.main_dark_200));
                cnt.setTextColor(ContextCompat.getColor(this,R.color.main_dark_200));
                reason.setTextColor(ContextCompat.getColor(this,R.color.main_dark_200));
            }
            else {
                reason.setTextColor(ContextCompat.getColor(this,R.color.secondary_grey_black_7));
                cnt.setTextColor(ContextCompat.getColor(this,R.color.secondary_grey_black_7));
                reason.setTextColor(ContextCompat.getColor(this,R.color.secondary_grey_black_7));
            }
        });
    }

    private void skipMission() {
        String accessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null); // 액세스 토큰 검색
        apiService = RetrofitClientJwt.getApiService(accessToken);

        Call<MissionSkipResponse> call = apiService.skipMyMission(accessToken,teamId,missionId,why.getText().toString());
        call.enqueue(new Callback<MissionSkipResponse>() {
            @Override
            public void onResponse(Call<MissionSkipResponse> call, Response<MissionSkipResponse> response) {
                if(response.isSuccessful()) {
                    MissionSkipResponse skipResponse = response.body();
                    if(skipResponse.getMessage().equals("개인별 미션 건너뛰기 성공")) {
                        // 연동 성공한 경우
                        Intent intent = new Intent();
                        intent.putExtra("value", "1");
                        setResult(RESULT_OK, intent);
                        finish();

                        Log.d(TAG,"정상적으로 건너뛰기 처리 완료!");
                    }
                    else if (skipResponse.getMessage().equals("만료된 토큰입니다.")) {
                        Log.d(TAG,"만료된 토큰 처리!");
                        ChangeJwt.updateJwtToken(MissionPassActivity.this);
                        skipMission();
                    }
                }
            }

            @Override
            public void onFailure(Call<MissionSkipResponse> call, Throwable t) {
                Log.d(TAG, "미션 건너뛰기 처리 실패...");
            }
        });
    }
}