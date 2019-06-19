package com.wbx.mall.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.bean.ScanOrderDetailBean;
import com.wbx.mall.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zero on 2017/1/17.
 */

public class ScanOrderListAdapter extends RecyclerView.Adapter<ScanOrderListAdapter.MyViewHolder> {
    private Activity mContext;

    private List<ScanOrderDetailBean> lstData = new ArrayList<>();

    public ScanOrderListAdapter(Activity context) {
        mContext = context;
    }

    public void update(List<ScanOrderDetailBean> data) {
        lstData.clear();
        lstData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scan_order_list, parent, false);
        MyViewHolder holder = new MyViewHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ScanOrderDetailBean data = lstData.get(position);
        holder.tvShopName.setText(data.getShop_name());
        holder.tvState.setText(data.getStatus());
        holder.tvTableNum.setText(data.getSeat());
        holder.tvOrderTime.setText(data.getCreate_time());
        holder.tvPeopleNum.setText(data.getPeople_num() + "人");
        holder.llContainer.removeAllViews();
        for (int i = 0; i < data.getGoods().size(); i++) {
            ScanOrderDetailBean.GoodsBean goodsBean = data.getGoods().get(i);
            View layout = LayoutInflater.from(mContext).inflate(R.layout.item_scan_order_list_food, null);
            ImageView imgGoods = layout.findViewById(R.id.iv_goods);
            GlideUtils.showMediumPic(mContext, imgGoods, goodsBean.getPhoto());
            TextView tvPrice = layout.findViewById(R.id.tv_price);
            tvPrice.setText("¥" + goodsBean.getPrice());
            if (i == data.getGoods().size() - 1) {
                layout.findViewById(R.id.divider_right).setVisibility(View.VISIBLE);
            }
            holder.llContainer.addView(layout);
        }
        holder.tvTotalFee.setText(String.format("合计费用：%s元", data.getNeed_price()));
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_shop_name)
        TextView tvShopName;
        @Bind(R.id.tv_state)
        TextView tvState;
        @Bind(R.id.tv_table_num)
        TextView tvTableNum;
        @Bind(R.id.tv_order_time)
        TextView tvOrderTime;
        @Bind(R.id.tv_people_num)
        TextView tvPeopleNum;
        @Bind(R.id.ll_container)
        LinearLayout llContainer;
        @Bind(R.id.tv_total_fee)
        TextView tvTotalFee;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
