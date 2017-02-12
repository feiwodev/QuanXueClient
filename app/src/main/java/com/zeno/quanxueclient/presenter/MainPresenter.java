package com.zeno.quanxueclient.presenter;

import com.zeno.quanxueclient.bean.MainBean;
import com.zeno.quanxueclient.model.minterface.ILoadDatasModel;
import com.zeno.quanxueclient.model.minterface.IMainModel;
import com.zeno.quanxueclient.model.MainModelImp;
import com.zeno.quanxueclient.view.vinterface.IMainView;

/**
 * Created by Administrator on 2016/12/7.
 */

public class MainPresenter extends BasePresenter<IMainView> {

    private IMainView iMainView;
    private IMainModel iMainModel = new MainModelImp();

    public MainPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
    }

    public void fetch() {
        if (iMainModel != null) {
             iMainView.showLoading();
             iMainModel.loadDatas(new ILoadDatasModel.LoadListener<MainBean>(){

                 @Override
                 public void onSuccess(MainBean mainBean) {
                     if (iMainView != null) {
                         iMainView.hideLoading();
                         iMainView.showDatas(mainBean);
                     }

                 }

                 @Override
                 public void onError(String msg) {
                    if (iMainView != null) {
                        iMainView.hideLoading();
                    }
                 }
             });
        }
    }
}
