package com.example.moing.board;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import com.example.moing.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BoardDotFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_dot, container, false);

        ImageButton btnClose = view.findViewById(R.id.board_dot_btn_close);
        ImageButton btnLink = view.findViewById(R.id.board_dot_btn_link);
        ImageButton btnFix = view.findViewById(R.id.board_dot_btn_fix);

        btnClose.setOnClickListener(closeClickListener);
        btnLink.setOnClickListener(linkClickListener);
        btnFix.setOnClickListener(fixClickListener);

        return view;
    }
    // 뒤로 가기
    View.OnClickListener closeClickListener = v -> dismiss();

    // 초대 코드 복사 팝업 띄우기
    View.OnClickListener linkClickListener = v ->{
        BoardInviteCodeFragment customPopup = new BoardInviteCodeFragment(requireContext());
        // 구현 예정 - 초대 코드를 서버로 부터 받아와 설정, 소모임장만 접근 가능하게 설정
        customPopup.setInviteCode("12345");
        // 팝업의 위치와 크기를 설정 하여 표시
        customPopup.showAtLocation(v, Gravity.CENTER, 0, 0);
        // BoardDotFragment 도 닫음
        customPopup.setOnDismissListener(this::dismiss);
    };

    // 소모임 정보 수정 액티비티 실행
    View.OnClickListener fixClickListener = v -> {
        Intent intent = new Intent(getContext(), BoardFixTeamActivity.class);
        startActivity(intent);
    };


}