package com.zeno.quanxueclient.adapter;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/13
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

/**
 * 所有RecyclerView adapter的基类
 */

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T extends Object> extends RecyclerView.Adapter<BaseViewHolder>{

    public interface OnItemClickListener {
        void onClick(View view, Object obj,int position);
    }
    public interface OnItemLongClickListener {
        void onLongClick(View view, Object obj,int position);
    }

    private @LayoutRes int mlayoutResId;
    private List<T> mDatas;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public BaseAdapter(@LayoutRes @NonNull int mlayoutResId, @NonNull List<T> datas) {
        this.mlayoutResId = mlayoutResId;
        this.mDatas = datas == null ? (List<T>) new ArrayList<>() : datas;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mlayoutResId, parent,false);
        BaseViewHolder viewHolder = new BaseViewHolder(view);
        initListener(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T t = mDatas.get(position);
        onBindView(holder,t);

    }


    protected abstract void onBindView(BaseViewHolder holder,T t);


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 初始化事件绑定
     * @param viewHolder
     */
    private void initListener(final BaseViewHolder viewHolder) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int layoutPosition = viewHolder.getLayoutPosition();
                T t = mDatas.get(layoutPosition);
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, t,layoutPosition);
                }
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int layoutPosition = viewHolder.getLayoutPosition();
                T t = mDatas.get(layoutPosition);
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onLongClick(v, t,layoutPosition);
                }
                return true;
            }
        });
    }

    /**
     * 单个条目Item click 事件
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置单个条目长按事件
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
