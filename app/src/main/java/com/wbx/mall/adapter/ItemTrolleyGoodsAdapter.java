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
 * Created by wushenghui on 2018/1/31.
 */

public class ItemTrolleyGoodsAdapter extends BaseAdapter<GoodsInfo2, Context> {

    public ItemTrolleyGoodsAdapter(List<GoodsInfo2> dataList, Context context) {
        super(dataList, context);

    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_trolley_goods;
    }

    @Override
    public void convert(final BaseViewHolder holder, final GoodsInfo2 goodsInfo, int position) {
        ImageView goodsPic = holder.getView(R.id.goods_pic_im);
        final ImageView imageView = holder.getView(R.id.check_im);
        GlideUtils.showMediumPic(mContext, goodsPic, goodsInfo.getPhoto().contains("http://") ? goodsInfo.getPhoto() : "http://www.wbx365.com" + goodsInfo.getPhoto());
        holder.setText(R.id.goods_name_tv, TextUtils.isEmpty(goodsInfo.getProduct_name()) ? goodsInfo.getTitle() : goodsInfo.getProduct_name())
                .setText(R.id.need_price_tv, String.format("¥%.2f", goodsInfo.getPrice() / 100.00))
                .setText(R.id.goods_num_tv, "" + goodsInfo.getNum());
        TextView tvSpec = holder.getView(R.id.tv_spec);
        if (!TextUtils.isEmpty(goodsInfo.getAttr_name()) || !TextUtils.isEmpty(goodsInfo.getNature_name())) {
            tvSpec.setVisibility(View.VISIBLE);
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
            tvSpec.setText(sb.toString());
        } else {
            tvSpec.setVisibility(View.GONE);
        }
        if (goodsInfo.getSales_promotion_is() == 1) {
            holder.getView(R.id.is_sales_pro_im).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.is_sales_pro_im).setVisibility(View.GONE);
        }
        if (goodsInfo.getIs_attr() == 1) {
            holder.getView(R.id.is_spec_im).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.is_spec_im).setVisibility(View.GONE);
        }
        imageView.setImageResource(goodsInfo.getSelected() == 1 ? R.drawable.selected : R.drawable.uncheck);
//        holder.getView(R.id.select_layout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                goodsInfo.setSelected(goodsInfo.getSelected()==1?0:1);
//                if(goodsInfo.getSelected()==1){
//                    checkCount++;
//                    imageView.setImageResource(R.drawable.selected);
//                }else{
//                    checkCount--;
//                    imageView.setImageResource(R.drawable.uncheck);
//                }
//                if(checkCount==getItemCount())mCheckListener.isCheckAll();else mCheckListener.unCheckAll();
//            }
//        });
    }


}
