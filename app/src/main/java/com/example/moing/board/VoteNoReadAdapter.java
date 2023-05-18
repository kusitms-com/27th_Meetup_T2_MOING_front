package com.example.moing.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moing.R;

import java.util.List;

public class VoteNoReadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<VoteInfo.voteData> noReadList;
    private Context context;

    public VoteNoReadAdapter(List<VoteInfo.voteData> noReadList, Context context) {
        this.noReadList = noReadList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_vote_noread, parent, false);
        return new VoteNoReadAdapter.VoteNoReadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VoteInfo.voteData voteData = noReadList.get(position);

    }

    @Override
    public int getItemCount() {
        return noReadList.size();
    }

    public class VoteNoReadViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public VoteNoReadViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_recycle_name);
        }
    }
}
