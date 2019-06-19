package com.wbx.mall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wbx.mall.R;

import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.bean.HomeCouponBean;

import java.util.List;


public class IndexCouponAdapter extends RecyclerView.Adapter<IndexCouponAdapter.VH> {
    Context context;
    List<HomeCouponBean.DataBean> list;

    public IndexCouponAdapter(Context context, List<HomeCouponBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.coupon_layout, parent, false);
        final VH vh = new VH(view);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recyclerViewItemClieck.recyclerViewItemClieck(vh.getAdapterPosition(), view, vh);
//            }
//        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.shop_name.setText(list.get(position).getShop_name());
        holder.coupon_monet.setText(list.get(position).getCondition_money() / 100 + "");
        holder.full_reduction.setText("满" + list.get(position).getMoney() / 100 + "元可用");

        holder.user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreDetailActivity.actionStart(context, list.get(position).getGrade_id(), list.get(position).getShop_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //    public IndexCouponAdapter(List<HomeCouponBean> dataList, Context context) {
//        super(dataList, context);
//    }
//
//    @Override
//    public int getLayoutId(int viewType) {
//        return R.layout.coupon_layout;
//    }
//
//    @Override
//    public void convert(BaseViewHolder holder, HomeCouponBean homeCouponBean, int position) {
//        TextView shop_name = holder.getView(R.id.shop_name_tv);
//        TextView full_reduction = holder.getView(R.id.full_reduction_tv);
//        TextView coupon_monet = holder.getView(R.id.coupon_monet_tv);
//
//        shop_name.setText(homeCouponBean.getShop_name());
//        full_reduction.setText(String.format("满" + "¥%.2f" + "元可用", homeCouponBean.getCondition_money() / 100));
//        coupon_monet.setText(String.format("", homeCouponBean.getMoney() / 100));
//    }
    class VH extends RecyclerView.ViewHolder {
        TextView shop_name;
        TextView full_reduction;
        TextView coupon_monet;
        Button user_button;

        public VH(View itemView) {
            super(itemView);
            shop_name = itemView.findViewById(R.id.shop_name_tv);
            full_reduction = itemView.findViewById(R.id.full_reduction_tv);
            coupon_monet = itemView.findViewById(R.id.coupon_monet_tv);
            user_button = itemView.findViewById(R.id.user_button);
        }
    }

    public RecyclerViewItemClieck recyclerViewItemClieck;

    public interface RecyclerViewItemClieck {
        void recyclerViewItemClieck(int position, View view, RecyclerView.ViewHolder viewHolder);
    }

    public void setRecyclerViewItemClieck(RecyclerViewItemClieck recyclerViewItemClieck) {
        this.recyclerViewItemClieck = recyclerViewItemClieck;
    }
}
