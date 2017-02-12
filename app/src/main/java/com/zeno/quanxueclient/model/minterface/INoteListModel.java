package com.zeno.quanxueclient.model.minterface;

import com.zeno.quanxueclient.bean.NoteBean;

import java.io.File;
import java.util.List;

/**
 * Created by Zeno on 2017/1/9.
 */

public interface INoteListModel{
    void loadDatas(String bookUrl, LoadListener<List<NoteBean>> loadListener);
    void removeNote(NoteBean noteBean,int position,RemoveListener removeListener);
    void saveNote(String bookUrl,String bookName,SaveNoteListener saveNoteListener);

    abstract class LoadListener<T> {
        public abstract void onSuccess(T t);
        public abstract void onError(String msg);
    }

    interface RemoveListener{
        void onSuccess(int position);
        void onError(String msg);
    }

    interface SaveNoteListener{
        void onSuccess(File file);
        void onError(String msg);
    }

}
