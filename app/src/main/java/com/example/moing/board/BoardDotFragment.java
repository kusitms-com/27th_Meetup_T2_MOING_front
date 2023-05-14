package com.example.moing.board;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.moing.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BoardDotFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_dot, container, false);

        ImageButton btnLink = view.findViewById(R.id.board_dot_btn_link);
        ImageButton btnFix = view.findViewById(R.id.board_dot_btn_fix);
        ImageButton btnClose = view.findViewById(R.id.board_dot_btn_close);

        btnLink.setOnClickListener(linkClickListener);
        btnClose.setOnClickListener(closeClickListener);

        return view;
    }
    View.OnClickListener linkClickListener = v ->{
        BoardInviteCodePopup customPopup = new BoardInviteCodePopup(requireContext());
        // 구현 예정 - 초대 코드를 서버로 부터 받아와 설정
        customPopup.setInviteCode("12345");
        // 팝업의 위치와 크기를 설정 하여 표시
        customPopup.showAtLocation(v, Gravity.CENTER, 0, 0);
        // BoardDotFragment 도 닫음
        customPopup.setOnDismissListener(this::dismiss);
    };

    // 뒤로 가기
    View.OnClickListener closeClickListener = v -> dismiss();
}