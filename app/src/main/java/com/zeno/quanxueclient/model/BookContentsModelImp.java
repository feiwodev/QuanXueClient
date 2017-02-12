package com.zeno.quanxueclient.model;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/14
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.elvishew.xlog.XLog;
import com.zeno.quanxueclient.App;
import com.zeno.quanxueclient.bean.BookBean;
import com.zeno.quanxueclient.bean.BookContentsBean;
import com.zeno.quanxueclient.db.gen.BookBeanDao;
import com.zeno.quanxueclient.db.gen.BookContentsBeanDao;
import com.zeno.quanxueclient.model.htmlparser.BookContentsParser;
import com.zeno.quanxueclient.model.minterface.IBookContentsModel;
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
 * 书籍详情及目录
 */
public class BookContentsModelImp implements IBookContentsModel {

    private final BookContentsBeanDao mBookContentsBeanDao;
    private final BookBeanDao mBookBeanDao;

    public BookContentsModelImp() {
        mBookContentsBeanDao = App.getInstance().getDaoSession().getBookContentsBeanDao();
        mBookBeanDao = App.getInstance().getDaoSession().getBookBeanDao();
    }

    @Override
    public void loadDatas(final String categoryUrl, String url, final LoadListener loadListener) {
        if (!isStorage(url))
            HttpUtils.get(url, new BookContentsCallBack(categoryUrl,url,loadListener));
        else
            loadListener.onSuccess(getBookContentsList(url));
    }

    @Override
    public void isCollectionBook(BookBean bookBean, final CollectionListener collectionListener) {
        Observable.just(bookBean).map(new Func1<BookBean, Boolean>() {
            @Override
            public Boolean call(BookBean bookBean) {
                return isCollectionBook(bookBean);
            }
        })
        .observeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                collectionListener.onSuccess(aBoolean.booleanValue());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                collectionListener.onError(throwable.getMessage());
            }
        });
    }

    /**
     * 請求數據處理
     */
    private class BookContentsCallBack implements HttpStringCallBack {

        private LoadListener listener;
        private String categoryUrl;
        private String bookUrl;

        public BookContentsCallBack(String categoryUrl,String bookUrl,LoadListener listener) {
            this.listener = listener;
            this.categoryUrl = categoryUrl;
            this.bookUrl = bookUrl;
        }

        @Override
        public void onSuccess(int what, String s) {
            Observable.just(s)
                    .map(new Func1<String, List<BookContentsBean>>() {
                        @Override
                        public List<BookContentsBean> call(String s) {

                            return BookContentsParser.parser(categoryUrl,bookUrl,s);

                        }
                    })
                    .observeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<BookContentsBean>>() {
                        @Override
                        public void call(List<BookContentsBean> bookBeen) {
                            XLog.e(bookBeen.toString());
                            listener.onSuccess(bookBeen);
                        }
                    });
        }

        @Override
        public void onFailed(int what, String s) {

        }
    }


    /**
     * 首页数据是否缓存到了数据库
     * @return
     */
    private boolean isStorage(String bookUrl) {
        Query<BookContentsBean> build = mBookContentsBeanDao.queryBuilder().where(BookContentsBeanDao.Properties.BookUrl.eq(bookUrl)).build();
        XLog.e(build.list().size());
        return build.list().size() > 0;
    }

    /**
     * 获取首页类别存储的全部数据
     * @return
     */
    private List<BookContentsBean> getBookContentsList(String bookUrl) {
        Query<BookContentsBean> build = mBookContentsBeanDao.queryBuilder().where(BookContentsBeanDao.Properties.BookUrl.eq(bookUrl)).build();
        List<BookContentsBean> list = build.list();
        return list;
    }

    private boolean isCollectionBook(BookBean bookBean) {
        BookBean bookBean1 = mBookBeanDao.queryBuilder().where(BookBeanDao.Properties.Url.eq(bookBean.getUrl())).build().unique();
        if (bookBean1 != null) {
            bookBean1.setIsCollection((short) (bookBean1.getIsCollection() == 0 ? 1 : 0));
            mBookBeanDao.update(bookBean1);
            return bookBean1.getIsCollection() == 1;
        }else{
            return false;
        }

    }
}
