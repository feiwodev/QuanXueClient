package com.zeno.quanxueclient.model.minterface;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/19
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.zeno.quanxueclient.bean.NoteBean;

public interface IAddNoteModel {

    void saveNote(NoteBean noteBean,SaveListener listener);

    interface SaveListener {
        void onSuccess();
        void onFailure();
    }

}
