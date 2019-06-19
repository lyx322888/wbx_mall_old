package com.wbx.mall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.CookInfo;
import com.wbx.mall.fragment.CookBookFragment;
import com.wbx.mall.widget.LoadingDialog;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/8/24.
 */

public class CookBookActivity extends BaseActivity {
    @Bind(R.id.tools)
    LinearLayout toolsLayout;
    @Bind(R.id.cooks_pager)
    ViewPager mViewPager;
    @Bind(R.id.tools_scrlllview)
    NestedScrollView scrollView;
    private List<CookInfo> cookInfoList;
    private TextView toolsTextViews[];
    private View views[];
    private int currentItem = 0;
    private int scrllViewWidth = 0, scrollViewMiddle = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cook_book;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData() {
        LoadingDialog.showDialogForLoading(this, "加载中...", true);
        new MyHttp().doPost(Api.getDefault().getCookInfo(), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                cookInfoList = JSONArray.parseArray(result.getJSONObject("data").getString("result"), CookInfo.class);
                showToolsView();
                initViewPager();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void initViewPager() {
        mViewPager.setAdapter(new CookAdapter(getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(onPageChangeListener);
    }


    /**
     * OnPageChangeListener<br/>
     * 监听ViewPager选项卡变化事的事件
     */

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int arg0) {
            if (mViewPager.getCurrentItem() != arg0) mViewPager.setCurrentItem(arg0);
            if (currentItem != arg0) {
                changeTextColor(arg0);
                changeTextLocation(arg0);
            }
            currentItem = arg0;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    /**
     * 改变栏目位置
     *
     * @param clickPosition
     */
    private void changeTextLocation(int clickPosition) {

        int x = (views[clickPosition].getTop() - getScrollViewMiddle() + (getViewheight(views[clickPosition]) / 2));
        scrollView.smoothScrollTo(0, x);
    }

    /**
     * 返回scrollview的中间位置
     *
     * @return
     */
    private int getScrollViewMiddle() {
        if (scrollViewMiddle == 0)
            scrollViewMiddle = getScrollViewheight() / 2;
        return scrollViewMiddle;
    }

    /**
     * 返回view的宽度
     *
     * @param view
     * @return
     */
    private int getViewheight(View view) {
        return view.getBottom() - view.getTop();
    }

    /**
     * 返回ScrollView的宽度
     *
     * @return
     */
    private int getScrollViewheight() {
        if (scrllViewWidth == 0)
            scrllViewWidth = scrollView.getBottom() - scrollView.getTop();
        return scrllViewWidth;
    }

    //显示左边分类
    private void showToolsView() {
        toolsTextViews = new TextView[cookInfoList.size()];
        views = new View[cookInfoList.size()];
        for (int i = 0; i < cookInfoList.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_b_top_nav_layout, null);
            view.setId(i);
            view.setOnClickListener(toolsItemListener);
            TextView textView = view.findViewById(R.id.text);
            textView.setText(cookInfoList.get(i).getName());
            toolsLayout.addView(view);
            toolsTextViews[i] = textView;
            views[i] = view;
        }
        changeTextColor(0);
    }

    @Override
    public void setListener() {
        findViewById(R.id.search_cook_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, CookListActivity.class));
            }
        });
    }

    private View.OnClickListener toolsItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(v.getId());
        }
    };

    /**
     * ViewPager 加载选项卡
     *
     * @author Administrator
     */
    private class CookAdapter extends FragmentPagerAdapter {
        public CookAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            Fragment fragment = new CookBookFragment();
            Bundle bundle = new Bundle();
            List<CookInfo> list = cookInfoList.get(arg0).getList();
            bundle.putSerializable("cookList", (Serializable) list);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return cookInfoList.size();
        }
    }

    /**
     * 改变textView的颜色
     *
     * @param id
     */
    private void changeTextColor(int id) {
        for (int i = 0; i < toolsTextViews.length; i++) {
            if (i != id) {
                toolsTextViews[i].setBackgroundResource(android.R.color.transparent);
                toolsTextViews[i].setTextColor(getResources().getColor(R.color.black));
            }
        }
        toolsTextViews[id].setBackgroundResource(android.R.color.white);
        toolsTextViews[id].setTextColor(getResources().getColor(R.color.app_color));
    }

}
