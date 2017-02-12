package com.zeno.quanxueclient.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.adapter.BaseAdapter;
import com.zeno.quanxueclient.adapter.BookContentsAdapter;
import com.zeno.quanxueclient.adapter.ReadBookContentAdapter;
import com.zeno.quanxueclient.bean.BookContentsBean;
import com.zeno.quanxueclient.presenter.ReadBookPresenter;
import com.zeno.quanxueclient.view.decoration.BookContentsListDecoration;
import com.zeno.quanxueclient.view.vinterface.IReadBookView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 阅读书籍
 */
public class ReadBookActivity extends BaseActivity<IReadBookView, ReadBookPresenter> implements IReadBookView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_book_content)
    RecyclerView rvBookContent;
    @BindView(R.id.rv_book_contents)
    RecyclerView rvBookContents;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private static final String BOOK_URL = "book_url";
    private static final String BOOK_NAME = "book_name";
    private static final String SECTION_NUMBER = "section_number";


    private List<BookContentsBean> mBookContentsBeanList = new ArrayList<>();
    private BookContentsAdapter mBookContentsAdapter;
    private ReadBookContentAdapter mReadBookContentAdapter;
    private LinearLayoutManager mBookContentlinearLayoutManager;
    private boolean move = false;
    private int mIndex = 0;
    private ClipboardManager mClipboardManager;
    private String mBookUrl;

    /**
     * 显示书籍阅读界面
     *
     * @param context
     * @param bookUrl
     */
    public static void startReadBookView(Context context, String bookUrl, String bookName, int sectionNumber) {
        Intent intent = new Intent(context, ReadBookActivity.class);
        intent.putExtra(BOOK_URL, bookUrl);
        intent.putExtra(BOOK_NAME, bookName);
        intent.putExtra(SECTION_NUMBER, sectionNumber);
        context.startActivity(intent);
    }

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*x5 webView 配置*/
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR1) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }

        super.setContentView(R.layout.activity_read_book);

        setToolBar(toolbar);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        /*清空剪贴板数据*/
        mClipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        mClipboardManager.setPrimaryClip(ClipData.newPlainText(null,""));

        if (mBookContentsAdapter == null)
            mBookContentsAdapter = new BookContentsAdapter(mBookContentsBeanList);

        if (mReadBookContentAdapter == null)
            mReadBookContentAdapter = new ReadBookContentAdapter(mBookContentsBeanList);

        mBookUrl = getIntent().getStringExtra(BOOK_URL);
        String bookName = getIntent().getStringExtra(BOOK_NAME);
        int sectionNumber = getIntent().getIntExtra(SECTION_NUMBER, 0);
        toolbar.setTitle(bookName);


        mPresenter.fetch(mBookUrl);

        setToolbarMenu();

        setRecyclerView();

        setOnListener();

        /*移到指定章节*/
        moveToPosition(sectionNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read_book_tool_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * 設置titleBar的左邊按鈕目錄，點擊之後呈現側邊欄即書籍目錄
     */
    private void setToolbarMenu() {
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * 設置RecyclerView
     */
    private void setRecyclerView() {
        rvBookContents.setLayoutManager(new LinearLayoutManager(this));
        rvBookContents.addItemDecoration(new BookContentsListDecoration(this));
        rvBookContents.setAdapter(mBookContentsAdapter);

        rvBookContent.addOnScrollListener(new RecyclerViewListener());
        mBookContentlinearLayoutManager = new LinearLayoutManager(this);
        rvBookContent.setLayoutManager(mBookContentlinearLayoutManager);
        rvBookContent.addItemDecoration(new BookContentsListDecoration(this));
        rvBookContent.setAdapter(mReadBookContentAdapter);

    }

    private void setOnListener() {
        mBookContentsAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Object obj, int position) {
                drawerLayout.closeDrawers();
                moveToPosition(position);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_close_page)
                    finish();

                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = mClipboardManager.getText();
                if (!TextUtils.isEmpty(text)) {
                    AddNoteActivity.showAddNoteView(ReadBookActivity.this,mBookUrl,text.toString());
                }else{
                    Toast.makeText(ReadBookActivity.this, "请先选中复制要记笔记的原文", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 移动RecyclerView到指定位置
     *
     * @param n
     */
    private void moveToPosition(int n) {

        int firstItem = mBookContentlinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mBookContentlinearLayoutManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            rvBookContent.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = rvBookContent.getChildAt(n - firstItem).getTop();
            rvBookContent.scrollBy(0, top);
        } else {
            rvBookContent.scrollToPosition(n);
            move = true;
        }

    }

    @Override
    protected ReadBookPresenter createPresenter() {
        return new ReadBookPresenter(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showDatas(List<BookContentsBean> bookContentBeen) {
        XLog.e(bookContentBeen.toString());
        mBookContentsBeanList.addAll(bookContentBeen);
        if (mBookContentsAdapter != null)
            mBookContentsAdapter.notifyDataSetChanged();

        if (mReadBookContentAdapter != null)
            mReadBookContentAdapter.notifyDataSetChanged();
    }


    class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = mIndex - mBookContentlinearLayoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < rvBookContent.getChildCount()) {
                    int top = rvBookContent.getChildAt(n).getTop();
                    rvBookContent.smoothScrollBy(0, top);
                }

            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move) {
                move = false;
                int n = mIndex - mBookContentlinearLayoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < rvBookContent.getChildCount()) {
                    int top = rvBookContent.getChildAt(n).getTop();
                    rvBookContent.scrollBy(0, top);
                }
            }
        }
    }
}
