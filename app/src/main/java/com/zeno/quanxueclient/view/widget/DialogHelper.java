package com.zeno.quanxueclient.view.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Zeno on 2017/2/11.
 */

public class DialogHelper {

    private static Dialog mDialog = null ;

    public static void showBasicDialog(Context context, String title, String msg, final DialogOkClickListener okClickListener) {
        if (mDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (dialog != null)
                        dialog.dismiss();
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (okClickListener != null)
                        okClickListener.onClick(dialog);
                }
            });
            mDialog = builder.create();
        }
        if (!((Activity) context).isFinishing()) {
            mDialog.show();
        }

    }

    public interface DialogOkClickListener {
        void onClick(DialogInterface dialog);
    }
}
