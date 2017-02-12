package com.zeno.quanxueclient.utils.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zeno on 2016/5/21.
 *
 * AppBarLayout Behavior
 */
public class ToolBarBehavior extends AppBarLayout.Behavior {

    private boolean isAnimation = false ;

    public ToolBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
       * 此方法指定观察的滑动方式 ，是垂直滑动 ， 还是横向滑动
       * */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes);
    }

    /*
    * 此方法是关联滑动方式 ， 滑动时候回调
    * */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

        // 向上滑动 ， 隐藏元素
        if (dyConsumed > 0 && !isAnimation) {
            animationOut(child) ;

        }
        // 向下滑动 ，显示元素
        else if (dyConsumed < 0 && !isAnimation) {
            animationIn(child) ;
        }

        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    /**
     * 隐藏AppBarLayout
     * @param child
     */
    private void animationOut(AppBarLayout child) {
        ViewCompat
                .animate(child)
                .translationY(-child.getHeight())
                .setListener(new AnimationLister())
                .start();
    }

    /**
     * 显示AppBarLayout
     * @param child
     */
    private void animationIn(AppBarLayout child) {
        ViewCompat
                .animate(child)
                .translationY(0)
                .setListener(new AnimationLister())
                .start();
    }

    class AnimationLister implements ViewPropertyAnimatorListener {

        @Override
        public void onAnimationStart(View view) {
            isAnimation = true ;
        }

        @Override
        public void onAnimationEnd(View view) {
            isAnimation = false ;
        }

        @Override
        public void onAnimationCancel(View view) {
            isAnimation = false ;
        }
    }
}
