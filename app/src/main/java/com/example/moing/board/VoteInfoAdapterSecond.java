package com.example.moing.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moing.R;

import java.util.List;

public class VoteInfoAdapterSecond extends RecyclerView.Adapter<VoteInfoAdapterSecond.VoteSecondViewHolder> {
    private List<VoteInfo.VoteChoice> items; // 리사이클러뷰 안에 들어갈 값 저장
    private List<String> userList;

    public VoteInfoAdapterSecond(List<VoteInfo.VoteChoice> items, List<String> userList) {
        this.items = items;
        this.userList = userList;
    }

    @NonNull
    @Override
    public VoteInfoAdapterSecond.VoteSecondViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_vote_second, parent, false);
        return new VoteInfoAdapterSecond.VoteSecondViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoteInfoAdapterSecond.VoteSecondViewHolder holder, int position) {
        String user = userList.get(position);
        holder.name.setText(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class VoteSecondViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public VoteSecondViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_recycle_name);
        }
    }
}
