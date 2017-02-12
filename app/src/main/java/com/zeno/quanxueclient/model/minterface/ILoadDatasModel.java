package com.zeno.quanxueclient.model.minterface;

/**
 * Created by Administrator on 2016/12/7.
 */

public interface ILoadDatasModel {

    void loadDatas(LoadListener loadListener);

    public abstract class LoadListener<T> {
        public abstract void onSuccess(T t);
        public abstract void onError(String msg);
    }
}
