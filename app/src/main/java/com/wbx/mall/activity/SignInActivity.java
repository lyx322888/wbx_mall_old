package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.SignInAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.bean.IntegrateBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 签到页面
 *
 * @author Zero
 * @date 2018/5/18
 */
public class SignInActivity extends BaseActivity implements BaseRefreshListener {

    @Bind(R.id.tv_score)
    TextView tvScore;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.ptrl)
    PullToRefreshLayout ptrl;
    @Bind(R.id.tv_sign_in)
    TextView tvSignIn;
    @Bind(R.id.tv_this_time_score)
    TextView tvThisTimeScore;
    @Bind(R.id.rl_sign_in_success)
    RelativeLayout rlSignInSuccess;
    private int currentPage = 1;
    private SignInAdapter adapter;
    private boolean canLoadMore = true;
    private List<IntegrateBean> lstData = new ArrayList<>();
    private MyHttp myHttp;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏，就是下面三个虚拟按钮
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    public void initPresenter() {
        myHttp = new MyHttp();
    }

    @Override
    public void initView() {
        ptrl.setRefreshListener(this);
        ptrl.setCanRefresh(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SignInAdapter(this);
        recyclerView.setAdapter(adapter);
        tvScore.setText(String.valueOf(userInfo.getIntegral()));
        tvSignIn.setText(userInfo.getIs_sign() == 1 ? "已签到" : "去签到");
        tvSignIn.setEnabled(userInfo.getIs_sign() != 1);
    }

    @Override
    public void fillData() {
        myHttp.doPost(Api.getDefault().getIntegralLogs(LoginUtil.getLoginToken(), currentPage, AppConfig.pageSize), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                List<IntegrateBean> data = JSONObject.parseArray(result.getString("data"), IntegrateBean.class);
                if (currentPage == 1) {
                    lstData.clear();
                }
                if (data.size() < AppConfig.pageSize) {
                    canLoadMore = false;
                }
                lstData.addAll(data);
                adapter.update(lstData);
                ptrl.finishLoadMore();
                ptrl.finishRefresh();
            }

            @Override
            public void onError(int code) {
                ptrl.finishLoadMore();
                ptrl.finishRefresh();
            }
        });
    }

    @Override
    public void setListener() {

    }

    @OnClick(R.id.tv_sign_in)
    public void onViewClicked() {
        signIn();
    }

    private void signIn() {
        myHttp.doPost(Api.getDefault().signIn(LoginUtil.getLoginToken()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                signSuccess(result);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void signSuccess(JSONObject result) {
        tvSignIn.setEnabled(false);
        String score = result.getString("msg");
        tvThisTimeScore.setText(score + "积分");
        int totalIntegral = userInfo.getIntegral() + Integer.valueOf(score);
        tvScore.setText(String.valueOf(totalIntegral));
        userInfo.setIntegral(totalIntegral);
        userInfo.setIs_sign(1);
        BaseApplication.getInstance().saveObject(userInfo, AppConfig.USER_DATA);
        currentPage = 1;
        canLoadMore = true;
        rlSignInSuccess.setVisibility(View.VISIBLE);
        tvSignIn.postDelayed(new Runnable() {
            @Override
            public void run() {
                rlSignInSuccess.setVisibility(View.GONE);
            }
        }, 2000);
        fillData();
    }

    @Override
    public void refresh() {
    }

    @Override
    public void loadMore() {
        currentPage++;
        if (!canLoadMore) {
            ptrl.finishLoadMore();
            ToastUitl.showShort("没有更多数据了");
            return;
        }
        fillData();
    }
}
