package com.example.moing;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class NoticeVoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_vote);

        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        tabHost1.setup();

        // 선택된 탭은 흰색, 선택되지 않은 탭은 회색
        tabHost1.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {

                // 선택되지 않은 것
                // 탭의 각 제목의 색깔을 바꾸기 위한 부분
                for (int i = 0; i < tabHost1.getTabWidget().getChildCount(); i++) {

                    TextView tv = (TextView) tabHost1.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tv.setTextColor(Color.parseColor("#535457"));
                }

                // 선택된 것
                // 선택되는 탭에 대한 제목의 색깔을 바꾸 부분

                TextView tp = (TextView) tabHost1.getTabWidget().getChildAt(tabHost1.getCurrentTab()).findViewById(android.R.id.title);
                tp.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("공지사항");
        tabHost1.addTab(ts1);

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("투표");
        tabHost1.addTab(ts2);

    }
}