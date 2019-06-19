package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.IntergalExchangeOrderDetailBean;
import com.wbx.mall.utils.DateUtil;
import com.wbx.mall.utils.GlideUtils;

import butterknife.Bind;

public class IntegralExchangeOrderDetailActivity extends BaseActivity {
    @Bind(R.id.receiver_name_tv)
    TextView receiverNameTv;
    @Bind(R.id.receiver_phone_tv)
    TextView receiverPhoneTv;
    @Bind(R.id.receiver_address_tv)
    TextView receiverAddressTv;
    @Bind(R.id.iv_goods)
    ImageView ivGoods;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_integral)
    TextView tvIntegral;
    @Bind(R.id.tv_total_integral)
    TextView tvTotalIntegral;
    @Bind(R.id.tv_send_type)
    TextView tvSendType;
    @Bind(R.id.tv_send_id)
    TextView tvSendId;
    @Bind(R.id.tv_order_id)
    TextView tvOrderId;
    @Bind(R.id.tv_order_time)
    TextView tvOrderTime;

    public static void actionStart(Context context, IntergalExchangeOrderDetailBean bean) {
        Intent intent = new Intent(context, IntegralExchangeOrderDetailActivity.class);
        intent.putExtra("data", bean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_integral_exchange_order_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
    }

    @Override
    public void fillData() {
        IntergalExchangeOrderDetailBean data = (IntergalExchangeOrderDetailBean) getIntent().getSerializableExtra("data");
        receiverNameTv.setText(data.getName());
        receiverPhoneTv.setText(data.getPhone());
        receiverAddressTv.setText(data.getAddress());
        GlideUtils.showMediumPic(this, ivGoods, data.getFace_pic());
        tvName.setText(data.getTitle());
        tvNum.setText("数量：" + data.getNum());
        tvIntegral.setText(data.getNeed_integral() / data.getNum() + "积分");
        tvTotalIntegral.setText(data.getNeed_integral() + "积分");
        tvSendType.setText("配送方式：" + data.getExpress_company());
        tvSendId.setText("快递单号：" + data.getExpress_num());
        tvOrderId.setText("订单编号：" + data.getExchange_id());
        tvOrderTime.setText("下单时间：" + DateUtil.formatDateAndTime(data.getCreate_time()));
    }

    @Override
    public void setListener() {

    }
}
