package com.example.moing.mission;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import com.example.moing.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MissionFixFragment extends BottomSheetDialogFragment {
    private Dialog fixDialogImpossible;
    private static final String TAG = "MissionFixFragment";
    private static final String PREF_NAME = "Token";
    private static final String JWT_ACCESS_TOKEN = "JWT_access_token";
    private Long teamId, missionId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mission_fix, container, false);

        // Intent로 값 받기.
        teamId = getActivity().getIntent().getLongExtra("teamId", 0);
        missionId = getActivity().getIntent().getLongExtra("missionId", 0);
        /** 소모임장 번호도 가져와야 한다. **/

        // 닫기 버튼
        ImageButton close = view.findViewById(R.id.board_dot_btn_close);
        close.setOnClickListener(closeClickListener);

        // 미션 수정 버튼
        ImageButton fix = view.findViewById(R.id.mission_fix);
        fix.setOnClickListener(fixClickListener);

        // 초대 코드 팝업 다이얼로그 - 불가능
        fixDialogImpossible = new Dialog(getContext());
        fixDialogImpossible.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        fixDialogImpossible.setContentView(R.layout.fragment_board_invite_code_impossible); // xml 레이아웃 파일 연결

        return view;
    }

    // 닫기 버튼 클릭 시
    View.OnClickListener closeClickListener = v -> dismiss();
    // 미션 수정 버튼 클릭 시
    View.OnClickListener fixClickListener = v -> {

        /** Intent 이용하여 미션 수정 화면으로 이동 **/
        // Intent intent = new Intent(getContext(), 수정액티비티.class);
        // intent.putExtra("teamId",teamId);
        // intent.putExtra("missionId",missionId);
        // startActivity(intent);
    };

    // 초대 코드 복사 불가 팝업
    private void showInviteDialogImpossible() {
        fixDialogImpossible.show(); // 다이얼로그 띄우기
        fixDialogImpossible.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경

        ImageButton btnClose =  fixDialogImpossible.findViewById(R.id.board_invite_code_impossible_btn_close);
        View llWhole = fixDialogImpossible.findViewById(R.id.board_invite_code_impossible_ll);

        // 터치시 종료
        llWhole.setOnClickListener(v->fixDialogImpossible.dismiss());
        btnClose.setOnClickListener(v->fixDialogImpossible.dismiss());
    }
}