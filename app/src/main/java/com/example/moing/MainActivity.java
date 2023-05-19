package com.example.moing;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.moing.team.Team;
import com.example.moing.team.TeamAdapter;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** sharedPreference 사용 **/

        // test data (삭제 예정)
        List<Team> teamList = new ArrayList<>();
        teamList.add(new Team(1,"소모임 이름",10,"시작일","종료일","이미지",false));
        teamList.add(new Team(2,"소모임 이름",10,"시작일","종료일","이미지",false));

        // 현재 소모임 수
        TextView tvCurTeam = findViewById(R.id.main_tv_cur_team);
        tvCurTeam.setText("진행 중 모임  "+teamList.size());

        // 소모임 목록 ViewPager
        ViewPager2 viewpager = findViewById(R.id.main_vp_team_list);

        // adapter 설정
        TeamAdapter teamAdapter = new TeamAdapter(teamList,this);
        viewpager.setAdapter(teamAdapter);

        // DotIndicator 설정
        DotsIndicator dotsIndicator = findViewById(R.id.main_dots_indicator);
        dotsIndicator.setViewPager2(viewpager);

    }
}