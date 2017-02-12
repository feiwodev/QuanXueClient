package com.zeno.quanxueclient.presenter;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/16
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.zeno.quanxueclient.bean.BookContentsBean;
import com.zeno.quanxueclient.model.minterface.IReadBookModel;
import com.zeno.quanxueclient.model.ReadBookModelImp;
import com.zeno.quanxueclient.view.vinterface.IReadBookView;

import java.util.List;

public class ReadBookPresenter extends BasePresenter<IReadBookView> {

    private IReadBookView iReadBookView;

    private IReadBookModel iReadBookModel = new ReadBookModelImp();

    public ReadBookPresenter(IReadBookView iReadBookView) {
        this.iReadBookView = iReadBookView;
    }

    public void fetch(String url){
        if (iReadBookModel != null){
            iReadBookModel.loadDatas(url,new IReadBookModel.LoadListener<List<BookContentsBean>>(){

                @Override
                public void onSuccess(List<BookContentsBean> bookContentBeen) {
                    if (iReadBookView != null){
                        iReadBookView.showDatas(bookContentBeen);
                        iReadBookView.hideLoading();
                    }
                }

                @Override
                public void onError(String msg) {

                }
            });
        }
    }
}
