package com.example.moing;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moing.Request.NoticeCommentRequest;
import com.example.moing.Response.NoticeCommentListResponse;
import com.example.moing.Response.NoticeCommentResponse;
import com.example.moing.Response.NoticeInfoResponse;
import com.example.moing.retrofit.ChangeJwt;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClientJwt;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeInfoActivity extends AppCompatActivity {
    Button back;
    ImageButton modal, send;
    TextView title, nickName, time, content, tv_noread;
    ImageView profile, noReadArrow;
    RecyclerView noReadRecycle, commentRecycle;
    CardView noreadCardView;
    LinearLayout layout_cardView;
    EditText et_comment;

    // 공지 안 읽은 사람의 리스트
    private List<String> noticeNoReadList;
    // 댓글 리스트
    private List<NoticeCommentListResponse.NoticeComment> noticeCommentList;
    private NoticeNoReadAdapter noticeNoReadAdapter;
    private NoticeCommentAdapter noticeCommentAdapter;

    private RetrofitAPI apiService;
    private static final String PREF_NAME = "Token";
    private static final String JWT_ACCESS_TOKEN = "JWT_access_token";
    private SharedPreferences sharedPreferences;
    private Long teamId, noticeId, userId, noticeCommentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_info);

        // Token을 사용할 SharedPreference
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Intent로 값 전달 받기.
        teamId = getIntent().getLongExtra("teamId", 0);
        noticeId = getIntent().getLongExtra("noticeId", 0);
        Log.d(TAG, "teamId 값 : " + teamId);
        Log.d(TAG, "noticeId 값 : " + noticeId);

        noticeNoReadList = new ArrayList<>();
        noticeCommentList = new ArrayList<>();

        // 뒤로 가기 버튼
        back = (Button) findViewById(R.id.btn_close);
        back.setOnClickListener(backClickListener);

        /** 모달 버튼 구현 예정 **/
        modal = (ImageButton) findViewById(R.id.imgbtn_dotIndicator);
        modal.setOnClickListener(modalClickListener);

        // 제목
        title = (TextView) findViewById(R.id.tv_title);
        // 프로필사진
        profile = (ImageView) findViewById(R.id.iv_profile);
        // 닉네임
        nickName = (TextView) findViewById(R.id.tv_name);
        // 날짜, 시간
        time = (TextView) findViewById(R.id.tv_time);
        // 투표 내용
        content = (TextView) findViewById(R.id.tv_content);

        // 안읽은 사람 리스트 상태 변경
        tv_noread = (TextView) findViewById(R.id.tv_noread);
        noReadArrow = (ImageView) findViewById(R.id.iv_noread);

        /** 투표 안 읽은 사람 리사이클러뷰 **/
        noReadRecycle = findViewById(R.id.recycle_noread);
        GridLayoutManager llm2 = new GridLayoutManager(this, 4);
        llm2.setSmoothScrollbarEnabled(true);
        llm2.setAutoMeasureEnabled(true);

        /** CardView 작성**/
        noreadCardView = (CardView) findViewById(R.id.cardView_noread);
        noreadCardView.setOnClickListener(cardViewClickListener);
        layout_cardView = findViewById(R.id.layout_card);
        layout_cardView.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        // 안읽은 사람 리스트(GridLayout) 간격 설정
        int grid_spacing = (int) getResources().getDimension(R.dimen.grid_spacing); // 8dp 의미.
        NoticeNoReadGridSpacing noticeNoReadGridSpacing = new NoticeNoReadGridSpacing(4, grid_spacing);
        noReadRecycle.addItemDecoration(noticeNoReadGridSpacing);

        // 안읽은 사람 리사이클러뷰 Layout 호출
        noReadRecycle.setLayoutManager(llm2);
        noReadRecycle.setHasFixedSize(true);

        // 공지 댓글 리사이클러뷰
        commentRecycle = findViewById(R.id.recycle_comment);
        LinearLayoutManager llm3 = new LinearLayoutManager(this);
        llm3.setSmoothScrollbarEnabled(true);
        llm3.setAutoMeasureEnabled(true);

        commentRecycle.setLayoutManager(llm3);
        commentRecycle.setHasFixedSize(true);


