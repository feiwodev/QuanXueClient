package com.zeno.quanxueclient.view.vinterface;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/13
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.zeno.quanxueclient.bean.BookBean;

import java.util.List;

public interface ICategoryView extends IBaseView<List<BookBean>> {

    void showError(String mssage);
}
