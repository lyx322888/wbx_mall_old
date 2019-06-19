package com.wbx.mall.adapter;

import android.content.Context;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.LocationInfo;

import java.util.List;

/**
 * Created by wushenghui on 2017/5/11.
 */

public class CityAdapter extends BaseAdapter<LocationInfo,Context> {
    public CityAdapter(List<LocationInfo> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_choose_city;
    }

    @Override
    public void convert(BaseViewHolder holder, LocationInfo cityInfo, int position) {
          holder.setText(R.id.city_name_tv,cityInfo.getName());
    }
}
