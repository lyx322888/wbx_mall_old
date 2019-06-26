package com.wbx.mall.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_merchant_data;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        MerchantPresenterImp presenterImp = new MerchantPresenterImp(this);
        presenterImp.getMerchant(LoginUtil.getLoginToken());
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
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
    }

    public void onBack(View view) {
        finish();
    }
}
