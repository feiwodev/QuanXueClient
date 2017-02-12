package com.zeno.quanxueclient.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.presenter.BasePresenter;

import butterknife.BindView;

public class AboutAppActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /**
     * 显示关于APP
     *
     * @param context
     */
    public static void startAboutView(Context context) {
        Intent intent = new Intent(context, AboutAppActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_about_app);

        setToolBar(toolbar,this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
