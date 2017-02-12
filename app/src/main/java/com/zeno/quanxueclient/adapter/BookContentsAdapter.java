package com.zeno.quanxueclient.adapter;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/15
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import android.support.annotation.NonNull;

import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.bean.BookContentsBean;

import java.util.List;

public class BookContentsAdapter extends BaseAdapter<BookContentsBean> {


    public BookContentsAdapter(@NonNull List<BookContentsBean> datas) {
        super(R.layout.item_book_contents_layout, datas);
    }

    @Override
    protected void onBindView(BaseViewHolder holder,BookContentsBean bookContentBeen) {
        holder.setText(R.id.tv_section_name,bookContentBeen.getSectionName());
    }
}
