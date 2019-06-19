package com.wbx.mall.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.adapter.SalesAdapter;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.SalesDetailBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.presenter.SalesPresenterImp;
import com.wbx.mall.utils.DateUtil;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.view.SalesView;

import butterknife.Bind;

public class SalesDetailActivity extends BaseActivity implements SalesView {
    @Bind(R.id.user_face)
    ImageView ivFace;
    @Bind(R.id.last_time)
    TextView tvTime;
    @Bind(R.id.user_name)
    TextView tvName;
    @Bind(R.id.user_agent)
    TextView tvAgent;
    @Bind(R.id.user_currency)
    TextView tvCurrency;
    @Bind(R.id.user_lifelong)
    TextView tvLifelong;
    @Bind(R.id.user_money)
    TextView tvMoney;
    @Bind(R.id.user_yesterday_sales_money)
    TextView tvSalesMoney;
    @Bind(R.id.user_yesterday_sales_num)
    TextView tvSalesNum;
    @Bind(R.id.user_shop_recycler)
    RecyclerView mRecycler;
    @Override
    public int getLayoutId() {
        return R.layout.activity_sales_detail;
    }

    @Override
    public void initPresenter() {
        SalesPresenterImp presenterImp=new SalesPresenterImp(this);
        presenterImp.getSales(LoginUtil.getLoginToken());

        mRecycler.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void getSales(SalesDetailBean data) {
        GlideUtils.showSmallPic(mContext,ivFace,data.getData().getSalesman().getFace());
        tvTime.setText("上次登录时间 "+DateUtil.formatDate3(data.getData().getSalesman().getLogin_time()));
        tvAgent.setText(DateUtil.settingphone(userInfo.getMobile()));
        tvName.setText(data.getData().getSalesman().getRank_name());
        tvCurrency.setText(data.getData().getSalesman().get_$480_software_num()+"");
        tvLifelong.setText(data.getData().getSalesman().get_$2080_software_num()+"");
        tvMoney.setText(data.getData().getSalesman().getOperation_money()/100+".00");
        tvSalesMoney.setText(data.getData().getSalesman().getYesterday_sales_money()+".00");
        tvSalesNum.setText(data.getData().getSalesman().getYesterday_sales_num()+"");


        SalesAdapter salesAdapter=new SalesAdapter(mContext,data.getData().getShops());
        mRecycler.setAdapter(salesAdapter);
    }
}
