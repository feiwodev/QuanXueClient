package com.zeno.quanxueclient.model.minterface;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/16
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

/**
 * 阅读书籍
 */
public interface IReadBookModel {

    void loadDatas(String url,LoadListener loadListener);

    interface LoadListener<T> {
        void onSuccess(T t);
        void onError(String msg);
    }
}
