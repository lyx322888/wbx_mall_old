package com.wbx.mall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.bean.WithdrawRecordBean;
import com.wbx.mall.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zero on 2017/1/17.
 */

public class WithdrawRecordAdapter extends RecyclerView.Adapter<WithdrawRecordAdapter.MyViewHolder> {
    private Context mContext;
    private List<WithdrawRecordBean> lstData = new ArrayList<>();

    public WithdrawRecordAdapter(Context context) {
        mContext = context;
    }

    public void update(List<WithdrawRecordBean> data) {
        lstData.clear();
        lstData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_withdraw_record, parent, false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WithdrawRecordBean data = lstData.get(position);
        holder.tvMoney.setText(String.format("微米提现: -%.2f", data.getMoney() / 100.00));
        if (data.getCommission() > 0) {
            holder.tvPoundage.setVisibility(View.VISIBLE);
            holder.tvPoundage.setText(String.format("(手续费%.2f)", data.getCommission() / 100.00));
        } else {
            holder.tvPoundage.setVisibility(View.GONE);
        }
        holder.tvRealityMoney.setText(String.format("实际到账: -%.2f", data.getMoney() / 100.00 - data.getCommission() / 100.00));
        holder.tvTime.setText(DateUtil.formatDateAndTime2(data.getAddtime()));
        if (data.getStatus() == 0) {
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.app_color));
            holder.tvStatus.setText("提现申请中");
        } else if (data.getStatus() == 1) {
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.app_color));
            holder.tvStatus.setText("申请通过");
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#F00A0A"));
            holder.tvStatus.setText("申请失败");
        }
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_money)
        TextView tvMoney;
        @Bind(R.id.tv_poundage)
        TextView tvPoundage;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_status)
        TextView tvStatus;
        @Bind(R.id.tv_reality_money)
        TextView tvRealityMoney;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
