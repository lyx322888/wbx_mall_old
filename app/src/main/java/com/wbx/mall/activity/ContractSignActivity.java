package com.wbx.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.activity.agent.ToBeAgentActivity;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.ApiConstants;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.utils.PictureUtil;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.UpLoadPicUtils;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.SignView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

public class ContractSignActivity extends BaseActivity {
    @Bind(R.id.tv_sign)
    TextView tvSign;
    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.ll_dialog)
    LinearLayout llDialog;
    @Bind(R.id.sign_view)
    SignView signView;
    @Bind(R.id.ll_sign)
    LinearLayout llSign;
    private boolean hasSign = false;

    public static void actionStart(Activity context, int rank) {
        Intent intent = new Intent(context, ContractSignActivity.class);
        intent.putExtra("rank", rank);
        context.startActivityForResult(intent, 0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_contract_sign;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        String contractUrl;
        contractUrl = (ApiConstants.DEBUG ? "http://vrzff.com" : "http://www.wbx365.com") + "/wap/message/wbx_join_agreement/login_token/" + SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN);
        webView.loadUrl(contractUrl);
    }

    @Override
    public void setListener() {
    }

    @OnClick({R.id.rl_back, R.id.tv_sign, R.id.iv_close, R.id.tv_sign_online, R.id.tv_review, R.id.tv_submit, R.id.tv_cancel_sign, R.id.tv_resign, R.id.tv_ensure_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_sign:
                llDialog.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_close:
                llDialog.setVisibility(View.GONE);
                break;
            case R.id.tv_sign_online:
                llDialog.setVisibility(View.GONE);
                llSign.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_review:
                llDialog.setVisibility(View.GONE);
                webView.reload();
                break;
            case R.id.tv_submit:
                llDialog.setVisibility(View.GONE);
                submit();
                break;
            case R.id.tv_cancel_sign:
                llSign.setVisibility(View.GONE);
                break;
            case R.id.tv_resign:
                signView.reDraw();
                break;
            case R.id.tv_ensure_sign:
                llSign.setVisibility(View.GONE);
                uploadSign();
                break;
        }
    }

    private void submit() {
        if (!hasSign) {
            Toast.makeText(mContext, "请先签名", Toast.LENGTH_SHORT).show();
            return;
        }
        SubmitAgentDetailActivity.actionStart(this, getIntent().getIntExtra("rank", ToBeAgentActivity.RANK_STAR_MAN));
    }

    private void uploadSign() {
        LoadingDialog.showDialogForLoading(this, "上传中...", false);
        Bitmap signBitmap = signView.save();
        try {
            String signPath = PictureUtil.saveBitmap(signBitmap, "sign");
            UpLoadPicUtils.upOnePic(signPath, new UpLoadPicUtils.UpLoadPicListener() {
                @Override
                public void success(String qiNiuPath) {
                    new MyHttp().doPost(Api.getDefault().addSignPhoto(SPUtils.getSharedStringData(ContractSignActivity.this, AppConfig.LOGIN_TOKEN), qiNiuPath), new HttpListener() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            LoadingDialog.cancelDialogForLoading();
                            hasSign = true;
                            webView.reload();
                        }

                        @Override
                        public void onError(int code) {

                        }
                    });
                }

                @Override
                public void error() {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
