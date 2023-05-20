package com.example.moing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.LayoutTransition;
import android.graphics.Color;
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

import com.example.moing.R;

import java.util.ArrayList;
import java.util.List;

public class NoticeInfoActivity extends AppCompatActivity {
    Button back;
    ImageButton modal, send;
    TextView title, nickName, time, content, tv_noread;
    ImageView profile, noReadArrow;
    RecyclerView voteRecycle, noReadRecycle, commentRecycle;
    CardView noreadCardView;
    LinearLayout layout_cardView;
    EditText et_comment;

    // 투표 안 읽은 사람의 리스트
    private List<String> noticeNoReadList;
    // 댓글 리스트
    private List<NoticeCommentResponse.NoticeComment> noticeCommentList;
    private NoticeNoReadAdapter noticeNoReadAdapter;
    private NoticeCommentAdapter noticeCommentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_info);

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

        // 투표 공지를 안 읽은 사람들을 위한 리사이클러뷰
        noReadRecycle = findViewById(R.id.recycle_noread);
        GridLayoutManager llm2 = new GridLayoutManager(this, 4);
        llm2.setSmoothScrollbarEnabled(true);
        llm2.setAutoMeasureEnabled(true);

        // 안읽은 사람 리스트 상태 변경
        tv_noread = (TextView) findViewById(R.id.tv_noread);
        noReadArrow = (ImageView) findViewById(R.id.iv_noread);

        // 예정 : 4명이 아직 안읽었어요 부터 백엔드 연동 해야함.
        /** 안읽은 사람 리스트에 대한 Adapter 테스트 코드 **/
        noticeNoReadList.add("손현석");
        noticeNoReadList.add("곽승엽");
        noticeNoReadList.add("이지현");
        noticeNoReadList.add("정승연");
        noticeNoReadAdapter = new NoticeNoReadAdapter(noticeNoReadList,this);

        /** 안읽은 사람 Adapter 객체 생성 **/
        // adapter2 = new RecyclerViewAdapter(dataList2);
        // recyclerView2.setAdapter(adapter2);

        /** CardView 작성**/
        noreadCardView = (CardView) findViewById(R.id.cardView_noread);
        noreadCardView.setOnClickListener(cardViewClickListener);
        layout_cardView = findViewById(R.id.layout_card);
        layout_cardView.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        // 안읽은 사람 리스트(GridLayout) 간격 설정
        int grid_spacing = (int) getResources().getDimension(R.dimen.grid_spacing); // 8dp 의미.
        NoticeNoReadGridSpacing voteNoReadGridSpacing = new NoticeNoReadGridSpacing(4, grid_spacing);
        noReadRecycle.addItemDecoration(voteNoReadGridSpacing);

        // 안읽은 사람 리사이클러뷰 Layout 호출
        noReadRecycle.setLayoutManager(llm2);
        noReadRecycle.setAdapter(noticeNoReadAdapter);
        noReadRecycle.setHasFixedSize(true);


        // 투표 댓글 리사이클러뷰
        commentRecycle = findViewById(R.id.recycle_comment);
        LinearLayoutManager llm3 = new LinearLayoutManager(this);
        llm3.setSmoothScrollbarEnabled(true);
        llm3.setAutoMeasureEnabled(true);


        // 댓글 리사이클러뷰 layout 호출
        commentRecycle.setLayoutManager(llm3);
        // Test 데이터 추가 2 (실제로 통신할 땐 VoteInfo의 Static 지워주어야 한다!)
        NoticeCommentResponse.NoticeComment noticeComment1 = new NoticeCommentResponse.NoticeComment(3, "그냥 죽여줘", 4, "test4", "string", "2023-05-04T01:04:28.224175");
        NoticeCommentResponse.NoticeComment noticeComment2 = new NoticeCommentResponse.NoticeComment(2, "뻥이야 살고 싶어", 3, "test3", "string", "2023-05-04T01:04:20.869323");
        NoticeCommentResponse.NoticeComment noticeComment3 = new NoticeCommentResponse.NoticeComment(1, "나를 죽여줘", 2, "test2", "string", "2023-05-04T01:04:11.809115");
        noticeCommentList.add(noticeComment1);
        noticeCommentList.add(noticeComment2);
        noticeCommentList.add(noticeComment3);

        noticeCommentAdapter = new NoticeCommentAdapter(noticeCommentList, this);
        commentRecycle.setAdapter(noticeCommentAdapter);
        commentRecycle.setHasFixedSize(true);

        et_comment = (EditText) findViewById(R.id.et_comment);
        setTextWatcher(et_comment);

        send = (ImageButton) findViewById(R.id.imgbtn_send);
        send.setOnClickListener(sendClickListener);
        send.setEnabled(false);
    }

    /** 뒤로 가기 버튼 클릭 리스너 **/
    View.OnClickListener backClickListener = v -> {
        finish();
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

    /** 댓글 남기기 버튼 클릭 리스너 **/
    View.OnClickListener sendClickListener = v -> {
        String s = et_comment.getText().toString().trim();
        if(!TextUtils.isEmpty(s))
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    };

    /** 댓글 남기기 입력창 관리 **/
    private void setTextWatcher(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    send.setEnabled(true);
                }
                else
                    send.setClickable(false);
            }
        });
    }
}
