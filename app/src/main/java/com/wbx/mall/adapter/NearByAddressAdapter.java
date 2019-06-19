package com.wbx.mall.adapter;

import android.content.Context;

import com.amap.api.services.core.PoiItem;
import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;

import java.util.List;

/**
 * Created by wushenghui on 2018/1/3.
 */

public class NearByAddressAdapter extends BaseAdapter<PoiItem, Context> {

    public NearByAddressAdapter(List<PoiItem> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_text_view;
    }

    @Override
    public void convert(BaseViewHolder holder, PoiItem item, int position) {
        holder.setText(R.id.tv_title, item.getTitle()).setText(R.id.tv_detail, item.getProvinceName() + item.getCityName() + item.getAdName() + item.getSnippet());
    }
}
