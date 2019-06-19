package com.wbx.mall.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

/**
 * Created by wushenghui on 2017/7/20.
 */

public class BookSeatOrderDetailAdapter extends BaseAdapter<GoodsInfo2, Context> {

    public BookSeatOrderDetailAdapter(List<GoodsInfo2> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_book_order_seat_goods;
    }

    @Override
    public void convert(BaseViewHolder holder, GoodsInfo2 goodsInfo, int position) {
        holder.setText(R.id.tv_name, TextUtils.isEmpty(goodsInfo.getProduct_name()) ? goodsInfo.getTitle() : goodsInfo.getProduct_name()).setText(R.id.tv_num, String.format("*%d", goodsInfo.getNum())).setText(R.id.tv_price, String.format("¥%.02f", goodsInfo.getPrice() / 100.00));
        TextView tvAttr = holder.getView(R.id.tv_attr);
        if (!TextUtils.isEmpty(goodsInfo.getAttr_name()) || !TextUtils.isEmpty(goodsInfo.getNature_name())) {
            tvAttr.setVisibility(View.VISIBLE);
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(goodsInfo.getAttr_name())) {
                sb.append(goodsInfo.getAttr_name());
            }
            if (!TextUtils.isEmpty(goodsInfo.getAttr_name()) && !TextUtils.isEmpty(goodsInfo.getNature_name())) {
                sb.append("+");
            }
            if (!TextUtils.isEmpty(goodsInfo.getNature_name())) {
                sb.append(goodsInfo.getNature_name());
            }
            tvAttr.setText(sb.toString());
        } else {
            tvAttr.setVisibility(View.GONE);
        }
        ImageView goodsIm = holder.getView(R.id.iv_goods);
        GlideUtils.showMediumPic(mContext, goodsIm, goodsInfo.getPhoto());
    }
}
