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

import com.example.moing.Response.AllNoticeResponse;

import java.util.List;

public class NoticeViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AllNoticeResponse.NoticeBlock> listData;
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

    public NoticeViewAdapter(List<AllNoticeResponse.NoticeBlock> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_notice, parent, false);
        return new NoticeViewAdapter.NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AllNoticeResponse.NoticeBlock item = listData.get(position);
        NoticeViewHolder vh = (NoticeViewHolder) holder;

        /** 이미지 빼고 연동했습니다. **/
        vh.image.setBackgroundResource(R.drawable.notice_profile); // 닉네임
        vh.name.setText(item.getNickName()); // 이름
        vh.crown.setBackgroundResource(R.drawable.notice_crown); // 왕관
        vh.title.setText(item.getTitle());
        vh.content.setText(item.getContent());
        vh.dot.setBackgroundResource(R.drawable.notice_dot);
        vh.count.setText(String.valueOf(item.getCommentNum()));

        boolean read = item.getRead();
        vh.dot.setVisibility(read ? View.INVISIBLE : View.VISIBLE); // 참이면 안보이게, 거짓이면 보이게
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {
        ImageView image, dot, crown, iv_msg;
        ConstraintLayout background;
        TextView name, title, content, count;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.recycle_background);
            crown = itemView.findViewById(R.id.notice_iv_crown);
            image = itemView.findViewById(R.id.notice_iv_user_image);
            name = itemView.findViewById(R.id.notice_tv_user_name);
            dot = itemView.findViewById(R.id.notice_iv_dot);
            title = itemView.findViewById(R.id.notice_tv_title);
            content = itemView.findViewById(R.id.notice_tv_content);
            iv_msg = itemView.findViewById(R.id.notice_iv_message);
            count = itemView.findViewById(R.id.comment_count);

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
