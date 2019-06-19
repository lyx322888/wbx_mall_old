package com.wbx.mall.fragment;


import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseFragment;

import butterknife.Bind;

public class IndexOrderFragment extends BaseFragment {
    private String[] title = new String[]{"全部订单", "待评价", "待退款"};
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    public IndexOrderFragment() {
    }

    public static IndexOrderFragment newInstance() {
        IndexOrderFragment fragment = new IndexOrderFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_index_order;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        tabLayout.setTabTextColors(getResources().getColor(R.color.gray_normal), getResources().getColor(R.color.app_color));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.app_color));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        OrderPageAdapter adapter = new OrderPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void fillData() {

    }

    @Override
    protected void bindEvent() {

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
}
