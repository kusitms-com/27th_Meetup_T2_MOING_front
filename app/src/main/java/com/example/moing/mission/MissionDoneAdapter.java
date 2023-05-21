package com.example.moing.mission;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moing.R;
import com.example.moing.team.Team;
import com.example.moing.team.TeamAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MissionDoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int DONE_TYPE = 0;
    private static final int PENDING_TYPE = 1;
    private List<Mission> missionList;

    private OnMissionClicklistener onMissionDoneClicklistener;

    public MissionDoneAdapter(List<Mission> missionList) {
        this.missionList = missionList;
    }

    public interface OnMissionClicklistener {
        void onItemClick(int status);
    }

    public void setOnItemClickListener(OnMissionClicklistener listener) {
        this.onMissionDoneClicklistener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        // COMPELTE -> 완료
        if (missionList.get(position).status.equals("COMPELTE")) {
            return DONE_TYPE;
        }
        // PENDING -> 건너뛰기
        return PENDING_TYPE;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // viewType 에 따라 ViewHolder 와 Layout 설정
        if(viewType == DONE_TYPE){
            // 완료 상태
            view = inflater.inflate(R.layout.recycler_item_mission_done,parent,false);
            viewHolder = new MissionDoneAdapter.DoneViewHolder(view);
        }
        else{
            // 건너뛰기 상태
            view = inflater.inflate(R.layout.recycler_item_mission_pending,parent,false);
            viewHolder = new MissionDoneAdapter.PendingViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // viewType 에 따라 text 및 image 설정
        Mission mission = missionList.get(position);

        switch (holder.getItemViewType()) {
            case DONE_TYPE:
                MissionDoneAdapter.DoneViewHolder doneViewHolder = (DoneViewHolder) holder;

                // 완료에 대한 리사이클러뷰들을 받아와 처리

                // 첫번째는 나 표시
                if(position == 0)
                    doneViewHolder.ivMe.setVisibility(View.VISIBLE);

                doneViewHolder.itemView.setOnClickListener(v ->{
                    if(onMissionDoneClicklistener != null)
                        onMissionDoneClicklistener.onItemClick(DONE_TYPE);
                });

                break;
            case PENDING_TYPE:
                MissionDoneAdapter.PendingViewHolder pendingViewHolder = (PendingViewHolder) holder;

                // 건너뛰기에 대한 리사이클러뷰들을 받아와 처리

                // 첫번째는 나 표시
                if(position == 0)
                    pendingViewHolder.ivMe.setVisibility(View.VISIBLE);

                pendingViewHolder.itemView.setOnClickListener(v ->{
                    if(onMissionDoneClicklistener != null)
                        onMissionDoneClicklistener.onItemClick(PENDING_TYPE);
                });


                break;
        }
    }

    @Override
    public int getItemCount() {
        return missionList.size();
    }

    class DoneViewHolder extends RecyclerView.ViewHolder{
        ImageView ivArchive;
        CircleImageView ivProfile;
        ImageView ivMe;
        TextView tvNickname;

        public DoneViewHolder(@NonNull View itemView) {
            super(itemView);
            ivArchive = itemView.findViewById(R.id.mission_done_iv_archive);
            ivProfile = itemView.findViewById(R.id.mission_done_iv_profile);
            ivMe = itemView.findViewById(R.id.mission_done_iv_me);
            tvNickname = itemView.findViewById(R.id.mission_done_tv_nickname);
        }
    }

    class PendingViewHolder extends RecyclerView.ViewHolder{
        CircleImageView ivProfile;
        ImageView ivMe;
        TextView tvReason;
        TextView tvNickname;

        public PendingViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.mission_pending_iv_profile);
            ivMe = itemView.findViewById(R.id.mission_pending_iv_me);
            tvReason = itemView.findViewById(R.id.mission_pending_tv_reason);
            tvNickname = itemView.findViewById(R.id.mission_pending_tv_nickname);

        }
    }
}
