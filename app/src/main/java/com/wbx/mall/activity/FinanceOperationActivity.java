package com.wbx.mall.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.wbx.mall.utils.CollectionUtils;
import com.wbx.mall.utils.MD5;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.iosdialog.ActionSheetDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/11/16.
 * //财务操作处理 提现 充值等
 */

public class FinanceOperationActivity extends BaseActivity implements ActionSheetDialog.OnSheetItemClickListener {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    @Bind(R.id.choose_third_layout)
    LinearLayout chooseThirdLayout;
    @Bind(R.id.tv_all_widthdraw)
    TextView tvAllWidthdraw;
    @Bind(R.id.iv_withdraw_hint)
    ImageView ivWithdrawHint;
    @Bind(R.id.scroll_view)
    NestedScrollView scrollView;
    @Bind(R.id.title_name_tv)
    TextView titleNameTv;
    @Bind(R.id.money_hint_tv)
    TextView moneyHintTv;
    @Bind(R.id.submit_btn)
    Button submitBtn;
    @Bind(R.id.show_user_money_tv)
    TextView showMoneyTv;
    @Bind(R.id.input_money_edit)
    EditText inputMoneyEdit;
    @Bind(R.id.pay_hint_tv)
    TextView payHintTv;
    @Bind(R.id.pay_mode_im)
    ImageView payModeIm;
    @Bind(R.id.pay_name_tv)
    TextView payNameTv;
    @Bind(R.id.ticket_tv)
    TextView ticketTv;//开票资料
    @Bind(R.id.show_ticket_tv)
    TextView showTicketTv;
    @Bind(R.id.cash_layout)
    LinearLayout cashLayout;
    private static String platform = "";
    private Dialog dialog;
    private HashMap<String, Object> mParams = new HashMap<>();
    private AppConfig.CashType cashType;
    private int canCashMoney;
    private int ticketType = 1;//默认代开发票
    private IWXAPI msgApi;
    private PayReq request;
    private boolean isWithdraw;//是否是提现操作
    private JSONObject data;
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
                        showShortToast("充值成功！");
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

    public static void rechargeStart(Context context) {
        Intent intent = new Intent(context, FinanceOperationActivity.class);
        intent.putExtra("isWithdraw", false);
        context.startActivity(intent);
    }

    public static void withdrawStart(Context context, int canCashMoney, AppConfig.CashType cashType) {
        Intent intent = new Intent(context, FinanceOperationActivity.class);
        intent.putExtra("canCashMoney", canCashMoney);
        intent.putExtra("cashType", cashType);
        intent.putExtra("isWithdraw", true);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_finance_operation;
    }

    @Override
    public void initPresenter() {
        CollectionUtils.setPricePoint(inputMoneyEdit);
    }

    @Override
    public void initView() {
        request = new PayReq();
        msgApi = WXAPIFactory.createWXAPI(mContext, AppConfig.WX_APP_ID);
    }

