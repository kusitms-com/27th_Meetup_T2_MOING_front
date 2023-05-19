package com.example.moing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class NoticeVoteActivity extends AppCompatActivity {

    public NoticeRecyclerAdapter mRecyclerAdapter;
    public NoticeRecyclerAdapter mRecyclerAdapter2;

    public ArrayList<NoticeItem> mNoticeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_vote);

        // 공지사항
        RecyclerView mRecyclerView = findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // 투표
        RecyclerView mRecyclerView2 = findViewById(R.id.recycler2);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView2.setLayoutManager(linearLayoutManager2);

        mRecyclerAdapter = new NoticeRecyclerAdapter();
        mRecyclerAdapter2 = new NoticeRecyclerAdapter();

        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView2.setAdapter(mRecyclerAdapter2);

        mNoticeItem = new ArrayList<>();
        mNoticeItem.add(new NoticeItem("뿅뿅이", "아 배고프다", "뼈해장국먹을사람", "15", R.drawable.notice_profile, R.drawable.notice_crown, R.drawable.notice_dot, R.drawable.notice_message));
        mNoticeItem.add(new NoticeItem("뿅뿅이", "아 배고프다", "뼈해장국먹을사람", "5", R.drawable.notice_profile, R.drawable.notice_crown, R.drawable.notice_dot, R.drawable.notice_message));
        mNoticeItem.add(new NoticeItem("뿅뿅이", "아 배고프다", "뼈해장국먹을사람", "15", R.drawable.notice_profile, R.drawable.notice_crown, R.drawable.notice_dot, R.drawable.notice_message));
        mNoticeItem.add(new NoticeItem("뿅뿅이", "아 배고프다", "뼈해장국먹을사람", "15", R.drawable.notice_profile, R.drawable.notice_crown, R.drawable.notice_dot, R.drawable.notice_message));
        mRecyclerAdapter.setNoticeList(this, mNoticeItem);

        mNoticeItem = new ArrayList<>();
        mNoticeItem.add(new NoticeItem("투표", "책 추천 받아요", "뼈해장국먹을사람", "15", R.drawable.notice_profile, R.drawable.notice_crown, R.drawable.notice_dot, R.drawable.notice_message));
        mNoticeItem.add(new NoticeItem("뿅뿅이", "아 배고프다", "뼈해장국먹을사람", "5", R.drawable.notice_profile, R.drawable.notice_crown, R.drawable.notice_dot, R.drawable.notice_message));
        mNoticeItem.add(new NoticeItem("뿅뿅이", "아 배고프다", "뼈해장국먹을사람", "15", R.drawable.notice_profile, R.drawable.notice_crown, R.drawable.notice_dot, R.drawable.notice_message));
        mNoticeItem.add(new NoticeItem("뿅뿅이", "아 배고프다", "뼈해장국먹을사람", "15", R.drawable.notice_profile, R.drawable.notice_crown, R.drawable.notice_dot, R.drawable.notice_message));
        mRecyclerAdapter2.setNoticeList(this, mNoticeItem);

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