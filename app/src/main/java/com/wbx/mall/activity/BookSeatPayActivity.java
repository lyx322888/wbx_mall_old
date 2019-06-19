package com.wbx.mall.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wbx.mall.R;
import com.wbx.mall.adapter.PayMentAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.bean.PayResult;
import com.wbx.mall.bean.PaymentInfo;
import com.wbx.mall.bean.WxPayInfo;
import com.wbx.mall.utils.FormatUtil;
import com.wbx.mall.utils.MD5;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/12/21.
 * 预定座位付款
 */

public class BookSeatPayActivity extends BaseActivity {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
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
//                         该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Intent intent = new Intent(mActivity, BookSeatOrderActivity.class);
                        startActivity(intent);
                        finish();
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


    @Bind(R.id.contacts_name_tv)
    TextView contactsNameTv;
    @Bind(R.id.contacts_phone_tv)
    TextView contactsPhoneTv;
    @Bind(R.id.use_people_num_tv)
    TextView usePeopleNumTv;
    @Bind(R.id.book_time_tv)
    TextView bookTimeTv;
    @Bind(R.id.remark_tv)
    TextView remarkTv;
    @Bind(R.id.book_recycler_view)
    RecyclerView mRecyclerView;
    private PayMentAdapter mPayMentAdapter;
    private List<PaymentInfo> payment = new ArrayList<>();
    private String payCode = AppConfig.PayType.money;
    private String bookId;
    private IWXAPI msgApi;
    private PayReq request;
    private Dialog dialog;
    private HashMap<String, Object> mParams = new HashMap<>();
    @Bind(R.id.need_price_tv)
    TextView needPriceTv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_seat_pay;
    }

    @Override
    public void initPresenter() {
        request = new PayReq();
        msgApi = WXAPIFactory.createWXAPI(mActivity, AppConfig.WX_APP_ID);
    }

    @Override
    public void initView() {
        mParams.put("code", AppConfig.PayType.money);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mPayMentAdapter = new PayMentAdapter(payment, mContext);
        mRecyclerView.setAdapter(mPayMentAdapter);
    }

    @Override
    public void fillData() {
        bookId = getIntent().getStringExtra("bookId");
        mParams.put("reserve_table_id", bookId);
        mParams.put("login_token", SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN));
        LoadingDialog.showDialogForLoading(mActivity, "信息获取中...", true);
        new MyHttp().doPost(Api.getDefault().getBookInfoAndPayInfo(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), bookId), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                JSONObject infoJsonObject = result.getJSONObject("data").getJSONObject("info");
                needPriceTv.setText(Html.fromHtml("订单总费用 : <font color=#ff0000>" + infoJsonObject.getIntValue("subscribe_money") / 100.00 + "</font>"));
                contactsNameTv.setText(infoJsonObject.getString("name"));
                contactsPhoneTv.setText(infoJsonObject.getString("mobile"));
                usePeopleNumTv.setText(infoJsonObject.getString("number"));
                remarkTv.setText(infoJsonObject.getString("note"));
                bookTimeTv.setText(FormatUtil.stampToDate2(infoJsonObject.getString("reserve_time"), "yyyy年MM月dd日 HH:mm"));
                payment.addAll(JSONArray.parseArray(result.getJSONObject("data").getString("payment"), PaymentInfo.class));
                payment.get(0).setChecked(true);
                mPayMentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @Override
    public void setListener() {
        mPayMentAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                mParams.put("code", payment.get(position).getCode());
                payCode = payment.get(position).getCode();
                for (PaymentInfo paymentBean : payment) {
                    paymentBean.setChecked(false);
                }
                payment.get(position).setChecked(true);
                mPayMentAdapter.notifyDataSetChanged();
            }
        });

        findViewById(R.id.submit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                mParams.put("pay_password", md5PayPsw);
                                pay();
                            }
                        }
                    });
                } else {
                    pay();
                }
            }
        });
    }

    private void pay() {
        new MyHttp().doPost(Api.getDefault().bookSeatPay(mParams), new HttpListener() {
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
                    showShortToast("下单成功！");
                    Intent intent = new Intent(mActivity, BookSeatOrderActivity.class);
                    startActivity(intent);
                    finish();
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
}
