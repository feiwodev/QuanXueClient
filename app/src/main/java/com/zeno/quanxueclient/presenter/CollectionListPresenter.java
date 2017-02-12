package com.zeno.quanxueclient.presenter;

import com.zeno.quanxueclient.bean.BookBean;
import com.zeno.quanxueclient.model.CollectionListModelImp;
import com.zeno.quanxueclient.model.minterface.ICollectionModel;
import com.zeno.quanxueclient.view.vinterface.ICollectionView;

import java.util.List;

/**
 * Created by Zeno on 2017/1/9.
 */

public class CollectionListPresenter extends BasePresenter<ICollectionView> {

    private ICollectionView iCollectionView;
    private ICollectionModel iCollectionModel = new CollectionListModelImp();

    public CollectionListPresenter(ICollectionView iCollectionView) {
        this.iCollectionView = iCollectionView;
    }

    public void fetch() {
        if (iCollectionModel != null) {
            iCollectionView.showLoading();
            iCollectionModel.loadDatas(new ICollectionModel.LoadListener<List<BookBean>>(){
                @Override
                public void onSuccess(List<BookBean> bookBeen) {
                    if (iCollectionView != null)
                        iCollectionView.showDatas(bookBeen);
                }
                @Override
                public void onError(String msg) {
                    if (iCollectionView != null)
                        iCollectionView.hideLoading();
                }
            });
        }
    }
}
