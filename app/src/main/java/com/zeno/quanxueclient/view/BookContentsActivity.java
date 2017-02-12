package com.zeno.quanxueclient.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.adapter.BaseAdapter;
import com.zeno.quanxueclient.adapter.BookContentsAdapter;
import com.zeno.quanxueclient.bean.BookBean;
import com.zeno.quanxueclient.bean.BookContentsBean;
import com.zeno.quanxueclient.presenter.BookContentsPresenter;
import com.zeno.quanxueclient.view.decoration.BookContentsListDecoration;
import com.zeno.quanxueclient.view.vinterface.IBookContentsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/14
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

/**
 * 书籍简介及目录
 */
public class BookContentsActivity extends BaseActivity<IBookContentsView, BookContentsPresenter> implements IBookContentsView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_book_pic)
    ImageView ivBookPic;
    @BindView(R.id.tv_cover_title)
    TextView tvCoverTitle;
    @BindView(R.id.tv_cover_author)
    TextView tvCoverAuthor;
    @BindView(R.id.tv_book_title)
    TextView tvBookTitle;
    @BindView(R.id.tv_book_desc)
    TextView tvBookDesc;
    @BindView(R.id.rv_book_contents)
    RecyclerView rvBookContents;
    @BindView(R.id.vf_switch)
    ViewFlipper vfSwitch;

    private static final String BOOK_BEAN_FLAG = "book_bean";
    @BindView(R.id.tv_book_collection)
    TextView tvBookCollection;


    private List<BookContentsBean> bookContentsBeanList = new ArrayList<>();
    private BookContentsAdapter mBookContentsAdapter;
    private BookBean mBookBean;

    /**
     * 显示书籍详情
     *
     * @param context
     * @param bookBean
     */
    public static void showBookContentsView(Context context, BookBean bookBean) {
        Intent intent = new Intent(context, BookContentsActivity.class);
        intent.putExtra(BOOK_BEAN_FLAG, bookBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_book_contents);

        init();

        /*設置toolbar*/
        setToolBar(toolbar, this);
    }

    /**
     * 初始化
     */
    private void init() {
        mBookBean = (BookBean) getIntent().getParcelableExtra(BOOK_BEAN_FLAG);
        initComponentData(mBookBean);

        mBookContentsAdapter = new BookContentsAdapter(bookContentsBeanList);

        setRecyclerView();

        setOnListener();
    }

    @OnClick({R.id.tv_book_note, R.id.tv_book_collection,R.id.tv_book_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_book_note: /*笔记*/
                NoteListActivity.showNoteListView(this, mBookBean.getUrl(), mBookBean.getName());
                break;
            case R.id.tv_book_collection: /*收藏书籍*/
                isCollectionBook();
                break;
            case R.id.tv_book_share: /*分享*/
                shareBook();
                break;
        }
    }

    /**
     * 初始化原始數據
     *
     * @param bookBean
     */
    private void initComponentData(BookBean bookBean) {
        toolbar.setTitle(bookBean.getName());
        /*如果当前版本每到达到5.0，这不使用动态切换书籍页面颜色*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(getResources(), R.drawable.book_cover, null);
            String[] regs = bookBean.getCoverRgb().split(",");
            if (regs.length == 3) {
                DrawableCompat.setTint(vectorDrawableCompat, Color.rgb(Integer.parseInt(regs[0]), Integer.parseInt(regs[1]), Integer.parseInt(regs[2])));
            }
            ivBookPic.setImageDrawable(vectorDrawableCompat);
        } else {
            ivBookPic.setImageDrawable(getResources().getDrawable(R.drawable.book_cover));
        }

        tvBookTitle.setText(bookBean.getName());
        tvBookDesc.setText(bookBean.getDesc());
        tvCoverTitle.setText(bookBean.getName());
        tvCoverAuthor.setText(bookBean.getAuthor());
        onCollectionSuccess(bookBean.getIsCollection() == 1);

        mPresenter.fetch(bookBean.getCategoryUrl(), bookBean.getUrl());
    }

    /**
     * 设置事件监听
     */
    private void setOnListener() {
        mBookContentsAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Object obj, int position) {
                ReadBookActivity.startReadBookView(BookContentsActivity.this, mBookBean.getUrl(), mBookBean.getName(), position);
            }
        });
    }

    /**
     * 设置RecyclerView
     */
    private void setRecyclerView() {
        rvBookContents.setLayoutManager(new LinearLayoutManager(this));
        rvBookContents.addItemDecoration(new BookContentsListDecoration(this));
        rvBookContents.setAdapter(mBookContentsAdapter);
    }

    /**
     * 书籍收藏或取消
     */
    private void isCollectionBook() {
        mPresenter.isCollectionBook(mBookBean);
    }

    /**
     * 书籍分享
     */
    private void shareBook() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mBookBean.getUrl());
        sendIntent.putExtra(Intent.EXTRA_SUBJECT,mBookBean.getDesc());
        sendIntent.putExtra(Intent.EXTRA_TITLE,mBookBean.getName());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "分享到..."));
    }

    @Override
    protected BookContentsPresenter createPresenter() {
        return new BookContentsPresenter(this);
    }

    @Override
    public void showLoading() {
        vfSwitch.setDisplayedChild(0);
    }

    @Override
    public void hideLoading() {
        vfSwitch.setDisplayedChild(1);
    }

    @Override
    public void showDatas(List<BookContentsBean> bookContentBeen) {
        bookContentsBeanList.addAll(bookContentBeen);
        if (mBookContentsAdapter != null)
            mBookContentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCollectionSuccess(boolean isCollection) {
        if (isCollection) {
            tvBookCollection.setText("已收藏");
            setTextViewTopDrawable(R.drawable.icon_collection_select);
        }else{
            tvBookCollection.setText("未收藏");
            setTextViewTopDrawable(R.drawable.icon_collection_normal);
        }
    }

    private void setTextViewTopDrawable(int drawableId) {
        Drawable drawable = getResources().getDrawable(drawableId);
        tvBookCollection.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
    }

    @Override
    public void onCollectionError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
