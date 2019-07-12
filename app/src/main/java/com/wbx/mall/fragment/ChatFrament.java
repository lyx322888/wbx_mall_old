package com.wbx.mall.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.wbx.mall.R;
import com.wbx.mall.activity.ChatActivity;
import com.wbx.mall.module.mine.ui.MessageCenterActivity;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseFragment;

public class ChatFrament extends BaseFragment {

    private EaseConversationListFragment easeChatFragment;
    private MyReceiver myReceiver;
    private MessageCenterActivity activity;

    public static ChatFrament newInstance() {
        ChatFrament fragment = new ChatFrament();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_chat_frament;
    }

    @Override
    public void initPresenter() {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter(AppConfig.REFRESH_UI);
        getActivity().registerReceiver(myReceiver, intentFilter);
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            easeChatFragment.refresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(myReceiver);
    }

    @Override
    protected void initView() {
        easeChatFragment = new EaseConversationListFragment();
        getChildFragmentManager().beginTransaction().add(R.id.fl_container, easeChatFragment).commit();
        easeChatFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                ChatActivity.actionStart(getActivity(), conversation.conversationId());
            }
        });
    }

    @Override
    protected void fillData() {
        activity = (MessageCenterActivity) getActivity();
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getUnreadNum();
    }

    private void getUnreadNum() {
        int unreadCount = 0;
        EMChatManager emChatManager = EMClient.getInstance().chatManager();
        if (null != emChatManager) {
            unreadCount = emChatManager.getUnreadMessageCount();
        }
        activity.updateMessageUnread(unreadCount);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getUnreadNum();
        }
    }
}
