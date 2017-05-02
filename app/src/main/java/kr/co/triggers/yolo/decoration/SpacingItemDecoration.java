package kr.co.triggers.yolo.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import kr.co.triggers.yolo.R;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpacingItemDecoration(Context context) {

        Resources resources = context.getResources();
        space = resources.getDimensionPixelSize(R.dimen.artist_feed_space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);

        int total = parent.getAdapter().getItemCount();
        int position = parent.getChildLayoutPosition(view);

        if (position < 1) {

            outRect.left = space * 2;
        }
        else {

            outRect.left = space;
        }

        if (position == total - 1) {

            outRect.right = space * 2;
        }
    }
}
