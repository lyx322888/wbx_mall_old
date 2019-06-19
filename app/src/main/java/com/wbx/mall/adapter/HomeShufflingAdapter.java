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
import com.wbx.mall.utils.TimerUtils;
import com.wbx.mall.widget.MarqueeView;

import java.util.List;

public class HomeShufflingAdapter extends MarqueeView.Adapter<HomeShufflingAdapter.VH> {

    Context context;
    List<HomeShufflingData.DataBean.ActivityUserBean> list;
//    private int size;

    public HomeShufflingAdapter(Context context, List<HomeShufflingData.DataBean.ActivityUserBean> list) {
        this.context = context;
        this.list = list;
//        size=list.size();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_homeshuffling, parent, false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (Util.isOnMainThread()) {
            GlideUtils.showSmallPic(context.getApplicationContext(), (ImageView) holder.user, list.get(position % list.size()).getFace());
        }
        String time = TimerUtils.timeParse(list.get(position % list.size()).getCreate_time());
        holder.tv_shuffling.setText(list.get(position % list.size()).getNickname() + " " + time + "分钟前购买了" + list.get(position % list.size()).getTitle() + " 免单成功");
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView user;
        TextView tv_shuffling;

        public VH(View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.iv_acitivity_user);
            tv_shuffling = itemView.findViewById(R.id.tv_shuffling);
        }
    }
}
