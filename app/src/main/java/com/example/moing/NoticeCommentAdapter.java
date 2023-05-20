package com.example.moing;

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

public class NoticeCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NoticeCommentResponse.NoticeComment> commentList;
    private Context context;

    public NoticeCommentAdapter(List<NoticeCommentResponse.NoticeComment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_notice_comment, parent, false);
        return new NoticeCommentAdapter.NoticeCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NoticeCommentResponse.NoticeComment noticeComment = commentList.get(position);
        NoticeCommentViewHolder vh = (NoticeCommentViewHolder) holder;

        // 예정 : Profile 매칭
        vh.nickname.setText(noticeComment.getNickName());
        vh.content.setText(noticeComment.getContent());

        if (position == commentList.size()-1) {
            vh.line.setVisibility(View.INVISIBLE);
        } else {
            vh.line.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return commentList.size();
    }

    private class NoticeCommentViewHolder extends RecyclerView.ViewHolder{
        ImageView profile;
        TextView nickname, content;
        View line;
        public NoticeCommentViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.iv_profile);
            nickname = itemView.findViewById(R.id.tv_nickname);
            content = itemView.findViewById(R.id.tv_content);
            line = itemView.findViewById(R.id.view_board_line);
        }
    }
}

