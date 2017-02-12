package com.zeno.quanxueclient.utils;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/7
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.google.gson.Gson;

public class JsonFormater {

    private static Gson gson;

    static {
        gson = new Gson();
    }

    public static <T> T getJsonParseObj(String jsonStr,Class<T> cls) {

        T t = null ;

        try {

            t = (T) gson.fromJson(jsonStr,cls);

        }catch (Exception e) {
            System.err.println("###JSON解析出错，类名：" + cls.getName() + "###，字符串是：" + jsonStr);
            e.printStackTrace();
        }

        return t;
    }

}
