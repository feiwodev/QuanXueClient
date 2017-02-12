package com.zeno.quanxueclient.net;

import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;

// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/7
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

public class HttpResponseListener implements OnResponseListener {


    private HttpCallBack httpCallBack;

    public HttpResponseListener(HttpCallBack httpCallBack) {
        this.httpCallBack = httpCallBack;
    }

    @Override
    public void onStart(int what) {

    }

    @Override
    public void onSucceed(int what, Response response) {
        httpCallBack.onSuccess(what,response.get());
    }

    @Override
    public void onFailed(int what, Response response) {
        httpCallBack.onFailed(what,response.getException().getMessage());
    }

    @Override
    public void onFinish(int what) {

    }
}
