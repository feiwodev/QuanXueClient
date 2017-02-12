package com.zeno.quanxueclient.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.bean.NoteBean;
import com.zeno.quanxueclient.presenter.AddNotePresenter;
import com.zeno.quanxueclient.view.vinterface.IAddNoteView;

import java.sql.Date;

import butterknife.BindView;

// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/19
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

/**
 * 添加笔记
 */
public class AddNoteActivity extends BaseActivity<IAddNoteView, AddNotePresenter> implements IAddNoteView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_book_content)
    TextView tvBookContent;
    @BindView(R.id.et_user_content)
    EditText etUserContent;

    private static final String BOOK_CONTENT = "book_content";
    private static final String BOOK_URL = "book_url";
    private String mBookUrl;


    /**
     * 显示添加笔记
     * @param context
     * @param bookContent
     */
    public static void showAddNoteView(Context context,String bookUrl, String bookContent) {
        Intent intent = new Intent(context,AddNoteActivity.class);
        intent.putExtra(BOOK_CONTENT,bookContent);
        intent.putExtra(BOOK_URL,bookUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_add_note);
        setToolBar(toolbar,this);

        init();

        setOnListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_note_tool_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * init
     */
    private void init() {
        mBookUrl = getIntent().getStringExtra(BOOK_URL);
        tvBookContent.setText(getIntent().getStringExtra(BOOK_CONTENT));
        showInputMethod(etUserContent);
    }

    /**
     * 事件监听
     */
    private void setOnListener() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_save)
                    save();

                return false;
            }
        });
    }

    @Override
    protected AddNotePresenter createPresenter() {
        return new AddNotePresenter(this);
    }

    @Override
    public void showAddSuccess() {
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showAddFailure() {
        Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 保存笔记信息
     */
    private void save() {
        NoteBean noteBean = new NoteBean();
        noteBean.setBookUrl(mBookUrl);
        noteBean.setBookContent(tvBookContent.getText().toString().trim());
        noteBean.setUserContent(etUserContent.getText().toString().trim());
        noteBean.setCreateTime(new Date(System.currentTimeMillis()));
        mPresenter.addNote(noteBean);
    }
}
