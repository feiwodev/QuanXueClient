package com.zeno.quanxueclient.utils;

import com.zeno.quanxueclient.App;

/**
 * Created by Zeno on 2017/2/11.
 */

public class ClearCacheUtils {

    public static void clear() {
        App.getInstance().getDaoSession().clear();
    }

}
