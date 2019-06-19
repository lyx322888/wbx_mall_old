package com.wbx.mall.adapter;

import android.content.Context;

import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.CookBookDetailInfo;

import java.util.List;

/**
 * Created by wushenghui on 2017/8/25.
 */

public class CookDetailAdapter extends BaseAdapter<CookBookDetailInfo,Context> {

    public CookDetailAdapter(List<CookBookDetailInfo> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return 0;
    }

    @Override
    public void convert(BaseViewHolder holder, CookBookDetailInfo cookBookDetailInfo, int position) {

    }
}
