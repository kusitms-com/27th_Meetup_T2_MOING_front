package com.example.moing.mission;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moing.R;

import java.util.ArrayList;
import java.util.List;

public class MissionStatusDoneFragment extends Fragment {
    private static final int DONE_TYPE = 0;
    private static final int PENDING_TYPE = 1;
    private Dialog doneDialog;
    private Dialog pendingDialog;
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
        adapter.setOnItemClickListener(status -> {
            // 아이템 클릭 시 팝업 창을 띄우는 로직
            switch (status) {
                case DONE_TYPE:
                    showDoneDialog();
                    break;
                case PENDING_TYPE:
                    showPendingDialog();
                    break;
            }

        });

        // 미션 완료 다이얼로그
        doneDialog = new Dialog(getContext());
        doneDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        doneDialog.setContentView(R.layout.fragment_mission_status_done_popup); // xml 레이아웃 파일 연결

        // 미션 건너뛰기 다이얼로그
        pendingDialog = new Dialog(getContext());
        pendingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        pendingDialog.setContentView(R.layout.fragment_mission_status_pending_popup); // xml 레이아웃 파일 연결

        TextView tvNum = view.findViewById(R.id.mission_status_done_tv_num);
        String text = missionList.size()+"명";
        tvNum.setText(text);

        // 완료 0 명 - 모두 미완료인 경우 이미지
        ImageView ivDoneAll = view.findViewById(R.id.mission_status_done_iv_undone_all);
        if(missionList.size() == 0)
            ivDoneAll.setVisibility(View.VISIBLE);
        else
            ivDoneAll.setVisibility(View.GONE);


        return view;
    }

    // 미션 완료 다이얼로그 띄우기
    private void showDoneDialog(){
        doneDialog.show(); // 다이얼로그 띄우기
        doneDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경

        ImageButton btnClose = doneDialog.findViewById(R.id.mission_status_done_popup_btn_close);
        ImageView ivProfile = doneDialog.findViewById(R.id.mission_status_done_popup_iv_profile);
        ImageView ivImage = doneDialog.findViewById(R.id.mission_status_done_popup_iv_image);
        TextView tvNickname = doneDialog.findViewById(R.id.mission_status_done_popup_tv_nickname);
        TextView tvDate = doneDialog.findViewById(R.id.mission_status_done_popup_tv_date);

        // 창 닫기
        btnClose.setOnClickListener(v -> doneDialog.dismiss());
    }

    // 미션 건너뛰기 다이얼로그 띄우기
    private void showPendingDialog(){
        pendingDialog.show(); // 다이얼로그 띄우기
        pendingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경

        ImageButton btnClose = pendingDialog.findViewById(R.id.mission_status_pending_popup_btn_close);
        ImageView ivProfile = pendingDialog.findViewById(R.id.mission_status_pending_popup_iv_profile);
        TextView tvReason = pendingDialog.findViewById(R.id.mission_status_pending_popup_tv_reason);
        TextView tvNickname = pendingDialog.findViewById(R.id.mission_status_pending_popup_tv_nickname);
        TextView tvDate = pendingDialog.findViewById(R.id.mission_status_pending_popup_tv_date);

        // 창 닫기
        btnClose.setOnClickListener(v -> pendingDialog.dismiss());
    }

}