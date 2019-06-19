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
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.utils.MD5;
import com.wbx.mall.widget.LoadingDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/5/8.
 * 找回密码
 */

public class ForgetPswActivity extends BaseActivity {
    @Bind(R.id.get_code_btn)
    Button getCodeBtn;
    @Bind(R.id.forget_phone_edit)
    EditText phoneEdit;
    @Bind(R.id.sms_code_edit)
    EditText mSMSCodeEdit;
    @Bind(R.id.forget_psw_edit)
    EditText pswEdit;
    @Bind(R.id.forget_psw_again_edit)
    EditText pswAgainEdit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_psw;
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

    @OnClick({R.id.get_code_btn, R.id.forget_submit_btn})
    public void onClick(View view) {
        String phone = phoneEdit.getText().toString();
        String code = mSMSCodeEdit.getText().toString();
        String psw = pswEdit.getText().toString();
        String pswAgain = pswAgainEdit.getText().toString();
        switch (view.getId()) {
            case R.id.get_code_btn:
                if ("".equals(phone)) {
                    showShortToast("请输入手机号码");
                } else {
                    getCode(phone);
                }
                break;
            case R.id.forget_submit_btn:
                if ("".equals(phone)) {
                    showShortToast("请输入手机号码");
                    return;
                }
                if ("".equals(code)) {
                    showShortToast("请输入验证码");
                    return;
                }
                if ("".equals(psw)) {
                    showShortToast("请输入密码");
                    return;
                }
                if ("".equals(pswAgain)) {
                    showShortToast("请输入确认密码");
                    return;
                }
                if (!psw.equals(pswAgain)) {
                    showShortToast("两次输入的密码不同，请重新输入");
                    return;
                }
                submit(phone, code, psw);
                break;
        }
    }

    private void submit(String phone, String code, String psw) {
        String md5Psw = new MD5().EncoderByMd5(psw);
        LoadingDialog.showDialogForLoading(mActivity, "信息提交中...", true);
        new MyHttp().doPost(Api.getDefault().forgetPassword(phone, code, md5Psw), new HttpListener() {
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

    //获取验证码
    private void getCode(String phone) {
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
}
