package com.wbx.mall.module.mine.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.MerchantDataAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.ListShopDataBean;
import com.wbx.mall.common.LoginUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 商家销量排行榜
 */
public class MerchantDataActivity extends BaseActivity {
    @Bind(R.id.recycler_merchant)
    RecyclerView mRecycler;
    private List<ListShopDataBean.ShopsBean> list = new ArrayList<>();
    private MerchantDataAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_merchant_data;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);
        adapter = new MerchantDataAdapter(MerchantDataActivity.this, list);
        mRecycler.setAdapter(adapter);
    }

    @Override
    public void fillData() {
        new MyHttp().doPost(Api.getDefault().getMerchant(LoginUtil.getLoginToken()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                ListShopDataBean indexCountBean = result.getObject("data", ListShopDataBean.class);
                list.addAll(indexCountBean.getShops());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    @Override
    public void setListener() {

    }

    public void onBack(View view) {
        finish();
    }
}
