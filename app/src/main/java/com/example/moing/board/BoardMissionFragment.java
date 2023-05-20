package com.example.moing.board;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.moing.R;
import com.example.moing.mission.MissionStatusActivity;

public class BoardMissionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_mission, container, false);

        Button btn = view.findViewById(R.id.board_mission_btn_temp);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MissionStatusActivity.class);
            startActivity(intent);
        });

        return view;
    }
}