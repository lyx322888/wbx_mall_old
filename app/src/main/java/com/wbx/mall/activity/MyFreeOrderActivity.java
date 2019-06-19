package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.fragment.MyFreeOrderFragment;

import butterknife.Bind;

public class MyFreeOrderActivity extends BaseActivity {
    private String[] title = new String[]{"全部", "进行中", "成功", "失败"};
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MyFreeOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_free_order;
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
        FreeActivityPageAdapter adapter = new FreeActivityPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }

    private class FreeActivityPageAdapter extends FragmentStatePagerAdapter {

        public FreeActivityPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            return MyFreeOrderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return title.length;
        }
    }
}
