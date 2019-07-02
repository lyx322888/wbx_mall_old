package com.wbx.mall.adapter;


import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.CateInfo;

import java.util.List;

public class GoodsTypeAdapter extends BaseQuickAdapter<CateInfo, BaseViewHolder> {
    private int checkItem = 0;

    public GoodsTypeAdapter(@Nullable List<CateInfo> data) {
        super(R.layout.item_cate, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CateInfo cateInfo) {
        LinearLayout itemLayout = helper.getView(R.id.item_layout);
        ImageView typeIconIm = helper.getView(R.id.type_icon_im);
        typeIconIm.setVisibility(View.VISIBLE);
        if ("特价".equals(cateInfo.getCate_name())) {
            typeIconIm.setImageResource(R.drawable.seckill_icon_type);
        } else if ("促销".equals(cateInfo.getCate_name())) {
            typeIconIm.setImageResource(R.drawable.pro_icon);
        } else if ("免单".equals(cateInfo.getCate_name())) {
            typeIconIm.setImageResource(R.drawable.mian_icon);
        } else {
            typeIconIm.setVisibility(View.GONE);
        }
        TextView tvMenuName = helper.getView(R.id.item_menu_tv);
        tvMenuName.setText(cateInfo.getCate_name());
        if (helper.getAdapterPosition() == checkItem) {
            itemLayout.setBackgroundResource(R.drawable.bg_shop_cate);
            tvMenuName.setTextColor(Color.BLACK);
        } else {
            itemLayout.setBackgroundResource(R.color.color_gray_line);
            tvMenuName.setTextColor(Color.parseColor("#6A6969"));
        }
        if (cateInfo.getBuy_num() > 0) {
            helper.getView(R.id.tv_num).setVisibility(View.VISIBLE);
            if (cateInfo.getBuy_num() < 100) {
                helper.setText(R.id.tv_num, String.valueOf(cateInfo.getBuy_num()));
            } else {
                helper.setText(R.id.tv_num, "99+");
            }
        } else {
            helper.getView(R.id.tv_num).setVisibility(View.GONE);
        }
    }

    public void setItemCheck(int i) {
        this.checkItem = i;
        notifyDataSetChanged();
    }
}
