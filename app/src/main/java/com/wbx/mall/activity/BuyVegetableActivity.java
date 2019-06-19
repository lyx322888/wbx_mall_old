package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.fragment.BuyFragment;

public class BuyVegetableActivity extends BaseActivity {
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, BuyVegetableActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_vegetable;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, BuyFragment.newInstance(true)).commit();
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }
}
