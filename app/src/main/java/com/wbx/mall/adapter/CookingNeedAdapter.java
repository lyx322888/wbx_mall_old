package com.wbx.mall.adapter;

import android.content.Context;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.CookBookDetailInfo;

import java.util.List;

/**
 * Created by wushenghui on 2017/8/25.
 */

public class CookingNeedAdapter extends BaseAdapter<CookBookDetailInfo.MaterialBean,Context> {

    public CookingNeedAdapter(List<CookBookDetailInfo.MaterialBean> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_cooking_need;
    }

    @Override
    public void convert(BaseViewHolder holder, CookBookDetailInfo.MaterialBean cookBookDetailInfo, int position) {
       holder.setText(R.id.name_tv,cookBookDetailInfo.getMname()).setText(R.id.amount_tv,cookBookDetailInfo.getAmount());
    }
}
