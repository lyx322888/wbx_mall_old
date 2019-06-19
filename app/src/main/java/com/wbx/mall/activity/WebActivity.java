package com.wbx.mall.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by wushenghui on 2018/1/3.
 */

public class WebActivity extends BaseActivity {
    @Bind(R.id.web_view)
    WebView mWebView;
    private String url;
    @Bind(R.id.title_name_tv)
    TextView titleNameTv;
    private boolean isChat;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        isChat = getIntent().getBooleanExtra("isChat", false);
        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
//        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        webSettings.setUseWideViewPort(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // 使用localStorage则必须打开
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
            titleNameTv.setText(getIntent().getStringExtra("title"));
        } else {
            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    if (title != null) {
                        titleNameTv.setText(title);
                    }
                }
            });
        }
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//               // 获取上下文, H5PayDemoActivity为当前页面
                final Activity context = WebActivity.this;
                // ------  对alipays:相关的scheme处理 -------
                if (url.startsWith("alipays:") || url.startsWith("alipay")) {
                    try {
                        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    } catch (Exception e) {
                        new AlertDialog.Builder(context)
                                .setMessage("未检测到支付宝客户端，请安装后重试。")
                                .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                        context.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                    }
                                }).setNegativeButton("取消", null).show();
                    }
                    return true;
                }
                // ------- 处理结束 -------

                if (!(url.startsWith("http") || url.startsWith("https"))) {
                    return true;
                }

                view.loadUrl(url);
                return true;
            }
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
//                        + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//            }
//            @Override
//            public void onReceivedError(WebView view, int errorCode,
//                                        String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
    }

    @Override
    public void fillData() {
        url = getIntent().getStringExtra("url");
        mWebView.loadUrl(url);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void finish() {
        if (isChat) {
            super.finish();
        } else {
            if (mWebView == null || TextUtils.isEmpty(mWebView.getUrl())) {
                super.finish();
            } else {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    super.finish();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    //判断是否安装支付宝app
    public static boolean checkAliPayInstalled(Context context) {

        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }
}
