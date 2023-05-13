package com.example.moing.board;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.moing.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BoardDotFragment extends BottomSheetDialogFragment {

    ImageButton link, fix, close;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_dot, container, false);

        link = view.findViewById(R.id.imgbtn_link);
        fix = view.findViewById(R.id.imgbtn_fix);
        close = view.findViewById(R.id.imgbtn_close);

        close.setOnClickListener(closeClickListener);



        return view;
    }

    // 뒤로 가기
    View.OnClickListener closeClickListener = v -> {
      dismiss();
    };
}