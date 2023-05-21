package com.example.moing.board;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moing.NoticeVoteActivity;
import com.example.moing.R;
import com.example.moing.Response.BoardFireResponse;
import com.example.moing.Response.BoardMoimResponse;
import com.example.moing.Response.BoardNoReadNoticeResponse;
import com.example.moing.Response.BoardNoReadVoteResponse;
import com.example.moing.retrofit.ChangeJwt;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClientJwt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardGoalFragment extends Fragment {
    private static final String TAG = "BoardGoalFragment";

    ScrollView scroll;
    TextView teamName, curDate, userName, tv_toggle, tv_all, tv_all2;
    TextView btn_notice, btn_vote;
    ImageView notice_sign, vote_sign;
    ImageButton dot, down;
    ImageView teamImg, btnRefresh, fire, imgTeam, imgUser;
    Button teamDay, btnAll;

    // Dot Indicator
    BoardDotFragment boardDotFragment;

    private RetrofitAPI apiService;
    private static final String PREF_NAME = "Token";
    private static final String JWT_ACCESS_TOKEN = "JWT_access_token";
    private SharedPreferences sharedPreferences;

    // RecyclerView
    RecyclerView recyclerView;
    BoardGoalAdapter<?> boardGoalAdapter;

    // API 연동시 필요한 변수
    String name, profileImg, remainPeriod, nowTime;
    Long teamId;
    List<BoardNoReadNoticeResponse.NoticeData> noticeDataList;
    List<BoardNoReadVoteResponse.VoteData> voteDataList;
    private int noReadNotice, noReadVote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_goal, container, false);

        // Token을 사용할 SharedPreference
        sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Intent로 값 받기
        teamId = getActivity().getIntent().getLongExtra("teamId", 0);
        Log.d(TAG, "teamId 값 : " + String.valueOf(teamId));

        // 버튼 클릭 리스너 객체 생성
        BtnOnClickListener btnOnClickListener = new BtnOnClickListener();

        // 스크롤뷰
        scroll = view.findViewById(R.id.scrollView);
        // 소모임 이름
        teamName = view.findViewById(R.id.tv_team);
        // Dot Indicator
        dot = view.findViewById(R.id.imgbtn_dotIndicator);
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
        // 다운 버튼
        down = view.findViewById(R.id.imgbtn_down);
        // 공지사항, 투표
        btn_notice = view.findViewById(R.id.btn_notice);
        btn_vote = view.findViewById(R.id.btn_vote);
        notice_sign = view.findViewById(R.id.iv_notice_sign);
        vote_sign = view.findViewById(R.id.iv_vote_sign);
        // 공지 or 투표 확인 텍스트뷰
        tv_toggle = view.findViewById(R.id.tv_toggle_text);
        // 공지 모두 읽었을 때 TextView
        tv_all = (TextView) view.findViewById(R.id.tv_all);
        tv_all2 = (TextView) view.findViewById(R.id.tv_all2);
        // 공지 전체보기 버튼
        btnAll = (Button) view.findViewById(R.id.btn_all);

        /** 원래 위치 **/
        // 안 읽은 공지, 투표의 개수 초기값 설정
        noReadNotice = noReadVote = -1;

        // 리사이클러뷰
        recyclerView = view.findViewById(R.id.recycle_toggle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        // 리싸이클러뷰 스크롤 방지
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        /** 공지 안 읽은 순을 위한 ArrayList **/
        noticeDataList = new ArrayList<>();
        /** 투표 안 읽은 순을 위한 ArrayList **/
        voteDataList = new ArrayList<>();

        /** API 통신 **/
        // 소모임 제목, 사진, d-day, 날짜
        getApi();
        /** 불 그래픽 통신 **/
        apiFire();
        /** 안 읽은 투표 API **/
        noReadVote(); // voteDataList, noReadVote 값 변경됨!!
        /** 안 읽은 공지 API **/
        noReadNotice(); // noticeDataList, noReadNotice 값 변경됨!!

        Log.d(TAG, "통신 후 noReadNotice 값 : " + noReadNotice);
        Log.d(TAG, "통신 후 noReadVote 값 : " + noReadVote);

        /** API 통신 완료 **/


        /** 버튼 클릭 리스너 **/
        // Dot Indicator, 새로고침, down 버튼 클릭 리스너
        dot.setOnClickListener(btnOnClickListener);
        btnRefresh.setOnClickListener(btnOnClickListener);
        down.setOnClickListener(btnOnClickListener);
        // 공지버튼, 투표버튼 클릭 리스너
        btn_notice.setOnClickListener(btnOnClickListener);
        btn_vote.setOnClickListener(btnOnClickListener);
        // 공지 전체 보기 클릭 리스너
        btnAll.setOnClickListener(btnOnClickListener);

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

    private class BtnOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // dot Indicator 버튼 클릭 시
                case R.id.imgbtn_dotIndicator:
                    boardDotFragment = new BoardDotFragment();
                    boardDotFragment.show(requireActivity().getSupportFragmentManager(), boardDotFragment.getTag());
                    break;
                // 새로 고침 버튼 클릭 시 불씨 그래픽 변화
                case R.id.imgBtn_refresh:
                    apiFire();
                    break;
                // 아래로 버튼 클릭 시 스크롤 변화
                case R.id.imgbtn_down:
                    scroll.post(new Runnable() {
                        @Override
                        public void run() {
                            scroll.fullScroll(View.FOCUS_DOWN);
                            // 아래로 내려가면 안보이도록 설정
                            down.setVisibility(View.INVISIBLE);
                        }
                    });
                    break;
                /** 투표 버튼 클릭 시 **/
                case R.id.btn_vote:
                    Log.d(TAG, "투표 버튼 클릭");
                    noReadVote();
                    break;
                /** 공지 버튼 클릭 시 **/
                case R.id.btn_notice:
                    noReadNotice();
                    break;

                /** 공지 전체 보기 클릭 **/
                case R.id.btn_all:
                    Intent intent = new Intent(requireContext(), NoticeVoteActivity.class);
                    intent.putExtra("teamId", teamId);
                    startActivity(intent);
                    break;
            }
        }
    }

    /**
     * 소모임 제목, 기간, 사진, 날짜 설정
     **/
    public void getApi() {
        String accessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null);
        Log.d(TAG, accessToken);
        apiService = RetrofitClientJwt.getApiService(accessToken);
        Call<BoardMoimResponse> call = apiService.moimInfo(accessToken, teamId);
        call.enqueue(new Callback<BoardMoimResponse>() {
            @Override
            public void onResponse(Call<BoardMoimResponse> call, Response<BoardMoimResponse> response) {
                BoardMoimResponse moimResponse = response.body();
                String msg = moimResponse.getMessage();
                if (msg.equals("목표보드 프로필을 조회하였습니다")) {
                    BoardMoimResponse.Data data = moimResponse.getData();
                    name = data.getName();
                    profileImg = data.getProfileImg();
                    remainPeriod = data.getRemainingPeriod();
                    nowTime = data.getNowTime();

                    /** 데이터 확인 **/
                    Log.d("BOARDGOALFRAGMENT", name);
                    Log.d("BOARDGOALFRAGMENT", profileImg);
                    Log.d("BOARDGOALFRAGMENT", remainPeriod);
                    Log.d("BOARDGOALFRAGMENT", nowTime);

                    // 소모임 이름 설정
                    teamName.setText(name);
                    if (isAdded()) {
                        Glide.with(getContext())
                                .load(new File(profileImg))
                                .into(teamImg);
                    }
//                    // 소모임 사진 설정
//                    Glide.with(getContext())
//                            .load(new File(profileImg))
//                            .into(teamImg);
                    // D-Day 설정
                    teamDay.setText("종료 " + remainPeriod);
                    // nowTime 설정
                    curDate.setText(nowTime);
                }

                /** 만료된 토큰일 시 재실행 **/
                else if (msg.equals("만료된 토큰입니다.")) {
                    ChangeJwt.updateJwtToken(requireContext());
                    getApi();
                }
            }

            @Override
            public void onFailure(Call<BoardMoimResponse> call, Throwable t) {
                Log.d("BoardGoalFragment", "만료된 토큰 - 연동 실패..");
            }
        });
    }

    /**
     * 불씨 (그래픽) 변화 API 통신
     **/
    public void apiFire() {
        String accessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null); // 액세스 토큰 검색
        apiService = RetrofitClientJwt.getApiService(accessToken);

        Call<BoardFireResponse> call = apiService.newBoardFire(accessToken, teamId);
        call.enqueue(new Callback<BoardFireResponse>() {
            @Override
            public void onResponse(Call<BoardFireResponse> call, Response<BoardFireResponse> response) {
                BoardFireResponse fireResponse = response.body();
                String msg = fireResponse.getMessage();

                Log.d(TAG, msg);

                if (msg.equals("개인별 개인별 미션 인증 현황 조회 성공")) {
                    Long data = fireResponse.getData();
                    checkFire(data);
                } else if (msg.equals("만료된 토큰입니다.")) {
                    ChangeJwt.updateJwtToken(requireContext());
                    apiFire();
                }
            }

            @Override
            public void onFailure(Call<BoardFireResponse> call, Throwable t) {
                Log.d(TAG, "불씨 변화 실패...");
            }
        });
    }

    /**
     * fire Data에 따른 상태 변화 메서드
     **/
    public void checkFire(Long num) {
        if (num <= 19) {
            // 배경색 변화
            Drawable d = ContextCompat.getDrawable(requireContext(), R.drawable.ic_board_fire1_3x);
            fire.setBackground(d);
        } else if (num >= 20 && num <= 49) {
            Drawable d = ContextCompat.getDrawable(requireContext(), R.drawable.ic_board_fire2_3x);
            fire.setBackground(d);
        } else if (num >= 50 && num <= 79) {
            Drawable d = ContextCompat.getDrawable(requireContext(), R.drawable.ic_board_fire3_3x);
            fire.setBackground(d);
        } else {
            Drawable d = ContextCompat.getDrawable(requireContext(), R.drawable.ic_board_fire4_3x);
            fire.setBackground(d);
        }
    }

    /**
     * 공지 안 읽은 것만 조회
     **/
    public void noReadNotice() {
        String accessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null); // 액세스 토큰 검색
        apiService = RetrofitClientJwt.getApiService(accessToken);

        Call<BoardNoReadNoticeResponse> call = apiService.noReadNotice(accessToken, teamId);
        call.enqueue(new Callback<BoardNoReadNoticeResponse>() {
            @Override
            public void onResponse(Call<BoardNoReadNoticeResponse> call, Response<BoardNoReadNoticeResponse> response) {
                BoardNoReadNoticeResponse noReadResponse = response.body();
                String msg = noReadResponse.getMessage();
                if (msg.equals("확인하지 않은 공지를 최신순으로 조회하였습니다")) {
                    // 리스트 저장
                    noticeDataList = noReadResponse.getData();
                    boardGoalAdapter = new BoardGoalAdapter<BoardNoReadNoticeResponse.NoticeData>
                            (noticeDataList, BoardGoalFragment.this);
                    recyclerView.setAdapter(boardGoalAdapter);
                    boardGoalAdapter.notifyDataSetChanged();

                    /** 리사이클러뷰 아이템 클릭 이벤트 처리 **/
                    boardGoalAdapter.setOnItemClickListener(new BoardGoalAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int pos) {
                            // 아이템 클릭 이벤트 처리
                            String s = pos + "번 메뉴 선택...";
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                        }
                    });

                    // 안 읽은 공지 개수 저장
                    noReadNotice = noticeDataList.size();
                    checkNoRead(noReadNotice, "공지");

                    Log.d(TAG, "noreadNotice 통신 중 : " + noReadNotice);
                } else if (msg.equals("만료된 토큰입니다.")) {
                    ChangeJwt.updateJwtToken(requireContext());
                    noReadNotice();
                }
            }

            @Override
            public void onFailure(Call<BoardNoReadNoticeResponse> call, Throwable t) {
                Log.d(TAG, "최신순 공지 조회 연동 실패 ...");
            }
        });
    }

    /**
     * 투표 안 읽은 것만 조회
     **/
    public void noReadVote() {
        String accessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null); // 액세스 토큰 검색
        apiService = RetrofitClientJwt.getApiService(accessToken);

        Call<BoardNoReadVoteResponse> call = apiService.noReadVote(accessToken, teamId);
        call.enqueue(new Callback<BoardNoReadVoteResponse>() {
            @Override
            public void onResponse(Call<BoardNoReadVoteResponse> call, Response<BoardNoReadVoteResponse> response) {
                BoardNoReadVoteResponse voteResponse = response.body();
                String msg = voteResponse.getMessage();
                if (msg.equals("확인하지 않은 투표를  최신순으로 조회하였습니다")) {
                    voteDataList = voteResponse.getData();
                    noReadVote = voteDataList.size();

                    boardGoalAdapter = new BoardGoalAdapter<BoardNoReadVoteResponse.VoteData>(voteDataList, BoardGoalFragment.this);
                    recyclerView.setAdapter(boardGoalAdapter);
                    boardGoalAdapter.notifyDataSetChanged();

                    /** 리사이클러뷰 아이템 클릭 이벤트 처리 **/
                    boardGoalAdapter.setOnItemClickListener(new BoardGoalAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int pos) {
                            // 아이템 클릭 이벤트 처리
                            String s = pos + "번 메뉴 선택...";
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                        }
                    });


                    checkNoRead(noReadVote, "투표");

                    Log.d(TAG, "noreadNotice : " + noReadVote);

                } else if (msg.equals("만료된 토큰입니다.")) {
                    ChangeJwt.updateJwtToken(requireContext());
                    noReadVote();
                }
            }

            @Override
            public void onFailure(Call<BoardNoReadVoteResponse> call, Throwable t) {
                Log.d(TAG, "안 읽은 투표 최신순 연동 실패 ...");
            }
        });
    }


    /**
     * 확인하지 않은 공지/투표가 "3개" 있어요 문장에서 "3개"만 색깔 변경 처리
     **/
    public void checkNoRead(int num, String str) {
        String text = str.equals("공지") ? "공지" : "투표";
        Log.d(TAG, "checkNoRead의 text : " + text);
        String result = "확인하지 않은 " + text + "가 " + num + "개 있어요";
        // 확인하지 않은 공지가 "3개" 있어요 할때 "3개"만 글자 색상 변경
        SpannableString sp = new SpannableString(result); // 객체 생성
        String word = num + "개";
        int start = result.indexOf(word);
        int end = start + word.length();
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#FF725F")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_toggle.setText(sp);

        ColorStateList co = ColorStateList.valueOf(requireContext().getResources().getColor(R.color.secondary_grey_black_1));
        ColorStateList co2 = ColorStateList.valueOf(requireContext().getResources().getColor(R.color.secondary_grey_black_13));

        Log.d(TAG, "수행완료1");
        // List의 개수가 0개일 때
        if (num ==0) {
            recyclerView.setVisibility(View.GONE);
            String allCheck = "모든 " + text + "를 다 확인했어요";
            tv_all.setText(allCheck);
            tv_all.setVisibility(View.VISIBLE);
            tv_all2.setVisibility(View.VISIBLE);

            Log.d(TAG, "수행완료2");
            if(str.equals("공지")) {
                notice_sign.setVisibility(View.INVISIBLE);
                btn_notice.setBackgroundTintList(co);
                btn_vote.setBackgroundTintList(co2);
                Log.d(TAG, "수행완료3");
            }
            else {
                vote_sign.setVisibility(View.INVISIBLE);
                btn_notice.setBackgroundTintList(co2);
                btn_vote.setBackgroundTintList(co);
                Log.d(TAG, "수행완료4");
            }
        }

        // List의 개수가 한 개 이상일 때
        else {
            Log.d(TAG, "수행완료5");
            recyclerView.setVisibility(View.VISIBLE);
            tv_all.setVisibility(View.GONE);
            tv_all2.setVisibility(View.GONE);

            if(str.equals("공지")) {
                Log.d(TAG, "수행완료6");
                notice_sign.setVisibility(View.VISIBLE);
                btn_notice.setBackgroundTintList(co);
                btn_vote.setBackgroundTintList(co2);
            }
            else {
                Log.d(TAG, "수행완료7");
                vote_sign.setVisibility(View.VISIBLE);
                btn_notice.setBackgroundTintList(co2);
                btn_vote.setBackgroundTintList(co);
            }
        }
    }
}