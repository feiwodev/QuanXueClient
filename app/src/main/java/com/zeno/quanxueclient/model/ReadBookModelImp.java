package com.zeno.quanxueclient.model;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/16
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import android.text.TextUtils;

import com.elvishew.xlog.XLog;
import com.zeno.quanxueclient.App;
import com.zeno.quanxueclient.bean.BookContentsBean;
import com.zeno.quanxueclient.db.gen.BookContentsBeanDao;
import com.zeno.quanxueclient.model.minterface.IReadBookModel;
import com.zeno.quanxueclient.net.HttpStringCallBack;
import com.zeno.quanxueclient.net.HttpUtils;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class ReadBookModelImp implements IReadBookModel {

    private final BookContentsBeanDao mBookContentsBeanDao;

    public ReadBookModelImp() {
        mBookContentsBeanDao = App.getInstance().getDaoSession().getBookContentsBeanDao();
    }

    @Override
    public void loadDatas(String url, LoadListener loadListener) {
        if (isContent(url))
            loadListener.onSuccess(getBookContentList(url));
        else
            HttpUtils.get(url,new HttpResponseCallBack());
    }

    private class HttpResponseCallBack implements HttpStringCallBack {

        @Override
        public void onSuccess(int what, String s) {
            XLog.e(s);
        }

        @Override
        public void onFailed(int what, String s) {

        }
    }

    /**
     * 首页数据是否缓存到了数据库
     * @return
     */
    private boolean isContent(String bookUrl) {
        Query<BookContentsBean> build = mBookContentsBeanDao.queryBuilder().where(BookContentsBeanDao.Properties.BookUrl.eq(bookUrl)).build();
        List<BookContentsBean> list = build.list();
        XLog.e(build.list().size());
        if (list.size() >0) {
            BookContentsBean bookContentsBean = list.get(0);
            String sectionContent = bookContentsBean.getSectionUrl();
            if (!TextUtils.isEmpty(sectionContent)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取首页类别存储的全部数据
     * @return
     */
    private List<BookContentsBean> getBookContentList(String bookUrl) {
        Query<BookContentsBean> build = mBookContentsBeanDao.queryBuilder().where(BookContentsBeanDao.Properties.BookUrl.eq(bookUrl)).build();
        List<BookContentsBean> list = build.list();
        return list;
    }
}
