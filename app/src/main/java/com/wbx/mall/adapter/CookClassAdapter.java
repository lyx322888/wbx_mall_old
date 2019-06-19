package com.wbx.mall.adapter;

import android.content.Context;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.CookInfo;

import java.util.List;

/**
 * Created by wushenghui on 2017/8/24.
 */

public class CookClassAdapter extends BaseAdapter<CookInfo,Context> {

    public CookClassAdapter(List<CookInfo> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_cook;
    }

    @Override
    public void convert(BaseViewHolder holder, CookInfo cookInfo, int position) {
             holder.setText(R.id.class_name_tv,cookInfo.getName());
    }
}
