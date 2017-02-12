package com.zeno.quanxueclient.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;

import com.zeno.quanxueclient.AppConfig;
import com.zeno.quanxueclient.bean.NoteBean;
import com.zeno.quanxueclient.model.NoteListModelImp;
import com.zeno.quanxueclient.model.minterface.INoteListModel;
import com.zeno.quanxueclient.view.vinterface.INoteListView;

import java.io.File;
import java.util.List;

/**
 * Created by Zeno on 2017/1/9.
 */

public class NoteListPresenter extends BasePresenter<INoteListView> {

    private INoteListView mNoteListView;
    private INoteListModel mNoteListModelImp = new NoteListModelImp();

    public NoteListPresenter(INoteListView iNoteListView) {
        this.mNoteListView = iNoteListView;
    }

    /**
     * 查询并显示笔记
     *
     * @param url
     */
    public void fetch(String url) {
        if (mNoteListModelImp != null) {
            mNoteListView.showLoading();
            mNoteListModelImp.loadDatas(url, new INoteListModel.LoadListener<List<NoteBean>>() {

                @Override
                public void onSuccess(List<NoteBean> noteBeen) {
                    if (mNoteListView != null)
                        mNoteListView.showDatas(noteBeen);
                }

                @Override
                public void onError(String msg) {
                    if (mNoteListView != null)
                        mNoteListView.hideLoading();
                }
            });
        }
    }

    /**
     * 删除笔记
     *
     * @param noteBean
     * @param position
     */
    public void remove(NoteBean noteBean, int position) {
        if (mNoteListModelImp != null) {
            mNoteListModelImp.removeNote(noteBean, position, new INoteListModel.RemoveListener() {
                @Override
                public void onSuccess(int position) {
                    if (mNoteListView != null)
                        mNoteListView.onRemoveSuccess(position);
                }

                @Override
                public void onError(String msg) {
                    if (mNoteListView != null)
                        mNoteListView.onRemoveError();
                }
            });
        }
    }

    /**
     * 保存笔记到文件
     *
     * @param bookUrl
     * @param bookName
     */
    public void saveNote(String bookUrl, String bookName, final int flag) {
        if (mNoteListModelImp != null) {
            mNoteListModelImp.saveNote(bookUrl, bookName, new INoteListModel.SaveNoteListener() {
                @Override
                public void onSuccess(File file) {
                    if (mNoteListView != null) {
                        notifyScanFile(file);
                        mNoteListView.onSaveSuccess(file, flag);
                    }
                }

                @Override
                public void onError(String msg) {
                    if (mNoteListView != null)
                        mNoteListView.onSaveError(msg);
                }
            });
        }
    }

    /**
     * 通知系统扫描文件
     * @param file
     */
    private void notifyScanFile(File file) {
        if (mNoteListView != null && mNoteListView instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                MediaScannerConnection.scanFile((Context) mNoteListView, new String[]{file.getAbsolutePath()
                }, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
            } else {
                // 4.4及以后的系统是不允许发送这个广播的，原因是你可能只增加可一个文件，然后就进行全盘扫描，这样很耗电，因此只有系统才能发送这个广播
                ((Context) mNoteListView).sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse(
                        "file://" + AppConfig.SAVE_NOTE_FILE_PATH)));
            }
        }
    }
}
