package com.wbx.mall.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wbx.mall.R;
import com.wbx.mall.bean.GoodsInfo2;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页免单推荐弹窗
 */
public class ProductDetailDialog extends Dialog {
    private Context mContext;
    private View layout;
    private GoodsInfo2 mGoodInfo;
    private List<String> mPhotoList = new ArrayList<>();
    private TextView tvCount;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvSoldNum;
    private ViewPager viewPager;
    private MyAdapter adapter;

    public ProductDetailDialog(Context context, GoodsInfo2 goodsInfo) {
        super(context, R.style.DialogTheme);
        this.mContext = context;
        this.mGoodInfo = goodsInfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = LayoutInflater.from(mContext).inflate(R.layout.dialog_product_detail, null);
        setContentView(layout);
        init();
        initView();
    }

    private void initView() {
        tvName = layout.findViewById(R.id.tv_name);
        tvPrice = layout.findViewById(R.id.tv_price);
        TextView tvMemberPrice = layout.findViewById(R.id.tv_member_price);
        tvSoldNum = layout.findViewById(R.id.tv_sold_num);
        tvCount = layout.findViewById(R.id.tv_count);
        viewPager = layout.findViewById(R.id.view_pager);
        adapter = new MyAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tvCount.setText(position + 1 + "/" + mPhotoList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tvName.setText(mGoodInfo.getTitle());
        tvPrice.setText(String.format("¥%.2f", mGoodInfo.getPrice() / 100.00));
        tvSoldNum.setText(String.format("销量: %d份", mGoodInfo.getSold_num()));
        if (mGoodInfo.getGoods_photo() == null || mGoodInfo.getGoods_photo().size() == 0) {
            mPhotoList.add(mGoodInfo.getPhoto());
        } else {
            mPhotoList.addAll(mGoodInfo.getGoods_photo());
        }
        tvCount.setText("1/" + mPhotoList.size());
        adapter.notifyDataSetChanged();
    }

    private void init() {
        getWindow().setGravity(Gravity.CENTER);//设置显示在底部
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);//设置Dialog的宽度为屏幕宽度
//        layoutParams.height = (int) (display.getHeight() / 0.8);//设置Dialog的高度
        getWindow().setAttributes(layoutParams);
        setCanceledOnTouchOutside(true);
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mPhotoList.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            String path = mPhotoList.get(position);
            Glide.with(mContext).load(path).error(R.drawable.loading_logo).fitCenter().into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
