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

import java.util.List;

public class MissionUndoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Mission> missionList;

    public MissionUndoneAdapter(List<Mission> missionList) {
        this.missionList = missionList;
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
        undoneViewHolder.tvNickname.setText("이름ㅇㅇㅇ");
    }

    @Override
    public int getItemCount() {return missionList.size();}

    class UndoneViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProfile;
        TextView tvNickname;

        public UndoneViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.mission_undone_iv_profile);
            tvNickname = itemView.findViewById(R.id.mission_undone_tv_nickname);
        }
    }
}
