package com.zeno.quanxueclient.model;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/13
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.elvishew.xlog.XLog;
import com.zeno.quanxueclient.App;
import com.zeno.quanxueclient.bean.BookBean;
import com.zeno.quanxueclient.db.gen.BookBeanDao;
import com.zeno.quanxueclient.db.gen.DaoSession;
import com.zeno.quanxueclient.model.htmlparser.CommonCategoryListParser;
import com.zeno.quanxueclient.model.htmlparser.OtherCategoryListParser;
import com.zeno.quanxueclient.model.minterface.ICategoryListModel;
import com.zeno.quanxueclient.net.HttpStringCallBack;
import com.zeno.quanxueclient.net.HttpUtils;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 书籍列表数据加载处理
 */
public class CategoryListModelImp implements ICategoryListModel {

    private final BookBeanDao mBookBeanDao;
    private LoadListener mLoadListener;

    public CategoryListModelImp() {
        DaoSession daoSession = App.getInstance().getDaoSession();
        mBookBeanDao = daoSession.getBookBeanDao();
    }

    @Override
    public void loadDatas(String url, LoadListener loadListener) {
        mLoadListener = loadListener;
        if (!isStorage(url))
            HttpUtils.get(url, new HttpRequestResponse(url));
        else
            mLoadListener.onSuccess(getCategoryList(url));
    }

    private class HttpRequestResponse implements HttpStringCallBack {


        private String categoryUrl;

        public HttpRequestResponse(String categoryUrl) {
            this.categoryUrl = categoryUrl;
        }

        @Override
        public void onSuccess(int what, String s) {
            Observable.just(s)
                    .map(new Func1<String, List<BookBean>>() {
                        @Override
                        public List<BookBean> call(String s) {

                            if (categoryUrl.contains("LS_") || categoryUrl.contains("CT_FoJia")) {
                                return OtherCategoryListParser.parser(s, categoryUrl);
                            } else {
                                return CommonCategoryListParser.parser(s, categoryUrl);
                            }

                        }
                    })
                    .doOnNext(new Action1<List<BookBean>>() {
                        @Override
                        public void call(List<BookBean> bookBeen) {
                            BookBeanDao bookBeanDao = App.getInstance().getDaoSession().getBookBeanDao();
                            /*缓存*/
                            for (BookBean bookBean : bookBeen) {
                                bookBeanDao.insert(bookBean);
                            }
                        }
                    })
                    .observeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<BookBean>>() {
                        @Override
                        public void call(List<BookBean> bookBeen) {
                            XLog.e(bookBeen.toString());
                            mLoadListener.onSuccess(bookBeen);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            XLog.e(throwable.getMessage());
                        }
                    });

        }

        @Override
        public void onFailed(int what, String s) {
            if (mLoadListener != null) {
                mLoadListener.onError(s);
            }
        }
    }

    /**
     * 首页数据是否缓存到了数据库
     * @return
     */
    private boolean isStorage(String categoryUrl) {
        Query<BookBean> build = mBookBeanDao.queryBuilder().where(BookBeanDao.Properties.CategoryUrl.eq(categoryUrl)).build();

        return build.list().size() > 0;
    }

    /**
     * 获取首页类别存储的全部数据
     * @return
     */
    private List<BookBean> getCategoryList(String categoryUrl) {
        Query<BookBean> build = mBookBeanDao.queryBuilder().where(BookBeanDao.Properties.CategoryUrl.eq(categoryUrl)).build();
        List<BookBean> list = build.list();
        return list;
    }
}
