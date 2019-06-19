package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.AddressInfo;
import com.wbx.mall.bean.IntegralGoodsDetailBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.iosdialog.AlertDialog;

import butterknife.Bind;
import butterknife.OnClick;

public class SubmitIntegralExchangeActivity extends BaseActivity {
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
    @Bind(R.id.tv_need_pay_integral)
    TextView tvNeedPayIntegral;
    private AddressInfo addressInfo;
    private IntegralGoodsDetailBean integralGoodsDetailBean;
    private int num;
    private MyHttp myHttp;

    public static void actionStart(Context context, IntegralGoodsDetailBean integralGoodsDetailBean, int num) {
        Intent intent = new Intent(context, SubmitIntegralExchangeActivity.class);
        intent.putExtra("integralGoodsDetailBean", integralGoodsDetailBean);
        intent.putExtra("num", num);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_submit_integral_exchange;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
    }

    @Override
    public void fillData() {
        integralGoodsDetailBean = (IntegralGoodsDetailBean) getIntent().getSerializableExtra("integralGoodsDetailBean");
        num = getIntent().getIntExtra("num", 0);
        LoadingDialog.showDialogForLoading(this);
        myHttp = new MyHttp();
        myHttp.doPost(Api.getDefault().getDefaultAddress(LoginUtil.getLoginToken()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                addressInfo = result.getObject("data", AddressInfo.class);
                udpateUI();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void udpateUI() {
        if (null != addressInfo) {
            receiverNameTv.setText(addressInfo.getXm());
            receiverAddressTv.setText(addressInfo.getArea_str() + addressInfo.getInfo());
            receiverPhoneTv.setText(addressInfo.getTel());
        } else {
            new AlertDialog(mActivity).builder()
                    .setTitle("提示")
                    .setMsg("收货地址尚未添加!")
                    .setPositiveButton("前往添加", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mActivity, AddressManagerActivity.class);
                            intent.putExtra("orderSelect", true);
                            startActivityForResult(intent, 0);
                        }
                    }).show();
        }
        GlideUtils.showMediumPic(this, ivGoods, integralGoodsDetailBean.getFace_pic());
        tvName.setText(integralGoodsDetailBean.getTitle());
        tvNum.setText("数量：" + num);
        tvIntegral.setText(integralGoodsDetailBean.getIntegral() + "积分");
        tvNeedPayIntegral.setText("所需积分:" + integralGoodsDetailBean.getIntegral() * num);
    }

    @Override
    public void setListener() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data) {
            return;
        }
        addressInfo = (AddressInfo) data.getSerializableExtra("selectAddress");
        receiverNameTv.setText(addressInfo.getXm());
        receiverAddressTv.setText(addressInfo.getArea_str() + addressInfo.getInfo());
        receiverPhoneTv.setText(addressInfo.getTel());
    }

    @OnClick({R.id.btn_ensure_exchange, R.id.submit_order_select_address_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ensure_exchange:
                exchange();
                break;
            case R.id.submit_order_select_address_layout:
                Intent intent = new Intent(mActivity, AddressManagerActivity.class);
                intent.putExtra("orderSelect", true);
                startActivityForResult(intent, 0);
                break;
        }
    }

    private void exchange() {
        if (addressInfo == null) {
            ToastUitl.showShort("请选择收货地址！");
            return;
        }
        myHttp.doPost(Api.getDefault().integralExchangeGoods(LoginUtil.getLoginToken(), integralGoodsDetailBean.getGoods_id(), num, addressInfo.getId()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                ToastUitl.showShort(result.getString("msg"));
                finish();
            }

            @Override
            public void onError(int code) {

            }
        });
    }
}
