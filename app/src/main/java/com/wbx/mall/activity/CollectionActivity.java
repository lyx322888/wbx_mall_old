package com.wbx.mall.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wbx.mall.R;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.fragment.CollectionFragment;
import com.wbx.mall.widget.SegmentView;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/5/13.
 */

public class CollectionActivity extends BaseActivity implements SegmentView.onSegmentViewClickListener {
    @Bind(R.id.collection_view_pager)
    ViewPager mViewPager;
    @Bind(R.id.collection_segment_view)
    SegmentView mSegmentView;
    private MyAdapter myAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        int type = getIntent().getIntExtra("type", 0);
        mSegmentView.setSegmentText("商品收藏", 0);
        mSegmentView.setSegmentText("店铺关注", 1);
        myAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myAdapter);
        if (type == AppConfig.CollectionType.STORE) {
            mViewPager.setCurrentItem(1);
        }
    }

    @Override
    public void setListener() {
        mSegmentView.setOnSegmentViewClickListener(this);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mSegmentView.setSelectTab(position);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onSegmentViewClick(View v, int position) {
        mViewPager.setCurrentItem(position);
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
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
            return 2;
        }
    }
}
