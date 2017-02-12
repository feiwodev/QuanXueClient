package com.zeno.quanxueclient.utils;
// +----------------------------------------------------------------------
// | QuanXue
// +----------------------------------------------------------------------
// | CreateDate : 2016/12/16
// +----------------------------------------------------------------------
// | Author: Zeno <zhuyongit@gmail.com>
// +----------------------------------------------------------------------

import com.elvishew.xlog.XLog;
import com.tencent.smtt.sdk.QbSdk;

public class QBSdkPreInit implements QbSdk.PreInitCallback {

    @Override
    public void onCoreInitFinished() {

    }

    @Override
    public void onViewInitFinished(boolean b) {
        XLog.e("onViewInitFinished");
    }
}