//        // 댓글 리사이클러뷰 layout 호출
//        commentRecycle.setLayoutManager(llm3);
//        // Test 데이터 추가 2 (실제로 통신할 땐 VoteInfo의 Static 지워주어야 한다!)
//        NoticeCommentListResponse.NoticeComment noticeComment1 = new NoticeCommentListResponse.NoticeComment(3, "그냥 죽여줘", 4, "test4", "string", "2023-05-04T01:04:28.224175");
//        NoticeCommentListResponse.NoticeComment noticeComment2 = new NoticeCommentListResponse.NoticeComment(2, "뻥이야 살고 싶어", 3, "test3", "string", "2023-05-04T01:04:20.869323");
//        NoticeCommentListResponse.NoticeComment noticeComment3 = new NoticeCommentListResponse.NoticeComment(1, "나를 죽여줘", 2, "test2", "string", "2023-05-04T01:04:11.809115");
//        noticeCommentList.add(noticeComment1);
//        noticeCommentList.add(noticeComment2);
//        noticeCommentList.add(noticeComment3);

        noticeCommentAdapter = new NoticeCommentAdapter(noticeCommentList, this);
        commentRecycle.setAdapter(noticeCommentAdapter);
        commentRecycle.setHasFixedSize(true);

        et_comment = (EditText) findViewById(R.id.et_comment);
        setTextWatcher(et_comment);

        send = (ImageButton) findViewById(R.id.imgbtn_send);
        send.setOnClickListener(sendClickListener);
        send.setEnabled(false);

        /** 투표 결과 API 호출 **/
        getNoticeResult();

        /** 댓글 API 호출 **/
        getComment();

    }

    /** 뒤로 가기 버튼 클릭 리스너 **/
    View.OnClickListener backClickListener = v -> {
        Intent intent = new Intent(getApplicationContext(), NoticeVoteActivity.class);
        intent.putExtra("teamId", teamId);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    };

    /** 모달 버튼 클릭 리스너 **/
    View.OnClickListener modalClickListener = v -> {

    };

    /** CardView(안읽은 사람 리스트) 클릭 리스너 **/
    View.OnClickListener cardViewClickListener = v -> {
        int visibility = (noReadRecycle.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

        TransitionManager.beginDelayedTransition(layout_cardView, new AutoTransition());
        noReadRecycle.setVisibility(visibility);
        // 보이는 상태라면
        if(visibility == View.VISIBLE)
            noReadArrow.setImageResource(R.drawable.arrow_up);
        else
            noReadArrow.setImageResource(R.drawable.arrow_down);
    };

    View.OnClickListener sendClickListener = v -> {
        String s = et_comment.getText().toString();
        if (!TextUtils.isEmpty(s)) {
            /** 스레드 순서 처리 **/
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
//                    makeComment();
//                    return null;
//                }).thenRunAsync(() -> {
//                    runOnUiThread(() -> {
//                        getComment();
//                    });
//                });
//                future.join();
//            }
            // 결과를 기다림
            /** 화면 새로고침 **/
            makeComment();
            getComment();
            Intent intent = getIntent();
            teamId = getIntent().getLongExtra("teamId", 0);
            noticeId = getIntent().getLongExtra("noticeId", 0);
            finish();
            overridePendingTransition(0, 0); // 인텐트 애니메이션 없애기
            startActivity(intent); // 현재 액티비티 재실행
            overridePendingTransition(0, 0); // 인텐트 애니메이션 없애기
        }
    };

    /**
     * 댓글 남기기 입력창 관리
     **/
    private void setTextWatcher(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    send.setEnabled(true);
                } else
                    send.setClickable(false);
            }
        });
    }

    /** API 통신 (공지 상세 조회) **/
    private void getNoticeResult() {
        String accessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null);
        apiService = RetrofitClientJwt.getApiService(accessToken);

        Call<NoticeInfoResponse> call = apiService.NoticeInfo(accessToken, teamId, noticeId);
        call.enqueue(new Callback<NoticeInfoResponse>() {
            @Override
            public void onResponse(Call<NoticeInfoResponse> call, Response<NoticeInfoResponse> response) {
                // 응답이 성공적일 때
                if (response.isSuccessful()) {
                    NoticeInfoResponse infoResponse = response.body();
                    // 제대로 된 연동 성공!
                    if (infoResponse.getMessage().equals("공지를 상세 조회하였습니다")) {
                        // 제목 set
                        title.setText(infoResponse.getData().getTitle());
                        // 내용 set
                        content.setText(infoResponse.getData().getContent());
                        // userId set
                        userId = infoResponse.getData().getUserId();

                        // 시간 set
                        String tmpTime = infoResponse.getData().getCreatedDate();
                        String[] tmp = tmpTime.split("T");
                        String date = tmp[0].substring(5, 10);
                        date = date.replace('-', '/');
                        String getTime = tmp[1].substring(0, 5);
                        String realTime = date + " " + getTime;

                        time.setText(realTime);
                        // 닉네임 set
                        nickName.setText(infoResponse.getData().getNickName());
                        /** profile 설정 */
                        Glide.with(NoticeInfoActivity.this)
                                .load(infoResponse.getData().getUserImageUrl())
                                .into(profile);


                        // 투표 안 읽은 사람 리스트
                        noticeNoReadList = infoResponse.getData().getNotReadUsersNickName();
                        noticeNoReadAdapter = new NoticeNoReadAdapter(noticeNoReadList, NoticeInfoActivity.this);
                        noReadRecycle.setAdapter(noticeNoReadAdapter);
                        tv_noread.setText(noticeNoReadList.size() + "명이 아직 안 읽었어요");

                    } else if (infoResponse.getMessage().equals("만료된 토큰입니다.")) {
                        ChangeJwt.updateJwtToken(NoticeInfoActivity.this);
                        getNoticeResult();
                    }
                } else
                    Log.d(TAG, "응답 성공X, msg : " + response.message().toString());
            }

            @Override
            public void onFailure(Call<NoticeInfoResponse> call, Throwable t) {
                Log.d(TAG, "공지 상세 조회 연동 실패...");
            }
        });
    }

    /** 공지 댓글 목록 조회 **/
    private void getComment() {
        String accessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null);
        apiService = RetrofitClientJwt.getApiService(accessToken);
        Call<NoticeCommentListResponse> call = apiService.makeNoticeCommentList(accessToken, teamId, noticeId);
        call.enqueue(new Callback<NoticeCommentListResponse>() {
            @Override
            public void onResponse(Call<NoticeCommentListResponse> call, Response<NoticeCommentListResponse> response) {
                if(response.isSuccessful()) {
                    NoticeCommentListResponse commentResponse = response.body();
                    if(commentResponse.getMessage().equals("공지 댓글 목록을 최신순으로 조회하였습니다"))
                    {
                        noticeCommentList = commentResponse.getData();
                        noticeCommentAdapter = new NoticeCommentAdapter(noticeCommentList, NoticeInfoActivity.this);
                        commentRecycle.setAdapter(noticeCommentAdapter);
                        noticeCommentAdapter.notifyDataSetChanged();
                    }
                    else if (commentResponse.getMessage().equals("만료된 토큰입니다.")) {
                        ChangeJwt.updateJwtToken(NoticeInfoActivity.this);
                        getComment();
                    }
                    else
                        Log.d(TAG, "공지 댓글 목록 최신순 조회 에러 메세지 : " + response.message().toString());
                }
            }

            @Override
            public void onFailure(Call<NoticeCommentListResponse> call, Throwable t) {
                Log.d(TAG, "공지 댓글 목록 연동 실패...");
            }
        });
    }

    /** 공지 댓글 생성 **/
    private void makeComment() {
        String accessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null);
        apiService = RetrofitClientJwt.getApiService(accessToken);
        String content = et_comment.getText().toString();
        NoticeCommentRequest commentRequest = new NoticeCommentRequest(content);
        Call<NoticeCommentResponse> call = apiService.makeNoticeComment(accessToken, teamId, noticeId, commentRequest);
        call.enqueue(new Callback<NoticeCommentResponse>() {
            @Override
            public void onResponse(Call<NoticeCommentResponse> call, Response<NoticeCommentResponse> response) {
                if(response.isSuccessful()) {
                    NoticeCommentResponse makeCommentResponse = response.body();
                    if(makeCommentResponse.getMessage().equals("공지의 댓글을 생성하였습니다")) {
                        noticeCommentId = makeCommentResponse.getData().getNoticeCommentId();

                        Log.d(TAG, "공지 댓글 연동 성공!");
                        Toast.makeText(getApplicationContext(), "댓글 생성이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else if (makeCommentResponse.getMessage().equals("만료된 토큰입니다.")) {
                        ChangeJwt.updateJwtToken(NoticeInfoActivity.this);
                        makeComment();
                    }
                    else {
                        Log.d(TAG, "공지 댓글 생성 에러 메세지 : " + response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<NoticeCommentResponse> call, Throwable t) {
                Log.d(TAG, "공지 댓글 생성 연동실패... : " + t.getMessage());
            }
        });
    }
}
