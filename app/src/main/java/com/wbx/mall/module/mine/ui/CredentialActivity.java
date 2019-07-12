package com.wbx.mall.module.mine.ui;

import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.LoadingDialog;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/9/5.
 * 授权证书
 */

public class CredentialActivity extends BaseActivity {
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.id_card_number_tv)
    TextView idCardNumberTv;
    @Bind(R.id.number_tv)
    TextView numberTv;
    @Bind(R.id.grade_tv)
    TextView gradeTv;
    private int rank;

    @Override
    public int getLayoutId() {
        return R.layout.activity_credential;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        rank = getIntent().getIntExtra("rank", 1);
        LoadingDialog.showDialogForLoading(this, "信息获取中...", true);
        new MyHttp().doPost(Api.getDefault().getCertificateInfo(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                nameTv.setText(String.format("兹授权:%s", result.getJSONObject("data").getString("name")));
                idCardNumberTv.setText(String.format("身份证号:%s", result.getJSONObject("data").getString("identity_card_no")));
                numberTv.setText(String.format("授权编号:%s", result.getJSONObject("data").getString("number")));
                switch (rank) {
                    case 1:
                        gradeTv.setText("创业合伙人");
                        break;
                    case 2:
                        gradeTv.setText("创业合伙人");
                        break;
                    case 3:
                        gradeTv.setText("事业合伙人");
                        break;
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @Override
    public void setListener() {

    }
}
