package com.zeno.quanxueclient.adapter;

import android.support.annotation.NonNull;

import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.bean.NoteBean;

import java.util.List;

/**
 * Created by Zeno on 2017/1/9.
 */

public class NoteListAdapter extends BaseAdapter<NoteBean> {

    public NoteListAdapter(@NonNull List<NoteBean> datas) {
        super(R.layout.item_note_list_layout, datas);
    }

    @Override
    protected void onBindView(BaseViewHolder holder, NoteBean noteBean) {
        holder.setText(R.id.tv_book_note_text,noteBean.getBookContent());
    }
}
