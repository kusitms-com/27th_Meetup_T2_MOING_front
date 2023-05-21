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

import com.example.moing.Response.AllVoteResponse;

import java.util.List;

public class VoteViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AllVoteResponse.VoteBlock> dataList;
    private Context context;

    public VoteViewAdapter(List<AllVoteResponse.VoteBlock> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    // 클릭 아이템 리스너
    private NoticeViewAdapter.OnItemClickListener onItemClickListener = null;

    // 클릭 리스너 인터페이스
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void voteClickListener(NoticeViewAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_notice, parent, false);
        return new VoteViewAdapter.VoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AllVoteResponse.VoteBlock item = dataList.get(position);
        VoteViewHolder vh = (VoteViewHolder) holder;

        /** 이미지 빼고 연동했습니다. **/
        vh.image.setBackgroundResource(R.drawable.notice_profile); // 닉네임
        vh.name.setText(item.getNickName()); // 이름
        vh.crown.setBackgroundResource(R.drawable.notice_crown); // 왕관
        vh.title.setText(item.getTitle());
        vh.content.setText(item.getMemo());
        vh.dot.setBackgroundResource(R.drawable.notice_dot);
        vh.cnt.setText(String.valueOf(item.getCommentNum()));

        boolean read = item.getRead();
        vh.dot.setVisibility(read ? View.INVISIBLE : View.VISIBLE); // 참이면 안보이게, 거짓이면 보이게
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VoteViewHolder extends RecyclerView.ViewHolder {
        private ImageView image, dot, crown, iv_msg;
        private ConstraintLayout background;
        private TextView name, title, content, cnt;

        public VoteViewHolder(@NonNull View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.recycle_background);
            crown = itemView.findViewById(R.id.notice_iv_crown);
            image = itemView.findViewById(R.id.notice_iv_user_image);
            name = itemView.findViewById(R.id.notice_tv_user_name);
            dot = itemView.findViewById(R.id.notice_iv_dot);
            title = itemView.findViewById(R.id.notice_tv_title);
            content = itemView.findViewById(R.id.notice_tv_content);
            iv_msg = itemView.findViewById(R.id.notice_iv_message);
            cnt = itemView.findViewById(R.id.comment_count);

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