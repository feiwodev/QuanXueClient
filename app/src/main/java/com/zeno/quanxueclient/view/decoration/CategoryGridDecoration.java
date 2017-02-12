package com.zeno.quanxueclient.view.decoration;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/13
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zeno.quanxueclient.R;

/**
 * 首页grid间隔线
 */
public class CategoryGridDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private final Drawable mDrawable;

    public CategoryGridDecoration(Context context) {
        this.context = context;
        mDrawable = context.getResources().getDrawable(R.drawable.item_recycler_view_grid_decoration);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        drawVerticalLine(c,parent);
        drawHorizontalLine(c,parent);
    }

    /**
     * 画垂直线
     * @param canvas
     * @param recyclerView
     */
    private void drawVerticalLine(Canvas canvas,RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();

            int left = childView.getRight() - layoutParams.rightMargin;
            int top = childView.getTop() - layoutParams.topMargin;
            int right = left + layoutParams.rightMargin + this.mDrawable.getIntrinsicWidth();
            int bottom = childView.getBottom() + layoutParams.bottomMargin;

            this.mDrawable.setBounds(left,top,right,bottom);
            this.mDrawable.draw(canvas);
        }
    }

    /**
     * 画水平线
     * @param canvas
     * @param recyclerView
     */
    private void drawHorizontalLine(Canvas canvas,RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            /*drawable左边*/
            int left = childView.getLeft() - layoutParams.leftMargin;
            int top = childView.getBottom() + layoutParams.bottomMargin;
            /*drawable右边，与左边合起来是drawable的宽度*/
            int right = childView.getRight() + layoutParams.rightMargin + this.mDrawable.getIntrinsicWidth();
            int bottom = top + this.mDrawable.getIntrinsicHeight();

            this.mDrawable.setBounds(left,top,right,bottom);
            this.mDrawable.draw(canvas);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int drawableHeight = mDrawable.getIntrinsicHeight();
        int drawableWidth = mDrawable.getIntrinsicWidth();

        outRect.set(0,0,drawableWidth,drawableHeight);
    }
}
