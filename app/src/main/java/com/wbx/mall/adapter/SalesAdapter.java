package com.wbx.mall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.bean.SalesDetailBean;
import com.wbx.mall.utils.DateUtil;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.VH> {

    Context context;
    List<SalesDetailBean.DataBean.ShopsBean> list;

    public SalesAdapter(Context context, List<SalesDetailBean.DataBean.ShopsBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_sales, parent, false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        GlideUtils.showSmallPic(context,holder.ivFace,list.get(position).getPhoto());
        holder.tvShopName.setText(list.get(position).getShop_name());
        holder.tvGradeName.setText(list.get(position).getGrade_name());
        holder.tvTime.setText("购买时间 "+DateUtil.formatDate3(list.get(position).getCreate_time()));
        holder.tvMoney.setText("到账￥"+list.get(position).getMoney()/100+".00");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView ivFace;
        TextView tvShopName;
        TextView tvGradeName;
        TextView tvTime;
        TextView tvMoney;

        public VH(View itemView) {
            super(itemView);
            ivFace = itemView.findViewById(R.id.user_face);
            tvShopName = itemView.findViewById(R.id.user_shop_name);
            tvGradeName = itemView.findViewById(R.id.user_grade_name);
            tvTime = itemView.findViewById(R.id.user_create_time);
            tvMoney = itemView.findViewById(R.id.user_money);
        }
    }
}
