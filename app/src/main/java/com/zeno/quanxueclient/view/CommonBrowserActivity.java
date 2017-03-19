package com.zeno.quanxueclient.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ViewFlipper;

import com.zeno.quanxueclient.R;
import com.zeno.quanxueclient.presenter.BasePresenter;
import com.zeno.quanxueclient.view.widget.X5WebView;

import butterknife.BindView;

/**
 * 通用浏览器
 */
public class CommonBrowserActivity extends BaseActivity {

    private static final String WEB_URL = "web_url";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_web_content)
    FrameLayout flWebContent;
    @BindView(R.id.vf_switch)
    ViewFlipper vfSwitch;
    private X5WebView mX5WebView;

    public static void startCommonBrowserView(Context context, String url) {
        Intent intent = new Intent(context, CommonBrowserActivity.class);
        intent.putExtra(WEB_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_common_browser);

        setToolBar(toolbar, this);

        init();

        setOnListener();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void init() {
        String url = getIntent().getStringExtra(WEB_URL);
        if (mX5WebView == null)
            mX5WebView = new X5WebView(this, null);

        mX5WebView.loadUrl(url);
        flWebContent.addView(mX5WebView);
    }

    private void setOnListener() {
        mX5WebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                setToolBarTitle(s);
            }
        });

        mX5WebView.setWebPageLoadListener(new X5WebView.WebPageLoadListener() {
            @Override
            public void shouldOverrideUrlLoading(WebView view, String url) {
                if (vfSwitch != null)
                    vfSwitch.setDisplayedChild(0);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                if (vfSwitch != null)
                    vfSwitch.setDisplayedChild(1);
            }

            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest) {
                if (vfSwitch != null)
                    vfSwitch.setDisplayedChild(2);
            }
        });
    }

    /**
     * 设置标题
     *
     * @param title
     */
    private void setToolBarTitle(final String title) {
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                toolbar.setTitle(TextUtils.isEmpty(title) ? getString(R.string.no_web_title_str) : title);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mX5WebView != null)
            mX5WebView.destroy();
        super.onDestroy();
    }
}
