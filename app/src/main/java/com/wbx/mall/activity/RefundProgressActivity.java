package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.RefundProgressAdapter;
import com.wbx.mall.adapter.RefundProgressGoodsAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.RefundProgressBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.DateUtil;
import com.wbx.mall.utils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 退款进度
 */
public class RefundProgressActivity extends BaseActivity {
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_refund_money)
    TextView tvRefundMoney;
    @Bind(R.id.tv_refund_account)
    TextView tvRefundAccount;
    @Bind(R.id.recycler_view_progress)
    RecyclerView rvProgress;
    @Bind(R.id.recycler_view_goods)
    RecyclerView rvGoods;
    @Bind(R.id.tv_apply_num)
    TextView tvApplyNum;
    @Bind(R.id.tv_apply_money)
    TextView tvApplyMoney;
    @Bind(R.id.tv_apply_time)
    TextView tvApplyTime;
    private List<RefundProgressBean.OrderTrackBean> lstTrack = new ArrayList<>();
    private List<RefundProgressBean.GoodsBean> lstGoods = new ArrayList<>();
    private RefundProgressAdapter refundProgressAdapter;
    private RefundProgressGoodsAdapter goodsAdapter;
    private RefundProgressBean data;

    public static void actionStart(Context context, String orderId, int orderType) {
        Intent intent = new Intent(context, RefundProgressActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("orderType", orderType);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_refund_progress;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        rvProgress.setLayoutManager(new LinearLayoutManager(this));
        refundProgressAdapter = new RefundProgressAdapter(this, lstTrack);
        rvProgress.setAdapter(refundProgressAdapter);
        rvProgress.setNestedScrollingEnabled(false);
        rvProgress.setFocusableInTouchMode(false);

        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        goodsAdapter = new RefundProgressGoodsAdapter(this, lstGoods);
        rvGoods.setAdapter(goodsAdapter);
        rvGoods.setNestedScrollingEnabled(false);
        rvGoods.setFocusableInTouchMode(false);
    }

    @Override
    public void fillData() {
        new MyHttp().doPost(Api.getDefault().getRefundDetail(LoginUtil.getLoginToken(), getIntent().getStringExtra("orderId"), getIntent().getIntExtra("orderType", 1)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                data = result.getObject("data", RefundProgressBean.class);
                updateUI();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void updateUI() {
        tvState.setText(data.getStatus_message());
        tvTime.setText(DateUtil.formatDateAndTime3(data.getStatus_time()));
        tvRefundMoney.setText(String.format("%.2f元", data.getNeed_pay() / 100.00));
        tvRefundAccount.setText(data.getPay_type());
        lstTrack.addAll(data.getOrder_track());
        refundProgressAdapter.notifyDataSetChanged();
        lstGoods.addAll(data.getGoods());
        goodsAdapter.notifyDataSetChanged();
        tvApplyNum.setText(String.valueOf(data.getNum()));
        tvApplyMoney.setText(String.format("¥%.2f", data.getNeed_pay() / 100.00));
        tvApplyTime.setText(DateUtil.formatDateAndTime3(data.getApply_time()));
    }

    @Override
    public void setListener() {

    }

    @OnClick(R.id.tv_merchant_phone)
    public void onViewClicked() {
        if (data == null || TextUtils.isEmpty(data.getTel())) {
            ToastUitl.showShort("暂未获取到该商家电话");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.getTel()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
