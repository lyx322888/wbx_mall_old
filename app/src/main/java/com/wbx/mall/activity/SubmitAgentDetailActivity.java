package com.wbx.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wbx.mall.R;
import com.wbx.mall.module.mine.ui.ToBeAgentActivity;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.bean.PayResult;
import com.wbx.mall.bean.UserInfo;
import com.wbx.mall.bean.WxPayInfo;
import com.wbx.mall.module.mine.ui.AuditResultActivity;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class SubmitAgentDetailActivity extends BaseActivity {
    @Bind(R.id.iv_agent)
    ImageView ivAgent;
    @Bind(R.id.tv_agent)
    TextView tvAgent;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.select_im)
    ImageView selectIm;
    @Bind(R.id.wx_pay_select_im)
    ImageView wxPaySelectIm;
    @Bind(R.id.al_pay_select_im)
    ImageView alPaySelectIm;
    @Bind(R.id.ll_pay_type)
    LinearLayout llPayType;
    @Bind(R.id.ll_account_info)
    LinearLayout llAccountInfo;
    private boolean isAliPay;
    private HashMap<String, Object> mParams = new HashMap<>();
    private int rank;

    public static void actionStart(Activity context, int rank) {
        Intent intent = new Intent(context, SubmitAgentDetailActivity.class);
        intent.putExtra("rank", rank);
        context.startActivityForResult(intent, 0);
    }


    private IWXAPI msgApi;
    private PayReq request;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    private Handler mHandler = new Handler() {
        @Override
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
                        showShortToast("支付成功！");
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_submit_agent_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        request = new PayReq();
        msgApi = WXAPIFactory.createWXAPI(mActivity, AppConfig.WX_APP_ID);
        rank = getIntent().getIntExtra("rank", ToBeAgentActivity.RANK_STAR_MAN);
        //星合伙人
        if (rank == ToBeAgentActivity.RANK_STAR_MAN) {
            ivAgent.setImageResource(R.drawable.agen_two_star);
            tvAgent.setText("星合伙人");
            tvPrice.setText("2500");
            llPayType.setVisibility(View.VISIBLE);
            llAccountInfo.setVisibility(View.GONE);
        } else if (rank == ToBeAgentActivity.RANK_LEADER) {
            ivAgent.setImageResource(R.drawable.agen_three_star);
            tvAgent.setText("领袖");
            tvPrice.setText("25000");
            llPayType.setVisibility(View.GONE);
            llAccountInfo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.choose_wxpay_ll, R.id.choose_alipay_ll, R.id.btn_charge, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose_wxpay_ll:
                alPaySelectIm.setImageResource(R.drawable.uncheck);
                wxPaySelectIm.setImageResource(R.drawable.selected);
                isAliPay = false;
                break;
            case R.id.choose_alipay_ll:
                alPaySelectIm.setImageResource(R.drawable.selected);
                wxPaySelectIm.setImageResource(R.drawable.uncheck);
                isAliPay = true;
                break;
            case R.id.btn_charge:
                charge();
                break;
            case R.id.btn_submit:
                paySuccess();
                break;
        }
    }

    private void charge() {
        mParams.put("login_token", SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN));
        mParams.put("code", isAliPay ? AppConfig.PayType.alipay : AppConfig.PayType.wxpay);
        mParams.put("rank", 2);
        mParams.put("join_fee", 2500);
        new MyHttp().doPost(Api.getDefault().getAgentPayInfo(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                if (isAliPay) {
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
                } else {
                    WxPayInfo data = result.getObject("data", WxPayInfo.class);
                    startWxPay(data);
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void startWxPay(WxPayInfo data) {
        genPayReq(data);
        //注册到微信
        msgApi.registerApp(AppConfig.WX_APP_ID);
        msgApi.sendReq(request);
    }

    private void genPayReq(WxPayInfo data) {
        request.appId = AppConfig.WX_APP_ID;
        request.partnerId = data.getMch_id();
        request.prepayId = data.getPrepay_id();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = data.getNonce_str();
        request.timeStamp = data.getTime() + "";
        request.sign = data.getTwo_sign();
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

    private void paySuccess() {
        setResult(RESULT_OK);
        LoadingDialog.showDialogForLoading(this);
        new MyHttp().doPost(Api.getDefault().checkSalesMan(SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN), rank), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                LoadingDialog.cancelDialogForLoading();
                UserInfo userInfo = (UserInfo) BaseApplication.getInstance().readObject(AppConfig.USER_DATA);
                userInfo.setRank(rank);
                userInfo.setRank_audit("0");
                BaseApplication.getInstance().saveObject(userInfo, AppConfig.USER_DATA);
                AuditResultActivity.actionStart(SubmitAgentDetailActivity.this, true);
                finish();
            }

            @Override
            public void onError(int code) {

            }
        });
    }
}
