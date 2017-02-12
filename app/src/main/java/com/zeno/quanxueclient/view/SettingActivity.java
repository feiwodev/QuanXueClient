package com.zeno.quanxueclient.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.tencent.bugly.beta.Beta;
import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.presenter.SettingPresenter;
import com.zeno.quanxueclient.utils.ClearCacheUtils;
import com.zeno.quanxueclient.view.vinterface.ISettingView;
import com.zeno.quanxueclient.view.widget.DialogHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置 ， 此界面实现空的MVP模式 ， 所有操作皆在本页面完成
 */
public class SettingActivity extends BaseActivity<ISettingView, SettingPresenter> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /**
     * 显示设置界面
     *
     * @param context
     */
    public static void startSettingView(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_setting);

        setToolBar(toolbar,this);
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    @OnClick({R.id.rl_check_update_app,
            R.id.rl_about_app,
            R.id.rl_clear_cache,
            R.id.rl_font_size
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_check_update_app:
                Beta.checkUpgrade(true,false); /*检查更新*/
                break;
            case R.id.rl_about_app: /*关于app*/
                AboutAppActivity.startAboutView(this);
                break;
            case R.id.rl_clear_cache: /*清除缓存*/
                DialogHelper.showBasicDialog(this, "清理缓存", "清理缓存之后，所保存的数据将会被全部删除掉，下次观看时将消耗流量。\n\n请谨慎操作！", new DialogHelper.DialogOkClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog) {
                        ClearCacheUtils.clear();
                        Toast.makeText(SettingActivity.this, "清理完成", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.rl_font_size: /*字体设置*/
                Toast.makeText(this, "此功能将在下个版本实现", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
