package com.wbx.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.base.Constants;
import com.wbx.mall.bean.CustomerInfo;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

/**
 * Created by wushenghui on 2017/10/23.
 */

public class MyCustomerAdapter extends BaseAdapter<CustomerInfo, Context> {

    public MyCustomerAdapter(List<CustomerInfo> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_customer;
    }

    @Override
    public void convert(BaseViewHolder holder, final CustomerInfo customerInfo, int position) {
        GlideUtils.showMediumPic(mContext, (ImageView) holder.getView(R.id.iv_user), customerInfo.getFace());
        holder.setText(R.id.tv_name, customerInfo.getNickname())
                .setText(R.id.tv_phone, customerInfo.getPhone())
                .setText(R.id.tv_num, String.valueOf(customerInfo.getShop_order_num()))
                .setText(R.id.tv_money, String.format("%.2f元", customerInfo.getRecommend_bonus() / 100.00));
        switch (customerInfo.getRank()) {
            case Constants.RANK_ONE_STAR:
            case Constants.RANK_TWO_STAR:
            case Constants.RANK_THREE_STAR:
                holder.setText(R.id.tv_rank, "星合伙人");
                break;
            case Constants.RANK_LEADER:
                holder.setText(R.id.tv_rank, "领袖");
                break;
            case Constants.RANK_PARTNER:
                holder.setText(R.id.tv_rank, "战略合作伙伴");
                break;
            case Constants.RANK_CITY_AGENT:
                holder.setText(R.id.tv_rank, "城市代理");
                break;
        }
        holder.getView(R.id.iv_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(customerInfo.getPhone())) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + customerInfo.getPhone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
