package com.zeno.quanxueclient.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ViewFlipper;

import com.elvishew.xlog.XLog;
import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.adapter.BaseAdapter;
import com.zeno.quanxueclient.adapter.CategoryListAdapter;
import com.zeno.quanxueclient.bean.BookBean;
import com.zeno.quanxueclient.bean.Category;
import com.zeno.quanxueclient.presenter.CategoryListPresenter;
import com.zeno.quanxueclient.view.decoration.CategoryListDecoration;
import com.zeno.quanxueclient.view.vinterface.ICategoryView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/13
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

/**
 * 类别列表
 */
public class CategoryListActivity extends BaseActivity<ICategoryView, CategoryListPresenter> implements ICategoryView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vf_switch)
    ViewFlipper vfSwitch;
    @BindView(R.id.rv_category_list)
    RecyclerView rvCategoryList;

    private static final String CATEGORY_LIST_BEAN = "category_list_bean";



    private List<BookBean> bookBeanList = new ArrayList<>();
    private CategoryListAdapter mCategoryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_category_list);

        init();
    }

    /**
     * 跳转到CategoryListView
     *
     * @param context
     * @param category
     */
    public static void showCategoryListView(Context context, Category category) {
        Intent intent = new Intent(context, CategoryListActivity.class);
        intent.putExtra(CATEGORY_LIST_BEAN, category);
        context.startActivity(intent);
    }

    /**
     * 初始化
     */
    private void init() {
        Category category = getIntent().getParcelableExtra(CATEGORY_LIST_BEAN);
        setTitleBar(category.getName());

        mPresenter.fetch(category.getUrl());

        setRecyclerView();


        setOnListener();
    }

    /**
     * 设置title
     *
     * @param title
     */
    public void setTitleBar(String title) {
        setToolBar(toolbar, this);
        toolbar.setTitle(title);
    }

    /**
     * 设置RecyclerView
     */
    private void setRecyclerView() {
        rvCategoryList.setLayoutManager(new LinearLayoutManager(this));
        rvCategoryList.addItemDecoration(new CategoryListDecoration(this));
        mCategoryListAdapter = new CategoryListAdapter(bookBeanList);
        rvCategoryList.setAdapter(mCategoryListAdapter);
    }

    /**
     * 设置事件监听
     */
    private void setOnListener() {
        mCategoryListAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Object obj,int position) {
                BookContentsActivity.showBookContentsView(CategoryListActivity.this,(BookBean) obj);
            }
        });
    }


    @Override
    protected CategoryListPresenter createPresenter() {
        return new CategoryListPresenter(this);
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
    public void showDatas(List<BookBean> bookBeen) {
        XLog.e(bookBeen.toString());
        bookBeanList.clear();
        bookBeanList.addAll(bookBeen);
        if (mCategoryListAdapter != null)
            mCategoryListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String mssage) {
        vfSwitch.setDisplayedChild(2);
    }
}
