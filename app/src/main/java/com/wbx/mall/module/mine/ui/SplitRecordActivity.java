package com.wbx.mall.module.mine.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.SplitRecordBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.module.mine.adapter.SplitRecordAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 分包记录
 */
public class SplitRecordActivity extends BaseActivity {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private SplitRecordAdapter adapter;
    private List<SplitRecordBean.SplitBean> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_split;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new SplitRecordAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void fillData() {
        new MyHttp().doPost(Api.getDefault().getSubPackageList(LoginUtil.getLoginToken()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                SplitRecordBean bean = result.getObject("data", SplitRecordBean.class);
                list.addAll(bean.getData());
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
}
