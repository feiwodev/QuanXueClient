package com.zeno.quanxueclient.presenter;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/14
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.zeno.quanxueclient.bean.BookBean;
import com.zeno.quanxueclient.bean.BookContentsBean;
import com.zeno.quanxueclient.model.BookContentsModelImp;
import com.zeno.quanxueclient.model.minterface.IBookContentsModel;
import com.zeno.quanxueclient.view.vinterface.IBookContentsView;

import java.util.List;

/**
 * 书籍目录列表
 */
public class BookContentsPresenter extends BasePresenter<IBookContentsView> {

    private IBookContentsView iBookContentsView;
    private IBookContentsModel iBookContentsModel = new BookContentsModelImp();

    public BookContentsPresenter(IBookContentsView iBookContentsView) {
        this.iBookContentsView = iBookContentsView;
    }

    public void fetch(String categoryUrl,String url) {
        if (iBookContentsModel != null) {
            iBookContentsView.showLoading();
            iBookContentsModel.loadDatas(categoryUrl,url,new IBookContentsModel.LoadListener<List<BookContentsBean>>(){

                @Override
                public void onSuccess(List<BookContentsBean> bookContentBeen) {
                    if (iBookContentsView != null){
                        iBookContentsView.showDatas(bookContentBeen);
                        iBookContentsView.hideLoading();
                    }
                }

                @Override
                public void onError(String msg) {
                }
            });
        }
    }

    public void isCollectionBook(BookBean bookBean) {
        if (iBookContentsModel != null) {
            iBookContentsModel.isCollectionBook(bookBean, new IBookContentsModel.CollectionListener() {
                @Override
                public void onSuccess(boolean isCollection) {
                    if (iBookContentsView != null)
                        iBookContentsView.onCollectionSuccess(isCollection);
                }

                @Override
                public void onError(String msg) {
                    if (iBookContentsView != null)
                        iBookContentsView.onCollectionError(msg);
                }
            });
        }
    }
}
