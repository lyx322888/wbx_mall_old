package com.wbx.mall.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.RefundProgressBean;
import com.wbx.mall.utils.DateUtil;

import java.util.List;

public class RefundProgressAdapter extends BaseQuickAdapter<RefundProgressBean.OrderTrackBean, BaseViewHolder> {
    private Context mcontext;

    public RefundProgressAdapter(Context context, @Nullable List<RefundProgressBean.OrderTrackBean> data) {
        super(R.layout.item_refund_progress, data);
        mcontext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundProgressBean.OrderTrackBean data) {
        ImageView ivIndex = helper.getView(R.id.iv_index);
        TextView tvTime = helper.getView(R.id.tv_time);
        TextView tvDetail = helper.getView(R.id.tv_detail);
        if (helper.getAdapterPosition() == 0) {
            ivIndex.setImageResource(R.drawable.circle_current_order_track);
            tvTime.setTextColor(mcontext.getResources().getColor(R.color.app_color));
            tvDetail.setTextColor(mcontext.getResources().getColor(R.color.app_color));
        } else {
            ivIndex.setImageResource(R.drawable.circle_order_track);
            tvTime.setTextColor(Color.parseColor("#9e9e9e"));
            tvDetail.setTextColor(Color.parseColor("#9e9e9e"));
        }
        tvTime.setText(DateUtil.formatDateAndTime3(data.getCreate_time()));
        tvDetail.setText(data.getStatus_message());
    }
}
