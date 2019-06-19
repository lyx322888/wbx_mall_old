package com.wbx.mall.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.MyBuyGoodsListBean;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

public class MyFreeOrderCouponGoodsAdapter extends BaseQuickAdapter<MyBuyGoodsListBean.GoodsBean, BaseViewHolder> {
    MyFreeOrderCouponGoodsAdapter(@Nullable List<MyBuyGoodsListBean.GoodsBean> data) {
        super(R.layout.item_my_free_order_coupon_goods, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyBuyGoodsListBean.GoodsBean item) {
        LinearLayout llContainer = helper.getView(R.id.ll_container);
        LinearLayout llLess = helper.getView(R.id.ll_goods_less);
        TextView tvLessNum = helper.getView(R.id.tv_goods_less_num);
        TextView tvNoEnough = helper.getView(R.id.tv_no_enough_goods_hint);
        TextView tvBuy = helper.getView(R.id.tv_buy_goods);
        TextView tvFree = helper.getView(R.id.tv_free_bt);
        TextView tvEnough = helper.getView(R.id.tv_free_gain_hint);
        TextView tvTitle = helper.getView(R.id.tv_goods_name);
        GlideUtils.showSmallPic(mContext, (ImageView) helper.getView(R.id.iv_goods_pic), item.getPhoto());
        llContainer.removeAllViews();

        if (item.getCurrent_num() >= item.getAccumulate_free_need_num()) {
            llLess.setVisibility(View.GONE);
            tvNoEnough.setVisibility(View.GONE);
            tvBuy.setVisibility(View.GONE);
            tvFree.setVisibility(View.VISIBLE);
            tvEnough.setVisibility(View.VISIBLE);
        } else {
            llLess.setVisibility(View.VISIBLE);
            tvNoEnough.setVisibility(View.VISIBLE);
            tvBuy.setVisibility(View.VISIBLE);
            tvFree.setVisibility(View.GONE);
            tvEnough.setVisibility(View.GONE);
            int num = item.getAccumulate_free_need_num() - item.getCurrent_num();
            tvLessNum.setText(String.valueOf(num));
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            tvTitle.setText(item.getTitle());
        } else {
            tvTitle.setText("");
        }
        for (int i = 0; i < item.getAccumulate_free_need_num(); i++) {
            View layout = LayoutInflater.from(mContext).inflate(R.layout.layout_smile, null);
            ImageView ivSmile = layout.findViewById(R.id.iv_smile);
            if (i < item.getCurrent_num()) {
                ivSmile.setImageResource(R.drawable.xiao);
            } else {
                ivSmile.setImageResource(R.drawable.xiao2);
            }
            llContainer.addView(layout);
        }
        helper.addOnClickListener(R.id.tv_free_bt)
                .addOnClickListener(R.id.tv_buy_goods);
    }
}
