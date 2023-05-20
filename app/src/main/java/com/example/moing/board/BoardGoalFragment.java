package com.example.moing.board;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.moing.R;
import com.example.moing.Response.BoardFireResponse;
import com.example.moing.Response.BoardMoimResponse;
import com.example.moing.Response.MakeTeamResponse;
import com.example.moing.retrofit.ChangeJwt;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClientJwt;
import com.example.moing.team.Team;
import com.example.moing.team.TeamAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardGoalFragment extends Fragment {

    ScrollView scroll;
    TextView teamName, curDate, userName, tv_toggle;
    ImageButton dot, down;
    ImageView teamImg, btnRefresh, fire, imgTeam, imgUser;
    Button teamDay, btnAll;
    ImageButton noticeLightSign, noticeLightNoSign, noticeDarkSign, noticeDarkNoSign;
    ImageButton voteLightSign, voteLightNoSign, voteDarkSign, voteDarkNoSign;

    RecyclerView recyclerView;
    BoardAdapter boardAdapter;

    // Dot Indicator
    BoardDotFragment boardDotFragment;

    private RetrofitAPI apiService;
    private static final String PREF_NAME = "Token";
    private static final String JWT_ACCESS_TOKEN = "JWT_access_token";
    private SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

    // API 연동시 필요한 변수
    String name, profileImg, remainPeriod, nowTime;
    Long teamId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_goal, container, false);

        // Intent로 값 받기
        Intent intent = getActivity().getIntent();
        //teamId = intent.getLongExtra("teamId");

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
        btnRefresh.setOnClickListener(newClickListener);
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

        // 공지버튼, 투표버튼 클릭 리스너
        noticeLightSign.setOnClickListener(noticeLignSignClickListener);
        voteDarkSign.setOnClickListener(voteDarkSignClickListener);
        /** 추가 구현 예정 **/



        // 리사이클러뷰
        recyclerView = view.findViewById(R.id.recycle_toggle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        // 리싸이클러뷰 스크롤 방지
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);

        // 공지 전체보기 버튼
        btnAll = (Button) view.findViewById(R.id.btn_all);
        btnAll.setOnClickListener(btnAllClickListener);
        // test data (삭제 예정)
        List<Board> boardList = new ArrayList<>();
        boardList.add(new Board("내용1입니다.","제목 1입니다.",1));
        boardList.add(new Board("내용2입니다.","제목 2입니다.",2));
        boardList.add(new Board("내용3입니다.","제목 3입니다.",3));

        // 리싸이클러뷰 어댑터 설정
        // adapter 설정
        boardAdapter = new BoardAdapter(boardList, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(boardAdapter);

        /** API 통신 **/
        // 소모임 제목, 사진, d-day, 날짜
        getApi();
        // 새로고침 버튼
        /** teamId -> Intent로 값 가져와야 함 **/
        apiFire();

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

        /** 리사이클러뷰 아이템 클릭 이벤트 처리 **/
        boardAdapter.setOnItemClickListener(new BoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                // 아이템 클릭 이벤트 처리
                String s = pos + "번 메뉴 선택...";
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
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

    // 새로 고침 버튼 클릭 시
    View.OnClickListener newClickListener = v -> {
        apiFire();
    };

    // Dot Indicator 버튼 클릭 시
    View.OnClickListener dotClickListener = v -> {
        boardDotFragment = new BoardDotFragment();
        boardDotFragment.show(requireActivity().getSupportFragmentManager(), boardDotFragment.getTag());
    };

    // 공지, 투표 전체보기 버튼 클릭 시
    View.OnClickListener btnAllClickListener = v -> {
        // 해당 공지사항 으로 이동
    };

    /** 공지사항 안 읽은 것 있을 때 버튼 클릭 리스너 **/
    View.OnClickListener noticeLignSignClickListener = v -> {

    };

    /** 투표 안 읽은 것 있을 때 버튼 클릭 리스너 **/
    View.OnClickListener voteDarkSignClickListener = v -> {
        // 투표 버튼은 하얗게 보이도록 함.
        voteDarkSign.setVisibility(View.GONE);
        voteLightSign.setVisibility(View.VISIBLE);
        // 공지사항 버튼은 안보이는 걸 보이게 함.
        noticeLightSign.setVisibility(View.GONE);
        noticeDarkSign.setVisibility(View.VISIBLE);

    };

    /** 소모임 제목, 기간, 사진, 날짜 설정 **/
    public void getApi() {
        String accessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null); // 액세스 토큰 검색
        apiService = RetrofitClientJwt.getApiService(accessToken);
        Call<BoardMoimResponse> call = apiService.moimInfo(accessToken);
        call.enqueue(new Callback<BoardMoimResponse>() {
            @Override
            public void onResponse(Call<BoardMoimResponse> call, Response<BoardMoimResponse> response) {
                BoardMoimResponse moimResponse = response.body();
                String msg = moimResponse.getMessage();
                if(msg.equals("목표보드 프로필을 조회하였습니다")) {
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
                    // 소모임 사진 설정
                    Glide.with(requireContext())
                            .load(profileImg)
                            .into(teamImg);
                    // D-Day 설정
                    teamDay.setText("종료 " + remainPeriod);
                    // nowTime 설정
                    curDate.setText(nowTime);
                }

                /** 만료된 토큰일 시 재실행 **/
                else if (msg.equals("만료된 토큰입니다.")) {
                    updateBoardInfoToken();
                }
            }

            @Override
            public void onFailure(Call<BoardMoimResponse> call, Throwable t) {

            }
        });
    }

    public void updateBoardInfoToken() {
        // Update Token
        ChangeJwt.updateJwtToken(requireContext());
        String accessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null); // 액세스 토큰 검색
        apiService = RetrofitClientJwt.getApiService(accessToken);
        Call<BoardMoimResponse> call2 = apiService.moimInfo(accessToken);
        call2.enqueue(new Callback<BoardMoimResponse>() {
            @Override
            public void onResponse(Call<BoardMoimResponse> call, Response<BoardMoimResponse> response) {
                BoardMoimResponse moimResponse = response.body();
                String msg = moimResponse.getMessage();
                if(msg.equals("목표보드 프로필을 조회하였습니다")) {
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
                    // 소모임 사진 설정
                    Glide.with(requireContext())
                            .load(profileImg)
                            .into(teamImg);
                    // D-Day 설정
                    teamDay.setText("종료 " + remainPeriod);
                    // nowTime 설정
                    curDate.setText(nowTime);
                }
            }
            @Override
            public void onFailure(Call<BoardMoimResponse> call, Throwable t) {
                Log.d("BoardGoalFragment", "만료된 토큰 - 연동 실패..");
            }
        });
    }

    public void apiFire() {
        String accessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null); // 액세스 토큰 검색
        apiService = RetrofitClientJwt.getApiService(accessToken);
        /** teamId Intent로 값 가져와야 한다! **/
        Call<BoardFireResponse> call = apiService.newBoardFire(accessToken, teamId);
        call.enqueue(new Callback<BoardFireResponse>() {
            @Override
            public void onResponse(Call<BoardFireResponse> call, Response<BoardFireResponse> response) {
                BoardFireResponse fireResponse = response.body();
                String msg = fireResponse.getMessage();

                if(msg.equals("개인별 개인별 미션 인증 현황 조회 성공")) {
                    Long data = fireResponse.getData();
                    checkFire(data);
                }
                else if (msg.equals("만료된 토큰입니다.")) {
                    updateApiFireToken();
                }
            }

            @Override
            public void onFailure(Call<BoardFireResponse> call, Throwable t) {

            }
        });
    }

    public void updateApiFireToken() {
        ChangeJwt.updateJwtToken(requireContext());
        String accessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null); // 액세스 토큰 검색
        apiService = RetrofitClientJwt.getApiService(accessToken);
        /** teamId Intent로 값 가져와야 한다! **/
        Call<BoardFireResponse> call2 = apiService.newBoardFire(accessToken, teamId);
        call2.enqueue(new Callback<BoardFireResponse>() {
            @Override
            public void onResponse(Call<BoardFireResponse> call, Response<BoardFireResponse> response) {
                BoardFireResponse fireResponse = response.body();
                String msg = fireResponse.getMessage();

                if(msg.equals("개인별 개인별 미션 인증 현황 조회 성공")) {
                    Long data = fireResponse.getData();
                    checkFire(data);
                }
            }

            @Override
            public void onFailure(Call<BoardFireResponse> call, Throwable t) {

            }
        });

    }

    /** fire Data에 따른 상태 변화 메서드 **/
    public void checkFire(Long num) {
        if (num <= 19) {
            // 배경색 변화
            Drawable d = getResources().getDrawable(R.drawable.ic_board_fire1_3x);
            fire.setBackground(d);
        }
        else if (num >= 20 && num <= 49) {
            Drawable d = getResources().getDrawable(R.drawable.ic_board_fire2_3x);
            fire.setBackground(d);
        }
        else if (num >= 50 && num <= 79) {
            Drawable d = getResources().getDrawable(R.drawable.ic_board_fire3_3x);
            fire.setBackground(d);
        }
        else {
            Drawable d = getResources().getDrawable(R.drawable.ic_board_fire4_3x);
            fire.setBackground(d);
        }
    }
}