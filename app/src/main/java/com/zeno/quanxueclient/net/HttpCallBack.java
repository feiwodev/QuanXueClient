package com.zeno.quanxueclient.net;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/7
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

public interface HttpCallBack<T> {

    void onSuccess(int what,T t) ;

    void onFailed(int what , T t );

}
