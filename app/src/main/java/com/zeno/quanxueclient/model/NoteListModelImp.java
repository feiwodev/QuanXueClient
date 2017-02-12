package com.zeno.quanxueclient.model;

import android.text.TextUtils;

import com.zeno.quanxueclient.App;
import com.zeno.quanxueclient.AppConfig;
import com.zeno.quanxueclient.bean.NoteBean;
import com.zeno.quanxueclient.db.gen.NoteBeanDao;
import com.zeno.quanxueclient.model.minterface.INoteListModel;
import com.zeno.quanxueclient.utils.FileUtils;

import java.io.File;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Zeno on 2017/1/9.
 */

public class NoteListModelImp implements INoteListModel {

    private final NoteBeanDao mNoteBeanDao;

    public NoteListModelImp() {
        mNoteBeanDao = App.getInstance().getDaoSession().getNoteBeanDao();
    }

    @Override
    public void loadDatas(String bookUrl, final LoadListener<List<NoteBean>> loadListener) {
        Observable.just(bookUrl).map(new Func1<String, List<NoteBean>>() {
            @Override
            public List<NoteBean> call(String s) {
                if (TextUtils.isEmpty(s))
                    return queryAll();
                else
                    return queryByBookUrl(s);
            }
        })
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<NoteBean>>() {
                    @Override
                    public void call(List<NoteBean> noteBeen) {
                        loadListener.onSuccess(noteBeen);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadListener.onError(throwable.getMessage());
                    }
                });

    }

    @Override
    public void removeNote(NoteBean noteBean, final int position, final RemoveListener removeListener) {
        Observable.just(noteBean).subscribe(new Action1<NoteBean>() {
            @Override
            public void call(NoteBean noteBean) {
                synchronized (NoteListModelImp.this) {
                    removeNote(noteBean);
                    removeListener.onSuccess(position);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                removeListener.onError(throwable.getMessage());
            }
        });
    }

    @Override
    public void saveNote(String bookUrl, final String bookName, final SaveNoteListener saveNoteListener) {
        Observable.just(bookUrl).map(new Func1<String, File>() {
            @Override
            public File call(String s) {
                return saveNoteToFile(s, bookName);
            }
        })
        .observeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<File>() {
            @Override
            public void call(File file) {
                saveNoteListener.onSuccess(file);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                saveNoteListener.onError(throwable.getMessage());
            }
        });
    }


    /**
     * 查询全部笔记
     *
     * @return
     */
    private List<NoteBean> queryAll() {
        return mNoteBeanDao.queryBuilder().orderDesc(NoteBeanDao.Properties.Id).build().list();
    }

    /**
     * 查询单个书籍的笔记
     *
     * @param bookUrl
     * @return
     */
    private List<NoteBean> queryByBookUrl(String bookUrl) {
        return mNoteBeanDao.queryBuilder().where(NoteBeanDao.Properties.BookUrl.eq(bookUrl)).orderDesc(NoteBeanDao.Properties.Id).build().list();
    }

    private void removeNote(NoteBean noteBean) {
        mNoteBeanDao.delete(noteBean);
    }

    /**
     * 将笔记保存到文件
     *
     * @param url
     * @param bookName
     */
    private File saveNoteToFile(String url, String bookName) {
        List<NoteBean> noteBeens;
        if (!TextUtils.isEmpty(url)) {
            noteBeens = queryByBookUrl(url);
        }
        else {
            noteBeens = queryAll();
        }

        if (noteBeens.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (NoteBean noteBeen : noteBeens) {
                builder.append("原文：");
                builder.append(noteBeen.getBookContent());
                builder.append("\r\n\r\n");
                builder.append("笔记：");
                builder.append(noteBeen.getUserContent());
                builder.append("\r\n\r\n\r\n\r\n\r\n");
            }
            return FileUtils.saveFile(AppConfig.SAVE_NOTE_FILE_PATH, bookName+".txt", builder.toString());
        }else {
            return null;
        }

    }
}
