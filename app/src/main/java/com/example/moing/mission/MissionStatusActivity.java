package com.example.moing.mission;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.moing.R;
import com.google.android.material.tabs.TabLayout;

public class MissionStatusActivity extends AppCompatActivity {
    private Fragment fragmentDone;
    private Fragment fragmentUnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_status);

        // 더미 - 삭제 예정
        String myStatus = "COMPLETE";

        // 내 인증 상태 확인 컴포넌트 - 진행 상태에 따라 다르게 나옴
        ImageView ivStatus = findViewById(R.id.mission_status_iv_status);
        TextView tvMissionTitle = findViewById(R.id.mission_status_tv_mission_title);
        setMyStatus(myStatus, ivStatus, tvMissionTitle);


        // 뒤로가기 - 클릭 시 종료
        ImageButton btnBack = findViewById(R.id.mission_status_btn_back);
        btnBack.setOnClickListener(view -> finish());

        // 인증 완료 Fragment
        fragmentDone = new MissionStatusDoneFragment();

        // 인증 미완료 Fragment
        fragmentUnDone  = new MissionStatusUndoneFragment();

        // FrameLayout 기본 Fragment - 인증 완료, hide(인증 미완료)
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mission_status_fl, fragmentDone)
                .add(R.id.mission_status_fl, fragmentUnDone)
                .hide(fragmentUnDone)
                .commit();

        // TabLayout - 인증 완료 / 인증 미완료
        TabLayout tabs = findViewById(R.id.mission_status_tl);
        tabs.addTab(tabs.newTab().setText("인증완료"));
        tabs.addTab(tabs.newTab().setText("인증미완료"));
        tabs.addOnTabSelectedListener(onTabSelectedListener);
    }

    TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            // 선택한 Fragment 로 교체
            int position = tab.getPosition();
            Fragment showFragment = null;
            Fragment hideFragment = null;
            switch (position) {
                case 0:
                    showFragment = fragmentDone;
                    hideFragment = fragmentUnDone;
                    break;
                case 1:
                    showFragment = fragmentUnDone;
                    hideFragment = fragmentDone;
                    break;
            }

            if (showFragment != null && hideFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .show(showFragment)
                        .hide(hideFragment)
                        .commit();
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {}

        @Override
        public void onTabReselected(TabLayout.Tab tab) {}
    };

    private void setMyStatus(String myStatus, ImageView ivStatus, TextView tvMissionTitle){
        if(myStatus.equals("COMPLETE"))
        {
            ivStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_mission_status_done));
            tvMissionTitle.setPaintFlags(tvMissionTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else if(myStatus.equals("PENDING")){
            ivStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_mission_status_pending));
            tvMissionTitle.setPaintFlags(tvMissionTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            ivStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_mission_status_undone));
            tvMissionTitle.setPaintFlags(tvMissionTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }
}