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

public class MissionStatusUndoneFragment extends Fragment {
    private Dialog undoneDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mission_status_undone, container, false);

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
        RecyclerView recyclerView = rootView.findViewById(R.id.mission_status_undone_rv);

        // 리사이클러뷰에 사용할 레이아웃매니저 - Grid 2개의 열
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        // 리사이클러뷰 아이템들 간 간격 설정
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 36, 0, 0);
            }
        });

        // 리사이클러뷰에 사용할 어댑터 설정
        MissionUndoneAdapter adapter = new MissionUndoneAdapter(missionList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this::showUndoneDialog);

        TextView tvNum = rootView.findViewById(R.id.mission_status_undone_tv_num);
        String text = missionList.size()+"명";
        tvNum.setText(text);

        // 미완료 0명 - 모두 완료 했을 경우 이미지
        ImageView ivDoneAll = rootView.findViewById(R.id.mission_status_undone_iv_done_all);
        if(missionList.size() == 0)
            ivDoneAll.setVisibility(View.VISIBLE);
        else
            ivDoneAll.setVisibility(View.GONE);

        // 미션 건너뛰기 다이얼로그
        undoneDialog = new Dialog(getContext());
        undoneDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        undoneDialog.setContentView(R.layout.fragment_mission_status_undone_popup); // xml 레이아웃 파일 연결

        return rootView;
    }

    // 미완료 상태 다이얼로그 띄우기 - 불 던지기 다이얼로그
    private void showUndoneDialog(ImageView ivProfile, ImageView ivFire, int position){
        undoneDialog.show(); // 다이얼로그 띄우기
        undoneDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경

        ImageButton btnClose = undoneDialog.findViewById(R.id.mission_status_undone_popup_btn_close);
        ImageButton btnThrow = undoneDialog.findViewById(R.id.mission_status_undone_popup_btn_throw);
        TextView tvThrow = undoneDialog.findViewById(R.id.mission_status_undone_popup_tv_throw);

        // 창 닫기
        btnClose.setOnClickListener(v -> undoneDialog.dismiss());

        // 불 던지기
        btnThrow.setOnClickListener(v -> {
            // 맞은 사람 표시
            ivProfile.setColorFilter(Color.parseColor("#2C0E0E9C"));
            ivFire.setVisibility(View.VISIBLE);

            // 불 맞은 리스트에 해당 아이템 추가 - 리스트에 추가해서 전달 - 구현예정

            undoneDialog.dismiss();
        });
    }

}