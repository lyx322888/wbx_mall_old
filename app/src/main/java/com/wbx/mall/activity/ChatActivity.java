package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.utils.UserDao;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.wbx.mall.R;
import com.wbx.mall.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/8/3.
 */

public class ChatActivity extends BaseActivity {
    @Bind(R.id.chat_user_name_tv)
    TextView toChatUserNameTv;

    public static void actionStart(Context context, String userId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, userId);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, String userId, String shopName, String shopAvatar) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, userId);
        intent.putExtra("shopName", shopName);
        intent.putExtra("shopAvatar", shopAvatar);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        String userId = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
        String shopName = getIntent().getStringExtra("shopName");

        if (!TextUtils.isEmpty(shopName)) {
            EaseUser easeUser = new EaseUser(userId);
            easeUser.setNickname(shopName);
            easeUser.setAvatar(getIntent().getStringExtra("shopAvatar"));
            UserDao userDao = new UserDao(mContext);
            userDao.openDataBase();
            userDao.insertData(easeUser);
        }

        EaseUser user = EaseUserUtils.getUserInfo(userId);
        if (!TextUtils.isEmpty(shopName)) {
            toChatUserNameTv.setText(shopName);
        } else if (null != user) {
            toChatUserNameTv.setText(user.getNickname());
        }
        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.setChatFragmentHelper(new EaseChatFragment.EaseChatFragmentHelper() {
            @Override
            public void onSetMessageAttributes(EMMessage message) {
                message.setAttribute("fromNickname", userInfo.getNickname());
                message.setAttribute("fromAvatar", userInfo.getFace());
            }

            @Override
            public void onEnterToChatDetails() {

            }

            @Override
            public void onAvatarClick(String username) {

            }

            @Override
            public void onAvatarLongClick(String username) {

            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {

            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                return null;
            }
        });
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, userId);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, chatFragment).commit();
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }
}
