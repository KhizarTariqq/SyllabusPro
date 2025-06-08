package com.example.syllabuspro;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;

    public HorizontalSpaceItemDecoration(int spaceInPx) {
        this.space = spaceInPx;
    }

    @Override
    public void getItemOffsets(
            Rect outRect,
            View view,
            RecyclerView parent,
            RecyclerView.State state
    ) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();

        // Add space to the right of every item except the last one
        if (position < itemCount - 1) {
            outRect.right = space;
        }
    }
}

