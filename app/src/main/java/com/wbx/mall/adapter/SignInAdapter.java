package com.wbx.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.bean.IntegrateBean;
import com.wbx.mall.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zero on 2017/1/17.
 */

public class SignInAdapter extends RecyclerView.Adapter<SignInAdapter.MyViewHolder> {
    private Context mContext;

    private List<IntegrateBean> lstData = new ArrayList<>();

    public SignInAdapter(Context context) {
        mContext = context;
    }

    public void update(List<IntegrateBean> data) {
        lstData.clear();
        lstData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_integrate, parent, false);
        MyViewHolder holder = new MyViewHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        IntegrateBean data = lstData.get(position);
        holder.tvType.setText(data.getIntro());
        holder.tvTime.setText(DateUtil.formatDateAndTime(data.getCreate_time()));
        holder.tvScore.setText(data.getIntegral());
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_type)
        TextView tvType;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_score)
        TextView tvScore;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
