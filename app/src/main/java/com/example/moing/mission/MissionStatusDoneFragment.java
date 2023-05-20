package com.example.moing.mission;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moing.R;

import java.util.ArrayList;
import java.util.List;

public class MissionStatusDoneFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mission_status_done, container, false);

        List<Mission> missionList = new ArrayList<>();
        missionList.add(new Mission(1,"1","img","COMPELTE","archive","submitDate"));
        missionList.add(new Mission(2,"1","img","COMPELTE","archive","submitDate"));
        missionList.add(new Mission(4,"1","img","PENDING","archive","submitDate"));
        missionList.add(new Mission(1,"1","img","PENDING","archive","submitDate"));
        missionList.add(new Mission(1,"1","img","COMPELTE","archive","submitDate"));
        missionList.add(new Mission(2,"1","img","COMPELTE","archive","submitDate"));
        missionList.add(new Mission(4,"1","img","PENDING","archive","submitDate"));
        missionList.add(new Mission(1,"1","img","PENDING","archive","submitDate"));

        // 리사이클러뷰
        RecyclerView recyclerView = view.findViewById(R.id.mission_status_done_rv);

        // 리사이클러뷰에 사용할 레이아웃매니저 - Grid 2개의 열
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        // 리사이클러뷰 아이템들 간 간격 설정
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 24, 0, 24);
            }
        });

        // 리사이클러뷰에 사용할 어댑터 설정
        MissionDoneAdapter adapter = new MissionDoneAdapter(missionList);
        recyclerView.setAdapter(adapter);

        TextView tvNum = view.findViewById(R.id.mission_status_done_tv_num);
        tvNum.setText(missionList.size()+"명");


        return view;
    }
}