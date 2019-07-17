package com.wbx.mall.module.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.NoticeAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.NoticeBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.widget.LoadingDialog;

import butterknife.Bind;

public class MessageCenterActivity extends BaseActivity {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private NoticeAdapter adapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MessageCenterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoticeAdapter(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void fillData() {
        LoadingDialog.showDialogForLoading(this);
        new MyHttp().doPost(Api.getDefault().getSystemMessageList(LoginUtil.getLoginToken()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                NoticeBean noticeBean = JSONObject.parseObject(result.toJSONString(), NoticeBean.class);
                adapter.update(noticeBean.getData());
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
