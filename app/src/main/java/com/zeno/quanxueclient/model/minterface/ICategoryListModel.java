package com.zeno.quanxueclient.model.minterface;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/13
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

public interface ICategoryListModel {

    void loadDatas(String url,LoadListener loadListener);

    interface LoadListener<T> {
         void onSuccess(T t);
         void onError(String msg);
    }
}
