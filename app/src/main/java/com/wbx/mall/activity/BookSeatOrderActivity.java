package com.wbx.mall.activity;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.fragment.BookSeatOrderFragment;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/12/22.
 * 我的预定订单
 */

public class BookSeatOrderActivity extends BaseActivity {
    @Bind(R.id.order_lab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.order_view_pager)
    ViewPager mOrderViewPager;
    private String[] mTitles = new String[] { "待付款","待接单","待用餐","待退款","已完成" };
    private MyPagerAdapter myViewPager;
    @Override
    public int getLayoutId() {
        return R.layout.activity_book_seat_order;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        myViewPager = new MyPagerAdapter(getSupportFragmentManager());
        mOrderViewPager.setAdapter(myViewPager);
        mTabLayout.setupWithViewPager(mOrderViewPager);
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }
    class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return BookSeatOrderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
