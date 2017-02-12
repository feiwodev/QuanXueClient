package com.zeno.quanxueclient.presenter;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/12/7.
 */

public class BasePresenter<V> {

    protected WeakReference<V> mViewRef;

    /**
     * 将Presenter 与 View 关联绑定
     * @param view
     */
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    /**
     * 将Presenter与View解绑定
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null ;
        }
    }
}
