package com.zeno.quanxueclient;

import android.app.Application;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.sdk.QbSdk;
import com.yolanda.nohttp.NoHttp;
import com.zeno.quanxueclient.db.DBManager;
import com.zeno.quanxueclient.db.gen.DaoSession;
import com.zeno.quanxueclient.utils.QBSdkPreInit;


/**
 * Created by Administrator on 2016/12/7.
 *
 */

public class App extends Application {

    private static App _INSTANCE;
    private DaoSession mDaoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        _INSTANCE = this;

        // 配置网络
        NoHttp.initialize(this);

        // 配置日志打印
        XLog.init(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE);

        // 初始化DB session
        mDaoSession = DBManager.getInstance(this).getDaoSession();

        /*初始化X5*/
        QbSdk.initX5Environment(getApplicationContext(),new QBSdkPreInit());

        /*bugly*/
        Bugly.init(getApplicationContext(), "7545014971", BuildConfig.DEBUG ? true : false);
    }


    public static App getInstance() {
        return _INSTANCE;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
