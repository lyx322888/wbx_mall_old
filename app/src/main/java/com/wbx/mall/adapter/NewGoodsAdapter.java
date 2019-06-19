package com.wbx.mall.adapter;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.GoodsInfo3;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.widget.NewAddWidget;

import java.util.List;

import static android.view.View.VISIBLE;

public class NewGoodsAdapter extends BaseQuickAdapter<GoodsInfo3, BaseViewHolder> {
    private boolean isShopMemberUser;
    private NewAddWidget.OnAddClick onAddClick;

    public NewGoodsAdapter(int layoutResId, @Nullable List<GoodsInfo3> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, GoodsInfo3 goodsInfo) {
        ImageView goodsPic = helper.getView(R.id.goods_pic_im);
        ImageView faceView = helper.getView(R.id.iv_free_activity);
        GlideUtils.showMediumPic(mContext, goodsPic, goodsInfo.getPhoto().contains("http://") ? goodsInfo.getPhoto().contains("wbx365888") ? goodsInfo.getPhoto() + "?imageView2/1/w/200/h/200" : goodsInfo.getPhoto() : "http://www.wbx365.com" + goodsInfo.getPhoto());
        helper.setText(R.id.goods_name_tv, goodsInfo.getTitle())
                .setText(R.id.need_price_tv, String.format("¥%.2f", goodsInfo.getPrice() / 100.00));
        if (goodsInfo.getSales_promotion_is() == 1) {
            helper.getView(R.id.is_sales_pro_im).setVisibility(VISIBLE);
        } else {
            helper.getView(R.id.is_sales_pro_im).setVisibility(View.GONE);
        }
        if (goodsInfo.getIs_attr() == 1) {
            helper.getView(R.id.is_spec_im).setVisibility(VISIBLE);
            helper.getView(R.id.tv_member_price).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.is_spec_im).setVisibility(View.GONE);
            helper.getView(R.id.tv_member_price).setVisibility(VISIBLE);
        }
        if (goodsInfo.getIs_seckill() == 1) {
            helper.getView(R.id.is_seckill_im).setVisibility(VISIBLE);
        } else {
            helper.getView(R.id.is_seckill_im).setVisibility(View.GONE);
        }
        if (goodsInfo.getIs_share_free() == 1 || goodsInfo.getIs_consume_free() == 1) {
            faceView.setVisibility(View.VISIBLE);
        } else {
            faceView.setVisibility(View.GONE);
        }
        TextView tvFreeOrderRule = helper.getView(R.id.tv_free_order_rule);
        if (goodsInfo.getIs_accumulate_free() == 1) {
            tvFreeOrderRule.setVisibility(VISIBLE);
            tvFreeOrderRule.setText(String.format("满%d免%d", goodsInfo.getAccumulate_free_need_num(), goodsInfo.getAccumulate_free_get_num()));
            helper.getView(R.id.ll_smile).setVisibility(VISIBLE);
            LinearLayout llSmileContainer = helper.getView(R.id.ll_container_smile);
            llSmileContainer.removeAllViews();
            for (int i = 0; i < goodsInfo.getAccumulate_free_need_num(); i++) {
                View layout = LayoutInflater.from(mContext).inflate(R.layout.layout_smile, null);
                ImageView ivSmile = layout.findViewById(R.id.iv_smile);
                if (i < goodsInfo.getCurrent_num()) {
                    ivSmile.setImageResource(R.drawable.xiao);
                } else {
                    ivSmile.setImageResource(R.drawable.xiao2);
                }
                llSmileContainer.addView(layout);
            }
            int stillNeedNum = goodsInfo.getAccumulate_free_need_num() - goodsInfo.getCurrent_num();
            if (stillNeedNum <= 0) {
                helper.getView(R.id.tv_free_gain).setVisibility(VISIBLE);
            } else {
                helper.getView(R.id.tv_free_gain).setVisibility(View.GONE);
            }
            helper.setText(R.id.tv_still_need_num, String.valueOf(stillNeedNum < 0 ? 0 : stillNeedNum));
        } else {
            tvFreeOrderRule.setVisibility(View.GONE);
            helper.getView(R.id.ll_smile).setVisibility(View.GONE);
            helper.getView(R.id.tv_free_gain).setVisibility(View.GONE);
        }
        TextView tvNeedPrice = helper.getView(R.id.need_price_tv);
        if (goodsInfo.getIs_shop_member() == 1) {
            helper.getView(R.id.ll_member_price).setVisibility(VISIBLE);
            helper.setText(R.id.tv_member_price, String.format("¥%.2f", goodsInfo.getShop_member_price() / 100.00));
            tvNeedPrice.setTextColor(Color.parseColor("#989898"));
            if (isShopMemberUser) {
                tvNeedPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                tvNeedPrice.getPaint().setFlags(0);
            }
        } else {
            helper.getView(R.id.ll_member_price).setVisibility(View.GONE);
            tvNeedPrice.setTextColor(mContext.getResources().getColor(R.color.app_color));
            tvNeedPrice.getPaint().setFlags(0);
        }
        NewAddWidget addWidget = helper.getView(R.id.addwidget);
        addWidget.setData(onAddClick, goodsInfo);
        helper.addOnClickListener(R.id.goods_pic_im)
                .addOnClickListener(R.id.tv_free_gain);
    }
}
