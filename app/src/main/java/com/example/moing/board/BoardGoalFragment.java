package com.example.moing.board;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.moing.R;

public class BoardGoalFragment extends Fragment {

    ScrollView scroll;
    TextView teamName, curDate, userName, tv_toggle;
    ImageButton dot, down;
    ImageView teamImg, btnRefresh, fire, imgTeam, imgUser;
    Button teamDay;
    ImageButton noticeLightSign, noticeLightNoSign, noticeDarkSign, noticeDarkNoSign;
    ImageButton voteLightSign, voteLightNoSign, voteDarkSign, voteDarkNoSign;

    // Dot Indicator
    BoardDotFragment boardDotFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_goal, container, false);

        // 스크롤뷰
        scroll = view.findViewById(R.id.scrollView);
        // 소모임 이름
        teamName = view.findViewById(R.id.tv_team);
        // Dot Indicator & 클릭 리스너
        dot = view.findViewById(R.id.imgbtn_dotIndicator);
        dot.setOnClickListener(dotClickListener);
        // 배경 사진
        teamImg = view.findViewById(R.id.img);
        // 종료 일자
        teamDay = view.findViewById(R.id.btn_dDay);
        // 새로고침 날짜, 시간
        curDate = view.findViewById(R.id.tv_date);
        // 새로고침 버튼
        btnRefresh = view.findViewById(R.id.imgBtn_refresh);
        // 불 이미지
        fire = view.findViewById(R.id.iv_fire);
        // 유저 닉네임 텍스트뷰
        userName = view.findViewById(R.id.tv_userHere);
        // 팀 불 프로그레스 이미지
        imgTeam = view.findViewById(R.id.iv_team);
        // 유저 불 프로그레스 이미지
        imgUser = view.findViewById(R.id.iv_user);
        // 다운 버튼 & 리스너
        down = view.findViewById(R.id.imgbtn_down);
        down.setOnClickListener(downClickListener);
        // 공지 or 투표 확인 텍스트뷰
        tv_toggle = view.findViewById(R.id.tv_toggle_text);

        // 공지 버튼 4개
        noticeLightSign = (ImageButton) view.findViewById(R.id.btn_notice_light_sign);
        noticeLightNoSign = (ImageButton) view.findViewById(R.id.btn_notice_light_noSign);
        noticeDarkSign = (ImageButton) view.findViewById(R.id.btn_notice_dark_sign);
        noticeDarkNoSign = (ImageButton) view.findViewById(R.id.btn_notice_dark_noSign);

        // 투표 버튼 4개
        voteLightSign = (ImageButton) view.findViewById(R.id.btn_vote_light_sign);
        voteLightNoSign = (ImageButton) view.findViewById(R.id.btn_vote_light_noSign);
        voteDarkSign = (ImageButton) view.findViewById(R.id.btn_vote_dark_sign);
        voteDarkNoSign = (ImageButton) view.findViewById(R.id.btn_vote_dark_noSign);




        // 스크롤 변화시 이미지 삭제
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY) {
                        // 스크롤 다운 시 다운 버튼 숨김
                        down.setVisibility(View.GONE);
                    }
                }
            });
        }
        return view;

    }

    // 다운 버튼 클릭시
    View.OnClickListener downClickListener = v -> {
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
                // 아래로 내려가면 안보이도록 설정
                down.setVisibility(View.INVISIBLE);
            }
        });
    };

    // Dot Indicator 버튼 클릭 시
    View.OnClickListener dotClickListener = v -> {
        boardDotFragment = new BoardDotFragment();
        boardDotFragment.show(requireActivity().getSupportFragmentManager(), boardDotFragment.getTag());
    };
}