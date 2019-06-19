package com.wbx.mall.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wbx.mall.fragment.ConsumptionRecordFragment;
import com.wbx.mall.fragment.MyWithdrawFragment;

/**
 * @author Zero
 * @date 2018/7/6
 */
public class BalanceLogPagerAdapter extends FragmentStatePagerAdapter {
    String[] titles = {"我的提现", "消费记录"};

    public BalanceLogPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = MyWithdrawFragment.newInstance();
                break;
            case 1:
                fragment = ConsumptionRecordFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
