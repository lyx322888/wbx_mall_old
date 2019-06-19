package com.wbx.mall.activity;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.baserx.MyRxBus;
import com.wbx.mall.bean.WeChatUserInfoBean;
import com.wbx.mall.utils.FormatUtil;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.LoadingDialog;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by wushenghui on 2017/11/7.
 * 绑定支付平台 提现到账账号
 */

public class BindPayPlatformActivity extends BaseActivity {
    @Bind(R.id.pay_account_tv)
    TextView payAccountTv;
    @Bind(R.id.pay_account_edit)
    EditText payAccountEdit;
    @Bind(R.id.name_edit)
    EditText nameEdit;
    @Bind(R.id.phone_edit)
    EditText phoneEdit;
    @Bind(R.id.sms_code_edit)
    EditText smsCodeEdit;
    @Bind(R.id.get_code_btn)
    Button getCodeBtn;
    private HashMap<String, Object> mParams = new HashMap<>();
    private boolean isWXPay;//是否是添加微信账号
    private IWXAPI wxapi;
    @Bind(R.id.we_chat_accredit_layout)
    LinearLayout weChatAccreditLayout;
    @Bind(R.id.we_chat_nickname_tv)
    TextView weChatNickNameTv;
    private Subscription subscribe;
    @Bind(R.id.ali_layout)
    LinearLayout aliLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_pay_plat_form;
    }

    @Override
    public void initPresenter() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        wxapi = WXAPIFactory.createWXAPI(this, AppConfig.WX_APP_ID, true);
        // 将该app注册到微信
        wxapi.registerApp(AppConfig.WX_APP_ID);
    }

    @Override
    public void initView() {
        isWXPay = getIntent().getStringExtra("payMode").equals(AppConfig.PayType.wxpay);
        if (isWXPay) {
            weChatAccreditLayout.setVisibility(View.VISIBLE);
            aliLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        if (!subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
        super.onDestroy();
    }

    @Override
    public void fillData() {
        subscribe = MyRxBus.getInstance().toObserverable(WeChatUserInfoBean.class)
                .subscribe(new Action1<WeChatUserInfoBean>() {
                    @Override
                    public void call(WeChatUserInfoBean weChatUserInfoBean) {
                        weChatNickNameTv.setText(weChatUserInfoBean.getNickname());
                        mParams.put("nick_name", weChatUserInfoBean.getNickname());
                        mParams.put("openid", weChatUserInfoBean.getOpenid());
                    }
                });

        mParams.put("login_token", SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN));
        getPayInfo();
    }

    //获取绑定账号信息
    private void getPayInfo() {
        LoadingDialog.showDialogForLoading(BindPayPlatformActivity.this, "获取数据中...", true);
        new MyHttp().doPost(isWXPay ? Api.getDefault().getWXPayInfo(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN))
                : Api.getDefault().getAliPayInfo(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                JSONObject data = result.getJSONObject("data");
                try {
                    String account = data.getString(isWXPay ? "weixinpay_account" : "alipay_account");
                    String name = data.getString(isWXPay ? "weixinpay_name" : "alipay_name");
                    mParams.put("withdraw_id", data.getString("withdraw_id"));
                    payAccountEdit.setText(account);
                    nameEdit.setText(name);
                } catch (NullPointerException e) {

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

    @OnClick({R.id.bind_submit_btn, R.id.get_code_btn, R.id.we_chat_accredit_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_submit_btn:
                if (canSubmit())
                    submit();
                break;
            case R.id.get_code_btn:
                getSmsCode();
                break;
            case R.id.we_chat_accredit_layout:
                //微信授权
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "state";
                wxapi.sendReq(req);
                break;
        }
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

    //获取验证码
    private void getSmsCode() {
        if (TextUtils.isEmpty(phoneEdit.getText().toString())) {
            showShortToast("请输入电话号码！");
            return;
        }
        if (!FormatUtil.isMobileNO(phoneEdit.getText().toString())) {
            showShortToast("请输入正确的电话号码！");
            return;
        }
        new MyHttp().doPost(Api.getDefault().sendSMSCode(phoneEdit.getText().toString()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                timer.start();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    //添加账号
    private void submit() {
        mParams.put("mobile", phoneEdit.getText().toString());
        mParams.put("code", smsCodeEdit.getText().toString());
        mParams.put(isWXPay ? "weixinpay_account" : "alipay_account", payAccountEdit.getText().toString());
        mParams.put(isWXPay ? "weixinpay_name" : "alipay_name", nameEdit.getText().toString());
        new MyHttp().doPost(isWXPay ? Api.getDefault().addWXPay(mParams) : Api.getDefault().addAliPay(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                showShortToast("添加成功");
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private boolean canSubmit() {
        if (!isWXPay) {
            if (TextUtils.isEmpty(payAccountEdit.getText().toString())) {
                showShortToast("请输入账号！");
                return false;
            }
        }

        if (TextUtils.isEmpty(nameEdit.getText().toString())) {
            showShortToast("请输入姓名！");
            return false;
        }
        if (TextUtils.isEmpty(phoneEdit.getText().toString())) {
            showShortToast("请输入电话号码！");
            return false;
        }
        if (!FormatUtil.isMobileNO(phoneEdit.getText().toString())) {
            showShortToast("请输入正确的电话号码！");
            return false;
        }
        if (TextUtils.isEmpty(smsCodeEdit.getText().toString())) {
            showShortToast("请输入验证码！");
            return false;
        }
        return true;
    }


}
