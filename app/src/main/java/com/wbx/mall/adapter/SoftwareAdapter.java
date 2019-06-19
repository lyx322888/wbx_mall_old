package com.wbx.mall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wbx.mall.R;
import com.wbx.mall.bean.SoftwareBean;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

public class SoftwareAdapter extends RecyclerView.Adapter<SoftwareAdapter.VH> {

    Context context;
    List<SoftwareBean> list;

    public SoftwareAdapter(Context context, List<SoftwareBean> list) {
        this.context = context;
        this.list = list;
    }

    public void update(List<SoftwareBean> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_software, parent, false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        GlideUtils.showSmallPic(context, holder.user_photo, list.get(position).getShops().get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView user_photo;

        public VH(View itemView) {
            super(itemView);
            user_photo = itemView.findViewById(R.id.iv_acitivity_user_1);

        }
    }
}
