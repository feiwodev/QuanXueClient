package com.zeno.quanxueclient.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.zeno.quanxueclient.R;

// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/13
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

/**
 * 封装ViewHolder ， 对于外部不可见
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private View mItemView;
    private SparseArray<View> mViews;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>();
        this.mItemView = itemView;
    }

    /**
     * 查找View
     * @param resId View 资源ID
     * @param <T> View 类型
     * @return View对象
     */
    public <T extends View> T getView(@IdRes int resId) {
        View view = mViews.get(resId);
        if (view == null) {
            view = mItemView.findViewById(resId);
            mViews.put(resId,view);
        }

        return (T) view;
    }

    /**
     * 快捷设置TextView 内容
     * @param resId
     * @param charSequence
     * @return
     */
    public BaseViewHolder setText(@IdRes int resId,CharSequence charSequence) {
        View view = getView(resId);
        if (view instanceof TextView) {
            ((TextView) view).setText(charSequence);
        }
        return this;
    }

    /**
     * 设置单个View的Click事件
     * @param resId
     * @param onClickListener
     */
    public void setOnClickListener(@IdRes int resId , View.OnClickListener onClickListener) {
        View view = getView(resId);
        view.setOnClickListener(onClickListener);
    }

    /**
     * 设置View的Tag
     * @param resId View的资源Id
     * @param object tag content
     * @return ViewHolder object
     */
    public BaseViewHolder setViewTag(@IdRes int resId,Object object) {
        View view = getView(resId);
        view.setTag(object);

        return this;
    }

    /**
     * 获取View的Tag
     * @param resId View的资源id
     * @return tag content
     */
    public Object getViewTag(@IdRes int resId) {
        View view = getView(resId);
        return view.getTag();
    }
}
