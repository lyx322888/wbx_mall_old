package com.wbx.mall.adapter;

import android.support.annotation.Nullable;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;

import java.util.List;

/**
 * @author Zero
 * @date 2018/8/23
 */
public class SelectReceivingAddressAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {
    public SelectReceivingAddressAdapter(int layoutResId, @Nullable List<PoiItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        helper.setText(R.id.tv_address, item.getTitle()).setText(R.id.tv_detail, item.getProvinceName() + item.getCityName() + item.getAdName() + item.getSnippet());
    }
}
