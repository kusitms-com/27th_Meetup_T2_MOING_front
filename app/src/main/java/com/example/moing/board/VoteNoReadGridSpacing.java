package com.example.moing.board;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class VoteNoReadGridSpacing extends RecyclerView.ItemDecoration {
    private int spanCount; // 그리드의 열 수
    private int spacing; // 아이템 간격
    private boolean includeEdge; // 가장자리에도 간격을 포함할지 여부

    public VoteNoReadGridSpacing(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // spanCount = 4, spacing = 8dp
        int position = parent.getChildAdapterPosition(view); // 아이템 위치
        int column = position % spanCount; // 아이템이 속한 열의 인덱스

        // 아이템 별 간격 설정
        if (column == 0) {
            outRect.left = spacing * 3; // layout_marginLeft="24dp"에 해당하는 값
            outRect.right = spacing;
        } else {
        }

        // 마지막 열의 오른쪽 마진 설정
        if (column == spanCount - 1) {
            outRect.right = spacing*3; // layout_marginRight="40dp"에 해당하는 값
            outRect.left = spacing;
        } else {
            outRect.left = spacing;
        }

        outRect.top = spacing; // 상하 간격은 동일하게 설정
        outRect.bottom = spacing;
    }
}
