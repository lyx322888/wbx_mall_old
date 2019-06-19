package com.wbx.mall.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.adapter.MerchantDataAdapter;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.ListShopDataBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.presenter.MerchantPresenterImp;
import com.wbx.mall.view.MerchantView;

import butterknife.Bind;

public class MerchantDataActivity extends BaseActivity implements MerchantView {

    @Bind(R.id.recycler_merchant)
    RecyclerView mRecycler;
    @Bind(R.id.text_all_turnover)
    TextView text_all_turnover;
    @Bind(R.id.text_all_order_num)
    TextView text_all_order_num;
    @Bind(R.id.text_all_order_people)
    TextView text_all_order_people;
    @Bind(R.id.iv_back)
    ImageView back;

    @Override
    public int getLayoutId() {
        return R.layout.activity_merchant_data;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Log.e("TAG",LoginUtil.getLoginToken());
        MerchantPresenterImp presenterImp = new MerchantPresenterImp(this);
        presenterImp.getMerchant(LoginUtil.getLoginToken());
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void getMerchant(ListShopDataBean listShopDataBean) {
        listShopDataBean.getData().getShops().size();
        MerchantDataAdapter adapter = new MerchantDataAdapter(this, listShopDataBean.getData().getShops());
        mRecycler.setAdapter(adapter);
        text_all_turnover.setText(listShopDataBean.getData().getAll_turnover()+".00");
        text_all_order_num.setText("交易共 " + listShopDataBean.getData().getAll_order_num() + " 笔");
        text_all_order_people.setText("顾客共 " + listShopDataBean.getData().getAll_order_people() + " 人");


    }
}
