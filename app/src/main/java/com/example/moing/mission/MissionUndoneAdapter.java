package com.example.moing.mission;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moing.R;
import com.example.moing.Response.MissionStatusListResponse;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MissionUndoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<MissionStatusListResponse.UserMission> missionList;
    private ArrayList<Integer> fireList;
    private OnMissionUndoneClickListener onMissionUndoneClickListener;
    private Context mainContext;

    public MissionUndoneAdapter(ArrayList<MissionStatusListResponse.UserMission> missionList, ArrayList<Integer> fireList, Context context) {
        this.missionList = missionList;
        this.fireList = fireList;
        this.mainContext = context;
    }

    public void setFireList(ArrayList<Integer> fireList) {
        this.fireList = fireList;
    }

    public interface OnMissionUndoneClickListener {
        void onItemClick(MissionUndoneAdapter.UndoneViewHolder holder, int position, MissionStatusListResponse.UserMission mission);
    }

    public void setOnItemClickListener(OnMissionUndoneClickListener listener) {
        this.onMissionUndoneClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recycler_item_mission_undone,parent,false);
        UndoneViewHolder viewHolder = new MissionUndoneAdapter.UndoneViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UndoneViewHolder undoneViewHolder = (MissionUndoneAdapter.UndoneViewHolder) holder;

        MissionStatusListResponse.UserMission mission = missionList.get(position);

        //닉네임 설정
        undoneViewHolder.tvNickname.setText(mission.getNickname());

        // 프로필 이미지 설정
        Glide.with(mainContext)
                .load(mission.getProfileImg())
                .into(undoneViewHolder.ivProfile);

        // 불 맞은 사람 - 표시
        if(fireList.contains(mission.getUserMissionId())){
            // 맞은 사람 표시
            undoneViewHolder.ivProfile.setColorFilter(Color.parseColor("#2C0E0E9C"));
            undoneViewHolder.ivFire.setVisibility(View.VISIBLE);
            undoneViewHolder.itemView.setClickable(false);
        }
        // 불 안맞은 사람 - 미완료 리사이클러뷰 클릭 리스너 설정
        else{
            undoneViewHolder.itemView.setOnClickListener(v ->{
                if(onMissionUndoneClickListener != null){
                    onMissionUndoneClickListener.onItemClick(undoneViewHolder,position,mission);
                }

            });
        }
    }

    @Override
    public int getItemCount() {return missionList.size();}

    class UndoneViewHolder extends RecyclerView.ViewHolder{
        CircleImageView ivProfile;
        TextView tvNickname;
        ImageView ivFire;

        public UndoneViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.mission_undone_iv_profile);
            tvNickname = itemView.findViewById(R.id.mission_undone_tv_nickname);
            ivFire = itemView.findViewById(R.id.mission_undone_iv_fire);
        }
    }
}
