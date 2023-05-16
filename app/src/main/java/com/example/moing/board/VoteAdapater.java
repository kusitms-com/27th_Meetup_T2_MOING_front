package com.example.moing.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moing.R;

import java.util.List;

public class VoteAdapater extends RecyclerView.Adapter<VoteAdapater.VoteViewHolder> {
    private List<Vote> voteList;
    private Context context;

    TextView et_content;
    Button btn_close;

    // 생성자
    public VoteAdapater(List<Vote> voteList, Context context) {
        this.voteList = voteList;
        this.context = context;
    }

    @NonNull
    @Override
    public VoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_vote, parent, false);
        return new VoteAdapater.VoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoteViewHolder holder, int position) {
        // 투표 항목의 길이가 0이 아닐 때
        if(voteList.get(position).getVoteContent().length() != 0)
            et_content.setText(voteList.get(position).getVoteContent());
    }

    @Override
    public int getItemCount() {
        return voteList.size();
    }

    class VoteViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public VoteViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            et_content = mView.findViewById(R.id.tv_vote_content);
            btn_close = mView.findViewById(R.id.btn_vote_erase);
        }
    }


//    @NonNull
//    @Override
//    public BoardAdapter.BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.recycler_item_board, parent, false);
//        return new BoardAdapter.BoardViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull BoardAdapter.BoardViewHolder holder, int position) {
//        // 값을 가져오는 곳
//
//        // 3개까지만 출력하고 싶을 때
//        if (position < 3) {
//            // 제목
//            boardTitle.setText(boardList.get(position).getTitle());
//            // 내용
//            boardContent.setText(boardList.get(position).getContent());
//            // 3번째 내용을 출력할때는 Line을 제거한다.
//            if (position == 2) {
//                boardLine.setVisibility(View.GONE);
//            }
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return boardList.size();
//    }
}
