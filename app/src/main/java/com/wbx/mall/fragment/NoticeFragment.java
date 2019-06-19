package com.wbx.mall.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.NoticeAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.NoticeBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.widget.LoadingDialog;

import butterknife.Bind;

public class NoticeFragment extends BaseFragment {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private NoticeAdapter adapter;

    public NoticeFragment() {
    }

    public static NoticeFragment newInstance() {
        NoticeFragment fragment = new NoticeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_notice;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new NoticeAdapter(getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void fillData() {
        LoadingDialog.showDialogForLoading(getActivity());
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
    protected void bindEvent() {

    }
}
