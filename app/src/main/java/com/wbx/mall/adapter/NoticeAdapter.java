package com.wbx.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.bean.NoticeBean;
import com.wbx.mall.utils.DateUtil;
import com.wbx.mall.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zero on 2017/1/17.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {
    private Context mContext;

    private List<List<NoticeBean.DataBean>> lstData = new ArrayList<>();

    public NoticeAdapter(Context context) {
        mContext = context;
    }

    public void update(List<List<NoticeBean.DataBean>> data) {
        lstData.clear();
        lstData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice, parent, false);
        MyViewHolder holder = new MyViewHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        List<NoticeBean.DataBean> data = lstData.get(position);
        holder.tvDate.setText(DateUtil.formatDate(data.get(0).getCreate_time()));
        holder.llContainer.removeAllViews();
        for (NoticeBean.DataBean dataBean : data) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_notice_message, null);
            String title = "";
            switch (dataBean.getStatus()) {
                case 1:
                    title = "等待发货";
                    break;
                case 2:
                    title = "商家已发货";
                    break;
                case 3:
                    title = "商家已拒单";
                    break;
                case 4:
                    title = "等待退款";
                    break;
                case 5:
                    title = "商家已退款";
                    break;
                case 6:
                    title = "商家拒退款";
                    break;
                case 8:
                    title = "扫码订单";
                    break;
                default:
                    break;
            }
            ((TextView) inflate.findViewById(R.id.tv_title)).setText(title);
            ImageView ivImage = inflate.findViewById(R.id.iv_image);
            GlideUtils.showMediumPic(mContext, ivImage, dataBean.getPhoto());
            ((TextView) inflate.findViewById(R.id.tv_detail)).setText(dataBean.getMsg());
            ((TextView) inflate.findViewById(R.id.tv_order_num)).setText("订单编号：" + dataBean.getOrder_id());
            holder.llContainer.addView(inflate);
        }
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.ll_container)
        LinearLayout llContainer;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
