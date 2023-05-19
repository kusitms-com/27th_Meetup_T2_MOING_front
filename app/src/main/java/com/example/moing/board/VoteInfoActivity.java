package com.example.moing.board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.LayoutTransition;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moing.R;

import java.util.ArrayList;
import java.util.List;

public class VoteInfoActivity extends AppCompatActivity {
    Button back, voteComplete;
    ImageButton modal;
    TextView title, nickName, time, content, voteCount, tvAnony, tv_noread;
    ImageView profile, ivAnony, noReadArrow;
    RecyclerView voteRecycle, noReadRecycle;
    CardView noreadCardView;
    LinearLayout layout_cardView;

    // 투표 리스트
    private List<VoteInfo.VoteChoice> voteChoiceList;
    // 투표 선택한 리스트
    private List<VoteInfo.VoteChoice> voteSelected;
    // 투표한 사람들의 이름 리스트
    private List<String> voteUserNameList;
    // 투표 안 읽은 사람의 리스트
    private List<String> voteNoReadList;

    private VoteInfoAdapterFirst voteInfoAdapterFirst;
    private VoteNoReadAdapter voteNoReadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_info);

        voteChoiceList = new ArrayList<>();
        voteSelected = new ArrayList<>();
        voteUserNameList = new ArrayList<>();
        voteNoReadList = new ArrayList<>();

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

        // 투표하기위한 리사이클러뷰
        voteRecycle = findViewById(R.id.recycle_vote);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setSmoothScrollbarEnabled(true);
        llm.setAutoMeasureEnabled(true);

        // 투표 공지를 안 읽은 사람들을 위한 리사이클러뷰
        noReadRecycle = findViewById(R.id.recycle_noread);
        GridLayoutManager llm2 = new GridLayoutManager(this, 4);
        llm2.setSmoothScrollbarEnabled(true);
        llm2.setAutoMeasureEnabled(true);

        // 투표 참여 인원 수
        voteCount = (TextView) findViewById(R.id.tv_count);
        // 익명 ImageView
        ivAnony = (ImageView) findViewById(R.id.iv_anony);
        // 익명 TextView
        tvAnony = (TextView) findViewById(R.id.tv_anony);
        // 투표 완료 버튼
        voteComplete = (Button) findViewById(R.id.btn_complete);
        voteComplete.setOnClickListener(completeClickListener);
        voteComplete.setClickable(false);

        // 안읽은 사람 리스트 상태 변경
        tv_noread = (TextView) findViewById(R.id.tv_noread);
        noReadArrow = (ImageView) findViewById(R.id.iv_noread);

        /** 테스트 데이터 **/
        voteUserNameList.add("손현석");
        voteUserNameList.add("곽승엽");

        // Test 데이터 추가 2 (실제로 통신할 땐 VoteInfo의 Static 지워주어야 한다!)
        VoteInfo.VoteChoice voteChoice1 = new VoteInfo.VoteChoice("4월 25일",3, new ArrayList<>(voteUserNameList));
        VoteInfo.VoteChoice voteChoice2 = new VoteInfo.VoteChoice("4월 26일",4, new ArrayList<>(voteUserNameList));
        VoteInfo.VoteChoice voteChoice3 = new VoteInfo.VoteChoice("4월 27일",2, new ArrayList<>(voteUserNameList));
        VoteInfo.VoteChoice voteChoice4 = new VoteInfo.VoteChoice("4월 28일",1, new ArrayList<>(voteUserNameList));
        voteChoiceList.add(voteChoice1);
        voteChoiceList.add(voteChoice2);
        voteChoiceList.add(voteChoice3);
        voteChoiceList.add(voteChoice4);
        /** 테스트 데이터 끝 **/

        // 투표 리사이클러뷰 어댑터 설정
        voteInfoAdapterFirst = new VoteInfoAdapterFirst(voteChoiceList, voteSelected, this);
        /** 투표 선택 클릭 리스너 **/
        voteInfoAdapterFirst.setOnItemClickListener(new VoteInfoAdapterFirst.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //Toast.makeText(getApplicationContext(), "pos : " + pos, Toast.LENGTH_SHORT).show();
                voteSelected = voteInfoAdapterFirst.getSelectedItems();
                Log.d("VoteInfoActivity", String.valueOf(voteSelected.size()));
                if(voteSelected.size() >= 1) {
                    voteComplete.setClickable(true);
                    voteComplete.setTextColor(Color.parseColor("#FFFFFF"));
                    voteComplete.setBackgroundColor(Color.parseColor("#FF725F"));
                }
                else {
                    voteComplete.setClickable(false);
                    voteComplete.setTextColor(Color.parseColor("#37383C"));
                    voteComplete.setBackgroundColor(Color.parseColor("#1A1919"));
                }
            }
        });

        // 예정 : 4명이 아직 안읽었어요 부터 백엔드 연동 해야함.
        /** 안읽은 사람 리스트에 대한 Adapter 테스트 코드 **/
        voteNoReadList.add("손현석");
        voteNoReadList.add("곽승엽");
        voteNoReadList.add("이지현");
        voteNoReadList.add("정승연");
        voteNoReadAdapter = new VoteNoReadAdapter(voteNoReadList,this);

        /** 안읽은 사람 Adapter 객체 생성 **/
        // adapter2 = new RecyclerViewAdapter(dataList2);
        // recyclerView2.setAdapter(adapter2);


        // 투표 현황 리사이클러뷰 Layout 호출
        voteRecycle.setLayoutManager(llm);
        voteRecycle.setAdapter(voteInfoAdapterFirst);
        voteRecycle.setHasFixedSize(true);

        /** CardView 작성**/
        noreadCardView = (CardView) findViewById(R.id.cardView_noread);
        noreadCardView.setOnClickListener(cardViewClickListener);
        layout_cardView = findViewById(R.id.layout_card);
        layout_cardView.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        // 안읽은 사람 리스트(GridLayout) 간격 설정
        int grid_spacing = (int) getResources().getDimension(R.dimen.grid_spacing); // 8dp 의미.
        VoteNoReadGridSpacing voteNoReadGridSpacing = new VoteNoReadGridSpacing(4, grid_spacing);
        noReadRecycle.addItemDecoration(voteNoReadGridSpacing);

        // 안읽은 사람 리사이클러뷰 Layout 호출
        noReadRecycle.setLayoutManager(llm2);
        noReadRecycle.setAdapter(voteNoReadAdapter);
        noReadRecycle.setHasFixedSize(true);


    }

    /** 뒤로 가기 버튼 클릭 리스너 **/
    View.OnClickListener backClickListener = v -> {
        finish();
    };

    /** 모달 버튼 클릭 리스너 **/
    View.OnClickListener modalClickListener = v -> {

    };

    /** 투표 완료 버튼 클릭 리스너 **/
    View.OnClickListener completeClickListener = v -> {
        voteSelected = voteInfoAdapterFirst.getSelectedItems();
        for ( VoteInfo.VoteChoice choice : voteSelected) {
            Log.d("VoteInfo", choice.getContent()+"에 투표하셨습니다.");
        }
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
}