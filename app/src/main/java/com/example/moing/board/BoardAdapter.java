package com.example.moing.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moing.R;

import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {
    private List<Board> boardList;
    private BoardGoalFragment context;

    TextView boardTitle, boardContent;
    View boardLine;

    // 생성자
    public BoardAdapter(List<Board> boardList, BoardGoalFragment context) {
        this.boardList = boardList;
        this.context = context;
    }

    class BoardViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            boardTitle = mView.findViewById(R.id.tv_board_title);
            boardContent = mView.findViewById(R.id.tv_board_content);
            boardLine = mView.findViewById(R.id.view_board_line);
        }
    }


    @NonNull
    @Override
    public BoardAdapter.BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_board, parent, false);
        return new BoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardAdapter.BoardViewHolder holder, int position) {
        // 값을 가져오는 곳

        // 3개까지만 출력하고 싶을 때
        if (position < 3) {
            // 제목
           boardTitle.setText(boardList.get(position).getTitle());
            // 내용
            boardContent.setText(boardList.get(position).getContent());
            // 3번째 내용을 출력할때는 Line을 제거한다.
            if (position == 2) {
                boardLine.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return boardList.size();
    }
}
