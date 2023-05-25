package com.example.moing.board;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.moing.MainActivity;
import com.example.moing.R;
import com.google.android.material.tabs.TabLayout;

public class BoardActivity extends AppCompatActivity {

    BoardGoalFragment boardGoalFragment;
    BoardMissionFragment boardMissionFragment;
    int activityTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        // teamId 값 받아오기
        long teamId = getIntent().getLongExtra("teamId", 0);
        activityTask = getIntent().getIntExtra("activityTask", 0);

        Bundle bundle = new Bundle();
        bundle.putLong("teamId", teamId); // 전달할 값 설정

        // 목표보드 Fragment
        boardGoalFragment = new BoardGoalFragment();
        boardGoalFragment.setArguments(bundle);

        // 미션진행 Fragment
        boardMissionFragment = new BoardMissionFragment();
        boardMissionFragment.setArguments(bundle);

        // 홈 버튼
        ImageButton btn_home = (ImageButton) findViewById(R.id.imgBtn_home);
        btn_home.setOnClickListener(homeClickListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, boardGoalFragment).commit();
        TabLayout tabs = findViewById(R.id.tab_layout);
        tabs.addTab(tabs.newTab().setText("목표보드"));
        tabs.addTab(tabs.newTab().setText("미션진행"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("BoardActivity", "선택된 탭 : " + position);

                Fragment selected = null;
                switch (position) {
                    case 0:
                        selected = boardGoalFragment;
                        break;
                    case 1:
                        selected = boardMissionFragment;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // 홈 버튼 클릭 시
    View.OnClickListener homeClickListener = view ->{
        // 소모임 초대 코드로 바로 입장한 경우
        if(activityTask == 1) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else
            finish();
    };
}