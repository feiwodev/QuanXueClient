package com.zeno.quanxueclient.view.vinterface;

import com.zeno.quanxueclient.bean.NoteBean;

import java.io.File;
import java.util.List;

/**
 * Created by Zeno on 2017/1/9.
 */

public interface INoteListView extends IBaseView<List<NoteBean>> {

    void onRemoveSuccess(int position);
    void onRemoveError();

    void onSaveSuccess(File file,int flag);
    void onSaveError(String msg);
}
