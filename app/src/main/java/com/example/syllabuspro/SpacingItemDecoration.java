package com.example.syllabuspro;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int space;
    private final int orientation;

    public SpacingItemDecoration(int space, int orientation) {
        if (orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL) {
            throw new IllegalArgumentException("Invalid orientation. Use LinearLayoutManager.HORIZONTAL or VERTICAL");
        }
        this.space = space;
        this.orientation = orientation;
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

        if (orientation == LinearLayoutManager.HORIZONTAL) {
            if (position == 0) {
                outRect.left = space;
            }
            if (position < itemCount - 1) {
                outRect.right = space;
            } else {
                outRect.right = space;
            }
        }

        // VERTICAL
        else
        {
            if (position == 0) {
                outRect.top = space;
            }
            if (position < itemCount - 1) {
                outRect.bottom = space;
            } else {
                outRect.bottom = space;
            }
        }
    }
}
