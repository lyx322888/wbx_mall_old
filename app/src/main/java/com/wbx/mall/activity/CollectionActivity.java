package com.wbx.mall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.fragment.CollectionFragment;

import butterknife.Bind;


public class CollectionActivity extends BaseActivity {
    private String[] title = new String[]{"商品收藏", "店铺关注"};
    @Bind(R.id.collection_view_pager)
    ViewPager mViewPager;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        mTabLayout.setTabTextColors(getResources().getColor(R.color.gray_normal), getResources().getColor(R.color.app_color));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.app_color));
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);
        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myAdapter);
    }

    @Override
    public void fillData() {
    }

    @Override
    public void setListener() {

    }

    class MyAdapter extends FragmentStatePagerAdapter {

        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            CollectionFragment fragment = new CollectionFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return title.length;
        }
    }
}
