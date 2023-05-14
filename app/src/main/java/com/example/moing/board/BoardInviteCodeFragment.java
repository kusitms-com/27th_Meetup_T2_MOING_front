package com.example.moing.board;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.moing.R;

public class BoardInviteCodeFragment extends PopupWindow {
    private final TextView tvInviteCode;

    public BoardInviteCodeFragment(Context context) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.fragment_board_invite_code_default, null);
        setContentView(contentView);

        tvInviteCode = contentView.findViewById(R.id.board_invite_code_tv_code);
        ImageButton btnClose = contentView.findViewById(R.id.board_invite_code_btn_close);
        Button btnPaste = contentView.findViewById(R.id.board_invite_code_btn_paste);

        // 창 닫기
        btnClose.setOnClickListener(v -> dismiss());

        // 복사 후 창 닫기
        btnPaste.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("invite_code", tvInviteCode.getText());
            clipboardManager.setPrimaryClip(clipData);
            dismiss();
        });
    }

    // 초대 코드 설정
    public void setInviteCode(String inviteCode) {tvInviteCode.setText(inviteCode);}
}
