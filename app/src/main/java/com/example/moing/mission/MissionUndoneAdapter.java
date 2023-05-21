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

import de.hdodenhof.circleimageview.CircleImageView;

public class MissionUndoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Mission> missionList;
    private OnMissionUndoneClickListener onMissionUndoneClickListener;

    public MissionUndoneAdapter(List<Mission> missionList) {
        this.missionList = missionList;
    }

    public interface OnMissionUndoneClickListener {
        void onItemClick(ImageView ivProfile, ImageView ivFire, int position);
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
        undoneViewHolder.tvNickname.setText("이름ㅇㅇㅇ");

        undoneViewHolder.itemView.setOnClickListener(v ->{
            if(onMissionUndoneClickListener != null){
                onMissionUndoneClickListener.onItemClick(undoneViewHolder.ivProfile,undoneViewHolder.ivFire,position);
            }

        });

        // 이미 불을 맞은 사람
        // "fireUserMissionList[]" 미인증한 사람 중 login 한 유저가 불 던진 userMissionId

        // 이 리스트에 들어가는 userMissionId를 가지면 틴트색을 바꾸고 클릭 불가능하게 설정

        // 안맞은 사람 - 클릭 리스너 달고 팝업창 띄워야함


        // 이미지뷰 틴트 컬러 적용
        // undoneViewHolder.ivProfile.setColorFilter(Color.parseColor("#2C0E0E9C"));
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
