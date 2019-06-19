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
 * Created by wushenghui on 2017/7/20.
 */

public class CollectGoodsAdapter extends BaseAdapter<GoodsInfo2, Context> {

    public CollectGoodsAdapter(List<GoodsInfo2> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_collection_goods;
    }

    @Override
    public void convert(BaseViewHolder holder, GoodsInfo2 goodsInfo, int position) {
        holder.setText(R.id.collect_goods_name_tv, goodsInfo.getTitle()).setText(R.id.collect_goods_mall_price_tv, "¥" + (goodsInfo.getMall_price() / 100.00)).setText(R.id.collect_goods_price_tv, "¥" + (goodsInfo.getPrice() / 100.00));
        ImageView view = holder.getView(R.id.goods_im);
        GlideUtils.showMediumPic(mContext, view, goodsInfo.getPhoto());
    }
}
