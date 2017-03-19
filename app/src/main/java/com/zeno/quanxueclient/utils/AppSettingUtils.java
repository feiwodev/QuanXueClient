package com.zeno.quanxueclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Zeno on 2017/3/19.
 */

public class AppSettingUtils {

    private static String PREFERENCES_NAME = "setting";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static String KEY_READ_CONTENT_BG_COLOR = "read_content_bg_color";

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private static boolean isInit() {
        if (sharedPreferences != null)
            return true;
        else
            return false;
    }

    public static void setKeyReadContentBgColor(int keyReadContentBgColor) {
        if (isInit()) {
            editor.putInt(KEY_READ_CONTENT_BG_COLOR,keyReadContentBgColor).apply();
        }else{
            new ExceptionInInitializerError("未初始化AppSettingUtils,AppSettingUtils.init()");
        }
    }

    public static int getKeyReadContentBgColor() {
        return sharedPreferences.getInt(KEY_READ_CONTENT_BG_COLOR,1);
    }
}
