package com.zeno.quanxueclient.db;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/13
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zeno.quanxueclient.db.gen.DaoMaster;
import com.zeno.quanxueclient.db.gen.DaoSession;

/**
 * 数据库管理
 */
public class DBManager {

    private static final String DB_NAME = "qx";

    private Context context;
    private final DaoMaster.DevOpenHelper devOpenHelper;
    private static DBManager _instance;

    public DBManager(Context context) {
        this.context = context;
        devOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
    }


    public static DBManager getInstance(Context context) {
        synchronized (DBManager.class) {
            if (_instance == null) {
                _instance = new DBManager(context);
            }
        }
        return _instance;
    }

    public DaoSession getDaoSession() {
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        return  new DaoMaster(database).newSession();
    }
}
