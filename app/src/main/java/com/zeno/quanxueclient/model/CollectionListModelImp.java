package com.zeno.quanxueclient.model;

import com.zeno.quanxueclient.App;
import com.zeno.quanxueclient.bean.BookBean;
import com.zeno.quanxueclient.db.gen.BookBeanDao;
import com.zeno.quanxueclient.model.minterface.ICollectionModel;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Zeno on 2017/1/9.
 */

public class CollectionListModelImp implements ICollectionModel {

    private final BookBeanDao mBookBeanDao;

    public CollectionListModelImp() {
        mBookBeanDao = App.getInstance().getDaoSession().getBookBeanDao();
    }

    @Override
    public void loadDatas(final LoadListener loadListener) {
        Observable.just("").map(new Func1<Object, List<BookBean>>() {
            @Override
            public List<BookBean> call(Object o) {
                return queryCollectionBookList();
            }
        })
        .observeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<BookBean>>() {
            @Override
            public void call(List<BookBean> bookBeen) {
                loadListener.onSuccess(bookBeen);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                loadListener.onError(throwable.getMessage());
            }
        });
    }

    private List<BookBean> queryCollectionBookList(){
        return mBookBeanDao.queryBuilder().where(BookBeanDao.Properties.IsCollection.eq(1)).build().list();
    }
}
