package com.wbx.mall.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wbx.mall.R;
import com.wbx.mall.adapter.SubmitOrderGoodsAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.AddressInfo;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.bean.OrderInfo;
import com.wbx.mall.bean.PayResult;
import com.wbx.mall.bean.PaymentInfo;
import com.wbx.mall.bean.WxPayInfo;
import com.wbx.mall.common.ActivityManager;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.dialog.DispatchingTimeDialog;
import com.wbx.mall.dialog.GuaKaDialogFragment;
import com.wbx.mall.dialog.PayWayDialog;
import com.wbx.mall.module.mine.ui.AddressManagerActivity;
import com.wbx.mall.module.mine.ui.BookSeatOrderActivity;
import com.wbx.mall.utils.MD5;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/7/17.
 * 提交订单
 */

public class SubmitOrderActivity extends BaseActivity implements PayWayDialog.DialogListener {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    public static final int REQUEST_SELECT_ADDRESS = 1000;
    public static final int REQUEST_ADD_REMARK = 1001;
    public static final int REQUEST_SELECT_COUPON = 1002;
    public static final int REQUEST_SELECT_RED_PACKET = 1003;
    public static final int REQUEST_FREE_ACTIVITY_PAY = 1004;
    @Bind(R.id.tv_packing_fee)
    TextView tvPackingFee;
    @Bind(R.id.ll_selected_address)
    LinearLayout llSelectedAddress;
    @Bind(R.id.recycler_view_goods)
    RecyclerView rvGoods;
    @Bind(R.id.tv_receive_info)
    TextView tvReceiveInfo;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_send_fee)
    TextView tvSendFee;
    @Bind(R.id.tv_actual_pay_fee)
    TextView tvActualPayFee;
    @Bind(R.id.tv_bonus)
    TextView tvBonus;
    @Bind(R.id.tv_red_packet)
    TextView tvRedPacket;
    @Bind(R.id.tv_remark)
    TextView tvRemark;
    @Bind(R.id.tv_coupon)
    TextView tvCoupon;
    @Bind(R.id.tv_full_reduce_fee)
    TextView tvFullReduceFee;
    @Bind(R.id.ll_no_select_address)
    LinearLayout llNoSelectAddress;
    @Bind(R.id.tv_pay_type)
    TextView tvPayType;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.tv_discount_fee)
    TextView tvDiscountFee;
    @Bind(R.id.tv_actual_pay_fee_bar)
    TextView tvActualPayFeeBar;
    @Bind(R.id.tv_discount_fee_bar)
    TextView tvDiscountFeeBar;
    @Bind(R.id.iv_select_address)
    ImageView ivSelectAddress;
    @Bind(R.id.ll_remark)
    LinearLayout llRemark;
    @Bind(R.id.tv_dispatching_time)
    TextView tvDispatchTime;
    private int addressId;
    private String orderId;
    private String payCode = AppConfig.PayType.money;
    private HashMap<String, Object> params = new HashMap<>();
    private IWXAPI msgApi;
    private PayReq request;
    private boolean isPhysical;
    private List<GoodsInfo2> lstGoods = new ArrayList<>();
    private List<PaymentInfo> lstPayType = new ArrayList<>();
    private SubmitOrderGoodsAdapter orderAdapter;
    private Dialog dialog;
    private boolean isBook;
    private MyHttp myHttp;
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
    private PayWayDialog mPayDialog;
    private DispatchingTimeDialog timeDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_submit_order;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initGoodsRv();
    }

    private void initGoodsRv() {
        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new SubmitOrderGoodsAdapter(lstGoods, mContext);
        rvGoods.setAdapter(orderAdapter);
    }

    @Override
    public void fillData() {
        isBook = getIntent().getBooleanExtra("isBook", false);
        orderId = getIntent().getStringExtra("orderId");
        isPhysical = getIntent().getBooleanExtra("isPhysical", false);
        request = new PayReq();
        msgApi = WXAPIFactory.createWXAPI(mActivity, AppConfig.WX_APP_ID);
        myHttp = new MyHttp();
        getPayInfo();
    }

    /**
     * 获得支付信息
     */
    private void getPayInfo() {
        LoadingDialog.showDialogForLoading(mActivity, "加载中...", true);
        myHttp.doPost(isPhysical ? Api.getDefault().getPayInfo(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), orderId)
                        : Api.getDefault().getShopPayInfo(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), orderId)
                , new HttpListener() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        JSONObject data = result.getJSONObject("data");
                        updateUI(data);
                    }

                    @Override
                    public void onError(int code) {

                    }
                });
    }

    private void updateUI(JSONObject data) {
        JSONObject reserveTable = data.getJSONObject("reserve_table");
        AddressInfo address = data.getObject("address", AddressInfo.class);
        if (isBook) {
            tvAddress.setText((TextUtils.isEmpty(reserveTable.getString("note")) ? "" : "备注:" + reserveTable.getString("note") + "     ") + "预定人数:" + reserveTable.getString("number"));
            tvReceiveInfo.setText(reserveTable.getString("name") + "  " + reserveTable.getString("mobile"));
            ivSelectAddress.setVisibility(View.GONE);
            llRemark.setVisibility(View.GONE);
        } else {
            if (null != address) {
                llSelectedAddress.setVisibility(View.VISIBLE);
                llNoSelectAddress.setVisibility(View.GONE);
                addressId = address.getId();
                tvAddress.setText(address.getArea_str() + address.getInfo());
                tvReceiveInfo.setText(address.getXm() + "  " + address.getTel());
            } else {
                llNoSelectAddress.setVisibility(View.VISIBLE);
                llSelectedAddress.setVisibility(View.GONE);
            }
        }
        lstPayType.clear();
        lstPayType.addAll(JSONArray.parseArray(data.getString("payment"), PaymentInfo.class));
        lstPayType.get(0).setChecked(true);
        tvPayType.setText(lstPayType.get(0).getName());

        lstGoods.clear();
        lstGoods.addAll(JSONArray.parseArray(data.getString("order_goods"), GoodsInfo2.class));
        orderAdapter.notifyDataSetChanged();

        OrderInfo order = data.getObject("order", OrderInfo.class);
        tvShopName.setText(order.getShop_name());
        double sendFee = order.getLogistics() == 0 ? order.getExpress_price() / 100.00 : order.getLogistics() / 100.00;
        if (sendFee == 0) {
            ((View) tvSendFee.getParent().getParent()).setVisibility(View.GONE);
        } else {
            ((View) tvSendFee.getParent().getParent()).setVisibility(View.VISIBLE);
            tvSendFee.setText(String.format("¥ %.2f", sendFee));
        }
        if (order.getCasing_price() == 0) {
            ((View) tvPackingFee.getParent().getParent()).setVisibility(View.GONE);
        } else {
            ((View) tvPackingFee.getParent().getParent()).setVisibility(View.VISIBLE);
            tvPackingFee.setText(String.format("¥ %.2f", order.getCasing_price() / 100.00));
        }
        if (order.getCoupon_money() == 0) {
            ((View) tvCoupon.getParent().getParent()).setVisibility(View.GONE);
        } else {
            ((View) tvCoupon.getParent().getParent()).setVisibility(View.VISIBLE);
            tvCoupon.setText(String.format("-¥%.2f", order.getCoupon_money() / 100.00));
        }
        if (order.getUser_subsidy_money() == 0) {
            ((View) tvBonus.getParent().getParent()).setVisibility(View.GONE);
        } else {
            ((View) tvBonus.getParent().getParent()).setVisibility(View.VISIBLE);
            tvBonus.setText(String.format("-¥%.2f", order.getUser_subsidy_money() / 100.00));
        }
        if (order.getFull_money_reduce() == 0) {
            ((View) tvFullReduceFee.getParent().getParent()).setVisibility(View.GONE);
        } else {
            ((View) tvFullReduceFee.getParent().getParent()).setVisibility(View.VISIBLE);
            tvFullReduceFee.setText(String.format("-¥%.2f", order.getFull_money_reduce() / 100.00));
        }
        if (order.getRed_packet_money() == 0) {
            ((View) tvRedPacket.getParent().getParent()).setVisibility(View.GONE);
        } else {
            ((View) tvRedPacket.getParent().getParent()).setVisibility(View.VISIBLE);
            tvRedPacket.setText(String.format("-¥%.2f", order.getRed_packet_money() / 100.00));
        }
        tvDiscountFee.setText(String.format("-¥%.2f", order.getDiscounts_all_money() / 100.00));
        tvDiscountFeeBar.setText(String.format("已优惠%.2f元", order.getDiscounts_all_money() / 100.00));
        tvActualPayFee.setText(String.format("%.2f", order.getTotal_price() / 100.00));
        tvActualPayFeeBar.setText(String.format("共¥%.2f", order.getTotal_price() / 100.00));
    }

    @Override
    public void setListener() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_SELECT_ADDRESS:
                AddressInfo selectAddress = (AddressInfo) data.getSerializableExtra("selectAddress");
                updateAddress(selectAddress);
                break;
            case REQUEST_ADD_REMARK:
                String remark = data.getStringExtra("remark");
                tvRemark.setText(remark);
                break;
        }
    }

    private void updateAddress(final AddressInfo selectAddress) {
        llNoSelectAddress.setVisibility(View.GONE);
        llSelectedAddress.setVisibility(View.VISIBLE);
        myHttp.doPost(isPhysical ? Api.getDefault().updateVegetableOrderAddress(LoginUtil.getLoginToken(), orderId, selectAddress.getId()) : Api.getDefault().updateOrderAddress(LoginUtil.getLoginToken(), orderId, selectAddress.getId()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                addressId = selectAddress.getId();
                tvReceiveInfo.setText(selectAddress.getXm() + "  " + selectAddress.getTel());
                tvAddress.setText(selectAddress.getArea_str() + selectAddress.getInfo());
                getPayInfo();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    /**
     * 开始支付
     */
    private void startPay() {
        myHttp.doPost((isPhysical ? Api.getDefault().marketPayOrder(params) : Api.getDefault().shopPayOrder(params)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                switch (payCode) {
                    case AppConfig.PayType.alipay: {
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
                        break;
                    }
                    case AppConfig.PayType.wxpay: {
                        WxPayInfo data = result.getObject("data", WxPayInfo.class);
                        startWxPay(data);
                        break;
                    }
                    case AppConfig.PayType.money:
                        //余额支付
                        paySuccess();
                        break;
                }

            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void paySuccess() {
        showShortToast("下单成功！");
        myHttp.doPost(Api.getDefault().getRedPacket(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), orderId, isPhysical ? 2 : 1), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                guaJiang(result.getJSONObject("data").getIntValue("money"));
            }

            @Override
            public void onError(int code) {
                if (code == AppConfig.ERROR_STATE.NO_30 || code == AppConfig.ERROR_STATE.CLOSE_REWARD) {
                    nextStep();
                }
            }
        });
    }

    private void nextStep() {
        for (Activity activity : ActivityManager.getAllActivity()) {
            if (activity instanceof StoreDetailActivity) {
                activity.finish();
            }
        }
        if (isBook) {
            startActivity(new Intent(mActivity, BookSeatOrderActivity.class));
        } else {
            OrderDetailActivity.actionStart(mActivity, orderId, isPhysical);
        }
        finish();
    }

    private void guaJiang(int money) {
        //直接领奖
        myHttp.doPost(Api.getDefault().setRedPacket(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), orderId, isPhysical ? 2 : 1), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                Toast.makeText(mContext, result.getString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code) {

            }
        });
        final GuaKaDialogFragment guaKaDialogFragment = GuaKaDialogFragment.newInstance("¥" + String.format("%.2f", (float) money / 100));
        guaKaDialogFragment.setCloseListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guaKaDialogFragment.getDialog().dismiss();
                nextStep();
            }
        });
        guaKaDialogFragment.show(getSupportFragmentManager(), "");
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

    @OnClick({R.id.rl_address, R.id.ll_pay_type, R.id.tv_pay_now, R.id.ll_remark, R.id.ll_dispatching_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_address:
                selectAddress();
                break;
            case R.id.ll_pay_type:
                if (mPayDialog == null) {
                    mPayDialog = new PayWayDialog(this, lstPayType, this);
                }
                mPayDialog.show();
                break;
            case R.id.tv_pay_now:
                payNow();
                break;
            case R.id.ll_remark:
                addRemark();
                break;
            case R.id.ll_dispatching_time:
                showTimeDialog();
                break;
        }
    }

    private void addRemark() {
        OrderRemarkActivity.actionStart(this, isPhysical, orderId, tvRemark.getText().toString());
    }

    private void selectAddress() {
        if (!isBook) {
            Intent intent = new Intent(mActivity, AddressManagerActivity.class);
            intent.putExtra("orderSelect", true);
            startActivityForResult(intent, REQUEST_SELECT_ADDRESS);
        }
    }

    private void payNow() {
        if (!isBook && addressId == 0) {
            ToastUitl.showShort("请选择收货地址");
            return;
        }
        params.clear();
        params.put("login_token", SPUtils.getSharedStringData(mActivity, AppConfig.LOGIN_TOKEN));
        params.put("order_id", orderId);
        params.put("code", payCode);
        params.put("addr_id", addressId);
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
                        LoadingDialog.showDialogForLoading(mActivity, "支付中...", false);
                        params.put("pay_password", md5PayPsw);
                        startPay();
                    }
                }
            });
        } else {
            LoadingDialog.showDialogForLoading(mActivity, "支付中...", false);
            startPay();
        }
    }

    @Override
    public void ListClick(View v, PaymentInfo info) {
        payCode = info.getCode();
        tvPayType.setText(info.getName());
    }

    private void showTimeDialog() {
        if (timeDialog == null) {
            timeDialog = new DispatchingTimeDialog(this, isPhysical, orderId, new DispatchingTimeDialog.OnSubmitListener() {
                @Override
                public void onSubmit(String time, String str) {
                    tvDispatchTime.setText(str);
                    timeDialog.dismiss();
                }
            });
        }
        timeDialog.show();
    }
}