    @Override
    public void fillData() {
        cashType = (AppConfig.CashType) getIntent().getSerializableExtra("cashType");
        isWithdraw = getIntent().getBooleanExtra("isWithdraw", true);
        canCashMoney = getIntent().getIntExtra("canCashMoney", -1);
        if (!isWithdraw) {
            platform = AppConfig.PayType.alipay;
            titleNameTv.setText("充值");
            moneyHintTv.setText("充值金额");
            submitBtn.setText("确认充值");
            payHintTv.setText("推荐已安装支付宝客户端的用户使用");
            tvAllWidthdraw.setVisibility(View.INVISIBLE);
            ivWithdrawHint.setVisibility(View.GONE);
        } else {
            //押金只能自行开票
            if (cashType == AppConfig.CashType.deposit) {
                ticketType = 0;
            } else {
                ticketTv.setVisibility(View.VISIBLE);
                cashLayout.setVisibility(View.VISIBLE);
            }
            platform = AppConfig.WidthdrawType.alipay;
            //获取绑定账户
            getBindPayInfo();
        }
        if (cashType == AppConfig.CashType.operation_money) {
            showMoneyTv.setText(String.format("提成余额%.2f元", canCashMoney / 100.00));
        } else if (cashType == AppConfig.CashType.recommend_bonus) {
            showMoneyTv.setText(String.format("分红余额%.2f元", canCashMoney / 100.00));
        } else if (cashType == AppConfig.CashType.deposit) {
            showMoneyTv.setText(String.format("押金余额%.2f元", canCashMoney / 100.00));
        } else {
            showMoneyTv.setText(canCashMoney == -1 ? String.format(isWithdraw ? "业务余额%.2f元" : "可用微米%.2f元", userInfo.getMoney() / 100.00) : String.format(isWithdraw ? "业务余额%.2f元" : "可用微米%.2f元", canCashMoney / 100.00));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (data != null && isWithdraw) {
            JSONObject userAliPay = data.getJSONObject("user_alipay");
            JSONObject userWeiXinPay = data.getJSONObject("user_weixinpay");
            JSONObject userBank = data.getJSONObject("user_bank");
            switch (platform) {
                case AppConfig.WidthdrawType.alipay:
                    payModeIm.setImageResource(R.drawable.ali_pay);
                    payNameTv.setText("支付宝");
                    payHintTv.setText(userAliPay.getString("depict"));
                    break;
                case AppConfig.WidthdrawType.wxpay:
                    payModeIm.setImageResource(R.drawable.icon_wechat_pay);
                    payNameTv.setText("微信");
                    payHintTv.setText(userWeiXinPay.getString("depict"));
                    break;
                case AppConfig.WidthdrawType.bank:
                    payModeIm.setImageResource(R.drawable.net_bind);
                    payNameTv.setText(userBank.getString("bank_name"));
                    payHintTv.setText(userBank.getString("depict"));
                    break;
            }
        } else {
            switch (platform) {
                case AppConfig.PayType.alipay:
                    payModeIm.setImageResource(R.drawable.ali_pay);
                    payNameTv.setText("支付宝");
                    payHintTv.setText("推荐已安装支付宝客户端的用户使用");
                    break;
                case AppConfig.PayType.wxpay:
                    payModeIm.setImageResource(R.drawable.icon_wechat_pay);
                    payNameTv.setText("微信");
                    payHintTv.setText("推荐已安装微信客户端的用户使用");
                    break;
            }
        }
    }

    private void getBindPayInfo() {
        new MyHttp().doPost(Api.getDefault().getBindPayInfo(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                data = result.getJSONObject("data");
                JSONObject userAliPay = data.getJSONObject("user_alipay");
                JSONObject userWeiXinPay = data.getJSONObject("user_weixinpay");
                JSONObject userBank = data.getJSONObject("user_bank");
                if (userAliPay == null && userWeiXinPay == null && userBank == null) {
                    showShortToast("请先绑定提现账户");
                    startActivity(new Intent(mContext, ChooseBindActivity.class));
                    finish();
                }
                if (null != userAliPay) {
                    platform = AppConfig.WidthdrawType.alipay;
                    payModeIm.setImageResource(R.drawable.ali_pay);
                    payNameTv.setText("支付宝");
                    payHintTv.setText(userAliPay.getString("depict"));
                } else if (null != userWeiXinPay) {
                    platform = AppConfig.WidthdrawType.wxpay;
                    payModeIm.setImageResource(R.drawable.icon_wechat_pay);
                    payNameTv.setText("微信");
                    payHintTv.setText(userWeiXinPay.getString("depict"));
                } else {
                    platform = AppConfig.WidthdrawType.bank;
                    payModeIm.setImageResource(R.drawable.net_bind);
                    payNameTv.setText(userBank.getString("bank_name"));
                    payHintTv.setText(userBank.getString("depict"));
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @Override
    public void setListener() {
        if (isWithdraw) {
            inputMoneyEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() == 0) {
                        if (cashType == AppConfig.CashType.operation_money) {
                            showMoneyTv.setText(String.format("提成余额%.2f元", canCashMoney / 100.00));
                        } else if (cashType == AppConfig.CashType.recommend_bonus) {
                            showMoneyTv.setText(String.format("分红余额%.2f元", canCashMoney / 100.00));
                        } else if (cashType == AppConfig.CashType.deposit) {
                            showMoneyTv.setText(String.format("押金余额%.2f元", canCashMoney / 100.00));
                        } else {
                            showMoneyTv.setText(canCashMoney == -1 ? String.format(isWithdraw ? "业务余额%.2f元" : "可用余额%.2f元", userInfo.getMoney() / 100.00) : String.format(isWithdraw ? "业务余额%.2f元" : "可用余额%.2f元", canCashMoney / 100.00));
                        }
                    } else {
                        double cashMoney = Double.valueOf(charSequence.toString());
                        if (cashMoney > canCashMoney / 100.00) {
                            showMoneyTv.setText("金额已超过可提现余额");
                            showMoneyTv.setTextColor(Color.parseColor("#F00C0C"));
                        } else {
                            showMoneyTv.setTextColor(Color.parseColor("#b9b9b9"));
                            showMoneyTv.setText(String.format("实际提现¥%.2f(手续费¥%.2f/费率%s)", ticketType == 1 ? cashMoney - (cashMoney * 0.17) : cashMoney - (cashMoney * 0.007), ticketType == 1 ? cashMoney * 0.17 : cashMoney * 0.007, ticketType == 1 ? "17%" : "0.7%"));
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    @OnClick({R.id.choose_third_layout, R.id.submit_btn, R.id.cash_layout, R.id.title_right_layout, R.id.tv_all_widthdraw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_third_layout:
                if (isWithdraw) {
                    //提现
                    ChooseFinanceWayActivity.withdrawStart(FinanceOperationActivity.this, data.toJSONString(), platform);
                } else {
                    //充值
                    ChooseFinanceWayActivity.rechargeStart(FinanceOperationActivity.this, platform);
                }
                break;
            case R.id.submit_btn:
                if (TextUtils.isEmpty(inputMoneyEdit.getText().toString())) {
                    showShortToast(isWithdraw ? "请输入提现金额" : "请输入充值金额");
                    return;
                }
                if (isWithdraw) {
                    if (Float.valueOf(inputMoneyEdit.getText().toString()) > canCashMoney / 100.00) {
                        showShortToast("输入金额超过余额");
                        return;
                    }
                    //弹出输入密码的dialog
                    showInputPswDialog();
                } else {
                    recharge();
                }
                break;
            case R.id.cash_layout:
                new ActionSheetDialog(mContext).builder()
                        .addSheetItem("代开发票", ActionSheetDialog.SheetItemColor.Blue, FinanceOperationActivity.this)
                        .addSheetItem("自行开票", ActionSheetDialog.SheetItemColor.Blue, FinanceOperationActivity.this)
                        .setTitle("请选择开票方式")
                        .setCancelable(true)
                        .show();
                break;
            case R.id.title_right_layout:
                //开票资料
                if (isWithdraw)
                    startActivity(new Intent(mContext, TicketInfoActivity.class));
                break;
            case R.id.tv_all_widthdraw:
                inputMoneyEdit.setText(String.format("%.2f", canCashMoney / 100.00));
                break;
        }
    }

    private void recharge() {
        new MyHttp().doPost(Api.getDefault().recharge(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), inputMoneyEdit.getText().toString(), platform), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                if (platform.equals(AppConfig.PayType.alipay)) {
                    final String data = result.getString("data");
                    //支付宝支付
                    Runnable payRunnable = new Runnable() {
                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(FinanceOperationActivity.this);
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
        //微信支付
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

    //弹出输入密码的dialog
    private void showInputPswDialog() {
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
                    applyCash(md5PayPsw);
                }

            }
        });
    }

    //申请提现
    private void applyCash(String md5PayPsw) {
        mParams.put("login_token", SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN));
        mParams.put("money", inputMoneyEdit.getText().toString());
        mParams.put("pay_password", md5PayPsw);
        String cash_type = "";
        if (cashType == AppConfig.CashType.money) {
            cash_type = "money";
        } else if (cashType == AppConfig.CashType.deposit) {
            cash_type = "deposit";
        } else if (cashType == AppConfig.CashType.operation_money) {
            cash_type = "operation_money";
        } else if (cashType == AppConfig.CashType.recommend_bonus) {
            cash_type = "recommend_bonus";
        }
        String.valueOf(cashType);
        mParams.put("cash_type", cash_type);
        mParams.put("pay_code", platform);
        mParams.put("is_tax", ticketType);
        new MyHttp().doPost(Api.getDefault().applyCash(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                showShortToast(result.getString("msg"));
                finish();
            }

            @Override
            public void onError(int code) {
                if (code == AppConfig.ERROR_STATE.NULL_PAY_PSW) {
                    startActivity(new Intent(mContext, ResetPayPswActivity.class));
                }
            }
        });
    }

    @Override
    public void onClick(int which) {
        if (which == 1) {
            ticketType = 1;
        } else if (which == 2) {
            ticketType = 0;
        }
        showTicketTv.setText(which == 1 ? "开票方式 (代开发票)" : "开票方式 (自行开票)");
        inputMoneyEdit.setText(inputMoneyEdit.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == ChooseFinanceWayActivity.REQUEST_CHOOSE_PLATFORM && data != null) {
            platform = data.getStringExtra("platform");
        }
    }
}
