package com.zeno.quanxueclient.adapter;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/13
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import android.support.annotation.LayoutRes;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.bean.Category;
import com.zeno.quanxueclient.net.API;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

/**
 * 首页grid adapter
 */
public class CategoryGridAdapter extends BaseAdapter<Category> {

    public CategoryGridAdapter(@NotNull List<Category> datas) {
        super(R.layout.item_main_category_layout, datas);
    }

    @Override
    protected void onBindView(BaseViewHolder holder, Category category) {
        Picasso.with(holder.itemView.getContext()).load(category.getPicUrl()).into((ImageView) holder.getView(R.id.iv_category_pic));
        holder.setText(R.id.tv_category_name,category.getName());
        holder.setText(R.id.tv_category_desc,category.getDesc());
    }
}
