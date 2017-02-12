package com.zeno.quanxueclient.view.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zeno.quanxueclient.R;

// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/14
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

/**
 * RecyclerView列表间隔线
 */
public class CategoryListDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDrawable;

    public CategoryListDecoration(Context context) {
        mDrawable = context.getResources().getDrawable(R.drawable.item_recycler_view_list_decoration);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontalLine(c,parent);
    }


    /**
     * 画水平线
     * @param canvas
     * @param recyclerView
     */
    private void drawHorizontalLine(Canvas canvas, RecyclerView recyclerView) {
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

        outRect.set(0,0,0,drawableHeight);
    }
}
