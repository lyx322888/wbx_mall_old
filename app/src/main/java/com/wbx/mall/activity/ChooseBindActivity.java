package com.wbx.mall.activity;

import android.content.Intent;
import android.view.View;

import com.wbx.mall.R;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;

import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/11/14.
 * 选择绑定账户
 */

public class ChooseBindActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_bind;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }
    @OnClick({R.id.ali_layout, R.id.weixin_layout, R.id.net_layout})
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.ali_layout:
                intent = new Intent(mContext,BindPayPlatformActivity.class);
                intent.putExtra("payMode", AppConfig.PayType.alipay);
                startActivity(intent);
                break;
            case R.id.weixin_layout:
                intent = new Intent(mContext,BindPayPlatformActivity.class);
                intent.putExtra("payMode", AppConfig.PayType.wxpay);
                startActivity(intent);
                break;
            case R.id.net_layout:
                intent = new Intent(mContext,BindUnionpayActivity.class);
                startActivity(intent);
                break;
        }
    }
}
