package com.wbx.mall.activity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.fragment.OrderFragment;

import butterknife.Bind;

public class IndexOrderActivity extends BaseActivity {

    private String[] title = new String[]{"全部订单", "待评价", "待退款"};
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_index_order;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tabLayout.setTabTextColors(getResources().getColor(R.color.gray_normal), getResources().getColor(R.color.app_color));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.app_color));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        OrderPageAdapter adapter = new OrderPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }

    private class OrderPageAdapter extends FragmentStatePagerAdapter {
        public OrderPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            return OrderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return title.length;
        }
    }

    public void onBack(View view) {
        finish();
    }
}
