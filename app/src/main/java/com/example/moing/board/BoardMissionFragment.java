package com.example.moing.board;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moing.MissionCreateActivity;
import com.example.moing.MissionListAdapter;
import com.example.moing.NoticeViewAdapter;
import com.example.moing.NoticeVoteActivity;
import com.example.moing.R;
import com.example.moing.Response.AllNoticeResponse;
import com.example.moing.Response.MissionListResponse;
import com.example.moing.retrofit.ChangeJwt;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClientJwt;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardMissionFragment extends Fragment {

    private RetrofitAPI apiService;
    private static final String PREF_NAME = "Token";
    private static final String JWT_ACCESS_TOKEN = "JWT_access_token";
    private SharedPreferences sharedPreferences;

    // RecyclerView
    private RecyclerView mRecyclerView;
    private List<MissionListResponse.MissionData> missionList;  // 추가된 부분

    Long teamId;
    ImageView createBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_mission, container, false);

        // Token을 사용할 SharedPreference
        sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Intent로 값 받기
        teamId = getActivity().getIntent().getLongExtra("teamId", 0);
        Log.d(TAG, "teamId 값 : " + String.valueOf(teamId));


        mRecyclerView = view.findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        createBtn = view.findViewById(R.id.mission_create_btn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), MissionCreateActivity.class);
                intent.putExtra("teamId", teamId);
                startActivity(intent);
            }
        });


        /** 공지사항 **/
        MissionList(teamId);

        return view;
    }

    /** 미션 리스트 출력 API **/
    public void MissionList(long teamId) {  // 수정된 부분
        String accessToken = sharedPreferences.getString(JWT_ACCESS_TOKEN, null); // 액세스 토큰 검색
        apiService = RetrofitClientJwt.getApiService(accessToken);

        Call<MissionListResponse> call = apiService.MissionList(accessToken, teamId);
        call.enqueue(new Callback<MissionListResponse>() {
            @Override
            public void onResponse(Call<MissionListResponse> call, Response<MissionListResponse> response) {
                MissionListResponse missionListResponse = response.body();
                String msg = missionListResponse.getMessage();
                if (msg.equals("미션을 조회하였습니다")) {
                    missionList = missionListResponse.getData();

                    MissionListAdapter adapter = new MissionListAdapter(missionList, requireContext());  // 수정된 부분
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    if (missionList.size() == 0) {
                        // 미션이 없는 경우 처리
                    }

                    adapter.setOnItemClickListener(new MissionListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int pos) {
                            /** 해당 공지사항으로 이동 **/
                            String s = pos + "번 미션 선택!";
                            Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (msg.equals("만료된 토큰입니다.")) {
                    ChangeJwt.updateJwtToken(requireContext());
                    MissionList(teamId);  // 수정된 부분
                }
            }

            @Override
            public void onFailure(Call<MissionListResponse> call, Throwable t) {
                Log.d(TAG, "공지 전체 조회 실패...");
            }
        });
    }

}
