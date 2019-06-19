package com.wbx.mall.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

/**
 * Created by wushenghui on 2017/7/20.
 */

public class CollectionShopAdapter extends BaseAdapter<ShopInfo2, Context> {

    public CollectionShopAdapter(List<ShopInfo2> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_collection_store;
    }

    @Override
    public void convert(BaseViewHolder holder, ShopInfo2 shopInfo, int position) {
        holder.setText(R.id.collection_store_name_tv, shopInfo.getShop_name());
        holder.setText(R.id.collection_delivery_tv, "配送范围:" + shopInfo.getPeisong_fanwei());
        ImageView shopPicIm = holder.getView(R.id.collection_store_pic_im);
        GlideUtils.showMediumPic(mContext, shopPicIm, shopInfo.getPhoto());
    }
}
