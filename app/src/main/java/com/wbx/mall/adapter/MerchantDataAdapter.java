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
import com.wbx.mall.bean.ListShopDataBean;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

public class MerchantDataAdapter extends RecyclerView.Adapter<MerchantDataAdapter.VH> {

    Context context;
    List<ListShopDataBean.DataBean.ShopsBean> list;

    public MerchantDataAdapter(Context context, List<ListShopDataBean.DataBean.ShopsBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_merchant_data, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tv_position.setText("");
        if (position == 0) {
            holder.tv_position.setBackgroundResource(R.drawable.img_gold);
        } else if (position == 1) {
            holder.tv_position.setBackgroundResource(R.drawable.img_silver);
        } else if (position == 2) {
            holder.tv_position.setBackgroundResource(R.drawable.img_copper);
        } else {
            holder.tv_position.setText(position + 1 + "");
        }
        GlideUtils.showMediumPic(context, holder.iv_photo, list.get(position).getPhoto().contains("http://") ? list.get(position).getPhoto().contains("wbx365888") ? list.get(position).getPhoto() + "?imageView2/1/w/200/h/200" : list.get(position).getPhoto() : "http://www.wbx365.com" + list.get(position).getPhoto());
        holder.tv_name.setText(list.get(position).getShop_name());
        holder.tv_price.setText("营业额 ￥ " + list.get(position).getTurnover());
        holder.tv_num.setText("人气浏览量 " + list.get(position).getOrder_num());
        //当当前的item为最后一个时隐藏view
        if (position == list.size() - 1) {
            holder.bottomLine.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView iv_photo;
        TextView tv_price;
        TextView tv_name;
        TextView tv_num;
        TextView tv_position;
        View bottomLine;

        VH(View itemView) {
            super(itemView);
            iv_photo = itemView.findViewById(R.id.iv_photo);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_position = itemView.findViewById(R.id.tv_position);
            bottomLine = itemView.findViewById(R.id.line);
        }
    }
}
