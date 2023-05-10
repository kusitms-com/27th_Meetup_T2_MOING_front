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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moing.R;

import java.util.List;

/** TeamRecyclerView 의 Adapter**/
public class TeamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int DEFAULT_TYPE = 0;
    private static final int TEAM_TYPE = 1;
    private static final int MAX_TEAM_NUM = 3;
    private static List<Team> teamList = null;
    private static Context mainContext = null;

    public TeamAdapter(List<Team> teamList, Context mainContext) {
        TeamAdapter.teamList = teamList;
        TeamAdapter.mainContext = mainContext;
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
        ImageView ivTeam;

        public DefaultViewHolder(@NonNull View itemView) {
            super(itemView);
            btnAddTeam = itemView.findViewById(R.id.team_default_btn_add_team);
            ivTeam = itemView.findViewById(R.id.team_default_iv);

            String text = "모임 추가하기 ("+(TeamAdapter.teamList.size()-1)+"/3)";
            btnAddTeam.setText(text);

            if(TeamAdapter.teamList.size()-1 == TeamAdapter.MAX_TEAM_NUM){
                // 최대 생성 가능 모임 (3)을 채웠을 경우 -> img 변경, 버튼 비활성화
                ivTeam.setBackgroundResource(R.drawable.ic_make_team_full);
                btnAddTeam.setClickable(false);
                btnAddTeam.setBackgroundColor(ContextCompat.getColor(TeamAdapter.mainContext,R.color.secondary_grey_black_13));
                btnAddTeam.setTextColor(ContextCompat.getColor(TeamAdapter.mainContext,R.color.secondary_grey_black_10));
            }
            else {
                // 버튼 활성화
                btnAddTeam.setOnClickListener(view -> {
                    Intent intent = new Intent(view.getContext(), AddTeamActivity.class);
                    view.getContext().startActivity(intent);
                });
            }
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
            image = itemView.findViewById(R.id.team_iv_team_image);
            title = itemView.findViewById(R.id.team_tv_team_title);
            memberNum = itemView.findViewById(R.id.team_tv_team_member_num);
            missionStart = itemView.findViewById(R.id.team_tv_mission_start);
            missionEnd = itemView.findViewById(R.id.team_tv_team_mission_end);
        }
    }
}
