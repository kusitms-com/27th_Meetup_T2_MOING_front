package com.example.moing.board;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.moing.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BoardDotFragment extends BottomSheetDialogFragment {
    private Dialog inviteDialog;
    private Dialog inviteDialogImpossible;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_dot, container, false);

        // 닫기 버튼
        ImageButton btnClose = view.findViewById(R.id.board_dot_btn_close);
        btnClose.setOnClickListener(closeClickListener);

        // 초대 코드 버튼
        ImageButton btnLink = view.findViewById(R.id.board_dot_btn_link);
        btnLink.setOnClickListener(linkClickListener);

        // 소모임 수정 버튼
        ImageButton btnFix = view.findViewById(R.id.board_dot_btn_fix);
        btnFix.setOnClickListener(fixClickListener);

        // 초대 코드 팝업 다이얼로그 - 가능
        inviteDialog = new Dialog(getContext());
        inviteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        inviteDialog.setContentView(R.layout.fragment_board_invite_code_default); // xml 레이아웃 파일 연결

        // 초대 코드 팝업 다이얼로그 - 불가능
        inviteDialogImpossible = new Dialog(getContext());
        inviteDialogImpossible.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        inviteDialogImpossible.setContentView(R.layout.fragment_board_invite_code_impossible); // xml 레이아웃 파일 연결

        return view;
    }
    // 뒤로 가기
    View.OnClickListener closeClickListener = v -> dismiss();

    // 초대 코드 복사 팝업 띄우기
    View.OnClickListener linkClickListener = v ->{
        // 초대 코드 설정
        showInviteDialogImpossible();
        showInviteDialog();
        dismiss();
    };

    // 소모임 정보 수정 액티비티 실행
    View.OnClickListener fixClickListener = v -> {
        Intent intent = new Intent(getContext(), BoardFixTeamActivity.class);
        startActivity(intent);
    };

    // 초대 코드 복사 팝업
    private void showInviteDialog(){
        inviteDialog.show(); // 다이얼로그 띄우기
        inviteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경

        TextView tvInviteCode = inviteDialog.findViewById(R.id.board_invite_code_tv_code);
        ImageButton btnClose = inviteDialog.findViewById(R.id.board_invite_code_btn_close);
        ImageButton btnPaste = inviteDialog.findViewById(R.id.board_invite_code_btn_paste);

        // 창 닫기
        btnClose.setOnClickListener(v -> inviteDialog.dismiss());

        // 복사 후 창 닫기
        btnPaste.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("invite_code", tvInviteCode.getText());
            clipboardManager.setPrimaryClip(clipData);
            inviteDialog.dismiss();
        });
    }

    // 초대 코드 복사 불가 팝업
    private void showInviteDialogImpossible() {
        inviteDialogImpossible.show(); // 다이얼로그 띄우기
        inviteDialogImpossible.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경

        ImageButton btnClose =  inviteDialogImpossible.findViewById(R.id.board_invite_code_impossible_btn_close);
        View llWhole = inviteDialogImpossible.findViewById(R.id.board_invite_code_impossible_ll);

        // 터치시 종료
        llWhole.setOnClickListener(v->inviteDialogImpossible.dismiss());
        btnClose.setOnClickListener(v->inviteDialogImpossible.dismiss());
    }
}