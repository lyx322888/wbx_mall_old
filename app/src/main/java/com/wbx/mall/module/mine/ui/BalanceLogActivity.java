package com.wbx.mall.module.mine.ui;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.wbx.mall.R;
import com.wbx.mall.adapter.BalanceLogPagerAdapter;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.utils.DisplayUtil;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/11/20.
 * 余额明细
 */

public class BalanceLogActivity extends BaseActivity {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_balance_log;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.app_color));
        tabLayout.setTabTextColors(Color.parseColor("#2C2C2C"), Color.parseColor("#2C2C2C"));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setSelectedTabIndicatorHeight(DisplayUtil.dip2px(2));
        BalanceLogPagerAdapter adapter = new BalanceLogPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }
}
