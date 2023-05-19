package com.example.moing.mission;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moing.R;
import com.example.moing.team.Team;

import java.util.ArrayList;

public class MissionDoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<Mission> missions;

    public MissionDoneAdapter(ArrayList<Mission> missions) {
        this.missions = missions;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.recycler_item_team_default,parent,false);
        viewHolder = new MissionDoneAdapter.DoneViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       DoneViewHolder doneViewHolder = (DoneViewHolder) holder;
       doneViewHolder.tvNickname.setText("hi");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class DoneViewHolder extends RecyclerView.ViewHolder {
        ImageView ivArchive;
        ImageView ivProfile;
        TextView tvNickname;


        public DoneViewHolder(@NonNull View itemView) {
            super(itemView);
            ivArchive = itemView.findViewById(R.id.mission_done_iv_archive);
            ivProfile = itemView.findViewById(R.id.mission_done_iv_profile);
            tvNickname = itemView.findViewById(R.id.mission_done_tv_nickname);

            // S3 -> Glide를 통한 이미지 설정


        }
    }
}
