package com.zeno.quanxueclient.model;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/19
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.zeno.quanxueclient.App;
import com.zeno.quanxueclient.bean.NoteBean;
import com.zeno.quanxueclient.db.gen.NoteBeanDao;
import com.zeno.quanxueclient.model.minterface.IAddNoteModel;

public class AddNoteModelImp implements IAddNoteModel {

    private final NoteBeanDao mNoteBeanDao;

    public AddNoteModelImp() {
        mNoteBeanDao = App.getInstance().getDaoSession().getNoteBeanDao();
    }

    @Override
    public void saveNote(NoteBean noteBean, SaveListener listener) {
        long insert = mNoteBeanDao.insert(noteBean);
        if (insert > 0) {
            listener.onSuccess();
        }else{
            listener.onFailure();
        }
    }
}
