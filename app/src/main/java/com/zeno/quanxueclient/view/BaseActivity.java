package com.zeno.quanxueclient.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zeno.quanxueclient.presenter.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zeno on 2016/12/7.
 *
 * all view base class
 *
 * a little default config
 */

public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    private Unbinder mButterKnifeBind;

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 去掉window默认的背景色 ， 减少绘制次数
        getWindow().setBackgroundDrawable(null);
        super.onCreate(savedInstanceState);
        // 创建Presenter
        mPresenter = createPresenter();
        // 将View与Presenter关联
        if (mPresenter != null)
            mPresenter.attachView((V) this);


    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        // add butter knife ui annotation , 最好用的UI注解框架 ， 可以使用AS插件自动生成UI组件注解实例。
        mButterKnifeBind = ButterKnife.bind(this);

    }

    /**
     * set toolbar
     * @param toolBar 将ToolBar设置到ActionBar中
     */
    protected void setToolBar(Toolbar toolBar, final Activity activity) {

        setSupportActionBar(toolBar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setElevation(4);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setLogo(null);

        if (activity != null) {
            toolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    /**
     * set toolbar
     * @param toolBar 将ToolBar设置到ActionBar中
     */
    protected void setToolBar(Toolbar toolBar) {

       setToolBar(toolBar,null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unbind butter knife
        mButterKnifeBind.unbind();
        // 解绑Presenter
        if (mPresenter != null)
            mPresenter.detachView();
    }

    /**
     * 创建Presenter
     * @return Presenter Obj
     */
    protected abstract T createPresenter();

    /**
     * 弹出对话框，并且焦点在指定的EditText上
     *
     * @param editText
     */
    protected void showInputMethod(final EditText editText)
    {
        // 弹出键盘
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                editText.setFocusableInTouchMode(true);
                editText.setFocusable(true);
                editText.requestFocus();
                editText.setSelection(editText.getText().length());
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(
                        editText, 0);
            }
        }, 300);
    }

    /**
     * 关闭指定EditText的输入法
     *
     * @param editText
     */
    protected void closeInputMethod(EditText editText)
    {
        ((InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText.getWindowToken(),
                0);
    }
}
