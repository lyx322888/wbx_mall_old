package com.wbx.mall.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.utils.MD5;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.LoadingDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/5/31.
 */

public class ResetPayPswActivity extends BaseActivity {
    @Bind(R.id.reset_pay_psw_ed)
    EditText payPsw;
    @Bind(R.id.reset_second_pay_psw_edit)
    EditText paySecondPsw;
    @Bind(R.id.reset_sms_edit)
    EditText SMSEdit;
    @Bind(R.id.sms_code_btn)
    Button getSMSCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_pay_psw;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }

    @OnClick(R.id.sms_code_btn)
    public void getCode() {
        LoadingDialog.showDialogForLoading(mActivity, "发送中...", true);
        new MyHttp().doPost(Api.getDefault().sendSMSCode(userInfo.getMobile()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                timer.start();
            }

            @Override
            public void onError(int code) {

            }
        });

    }

    public void reset(View view) {
        if (isSuccess()) {
            String payPswStr = payPsw.getText().toString();
            String md5PayPsw = new MD5().EncoderByMd5(new MD5().EncoderByMd5(payPswStr));
            new MyHttp().doPost(Api.getDefault().resetPayPassword(SPUtils.getSharedStringData(mActivity, AppConfig.LOGIN_TOKEN), SMSEdit.getText().toString(), userInfo.getMobile(), md5PayPsw), new HttpListener() {
                @Override
                public void onSuccess(JSONObject result) {
                    showShortToast(result.getString("msg"));
                    finish();
                }

                @Override
                public void onError(int code) {

                }
            });
        }

    }

    private boolean isSuccess() {
        if ("".equals(payPsw.getText().toString())) {
            showShortToast("请输入新的密码");
            return false;
        }
        if ("".equals(paySecondPsw.getText().toString())) {
            showShortToast("请再次输入密码");
            return false;
        }
        if ("".equals(SMSEdit.getText().toString())) {
            showShortToast("请输入验证码");
            return false;
        }
        if (!payPsw.getText().toString().equals(paySecondPsw.getText().toString())) {
            showShortToast("两次输入的密码不同");
            return false;
        }
        return true;
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            getSMSCode.setEnabled(false);
            getSMSCode.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            getSMSCode.setEnabled(true);
            getSMSCode.setText("获取验证码");
        }
    };
}
