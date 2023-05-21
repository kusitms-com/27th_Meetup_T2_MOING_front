package com.example.moing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moing.Response.MissionListResponse;

import java.util.List;

public class MissionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MissionListResponse.MissionData> listData;
    private Context context;

    // 클릭 아이템 리스너
    private OnItemClickListener onItemClickListener = null;

    // 클릭 리스너 인터페이스
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public MissionListAdapter(List<MissionListResponse.MissionData> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_mission, parent, false);
        return new MissionListAdapter.MissionListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MissionListResponse.MissionData item = listData.get(position);
        MissionListHolder vh = (MissionListHolder) holder;

        vh.tv_title.setText(item.getTitle()); // 제목
        vh.tv_date.setText(item.getDueTo()); // 날짜

        vh.btn_check.setBackgroundResource(R.drawable.mission_not); // 미션 체크
        if (item.getStatus().equals("COMPLETE")) {
            vh.btn_check.setBackgroundResource(R.drawable.mission_success);
        } else {
            vh.btn_check.setBackgroundResource(R.drawable.mission_not);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MissionListHolder extends RecyclerView.ViewHolder {

        ConstraintLayout background;
        ImageView btn_check;
        TextView tv_title, tv_date;

        public MissionListHolder(@NonNull View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.recycle_background);
            btn_check = itemView.findViewById(R.id.notice_iv_crown);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);

            // 아이템 클릭 이벤트 추가
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체 메서드 호출
                        if(onItemClickListener != null) {
                            onItemClickListener.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
