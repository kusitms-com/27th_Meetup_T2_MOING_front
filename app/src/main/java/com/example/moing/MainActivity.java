package com.example.moing;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moing.team.SwipeCallback;
import com.example.moing.team.Team;
import com.example.moing.team.TeamAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // test data (삭제 예정)
        List<Team> teamList = new ArrayList<>();
        teamList.add(new Team("소모임 이름","소모임 몇명","시작일","종료일"));

        // 소모임 목록 리사이클러뷰
        RecyclerView recyclerView = findViewById(R.id.rvTeamList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        // adapter 설정
        TeamAdapter teamAdapter = new TeamAdapter(teamList);
        recyclerView.setAdapter(teamAdapter);

        // ItemTouchHelper 생성 및 연결
        SwipeCallback swipeCallback = new SwipeCallback(recyclerView, teamAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}