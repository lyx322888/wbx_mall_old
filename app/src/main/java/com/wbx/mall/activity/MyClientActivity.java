package com.wbx.mall.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.fragment.ClientFragment;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/6/10.
 */

public class MyClientActivity extends BaseActivity {
    private String[] mTitles = new String[] { "我的商户", "即将到期","逾期客户"};
    private MyViewPager myViewPager;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.lab_layout)
    TabLayout mTabLayout;
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_client;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        myViewPager = new MyViewPager(getSupportFragmentManager());
        mViewPager.setAdapter(myViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }
    class MyViewPager extends FragmentStatePagerAdapter {

        public MyViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ClientFragment.newInstance(position);
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
