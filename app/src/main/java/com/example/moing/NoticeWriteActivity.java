package com.example.moing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moing.Request.MissionCreateRequest;
import com.example.moing.Request.NoticeCreateRequest;
import com.example.moing.Response.MissionCreateResponse;
import com.example.moing.Response.NoticeCreateResponse;
import com.example.moing.board.BoardActivity;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClientJwt;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeWriteActivity extends AppCompatActivity {

    private RetrofitAPI apiService;
    private static final String PREF_NAME = "Token";
    private static final String JWT_ACCESS_TOKEN = "JWT_access_token";
    private SharedPreferences sharedPreferences;
    private Long teamId;

    Button btn_close, upload;
    EditText title, content;
    TextView titleCount, contentCount, titleTv, contentTv;

    ImageView xIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_write);

        // Intent 값 전달받는다.
        Intent intent = getIntent();
        teamId = intent.getLongExtra("teamId", 0);

        // Token을 사용할 SharedPreference
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // 투표 만들기 취소 버튼 & 클릭 리스너
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(closeClickListener);
        // 제목
        title = (EditText) findViewById(R.id.et_title);
        // 제목 글자 수
        titleCount = (TextView) findViewById(R.id.tv_titleCount);
        // 내용
        content = (EditText) findViewById(R.id.et_content);
        // 내용 글자 수
        contentCount = (TextView) findViewById(R.id.tv_contentCount);

        // 제목
        titleTv = (TextView) findViewById(R.id.titleTv);

        // 내용
        contentTv = (TextView) findViewById(R.id.contentTv);

        xIcon = (ImageView) findViewById(R.id.xIcon);

        xIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title.setText(null);

            }
        });

        // TextWatcher 등록
        setTextWatcher(title, titleCount, 15);
        setTextWatcher(content, contentCount, 300);

        // 업로드 버튼
        upload = (Button) findViewById(R.id.btn_upload);
        upload.setOnClickListener(uploadClickListener);
        upload.setClickable(false);

    }

    // 취소 버튼
    View.OnClickListener closeClickListener = v -> {
        finish();
    };

    /** EditText 입력 시 입력된 글자 수 변경 **/
    private void setTextWatcher(EditText editText, TextView countTextView, int maxLength) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 0) {
                    String result = "(" + editable.length() + "/" + maxLength + ")";
                    countTextView.setText(result);
                    checkInputs();

                    xIcon.setVisibility(View.VISIBLE);
                    titleTv.setTextColor(Color.parseColor("#FFBEB5"));
                    contentTv.setTextColor(Color.parseColor("#FFBEB5"));

                } else {

                    xIcon.setVisibility(View.INVISIBLE);
                    titleTv.setTextColor(Color.parseColor("#959698"));
                    contentTv.setTextColor(Color.parseColor("#959698"));

                }
            }
        });
    }

    // 각 EditText들의 null값 여부 확인
    public boolean isEditTextFilled() {
        if (title.getText().toString().isEmpty() || content.getText().toString().isEmpty()) {
            return false;
        }

        return true;
    }

    // 입력값 확인
    public void checkInputs() {
        if (isEditTextFilled() ) {
            upload.setClickable(true);
            upload.setTextColor(Color.parseColor("#202020"));
            upload.setBackgroundColor(Color.parseColor("#FFFFFF"));

            // 버튼의 둥근 모서리 설정
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setCornerRadius(10); // 원하는 모서리 반지름 설정
            gradientDrawable.setColor(Color.parseColor("#FFFFFF")); // 배경 색상 설정
            upload.setBackground(gradientDrawable);

        }

        // upload버튼 비활성
        else {
            upload.setClickable(false);
            upload.setTextColor(ContextCompat.getColorStateList(NoticeWriteActivity.this, R.color.secondary_grey_black_10));
            upload.setBackgroundColor(Color.parseColor("#202020"));

            // 버튼의 둥근 모서리 설정
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setCornerRadius(10); // 원하는 모서리 반지름 설정
            gradientDrawable.setColor(Color.parseColor("#202020")); // 배경 색상 설정
            upload.setBackground(gradientDrawable);
        }
    }

    /** 업로드하기 버튼 **/
    View.OnClickListener uploadClickListener = v -> {
        // 미션 정보를 입력한 후 업로드하기 버튼을 클릭할 때 수행되는 코드

        String accessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null); // 액세스 토큰 검색
        apiService = RetrofitClientJwt.getApiService(accessToken);

        // 공지 정보를 가져옴
        String title1 = title.getText().toString();
        String content2 =  content.getText().toString();

        // 공지 생성 요청 객체 생성
        NoticeCreateRequest noticeCreateRequest = new NoticeCreateRequest(title1, content2);

        Call<NoticeCreateResponse> call = apiService.makeNotice(accessToken, teamId, noticeCreateRequest);

        call.enqueue(new Callback<NoticeCreateResponse>() {
            @Override
            public void onResponse(Call<NoticeCreateResponse> call, Response<NoticeCreateResponse> response) {
                if (response.isSuccessful()) {
                    // 요청이 성공적으로 처리됨
                    NoticeCreateResponse noticeCreateResponse = response.body();
                    // 생성된 미션 데이터에 접근하여 필요한 작업 수행
                    NoticeCreateResponse.Data noticeData = noticeCreateResponse.getData();

                    Long noticeId = noticeData.getNoticeId();

                    Intent intent = new Intent(NoticeWriteActivity.this, NoticeVoteActivity.class);
                    intent.putExtra("teamId", teamId);
                    intent.putExtra("noticeId", noticeId);
                    Log.d("공지생성", String.valueOf(noticeId));
                    startActivity(intent);

                } else {
                    // 요청이 실패함
                    // 실패 처리를 위한 코드 작성
                }
            }

            @Override
            public void onFailure(Call<NoticeCreateResponse> call, Throwable t) {
                // 요청이 실패함
                // 실패 처리를 위한 코드 작성
            }
        });
    };

}