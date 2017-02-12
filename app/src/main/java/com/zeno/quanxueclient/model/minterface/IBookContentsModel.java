package com.zeno.quanxueclient.model.minterface;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/14
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.zeno.quanxueclient.bean.BookBean;

public interface IBookContentsModel{

    void loadDatas(String categoryUrl,String url,LoadListener loadListener);
    void isCollectionBook(BookBean bookBean,CollectionListener collectionListener);

    interface LoadListener<T> {
        void onSuccess(T t);
        void onError(String msg);
    }

    interface CollectionListener {
        void onSuccess(boolean isCollection);
        void onError(String msg);
    }
}
