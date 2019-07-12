package com.wbx.mall;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.utils.UserDao;
import com.wbx.mall.activity.LoginActivity;
import com.wbx.mall.activity.ShoppingCartActivity;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.common.ActivityManager;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.dialog.UpdateDialogFragment;
import com.wbx.mall.module.find.fragment.FindFragment;
import com.wbx.mall.fragment.FreeFragment;
import com.wbx.mall.fragment.IndexFragment02;
import com.wbx.mall.fragment.IndexOrderFragment;
import com.wbx.mall.module.mine.fragment.MineFragment;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.TabFragmentHost;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements EMMessageListener, EMConnectionListener {
    @Bind(android.R.id.tabhost)
    TabFragmentHost mTabHost;
    TabWidget mTabWidget;
    private long mExitTime;
    public static boolean isGoBuy = false;
    private MyHttp myHttp;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {
        myHttp = new MyHttp();
        EMClient.getInstance().chatManager().addMessageListener(this);
        EMClient.getInstance().addConnectionListener(this);
    }

    @Override
    public void initView() {
        initTabHost();
        initFragment();
    }

    private void initFragment() {
        initIndexFragment();//首页
        initDiscoveryFragment();//发现
        initNearByFragment();//附近
        initOrderFragment();//订单
        initMineFragment();//我的
    }

    private void initMineFragment() {
        View mainTabView = getTabItemView(R.drawable.yst_mine_tab_btn_selector, "我的");
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec("4").setIndicator(mainTabView);
        mTabHost.addTab(tabSpec, MineFragment.class, null);
    }

    private View getTabItemView(int tabImageId, String tabStr) {
        View view = getLayoutInflater().inflate(
                R.layout.yst_main_tab_item_view, null);
        ImageView imageView = view.findViewById(R.id.tabItemIV);
        TextView textView = view.findViewById(R.id.tab_item_tv);
        textView.setText(tabStr);
        imageView.setImageResource(tabImageId);
        return view;
    }

    private void initOrderFragment() {
        View mainTabView = getTabItemView(R.drawable.index_order_selector, "订单");
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec("3").setIndicator(mainTabView);
        mTabHost.addTab(tabSpec, IndexOrderFragment.class, null);
    }

    private void initNearByFragment() {
        View mainTabView = getTabItemView(R.drawable.yst_nearby_center_selector, "");
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec("2").setIndicator(mainTabView);
//        mTabHost.addTab(tabSpec, BuyFragment.class, null);
        mTabHost.addTab(tabSpec, FreeFragment.class, null);
    }

    private void initDiscoveryFragment() {
        View mainTabView = getTabItemView(R.drawable.yst_buy_tab_btn_selector, "发现");
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec("1").setIndicator(mainTabView);
        mTabHost.addTab(tabSpec, FindFragment.class, null);
    }

    private void initIndexFragment() {
        View mainTabView = getTabItemView(R.drawable.yst_index_tab_btn_selector, "首页");
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec("0").setIndicator(mainTabView);
        mTabHost.addTab(tabSpec, IndexFragment02.class, null);
    }

    private void initTabHost() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabWidget = mTabHost.getTabWidget();
        mTabWidget.setDividerDrawable(null);
        mTabWidget.setStripEnabled(false);
    }

    @Override
    public void fillData() {
        myHttp.doPost(Api.getDefault().getVersion(), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                JSONObject data = result.getJSONObject("data");
                int serviceVersion = Integer.parseInt(data.getString("version").replace(".", ""));
                String version = BaseApplication.getInstance().getVersion();
                int appVersion = Integer.parseInt(version.replace(".", ""));
                if (appVersion < serviceVersion) {
                    upDateApp(JSONObject.toJSONString(result));
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void upDateApp(String result) {
        UpdateDialogFragment.newInstent(result).show(getSupportFragmentManager(), "");
    }

    @Override
    public void setListener() {
        mTabWidget.getChildTabViewAt(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SPUtils.getSharedBooleanData(mActivity, AppConfig.LOGIN_STATE)) {
                    Intent intent = new Intent(mActivity, LoginActivity.class);
                    intent.putExtra("isMainTo", true);
                    startActivity(intent);
                } else {
                    mTabHost.setCurrentTab(4);
                }

            }
        });
        mTabWidget.getChildTabViewAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA)) {
                    mTabHost.setCurrentTab(1);
                }
            }
        });
        mTabWidget.getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA)) {
                    mTabHost.setCurrentTab(2);
                }
            }
        });
        mTabWidget.getChildTabViewAt(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA)) {
                    mTabHost.setCurrentTab(3);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                showShortToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for (EMMessage emMessage : list) {
            Map<String, Object> ext = emMessage.ext();
            EaseUser easeUser = new EaseUser(emMessage.getFrom());
            easeUser.setAvatar((String) ext.get("fromAvatar"));
            easeUser.setNick((String) ext.get("fromNickname"));
            easeUser.setNickname((String) ext.get("fromNickname"));
            UserDao userDao = new UserDao(mContext);
            userDao.openDataBase();
            userDao.insertData(easeUser);
            userDao.closeDataBase();
        }

        EaseUI.getInstance().getNotifier().onNewMesg(list);
        //收到消息
        Intent intent = new Intent();
        intent.setAction(AppConfig.REFRESH_UI);
        //发送广播
        sendBroadcast(intent);
        EaseAtMessageHelper.get().parseMessages(list);

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected(final int error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (error == EMError.USER_REMOVED) {
                    // 显示帐号已经被移除
//                      showAccountRemovedDialog();
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    //账号在其它设备登录
                    showShortToast("该账号已经在其它设备登录！");
                    ActivityManager.finishAllActivity();
                    SPUtils.setSharedBooleanData(mContext, AppConfig.LOGIN_STATE, false);
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGoBuy) {
            mTabHost.setCurrentTab(1);
        }
        isGoBuy = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(this);
        EMClient.getInstance().removeConnectionListener(this);
    }

    @OnClick(R.id.fl_shopping_cart)
    public void onViewClicked() {
        if (LoginUtil.isLogin()) {
            ShoppingCartActivity.actionStart(this);
        } else {
            LoginUtil.login();
        }
    }
}
