package com.zeno.quanxueclient.view.vinterface;

/**
 * Created by Administrator on 2016/12/7.
 */

public interface IBaseView<T> {

    void showLoading();

    void hideLoading();

    void showDatas(T t);

}
