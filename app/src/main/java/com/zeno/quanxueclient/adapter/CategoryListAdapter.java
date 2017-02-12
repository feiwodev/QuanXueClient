package com.zeno.quanxueclient.adapter;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/14
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.bean.BookBean;

import java.util.List;

public class CategoryListAdapter extends BaseAdapter<BookBean> {


    public CategoryListAdapter(@NonNull List<BookBean> datas) {
        super(R.layout.item_category_list_layout, datas);
    }

    @Override
    protected void onBindView(BaseViewHolder holder, BookBean bookBean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(holder.itemView.getResources(), R.drawable.book_cover, null);
            String[] regs = bookBean.getCoverRgb().split(",");
            if (regs.length == 3) {
                DrawableCompat.setTint(vectorDrawableCompat,Color.rgb(Integer.parseInt(regs[0]),Integer.parseInt(regs[1]),Integer.parseInt(regs[2])));
            }
            ((ImageView) holder.getView(R.id.iv_book_pic)).setImageDrawable(vectorDrawableCompat);
        }else{
            ((ImageView) holder.getView(R.id.iv_book_pic)).setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.book_cover));
        }

        holder.setText(R.id.tv_book_title,bookBean.getName());
        holder.setText(R.id.tv_book_desc,bookBean.getDesc());
        holder.setText(R.id.tv_book_author,bookBean.getAuthor());

        holder.setText(R.id.tv_cover_title,bookBean.getName());
        holder.setText(R.id.tv_cover_author,bookBean.getAuthor());
    }
}
