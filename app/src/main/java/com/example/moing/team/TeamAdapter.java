package com.example.moing.team;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moing.R;

import java.util.List;

/** TeamRecyclerView 의 Adapter**/
public class TeamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int DEFAULT_TYPE = 0;
    private static final int TEAM_TYPE = 1;
    private final List<Team> teamList;

    public TeamAdapter(List<Team> teamList) {
        this.teamList = teamList;
        teamList.add(new Team("Default","Default","Default","Default"));
    }

    @Override
    public int getItemViewType(int position) {
        // 마지막 DefaultType
        if (position  == getItemCount()-1) {
            return DEFAULT_TYPE;
        }
        // 그 외 TeamType
        return TEAM_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // viewType 에 따라 ViewHolder 와 Layout 설정
        if(viewType == DEFAULT_TYPE){
            view = inflater.inflate(R.layout.recycler_item_team_default,parent,false);
            viewHolder = new DefaultViewHolder(view);
        }
        else{
            view = inflater.inflate(R.layout.recycler_item_team,parent,false);
            viewHolder = new TeamViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // viewType 에 따라 text 및 image 설정
        Team team = teamList.get(position);
        switch (holder.getItemViewType()) {
            case DEFAULT_TYPE:
                break;
            case TEAM_TYPE:
                TeamViewHolder teamViewHolder = (TeamViewHolder) holder;
                teamViewHolder.title.setText(team.getTitle());
                teamViewHolder.memberNum.setText(team.memberNum);
                teamViewHolder.missionStart.setText(team.missionStart);
                teamViewHolder.missionEnd.setText(team.missionEnd);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }


    public static class DefaultViewHolder extends RecyclerView.ViewHolder {
        Button btnAddTeam;

        public DefaultViewHolder(@NonNull View itemView) {
            super(itemView);
            btnAddTeam = itemView.findViewById(R.id.btnAddTeam);
            btnAddTeam.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(),AddTeamActivity.class);
                view.getContext().startActivity(intent);
            });
        }
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView memberNum;
        TextView missionStart;
        TextView missionEnd;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivTeamImage);
            title = itemView.findViewById(R.id.tvTeamTitle);
            memberNum = itemView.findViewById(R.id.tvTeamMemberNum);
            missionStart = itemView.findViewById(R.id.tvTeamMissionStart);
            missionEnd = itemView.findViewById(R.id.tvTeamMissionEnd);
        }
    }
}
