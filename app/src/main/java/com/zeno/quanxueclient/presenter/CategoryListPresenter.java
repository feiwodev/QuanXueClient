package com.zeno.quanxueclient.presenter;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/13
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.zeno.quanxueclient.bean.BookBean;
import com.zeno.quanxueclient.model.CategoryListModelImp;
import com.zeno.quanxueclient.model.minterface.ICategoryListModel;
import com.zeno.quanxueclient.view.vinterface.ICategoryView;

import java.util.List;

public class CategoryListPresenter extends BasePresenter<ICategoryView> {

    ICategoryView iCategoryView;
    ICategoryListModel iCategoryListModel = new CategoryListModelImp();

    public CategoryListPresenter(ICategoryView iCategoryView) {
        this.iCategoryView = iCategoryView;
    }

    public void fetch(String url) {
        if (iCategoryListModel != null) {
            iCategoryView.showLoading();
            iCategoryListModel.loadDatas(url,new ICategoryListModel.LoadListener<List<BookBean>>(){

                @Override
                public void onSuccess(List<BookBean> bookBeen) {
                    if (iCategoryView != null) {
                        iCategoryView.showDatas(bookBeen);
                        iCategoryView.hideLoading();
                    }
                }

                @Override
                public void onError(String msg) {
                    iCategoryView.showError(msg);
                }
            });
        }
    }

}
