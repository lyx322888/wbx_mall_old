package com.wbx.mall.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.Constants;
import com.wbx.mall.utils.FormatUtil;
import com.wbx.mall.utils.MD5;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/7/11.
 */

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.register_get_code_btn)
    Button getCodeBtn;
    @Bind(R.id.register_phone_edit)
    EditText mPhoneEdit;
    @Bind(R.id.register_sms_edit)
    EditText mSMSCode;
    @Bind(R.id.register_first_psw_edit)
    EditText mFirstPswEdit;
    @Bind(R.id.register_second_psw_edit)
    EditText mSecondPswEdit;
    private HashMap<String, Object> mParams = new HashMap<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
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

    @OnClick({R.id.register_get_code_btn, R.id.do_register_btn, R.id.tv_register_protocol})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_get_code_btn:
                String phone = mPhoneEdit.getText().toString();
                if (isInputSuccess(phone)) {
                    senCode(phone);
                }
                break;
            case R.id.do_register_btn:
                doRegister();
                break;
            case R.id.tv_register_protocol:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("url", Constants.REGISTER_PROTOCOL);
                intent.putExtra("title", "用户注册协议");
                startActivity(intent);
                break;
        }
    }

    private void doRegister() {
        String phone = mPhoneEdit.getText().toString();
        String smsCode = mSMSCode.getText().toString();
        String firstPsw = mFirstPswEdit.getText().toString();
        String secondPsw = mSecondPswEdit.getText().toString();
        String MD5psw = new MD5().EncoderByMd5(firstPsw);
        if (isInputSuccess(phone) && registerIsSuccess(smsCode, firstPsw, secondPsw)) {
            mParams.put("mobile", phone);
            mParams.put("code", smsCode);
            mParams.put("password", MD5psw);
            new MyHttp().doPost(Api.getDefault().register(mParams), new HttpListener() {
                @Override
                public void onSuccess(JSONObject result) {
                    showShortToast("注册成功");
                    finish();
                }

                @Override
                public void onError(int code) {

                }
            });
        }
    }

    private void senCode(String phone) {
        new MyHttp().doPost(Api.getDefault().sendSMSCode(phone), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                timer.start();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            getCodeBtn.setEnabled(false);
            getCodeBtn.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            getCodeBtn.setEnabled(true);
            getCodeBtn.setText("获取验证码");
        }
    };

    private boolean isInputSuccess(String phone) {
        if ("".equals(phone)) {
            showShortToast("请输入手机号码");
            return false;
        }
        if (!FormatUtil.isMobileNO(phone)) {
            showShortToast("请输入正确的手机号码");
            return false;
        }
        return true;
    }

    /**
     * 注册验证
     *
     * @param smsCode
     * @param firstPsw
     * @param secondPsw
     * @return
     */
    private boolean registerIsSuccess(String smsCode, String firstPsw, String secondPsw) {
        if ("".equals(smsCode)) {
            showShortToast("请输入验证码");
            return false;
        }
        if ("".equals(firstPsw)) {
            showShortToast("请输入密码");
            return false;
        }
        if ("".equals(secondPsw)) {
            showShortToast("请输入确认密码");
            return false;
        }
        if (!firstPsw.equals(secondPsw)) {
            showShortToast("两次输入的密码不一致");
            return false;
        }
        return true;
    }
}
