package com.wbx.mall.module.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.fragment.ChatFrament;
import com.wbx.mall.fragment.NoticeFragment;

import butterknife.Bind;
import butterknife.OnClick;

public class MessageCenterActivity extends BaseActivity {
    @Bind(R.id.tv_message)
    TextView tvMessage;
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    @Bind(R.id.tv_unread_message)
    TextView tvUnreadMessage;
    @Bind(R.id.tv_unread_notice)
    TextView tvUnreadNotice;
    private ChatFrament chatFragment;
    private NoticeFragment noticeFragment;
    private Fragment currentFragment;
    private FragmentManager supportFragmentManager;

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
        tvMessage.setSelected(true);
        chatFragment = ChatFrament.newInstance();
        noticeFragment = NoticeFragment.newInstance();
        supportFragmentManager = getSupportFragmentManager();
        changeFragment(chatFragment);
    }

    public void updateNoticeUnread(int num) {
        if (num > 0) {
            tvUnreadNotice.setVisibility(View.VISIBLE);
            tvUnreadNotice.setText(String.valueOf(num));
        } else {
            tvUnreadNotice.setVisibility(View.GONE);
        }
    }

    public void updateMessageUnread(int num) {
        if (num > 0) {
            tvUnreadMessage.setVisibility(View.VISIBLE);
            tvUnreadMessage.setText(String.valueOf(num));
        } else {
            tvUnreadMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.rl_message, R.id.rl_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_message:
                tvMessage.setSelected(true);
                tvNotice.setSelected(false);
                changeFragment(chatFragment);
                break;
            case R.id.rl_notice:
                tvMessage.setSelected(false);
                tvNotice.setSelected(true);
                changeFragment(noticeFragment);
                updateNoticeUnread(0);
                break;
        }
    }

    private void changeFragment(Fragment fragment) {
        if (fragment == currentFragment) {
            return;
        }
        if (currentFragment == null) {//第一次添加
            supportFragmentManager.beginTransaction().add(R.id.rl_container, fragment).commit();
        } else {
            if (fragment.isAdded()) {
                supportFragmentManager.beginTransaction().hide(currentFragment).show(fragment).commit();
            } else {
                supportFragmentManager.beginTransaction().hide(currentFragment).add(R.id.rl_container, fragment).commit();
            }
        }
        currentFragment = fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUnreadNum();
    }

    private void getUnreadNum() {
        new MyHttp().doPost(Api.getDefault().getUnreadSystemMessageNum(LoginUtil.getLoginToken()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                int messageUnreadCount = 0;
                if (result.getJSONObject("data") != null && result.getJSONObject("data").getInteger("count") > 0) {
                    messageUnreadCount = result.getJSONObject("data").getInteger("count");
                }
                updateNoticeUnread(messageUnreadCount);
            }

            @Override
            public void onError(int code) {

            }
        });
    }
}
