package com.wbx.mall.adapter;


import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.GoodsInfo2;

import java.util.Iterator;
import java.util.List;

public class ShopCarAdapter extends BaseQuickAdapter<GoodsInfo2, BaseViewHolder> {
    /**
     * 是否是店铺会员
     */
    public boolean isShopMemberUser = false;

    public ShopCarAdapter(@Nullable List<GoodsInfo2> data) {
        super(R.layout.item_shop_car_detail, data);
    }

    public void setIsShopMemberUser(boolean isShopMemberUser) {
        this.isShopMemberUser = isShopMemberUser;
    }

    @Override
    protected void convert(BaseViewHolder holder, GoodsInfo2 goodsInfo) {
        StringBuilder sbSpec = new StringBuilder();
        if (goodsInfo.getIs_attr() == 1) {
            sbSpec.append(goodsInfo.getAttr_name());
        }
        if (!TextUtils.isEmpty(goodsInfo.getAttr_name()) && !TextUtils.isEmpty(goodsInfo.getNature_name())) {
            sbSpec.append("+");
        }
        if (!TextUtils.isEmpty(goodsInfo.getNature_name())) {
            sbSpec.append(goodsInfo.getNature_name());
        }
        holder.setText(R.id.tv_name, TextUtils.isEmpty(goodsInfo.getProduct_name()) ? goodsInfo.getTitle() : goodsInfo.getProduct_name())
                .setText(R.id.tv_spec, sbSpec.toString());
        Iterator<String> iterator = goodsInfo.getHmBuyNum().keySet().iterator();
        if (iterator.hasNext()) {
            holder.setText(R.id.tv_goods_num, "" + goodsInfo.getHmBuyNum().get(iterator.next()));
        } else {
            holder.setText(R.id.tv_goods_num, "0");
        }
        holder.setText(R.id.tv_price, String.format("¥%.2f", goodsInfo.getPrice() / 100.00));
        if (TextUtils.isEmpty(sbSpec.toString())) {
            holder.getView(R.id.tv_spec).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.tv_spec).setVisibility(View.VISIBLE);
        }
        if (isShopMemberUser && goodsInfo.getIs_shop_member() == 1) {
            ((TextView) holder.getView(R.id.tv_price)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.getView(R.id.tv_member_price).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_member_price, String.format("¥%.2f", goodsInfo.getShop_member_price() / 100.00));
        } else {
            ((TextView) holder.getView(R.id.tv_price)).getPaint().setFlags(0);
            holder.getView(R.id.tv_member_price).setVisibility(View.GONE);
        }
        holder.addOnClickListener(R.id.iv_reduce_goods).addOnClickListener(R.id.iv_add_goods);
    }
}
