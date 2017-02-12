package com.zeno.quanxueclient.utils.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Zeno on 2016/5/21.
 *
 *  FloatingActionButton Behavior
 */
public class FABBehavior extends FloatingActionButton.Behavior {

    private boolean isAnimation = false;

    /**
     * 构造函数一定要创建这两个参数的构造函数 ，不然会报错 ，inflater错误
     * @param context
     * @param attr
     */
    public FABBehavior(Context context, AttributeSet attr) {

    }

    /*
        * 此方法指定观察的滑动方式 ，是垂直滑动 ， 还是横向滑动
        * */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        // 当前观察的那个View 发生滑动时候回调方法
        // nestedScrollAxes 滑动关联轴 ，此处只关心垂直滑动
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    /*
    * 此方法是关联滑动方式 ， 滑动时候回调
    * */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

        // 观察的View滑动时候回调
        // 像上滑动 , 隐藏元素
        if (dyConsumed > 0 && !isAnimation) {
            animationOut(child) ;
        }
        // 向下滑动 ，显示元素
        else if (dyConsumed < 0&& !isAnimation) {
            animationIn(child) ;
        }

        Log.i("zeno",dyConsumed+"---") ; // 向上滑动打印正数 ， 向下滑动打印负数
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

    }

    /**
     * FloatingActionButton 显示动画
     * @param child
     */
    private void animationOut(View child) {
        ViewCompat
                .animate(child)
                .translationY(child.getHeight() * 2)
//                .scaleX(1.0f)
//                .scaleY(1.0f)
                .setListener(new AnimationLister())
                .start();
    }

    private void animationIn(View child) {
        ViewCompat
                .animate(child)
                .translationY(0)
//                .scaleX(0.0f)
//                .scaleY(0.0f)
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
