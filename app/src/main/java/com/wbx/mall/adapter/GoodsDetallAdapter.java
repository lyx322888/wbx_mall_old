package com.wbx.mall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

public class GoodsDetallAdapter extends RecyclerView.Adapter<GoodsDetallAdapter.VH> {

    Context context;
    List<GoodsInfo2> list;
    private boolean isShopMemberUser;

    public GoodsDetallAdapter(Context context, List<GoodsInfo2> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_goodsdetall, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        GlideUtils.showSmallPic(context, holder.iv_goods, list.get(position).getPhoto());
        holder.tv_name.setText(list.get(position).getTitle());
        if (list.get(position).getIs_shop_member() == 1) {
            holder.tv_vip_price.setText(String.format("%.2f", list.get(position).getShop_member_price() / 100.00));
            holder.tv_price.setText(String.format("商城价¥%.2f", (list.get(position).getSales_promotion_is() == 1 ? list.get(position).getSales_promotion_price() : list.get(position).getMall_price()) / 100.00));
            if (isShopMemberUser && list.get(position).getIs_shop_member() == 1) {
                holder.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        } else {
            holder.iv_member.setVisibility(View.GONE);
            holder.tv_vip_price.setText(String.format("%.2f", (list.get(position).getSales_promotion_is() == 1 ? list.get(position).getSales_promotion_price() : list.get(position).getMall_price()) / 100.00));
            holder.tv_price.setVisibility(View.INVISIBLE);
        }

        int count = 0;
        for (String s : list.get(position).getHmBuyNum().keySet()) {
            count += list.get(position).getHmBuyNum().get(s);
        }
        holder.tvBuyNum.setText(String.valueOf(count));
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView iv_goods;
        TextView tv_name;
        TextView tv_price;
        ImageView iv_member;
        TextView tv_vip_price;
        ImageView sub;
        ImageView add;
        TextView tvBuyNum;

        VH(View itemView) {
            super(itemView);
            iv_goods = itemView.findViewById(R.id.img_goods);
            tv_name = itemView.findViewById(R.id.detail_goods_name_tv);
            tv_price = itemView.findViewById(R.id.tv_member_price);
            iv_member = itemView.findViewById(R.id.iv_member);
            tv_vip_price = itemView.findViewById(R.id.goods_price_tv);
            sub = itemView.findViewById(R.id.sub_product_im);
            add = itemView.findViewById(R.id.add_product_im);
            tvBuyNum = itemView.findViewById(R.id.show_num_buy_tv);
        }
    }
}
