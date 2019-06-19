package com.wbx.mall.adapter;


import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.OrderTrackBean;

import java.util.List;

public class OrderTrackAdapter extends BaseQuickAdapter<OrderTrackBean, BaseViewHolder> {
    private List<OrderTrackBean> lstData;
    private Context mContext;

    public OrderTrackAdapter(Context context, @Nullable List<OrderTrackBean> data) {
        super(R.layout.item_order_track, data);
        mContext = context;
        lstData = data;
    }

    @Override
    protected void convert(BaseViewHolder holder, OrderTrackBean orderTrackBean) {
        TextView tvState = holder.getView(R.id.tv_state);
        TextView tvTime = holder.getView(R.id.tv_time);
        ImageView ivIndex = holder.getView(R.id.iv_index);
        View indexLine = holder.getView(R.id.index_line);
        if (holder.getAdapterPosition() == lstData.size() - 1) {
            tvState.setTextColor(mContext.getResources().getColor(R.color.black));
            tvTime.setTextColor(mContext.getResources().getColor(R.color.black));
            ivIndex.setImageResource(R.drawable.circle_current_order_track);
            indexLine.setVisibility(View.INVISIBLE);
        } else {
            tvState.setTextColor(mContext.getResources().getColor(R.color.gray));
            tvTime.setTextColor(mContext.getResources().getColor(R.color.gray));
            ivIndex.setImageResource(R.drawable.circle_order_track);
            indexLine.setVisibility(View.VISIBLE);
        }
        tvState.setText(orderTrackBean.getStatus_message());
        tvTime.setText(orderTrackBean.getCreate_time());
    }
}
