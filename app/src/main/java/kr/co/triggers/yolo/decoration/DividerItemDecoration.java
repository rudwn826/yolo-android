package kr.co.triggers.yolo.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import kr.co.triggers.yolo.R;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable divider;
    private int rightPadding, childLayout;

    public DividerItemDecoration(Context context, int layout) {
        childLayout = layout;
        Resources resources = context.getResources();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            divider = resources.getDrawable(R.drawable.drawable_separator_colored, context.getTheme());
        }
        else {

            divider = resources.getDrawable(R.drawable.drawable_separator_colored);
        }

        rightPadding = resources.getDimensionPixelSize(R.dimen.artist_list_item_divider_right_padding);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {

            View child = parent.getChildAt(i);
            //View textViewArtistName = child.findViewById(R.id.textViewArtistName);
            View textViewArtistName = child.findViewById(childLayout);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();

            divider.setBounds(left + textViewArtistName.getLeft(), top, right - rightPadding, bottom);
            divider.draw(c);
        }
    }
}
