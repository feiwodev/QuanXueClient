package com.zeno.quanxueclient.model;

import android.os.Handler;
import android.os.Message;

import com.zeno.quanxueclient.App;
import com.zeno.quanxueclient.bean.Bulletins;
import com.zeno.quanxueclient.bean.Category;
import com.zeno.quanxueclient.bean.MainBean;
import com.zeno.quanxueclient.bean.MainSliders;
import com.zeno.quanxueclient.db.gen.CategoryDao;
import com.zeno.quanxueclient.db.gen.DaoSession;
import com.zeno.quanxueclient.model.htmlparser.MainCategoryParser;
import com.zeno.quanxueclient.model.minterface.IMainModel;
import com.zeno.quanxueclient.net.API;
import com.zeno.quanxueclient.net.HttpStringCallBack;
import com.zeno.quanxueclient.net.HttpUtils;
import com.zeno.quanxueclient.utils.JsonFormater;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/7.
 */

public class MainModelImp implements IMainModel {

    private static final String TAG = MainModelImp.class.getSimpleName();

    /**
     * 幻灯片
     */
    private static final int IMAGE_SLIDER_REQUEST_CODE = 1 ;
    /**
     * 公告
     */
    private static final int BULLETIN_REQUEST_CODE = 2 ;
    /**
     * 劝学网首页
     */
    private static final int BOOK_CATEGORY_REQUEST_CODE = 3;

    private MainBean mainBean = new MainBean();
    private final CategoryDao categoryDao;
    private LoadListener mListener;

    public MainModelImp() {
        DaoSession daoSession = App.getInstance().getDaoSession();
        categoryDao = daoSession.getCategoryDao();
    }

    @Override
    public void loadDatas(LoadListener loadListener) {
        this.mListener = loadListener;

        HttpUtils.get(API.IMAGE_SLIDERS_URL,null,IMAGE_SLIDER_REQUEST_CODE,new HttpRequestResponse(loadListener));
        HttpUtils.get(API.BULLETIN_URL,null,BULLETIN_REQUEST_CODE,new HttpRequestResponse(loadListener));
//        HttpUtils.get(API.WEB_HOST,null,BOOK_CATEGORY_REQUEST_CODE,new HttpRequestResponse(loadListener));
        /*如果有缓存 ， 则取缓存中的数据，不进行网络请求*/
        if (!isStorage())
            HttpUtils.get(API.WEB_HOST,null,BOOK_CATEGORY_REQUEST_CODE,new HttpRequestResponse(loadListener));
        else
            mainBean.setCategories(getCategoryAll());

    }


    private class HttpRequestResponse implements HttpStringCallBack {

        private LoadListener loadListener;

        public HttpRequestResponse(LoadListener loadListener) {
            this.loadListener = loadListener;
        }

        @Override
        public void onSuccess(int what, String s) {

            switch (what) {
                case IMAGE_SLIDER_REQUEST_CODE:
                    MainSliders mainSliders = JsonFormater.getJsonParseObj(s, MainSliders.class);
                    mainBean.setImageSliders(mainSliders.getData());
                    break;
                case BULLETIN_REQUEST_CODE:
                    Bulletins bulletins = JsonFormater.getJsonParseObj(s, Bulletins.class);
                    mainBean.setBulletins(bulletins.getBulletins());
                    break;
                case BOOK_CATEGORY_REQUEST_CODE:
                    rxParserHtml(s);
                    break;

            }
            mHandler.sendEmptyMessage(0);
        }

        @Override
        public void onFailed(int what, String s) {
            if (loadListener != null) {
                loadListener.onError(s);
            }
        }
    }

    /**
     * 异步处理html解析
     * @param s
     */
    private void rxParserHtml(String s) {
        Observable.just(s)
                .map(new Func1<String, List<Category>>() {
                    @Override
                    public List<Category> call(String s) {
                        return MainCategoryParser.paser(s);
                    }
                })
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Category>>() {
                    @Override
                    public void call(List<Category> categories) {
                        mainBean.setCategories(categories);
                        mHandler.sendEmptyMessage(0);
                    }
                });
    }

    /**
     * 首页数据是否缓存到了数据库
     * @return
     */
    private boolean isStorage() {
        long count = categoryDao.count();
        return count > 0;
    }

    /**
     * 获取首页类别存储的全部数据
     * @return
     */
    private List<Category> getCategoryAll() {
        Query<Category> build = categoryDao.queryBuilder().build();
        List<Category> list = build.list();
        return list;
    }

    /**
     * 等数据都准备好，发送消息给前端显示
     */
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mListener != null) {
                if (mainBean.getImageSliders() != null && mainBean.getBulletins() != null && mainBean.getCategories() != null && mainBean.getCategories().size() >0) {
                    mListener.onSuccess(mainBean);
                    mHandler.removeMessages(0);
                }
            }
        }
    };
}
