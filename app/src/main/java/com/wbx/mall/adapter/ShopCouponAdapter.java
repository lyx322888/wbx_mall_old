package com.wbx.mall.adapter;


import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.CouponInfo;
import com.wbx.mall.utils.DateUtil;

import java.util.List;

public class ShopCouponAdapter extends BaseQuickAdapter<CouponInfo, BaseViewHolder> {

    public ShopCouponAdapter(@Nullable List<CouponInfo> data) {
        super(R.layout.item_shop_coupon_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, CouponInfo couponInfo) {
        holder.setText(R.id.tv_money, String.format("%.2f", couponInfo.getMoney() / 100.00)).setText(R.id.tv_condition, String.format("满%d元使用", couponInfo.getCondition_money() / 100)).setText(R.id.tv_end_time, String.format("限本店使用，%s到期", DateUtil.formatDate3(couponInfo.getEnd_time())));
        ImageView ivReceive = holder.getView(R.id.iv_receive);
        if (couponInfo.getIs_receive() == 1) {
            ivReceive.setImageResource(R.drawable.icon_received_coupon);
        } else {
            ivReceive.setImageResource(R.drawable.icon_receive_coupon);
        }
        holder.addOnClickListener(R.id.iv_receive);
    }
}
