package com.wbx.mall.activity;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.utils.SPUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/11/20.
 */

public class VoucherActivity extends BaseActivity {
    @Bind(R.id.scroll_view)
    NestedScrollView scrollView;
    @Bind(R.id.input_money_edit)
    EditText inputMoneyEdit;
    @Bind(R.id.show_user_money_tv)
    TextView showMoneyTv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_voucher;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        showMoneyTv.setText(String.format("可用余额%.2f元", userInfo.getMoney() / 100.00));
    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.submit_btn, R.id.finance_detail_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                if (TextUtils.isEmpty(inputMoneyEdit.getText().toString())) {
                    showShortToast("请输入充值卡密");
                    return;
                }
                submit();
                break;
            case R.id.finance_detail_rl:
                startActivity(new Intent(mContext, VoucherLogsActivity.class));
                break;
        }
    }

    private void submit() {
        new MyHttp().doPost(Api.getDefault().cardRecharge(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), inputMoneyEdit.getText().toString()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                showShortToast("充值成功！");
            }

            @Override
            public void onError(int code) {

            }
        });
    }
}
