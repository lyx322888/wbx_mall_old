package com.wbx.mall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.wbx.mall.R;
import com.wbx.mall.bean.HomeShufflingData;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.widget.MarqueeView;

import java.util.List;

public class HomeShufflingAdapter extends MarqueeView.Adapter<HomeShufflingAdapter.VH> {

    Context context;
    List<HomeShufflingData.DataBean.OrderBean> list;

    public HomeShufflingAdapter(Context context, List<HomeShufflingData.DataBean.OrderBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_index_notify, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (Util.isOnMainThread()) {
            GlideUtils.showSmallPic(context.getApplicationContext(), holder.ivHead, list.get(position % list.size()).getFace());
        }
//        String time = TimerUtils.timeParse(list.get(position % list.size()).getCreate_time());
        holder.tvTitle.setText(list.get(position % list.size()).getNickname() + "十分钟前购买了" + list.get(position % list.size()).getTitle());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView ivHead;
        TextView tvTitle;

        VH(View itemView) {
            super(itemView);
            ivHead = itemView.findViewById(R.id.iv_head);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
