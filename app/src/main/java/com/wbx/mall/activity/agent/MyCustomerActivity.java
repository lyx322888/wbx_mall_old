package com.wbx.mall.activity.agent;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.MyCustomerAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.CustomerInfo;
import com.wbx.mall.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/10/23.
 * 星伙推荐 我的客户列表
 */

public class MyCustomerActivity extends BaseActivity {
    @Bind(R.id.customer_recycler_view)
    RecyclerView mRecyclerView;
    private List<CustomerInfo> customerInfoList = new ArrayList<>();
    private MyCustomerAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_cutomer;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MyCustomerAdapter(customerInfoList, mContext);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void fillData() {
        new MyHttp().doPost(Api.getDefault().myCustomer(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                customerInfoList.addAll(JSONArray.parseArray(result.getString("data"), CustomerInfo.class));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @Override
    public void setListener() {

    }
}
