package com.zeno.quanxueclient.view.vinterface;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/14
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.zeno.quanxueclient.bean.BookContentsBean;

import java.util.List;

public interface IBookContentsView extends IBaseView<List<BookContentsBean>> {

    void onCollectionSuccess(boolean isCollection);
    void onCollectionError(String msg);
}
