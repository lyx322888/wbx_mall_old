package com.wbx.mall.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wbx.mall.MainActivity;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.baserx.MyRxBus;
import com.wbx.mall.bean.UserInfo;
import com.wbx.mall.bean.WeChatUserInfoBean;
import com.wbx.mall.dialog.BindPhoneDialog;
import com.wbx.mall.utils.DeviceUtils;
import com.wbx.mall.utils.FormatUtil;
import com.wbx.mall.utils.MD5;
import com.wbx.mall.utils.SPUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by wushenghui on 2017/7/10.
 * 登录
 */

public class LoginActivity extends BaseActivity {
    @Bind(R.id.login_account_edit)
    EditText etPhone;
    @Bind(R.id.index_code)
    View indexCode;
    @Bind(R.id.index_password)
    View indexPassword;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.tv_send_code)
    TextView tvSendCode;
    @Bind(R.id.ll_code)
    LinearLayout llCode;
    @Bind(R.id.tv_login_by_code)
    TextView tvLoginByCode;
    @Bind(R.id.tv_login_by_password)
    TextView tvLoginByPassword;
    private boolean isMainTo;
    private String account;
    private String hxUserName;
    private String hxPsw;
    private Subscription subscribe;
    private int hxErrorCount = 0;
    private Dialog mLoadingDialog;
    private boolean isLoginByCode = true;//通过短信登录

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        updateLoginType();
    }

    private void updateLoginType() {
        if (isLoginByCode) {
            tvLoginByCode.setSelected(true);
            tvLoginByPassword.setSelected(false);
            indexCode.setVisibility(View.VISIBLE);
            indexPassword.setVisibility(View.INVISIBLE);
            llCode.setVisibility(View.VISIBLE);
            etPassword.setVisibility(View.GONE);
        } else {
            tvLoginByCode.setSelected(false);
            tvLoginByPassword.setSelected(true);
            indexCode.setVisibility(View.INVISIBLE);
            indexPassword.setVisibility(View.VISIBLE);
            llCode.setVisibility(View.GONE);
            etPassword.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void fillData() {
        isMainTo = getIntent().getBooleanExtra("isMainTo", false);
        if (null != userInfo) etPhone.setText(userInfo.getMobile());
        subscribe = MyRxBus.getInstance().toObserverable(WeChatUserInfoBean.class)
                .subscribe(new Action1<WeChatUserInfoBean>() {
                    @Override
                    public void call(WeChatUserInfoBean weChatUserInfoBean) {
                        getWxUserInfo(weChatUserInfoBean);
                    }
                });
    }

    private void getWxUserInfo(final WeChatUserInfoBean weChatUserInfoBean) {
        if (weChatUserInfoBean == null) {
            Toast.makeText(mContext, "微信登陆失败", Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading();
        new MyHttp().doPost(Api.getDefault().loginByWeChat(weChatUserInfoBean.getOpenid(), weChatUserInfoBean.getUnionid(), weChatUserInfoBean.getNickname(),
                weChatUserInfoBean.getHeadimgurl(), "android", BaseApplication.getInstance().getVersion(), JPushInterface.getRegistrationID(this), DeviceUtils.getManufacturer() + "/" + DeviceUtils.getModel() + "/" + DeviceUtils.getSDKVersionName()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                loginSuccess(result);
            }

            @Override
            public void onError(int code) {
                dimissLoading();
                if (code == AppConfig.ERROR_STATE.NO_BIND_PHONE) {
                    BindPhoneDialog bindPhoneDialog = BindPhoneDialog.newInstance(LoginActivity.this);
                    bindPhoneDialog.setCancelable(false);
                    bindPhoneDialog.setResultListener(new BindPhoneDialog.OnResultListener() {
                        @Override
                        public void onSuccess(String phone, String code, String password) {
                            bindPhone(weChatUserInfoBean, phone, code, password);
                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                    bindPhoneDialog.show(LoginActivity.this.getSupportFragmentManager(), "");
                }
            }
        });
    }

    private void bindPhone(WeChatUserInfoBean weChatUserInfoBean, String phone, String code, String password) {
        password = new MD5().EncoderByMd5(password);
        showLoading();
        new MyHttp().doPost(Api.getDefault().bindPhone(weChatUserInfoBean.getOpenid(), weChatUserInfoBean.getUnionid(), weChatUserInfoBean.getNickname(),
                weChatUserInfoBean.getHeadimgurl(), "android", BaseApplication.getInstance().getVersion(), JPushInterface.getRegistrationID(this),
                phone, code, password, DeviceUtils.getManufacturer() + "/" + DeviceUtils.getModel() + "/" + DeviceUtils.getSDKVersionName()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                loginSuccess(result);
            }

            @Override
            public void onError(int code) {
                dimissLoading();
            }
        });
    }

    @Override
    public void setListener() {

    }

    private void doLogin() {
        account = etPhone.getText().toString();
        if (TextUtils.isEmpty(account) || (isLoginByCode ? TextUtils.isEmpty(etCode.getText().toString()) : TextUtils.isEmpty(etPassword.getText().toString()))) {
            Toast.makeText(mContext, isLoginByCode ? "请输入账号验证码" : "请输入账号密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (account.length() != 11) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isLoginByCode) {
            //短信登录
            showLoading();
            new MyHttp().doPost(Api.getDefault().loginByCode(account, etCode.getText().toString(), "android", BaseApplication.getInstance().getVersion(), JPushInterface.getRegistrationID(this), DeviceUtils.getManufacturer() + "/" + DeviceUtils.getModel() + "/" + DeviceUtils.getSDKVersionName()), new HttpListener() {
                @Override
                public void onSuccess(JSONObject result) {
                    loginSuccess(result);
                }

                @Override
                public void onError(int code) {
                    dimissLoading();
                }
            });
        } else {
            //密码登录
            String passWord = etPassword.getText().toString();
            String md5Psw = new MD5().EncoderByMd5(passWord);
            showLoading();
            HashMap<String, Object> params = new HashMap<>();
            params.put("account", account);
            params.put("password", md5Psw);
            params.put("app_type", "android");
            params.put("version", BaseApplication.getInstance().getVersion());
            params.put("registration_id", JPushInterface.getRegistrationID(this));
            params.put("phone_type", DeviceUtils.getManufacturer() + "/" + DeviceUtils.getModel() + "/" + DeviceUtils.getSDKVersionName());
            new MyHttp().doPost(Api.getDefault().login(params), new HttpListener() {

                @Override
                public void onSuccess(JSONObject result) {
                    loginSuccess(result);
                }

                @Override
                public void onError(int code) {
                    dimissLoading();
                }
            });
        }
    }

    private void loginSuccess(JSONObject result) {
        JSONObject data = result.getJSONObject("data");
        String login_token = data.getString("login_token");
        hxUserName = data.getString("hx_username");
        hxPsw = data.getString("hx_password");
        String face = data.getString("face");
        if (null == userInfo) {
            userInfo = new UserInfo();
        }
        userInfo.setHx_username(hxUserName);
        userInfo.setFace(face);
        userInfo.setIs_salesman(data.getIntValue("is_salesman"));
        BaseApplication.getInstance().saveObject(userInfo, AppConfig.USER_DATA);
        SPUtils.setSharedStringData(mContext, AppConfig.LOGIN_TOKEN, login_token);
        SPUtils.setSharedBooleanData(mActivity, AppConfig.LOGIN_STATE, true);//保存登录状态
        loginHx();
    }

    /**
     * 登录环信
     */
    private void loginHx() {
        if (TextUtils.isEmpty(userInfo.getHx_username())) {
            nextStep();
        } else {
            EMClient.getInstance().login(hxUserName, hxPsw, new EMCallBack() {
                @Override
                public void onSuccess() {
                    nextStep();
                }

                @Override
                public void onError(int error, String s) {
                    if (error == EMError.USER_ALREADY_LOGIN) {
                        hxErrorCount++;
                        if (hxErrorCount >= 3) {
                            nextStep();
                        } else {
                            EMClient.getInstance().logout(true);
                            loginHx();
                        }
                    } else {
                        dimissLoading();
                        Looper.prepare();
                        Toast.makeText(mContext, "环信异常：" + error + "," + s, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
    }

    private void nextStep() {
        dimissLoading();
        if (isMainTo) {
            finish();
        } else {
            startActivity(new Intent(mActivity, MainActivity.class));
            finish();
        }
    }

    private void loginByWeChat() {
        IWXAPI wxapi = WXAPIFactory.createWXAPI(this, AppConfig.WX_APP_ID);
        wxapi.registerApp(AppConfig.WX_APP_ID);
        if (!wxapi.isWXAppInstalled()) {
            Toast.makeText(this, "没有安装微信客户端，无法微信登陆", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "none";
        wxapi.sendReq(req);
    }

    @Override
    protected void onDestroy() {
        if (!subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
        super.onDestroy();
    }

    private void showLoading() {
        dimissLoading();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_loading, null);
        TextView loadingText = view.findViewById(R.id.id_tv_loading_dialog_text);
        loadingText.setText("登录中...");
        ImageView loadingIm = view.findViewById(R.id.loading_im);
        AnimationDrawable mAnim = (AnimationDrawable) loadingIm.getDrawable();
        mAnim.start();
        mLoadingDialog = new Dialog(this, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
    }

    private void dimissLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog.cancel();
        }
    }

    @OnClick({R.id.ll_login_by_code, R.id.ll_login_by_password, R.id.tv_send_code, R.id.login_login_btn, R.id.register_tv, R.id.forget_psw_tv, R.id.iv_login_by_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_login_by_code:
                isLoginByCode = true;
                updateLoginType();
                break;
            case R.id.ll_login_by_password:
                isLoginByCode = false;
                updateLoginType();
                break;
            case R.id.tv_send_code:
                String phone = etPhone.getText().toString();
                if (isInputSuccess(phone)) {
                    senCode(phone);
                }
                break;
            case R.id.login_login_btn:
                doLogin();
                break;
            case R.id.register_tv:
                startActivity(new Intent(mActivity, RegisterActivity.class));
                break;
            case R.id.forget_psw_tv:
                startActivity(new Intent(mActivity, ForgetPswActivity.class));
                break;
            case R.id.iv_login_by_wechat:
                loginByWeChat();
                break;
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
            tvSendCode.setEnabled(false);
            tvSendCode.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            tvSendCode.setEnabled(true);
            tvSendCode.setText("获取验证码");
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
}
