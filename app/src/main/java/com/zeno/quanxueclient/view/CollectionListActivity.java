package com.zeno.quanxueclient.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ViewFlipper;

import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.adapter.BaseAdapter;
import com.zeno.quanxueclient.adapter.CategoryListAdapter;
import com.zeno.quanxueclient.bean.BookBean;
import com.zeno.quanxueclient.presenter.CollectionListPresenter;
import com.zeno.quanxueclient.view.vinterface.ICollectionView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CollectionListActivity extends BaseActivity<ICollectionView, CollectionListPresenter> implements ICollectionView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_book_collection_list)
    RecyclerView rvBookCollectionList;
    @BindView(R.id.vf_switch)
    ViewFlipper vfSwitch;

    private List<BookBean> bookBeanList = new ArrayList<>();
    private CategoryListAdapter mCollectionListAdapter;

    public static void showCollectionLisView(Context context) {
        Intent intent = new Intent(context,CollectionListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_collection_list);
        setToolBar(toolbar,this);

        init();

        setOnListener();
    }

    @Override
    protected CollectionListPresenter createPresenter() {
        return new CollectionListPresenter(this);
    }


    private void init() {
        mCollectionListAdapter = new CategoryListAdapter(bookBeanList);
        mPresenter.fetch();

        setRecyclerView();
    }

    private void setRecyclerView() {
        rvBookCollectionList.setLayoutManager(new LinearLayoutManager(this));
        rvBookCollectionList.setAdapter(mCollectionListAdapter);
    }

    /**
     * 设置事件监听
     */
    private void setOnListener() {
        mCollectionListAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Object obj, int position) {
                BookContentsActivity.showBookContentsView(CollectionListActivity.this,(BookBean) obj);
            }
        });
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
    public void showDatas(List<BookBean> bookBeen) {
        if (bookBeen != null) {
            bookBeanList.clear();
            bookBeanList.addAll(bookBeen);
            mCollectionListAdapter.notifyDataSetChanged();
            vfSwitch.setDisplayedChild(1);
        }else{
            vfSwitch.setDisplayedChild(2);
        }
    }
}
