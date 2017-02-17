package com.zeno.quanxueclient.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.elvishew.xlog.XLog;
import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.adapter.BaseAdapter;
import com.zeno.quanxueclient.adapter.CategoryGridAdapter;
import com.zeno.quanxueclient.bean.Category;
import com.zeno.quanxueclient.bean.MainBean;
import com.zeno.quanxueclient.bean.MainSliders;
import com.zeno.quanxueclient.presenter.MainPresenter;
import com.zeno.quanxueclient.view.decoration.CategoryGridDecoration;
import com.zeno.quanxueclient.view.vinterface.IMainView;
import com.zeno.quanxueclient.view.widget.VerticalTextview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * App 首页 ， 显示劝学网首页分类模块 ， 以及推荐文章幻灯片和公告
 */
public class MainActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView, BaseSliderView.OnSliderClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.rv_book_category)
    RecyclerView rvBookCategory;
    @BindView(R.id.auto_vertical_text)
    VerticalTextview autoText;
    @BindView(R.id.vf)
    ViewFlipper vf;

    private List<Category> mLists = new ArrayList<>();
    private CategoryGridAdapter mCategoryGridAdapter;

    public static void startMainView(Context context) {
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        init();

    }

    private void init() {

        /*设置toolbar*/
        setToolBar(toolbar);

        /*类别列表设置*/
        setupRecyclerView();

        // 公告设置
        setupAutoText();


        /*设置幻灯片延时切换*/
        setupImageSlider();

        // 从Presenter中取数据
        mPresenter.fetch();

        /*设置事件监听*/
        setListener();
    }


    /**
     * 设置幻灯片延时切换
     */
    private void setupImageSlider() {
        slider.stopAutoCycle();
        /*延时幻开启幻灯片，不设置，则会出现打开APP，幻灯片快速切换到第二张图片，不友好*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (slider != null)
                    slider.startAutoCycle();
            }
        }, 5000);

    }

    /**
     * 公告设置
     */
    private void setupAutoText() {
        autoText.setText(14, 5, Color.RED);//设置属性,具体跟踪源码
        autoText.setTextStillTime(3000);//设置停留时长间隔
        autoText.setAnimTime(1000);//设置进入和退出的时间间隔
    }

    /**
     * 类别List设置
     */
    private void setupRecyclerView() {
        mCategoryGridAdapter = new CategoryGridAdapter(mLists);
        /*解决NestedScrollView与RecyclerView滑动冲突*/
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        rvBookCategory.setLayoutManager(gridLayoutManager);
        rvBookCategory.setHasFixedSize(true);
        rvBookCategory.setNestedScrollingEnabled(false);

        rvBookCategory.addItemDecoration(new CategoryGridDecoration(this));
        rvBookCategory.setAdapter(mCategoryGridAdapter);



    }

    /**
     * 设置事件监听
     */
    private void setListener() {
        mCategoryGridAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Object obj,int position) {
                Category category = (Category) obj;
                if (!"南怀瑾".equals(category.getName()))
                    CategoryListActivity.showCategoryListView(MainActivity.this,category);
                else
                    Toast.makeText(MainActivity.this, "此模块暂未解析完全", Toast.LENGTH_SHORT).show();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_setting:
                        SettingActivity.startSettingView(MainActivity.this);
                        break;
                    case R.id.menu_personal_center:
                        PersonalCenterActivity.startPersonalCenterView(MainActivity.this);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_tool_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void showLoading() {
        if (vf != null)
            vf.setDisplayedChild(0);
    }

    @Override
    public void hideLoading() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (vf != null)
                    vf.setDisplayedChild(1);
            }
        },800);

    }

    @Override
    public void showDatas(MainBean mainBean) {
        // 设置幻灯片数据
        setImageSlider(mainBean.getImageSliders());
        // 设置公告数据
        setAutoText(mainBean.getBulletins());

        XLog.e(mainBean.getCategories());
        mLists.addAll(mainBean.getCategories());
        mCategoryGridAdapter.notifyDataSetChanged();

    }


    /***
     * 设置首页幻灯片
     *
     * @param imageSliders
     */
    private void setImageSlider(List<MainSliders.DataBean> imageSliders) {

        for (MainSliders.DataBean bean : imageSliders) {
            TextSliderView textSliderView = new TextSliderView(this);
            Bundle bundle = new Bundle();
            bundle.putString("url",bean.getUrl());
            textSliderView
                    .description(bean.getIntro())
                    .setOnSliderClickListener(this)
                    .bundle(bundle)
                    .image(bean.getImage().replace("\\", "/"));  // 过滤url中不友好的\ , 这个操作应该放在服务端的，先就此处理着。
            if (slider != null)
                slider.addSlider(textSliderView);
        }


    }

    /**
     * 设置轮播公告
     *
     * @param bulletins 轮播公告数据
     */
    private void setAutoText(final List<String> bulletins) {
        if (bulletins != null && bulletins.size() > 0 && autoText != null) {
            autoText.setTextList(bulletins);//加入显示内容,集合类型
            //对单条文字的点击监听
            autoText.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(MainActivity.this, bulletins.get(position), Toast.LENGTH_SHORT).show();

                }
            });

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        autoText.startAutoScroll();

    }

    @Override
    protected void onPause() {
        super.onPause();
        autoText.stopAutoScroll();
    }

    @Override
    protected void onStop() {
        slider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        String url = slider.getBundle().getString("url");
        if (!TextUtils.isEmpty(url)&&url.startsWith("http"))
            CommonBrowserActivity.startCommonBrowserView(MainActivity.this,url);
    }
}
