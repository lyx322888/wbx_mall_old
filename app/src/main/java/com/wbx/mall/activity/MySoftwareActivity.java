package com.wbx.mall.activity;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.SoftwareInfo;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.fragment.MySoftwareFragment;
import com.wbx.mall.presenter.SoftwarePresenterImp;
import com.wbx.mall.view.SoftwareView;

import java.util.ArrayList;

import butterknife.Bind;

public class MySoftwareActivity extends BaseActivity implements SoftwareView {

    @Bind(R.id.ll_color_rgb)
    LinearLayout ll_color;
    @Bind(R.id.rl_color_rgb)
    RelativeLayout rl_color;
    private String[] title = new String[]{"扣除记录", "进账记录"};
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.viewPager)
    ViewPager viewPager2;
    @Bind(R.id.viewGroup)
    ViewGroup group;
    private View view1;
    private View view2;
    private ArrayList<View> pageview;
    private ImageView[] tips = new ImageView[2];
    private ImageView imageView;

    private TextView software_num1, software_num2;
    private TextView all_shop_money1, all_shop_money2;

    //圆点组的对象
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_software;
    }

    @Override
    public void initPresenter() {

//        Log.e("TAG",LoginUtil.getLoginToken());
        ll_color.setBackgroundColor(Color.parseColor("#47443E"));
        rl_color.setBackgroundColor(Color.parseColor("#47443E"));

        SoftwarePresenterImp presenterImp=new SoftwarePresenterImp(this);
        presenterImp.getSoftware(LoginUtil.getLoginToken());
    }

    @Override
    public void initView() {
        tabLayout.setTabTextColors(getResources().getColor(R.color.gray_normal), getResources().getColor(R.color.app_color));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.app_color));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        SoftPageAdapter adapter = new SoftPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        view1 = getLayoutInflater().inflate(R.layout.item_software1, null);
        view2 = getLayoutInflater().inflate(R.layout.item_software2, null);

        software_num1 = view1.findViewById(R.id.software_num);
        all_shop_money1 = view1.findViewById(R.id.all_shop_money);
        software_num2 = view2.findViewById(R.id.software_num);
        all_shop_money2 = view2.findViewById(R.id.all_shop_money);

        pageview = new ArrayList<View>();
        pageview.add(view1);
        pageview.add(view2);
        tips = new ImageView[pageview.size()];
        for (int i = 0; i < pageview.size(); i++) {
            imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
            imageView.setPadding(20, 0, 20, 0);
            tips[i] = imageView;

            //默认第一张图显示为选中状态
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }

            group.addView(tips[i]);
        }
        //这里的mypagerAdapter是第三步定义好的。
        viewPager2.setAdapter(new mypagerAdapter(pageview));
        //这里的GuiPageChangeListener是第四步定义好的。
        viewPager2.addOnPageChangeListener(new GuidePageChangeListener());
    }

    @Override
    public void fillData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void getSoftware(SoftwareInfo softwareInfo) {
        software_num1.setText("总套数:" + softwareInfo.getData().getSalesman_data().get(0).getSoftware_num());
        all_shop_money1.setText("进账金额:￥" + softwareInfo.getData().getSalesman_data().get(0).getAll_shop_money() + ".00");
        software_num2.setText("总套数:" + softwareInfo.getData().getSalesman_data().get(1).getSoftware_num());
        all_shop_money2.setText("进账金额:￥" + softwareInfo.getData().getSalesman_data().get(1).getAll_shop_money() + ".00");
    }

    class mypagerAdapter extends PagerAdapter {
        private ArrayList<View> pageview1;

        public mypagerAdapter(ArrayList<View> pageview1) {
            this.pageview1 = pageview1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (pageview1.get(position) != null) {
                container.removeView(pageview1.get(position));
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageview1.get(position));
            return pageview1.get(position);
        }

        @Override
        public int getCount() {
            return pageview1.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }
    }

    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }


        @Override
        //切换view时，下方圆点的变化。
        public void onPageSelected(int position) {
            tips[position].setBackgroundResource(R.drawable.page_indicator_focused);
            //这个图片就是选中的view的圆点
            for (int i = 0; i < pageview.size(); i++) {
                if (position != i) {
                    tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
                    //这个图片是未选中view的圆点
                }
            }
        }
    }


    private class SoftPageAdapter extends FragmentStatePagerAdapter {

        public SoftPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            return MySoftwareFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return title.length;
        }
    }
}
