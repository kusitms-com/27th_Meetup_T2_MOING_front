package com.example.moing.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moing.R;
import com.example.moing.Response.BoardVoteCommentResponse;

import java.util.List;

public class VoteCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BoardVoteCommentResponse.VoteData> commentList;
    private Context context;

    public VoteCommentAdapter(List<BoardVoteCommentResponse.VoteData> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_vote_comment, parent, false);
        return new VoteCommentAdapter.VoteCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BoardVoteCommentResponse.VoteData voteComment = commentList.get(position);
        VoteCommentViewHolder vh = (VoteCommentViewHolder) holder;

        vh.nickname.setText(voteComment.getNickName());
        vh.content.setText(voteComment.getContent());
        Glide.with(context).load(voteComment.getUserImageUrl()).into(vh.profile);

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

    private class VoteCommentViewHolder extends RecyclerView.ViewHolder{
        ImageView profile;
        TextView nickname, content;
        View line;
        public VoteCommentViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.iv_profile);
            nickname = itemView.findViewById(R.id.tv_nickname);
            content = itemView.findViewById(R.id.tv_content);
            line = itemView.findViewById(R.id.view_board_line);
        }
    }
}
