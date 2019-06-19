package com.wbx.mall.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

/**
 * Created by wushenghui on 2017/7/18.
 */

public class ItemShopGoodsAdapter extends BaseAdapter<GoodsInfo2, Context> {

    public ItemShopGoodsAdapter(List<GoodsInfo2> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_shop_goods_layout;
    }

    @Override
    public void convert(BaseViewHolder holder, GoodsInfo2 goodsInfo, int position) {
        ImageView view = holder.getView(R.id.goods_pic_im);
        GlideUtils.showMediumPic(mContext, view, goodsInfo.getPhoto());
        holder.setText(R.id.goods_name_tv, goodsInfo.getTitle()).setText(R.id.goods_price_tv, String.format("¥%.2f", goodsInfo.getSales_promotion_price() == 0 ? goodsInfo.getPrice() / 100.00 : goodsInfo.getSales_promotion_price() / 100.00));
    }
}
