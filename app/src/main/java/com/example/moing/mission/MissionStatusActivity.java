package com.example.moing.mission;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
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

        // 구현 예정 - 더보기 버튼. 클릭 시 어떤 것을 띄우는지
        // 구현 예정 - 인증 상태를 확인할 수 있는 컴포넌트 디자인 및 기능 추가


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
}