package com.zeno.quanxueclient.net;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/7
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

import java.util.Map;

/**
 * 网络请求帮助类
 */
public class HttpUtils {

    private static RequestQueue requestQueue = NoHttp.newRequestQueue();

    public static void get(String url,Map<String,String> params,int what,HttpCallBack callBack) {
        request(what,url,params,RequestMethod.GET,callBack);
    }

    public static void get(String url,Map<String,String> params,HttpCallBack callBack) {
        get(url,params,0,callBack);
    }

    public static void get(String url,HttpCallBack callBack) {
        get(url,null,callBack);
    }

    public static void post(String url,Map<String,String> params,int what,HttpCallBack httpCallBack) {
        request(what,url,params,RequestMethod.POST,httpCallBack);
    }

    public static void post(String url,Map<String,String> params,HttpCallBack httpCallBack) {
        request(0,url,params,RequestMethod.POST,httpCallBack);
    }

    /**
     * 网络请求
     * @param url
     * @param params
     * @param method
     */
    public static void request(int what,String url, Map<String,String> params, RequestMethod method,HttpCallBack httpCallBack) {
        Request<String> request = NoHttp.createStringRequest(url, method);
        request.add(params);
        requestQueue.add(what,request,new HttpResponseListener(httpCallBack));
    }

}
