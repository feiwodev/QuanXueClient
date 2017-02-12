package com.zeno.quanxueclient.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.elvishew.xlog.XLog;
import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.adapter.BaseAdapter;
import com.zeno.quanxueclient.adapter.NoteListAdapter;
import com.zeno.quanxueclient.bean.NoteBean;
import com.zeno.quanxueclient.presenter.NoteListPresenter;
import com.zeno.quanxueclient.view.vinterface.INoteListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 笔记列表
 */
public class NoteListActivity extends BaseActivity<INoteListView, NoteListPresenter> implements INoteListView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final String BOOK_URL = "book_url";
    private static final String BOOK_NAME = "book_name";
    @BindView(R.id.rv_book_note_list)
    RecyclerView rvBookNoteList;
    @BindView(R.id.vf_switch)
    ViewFlipper vfSwitch;

    private List<NoteBean> mNoteBeanList = new ArrayList<>();
    private NoteListAdapter mNoteListAdapter;
    private String mBookUrl = "";
    private String mBookName;

    public static void showNoteListView(Context context, String bookUrl, String bookName) {
        Intent intent = new Intent(context, NoteListActivity.class);
        intent.putExtra(BOOK_URL, bookUrl);
        intent.putExtra(BOOK_NAME, bookName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_note_list);
        setToolBar(toolbar, this);
        init();
        setOnListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_list_tool_bar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        mBookUrl = getIntent().getStringExtra(BOOK_URL);
        mBookName = TextUtils.isEmpty(mBookUrl) ? "全部笔记" : getIntent().getStringExtra(BOOK_NAME) + "笔记";
        toolbar.setTitle(mBookName);
        mPresenter.fetch(mBookUrl);

        setRecyclerView();
    }

    /**
     * 设置RecyclerView
     */
    private void setRecyclerView() {
        rvBookNoteList.setLayoutManager(new LinearLayoutManager(this));
        mNoteListAdapter = new NoteListAdapter(mNoteBeanList);
        rvBookNoteList.setAdapter(mNoteListAdapter);
    }


    /**
     * 设置事件监听
     */
    private void setOnListener() {
        if (mNoteListAdapter !=null) {
            mNoteListAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
                @Override
                public void onLongClick(View view, Object obj, int position) {
                    NoteBean noteBean = (NoteBean) obj;
                    mPresenter.remove(noteBean,position);
                }
            });
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_export_txt: /*导出为txt*/
                        showSaveNoteDialog();
                        break;
                }
                return false;
            }
        });
    }

    private void showSaveNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("导出TXT");
        builder.setMessage("可以将笔记导出生成TXT文件，也可以直接分享生成的TXT文件，方便存入电脑，分享给好友。");
        builder.setPositiveButton("保存文件并发送", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveNoteToFile(2);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("保存文件", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveNoteToFile(1);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 保存笔记到文件
     */
    private void saveNoteToFile(int flag) {
        mPresenter.saveNote(mBookUrl,mBookName,flag);
    }



    @Override
    protected NoteListPresenter createPresenter() {
        return new NoteListPresenter(this);
    }

    @Override
    public void showLoading() {
        vfSwitch.setDisplayedChild(0);
    }

    @Override
    public void hideLoading() {
        vfSwitch.setDisplayedChild(2);
    }

    @Override
    public void showDatas(List<NoteBean> noteBeen) {
        XLog.e(noteBeen.toArray());
        if (noteBeen != null) {
            mNoteBeanList.clear();
            mNoteBeanList.addAll(noteBeen);
            mNoteListAdapter.notifyDataSetChanged();
            vfSwitch.setDisplayedChild(1);
        }else{
            vfSwitch.setDisplayedChild(2);
        }
    }

    @Override
    public void onRemoveSuccess(int position) {
        mNoteBeanList.remove(position);
        mNoteListAdapter.notifyItemChanged(position);
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveError() {
        Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveSuccess(File file,int flag) {
        if (file != null) {
            if (flag == 1) { /*保存文件*/
                Snackbar.make(rvBookNoteList,"保存文件成功，文件地址:\n"+file.getPath(),Snackbar.LENGTH_SHORT).show();
            }else if (flag == 2) { /*保存并分享*/
                shareFileToOtherApp(file);
            }
        }else{
            Toast.makeText(this, "没有笔记可以导出", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareFileToOtherApp(File file) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        shareIntent.setType("*/*");
        startActivity(Intent.createChooser(shareIntent, "发送文件到..."));
    }

    @Override
    public void onSaveError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
