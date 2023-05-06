package com.example.moing.team;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/** TeamAdapter 좌/우 swipe event callback class**/
public class SwipeCallback extends ItemTouchHelper.Callback {

    private final RecyclerView recyclerView;
    private final TeamAdapter adapter;

    public SwipeCallback(RecyclerView recyclerView, TeamAdapter adapter) {
        this.recyclerView = recyclerView;
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // 스와이프 방향 설정
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        // 드래그 방향 설정
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        // 스와이프 및 드래그 방향 설정
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        // 드래그 이벤트 처리
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // 스와이프 이벤트 처리
        if (direction == ItemTouchHelper.START) {
            // 왼쪽으로 스와이프
            int position = viewHolder.getAdapterPosition();
            if (position > 0) {
                recyclerView.smoothScrollToPosition(position - 1);
            }
        } else if (direction == ItemTouchHelper.END) {
            // 오른쪽으로 스와이프
            int position = viewHolder.getAdapterPosition();
            if (position < adapter.getItemCount() - 1) {
                recyclerView.smoothScrollToPosition(position + 1);
            }
        }
    }
}
