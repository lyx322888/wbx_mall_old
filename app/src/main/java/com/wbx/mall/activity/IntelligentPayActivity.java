package com.wbx.mall.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.PayResult;
import com.wbx.mall.bean.WxPayInfo;
import com.wbx.mall.utils.MD5;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 支付订单
 */
public class IntelligentPayActivity extends BaseActivity {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    @Bind(R.id.et_price)
    EditText etPrice;
    @Bind(R.id.cb_balance)
    CheckBox cbBalance;
    @Bind(R.id.cb_wechat)
    CheckBox cbWechat;
    @Bind(R.id.cb_ali)
    CheckBox cbAli;
    private String shop_id;
    private String payCode = AppConfig.PayType.money;
    private Dialog dialog;
    private IWXAPI msgApi;

    public static void actionStart(Context context, String shop_id) {
        Intent intent = new Intent(context, IntelligentPayActivity.class);
        intent.putExtra("shop_id", shop_id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_intelligent_pay;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
    }

    @Override
    public void fillData() {
        shop_id = getIntent().getStringExtra("shop_id");
    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.ll_balance, R.id.ll_wechat, R.id.ll_ali, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_balance:
                cbBalance.setChecked(true);
                cbWechat.setChecked(false);
                cbAli.setChecked(false);
                break;
            case R.id.ll_wechat:
                cbBalance.setChecked(false);
                cbWechat.setChecked(true);
                cbAli.setChecked(false);
                break;
            case R.id.ll_ali:
                cbBalance.setChecked(false);
                cbWechat.setChecked(false);
                cbAli.setChecked(true);
                break;
            case R.id.tv_pay:
                goPay();
                break;
        }
    }

    private String pay_password;

    private void goPay() {
        if (TextUtils.isEmpty(etPrice.getText().toString())) {
            Toast.makeText(mContext, "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Float.parseFloat(etPrice.getText().toString()) == 0) {
            Toast.makeText(mContext, "金额必须大于0", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cbBalance.isChecked()) {
            payCode = AppConfig.PayType.money;
        } else if (cbWechat.isChecked()) {
            payCode = AppConfig.PayType.wxpay;
        } else if (cbAli.isChecked()) {
            payCode = AppConfig.PayType.alipay;
        }
        if (payCode.equals(AppConfig.PayType.money)) {
            //钱包支付
            View inflate = getLayoutInflater().inflate(R.layout.balance_pay_view, null);
            if (null == dialog) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(inflate);
                dialog = builder.create();
            }
            dialog.show();
            final EditText payEdit = inflate.findViewById(R.id.pay_balance_edit);
            inflate.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            inflate.findViewById(R.id.sure_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String payPsw = payEdit.getText().toString();
                    if ("".equals(payPsw)) {
                        showShortToast("请填写支付密码");
                    } else {
                        dialog.dismiss();
                        String md5PayPsw = new MD5().EncoderByMd5(new MD5().EncoderByMd5(payPsw));
                        LoadingDialog.showDialogForLoading(mActivity, "支付中...", true);
                        pay_password = md5PayPsw;
                        startPay();
                    }

                }
            });
        } else {
            pay_password = "";
            LoadingDialog.showDialogForLoading(mActivity, "支付中...", true);
            startPay();
        }

    }

    /**
     * 开始支付
     */
    private void startPay() {
        new MyHttp().doPost(Api.getDefault().intelligentPay(SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN), shop_id, String.valueOf(Float.parseFloat(etPrice.getText().toString())), payCode, pay_password), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                if (payCode.equals(AppConfig.PayType.alipay)) {
                    final String data = result.getString("data");
                    //支付宝支付
                    Runnable payRunnable = new Runnable() {
                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(mActivity);
                            Map<String, String> payResult = alipay.payV2(data, true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = payResult;
                            mHandler.sendMessage(msg);
                        }
                    };
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                } else if (payCode.equals(AppConfig.PayType.wxpay)) {
                    WxPayInfo data = result.getObject("data", WxPayInfo.class);
                    startWxPay(data);
                } else if (payCode.equals(AppConfig.PayType.money)) {
                    //余额支付
                    paySuccess();
                }

            }

            @Override
            public void onError(int code) {

            }
        });

    }


    private void paySuccess() {
        IntelligentPayListActivity.actionStart(this);
        finish();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        paySuccess();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            showShortToast("支付结果确认中");

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            showShortToast("支付失败");
                        }
                    }
                    break;


                }
                case SDK_CHECK_FLAG: {
                    showShortToast("检查结果为：" + msg.obj);
                    break;
                }
                default:
                    break;
            }
        }

    };

    private void startWxPay(WxPayInfo data) {
        PayReq payReq = genPayReq(data);
        msgApi = WXAPIFactory.createWXAPI(mActivity, AppConfig.WX_APP_ID);
        //注册到微信
        msgApi.registerApp(AppConfig.WX_APP_ID);
        msgApi.sendReq(payReq);
    }

    private PayReq genPayReq(WxPayInfo data) {
        PayReq request = new PayReq();
        request.appId = AppConfig.WX_APP_ID;
        request.partnerId = data.getMch_id();
        request.prepayId = data.getPrepay_id();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = data.getNonce_str();
        request.timeStamp = data.getTime() + "";
        request.sign = data.getTwo_sign();
        return request;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if ("PaySuccess".equals(event)) {
            paySuccess();
        }
    }
}
