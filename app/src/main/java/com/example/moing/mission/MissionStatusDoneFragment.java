package com.example.moing.mission;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moing.R;
import com.example.moing.team.Team;

import java.util.ArrayList;
import java.util.List;

public class MissionStatusDoneFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mission_status_done, container, false);

        ArrayList<Mission> missions = new ArrayList<>();
        missions.add(new Mission(1,"1","img","COMPLETE","archive","submitDate"));
        missions.add(new Mission(2,"1","img","PENDING","archive","submitDate"));
        missions.add(new Mission(3,"1","img","COMPLETE","archive","submitDate"));
        missions.add(new Mission(4,"1","img","COMPLETE","archive","submitDate"));
        missions.add(new Mission(1,"1","img","PENDING","archive","submitDate"));

        // 리사이클러뷰
        RecyclerView recyclerView = view.findViewById(R.id.mission_status_done_rv);

        // 리사이클러뷰에 사용할 레이아웃매니저 - Grid 2개의 열
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        // 리사이클러뷰에 사용할 어댑터 설정
        MissionDoneAdapter adapter = new MissionDoneAdapter(missions);
        recyclerView.setAdapter(adapter);


        return view;
    }
}