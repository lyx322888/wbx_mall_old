package com.wbx.mall.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.IntergalExchangeOrderDetailBean;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

/**
 * Created by wushenghui on 2017/8/16.
 */

public class IntegralExchangeRecordAdapter extends BaseAdapter<IntergalExchangeOrderDetailBean, Context> {

    public IntegralExchangeRecordAdapter(List<IntergalExchangeOrderDetailBean> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_record;
    }

    @Override
    public void convert(BaseViewHolder holder, IntergalExchangeOrderDetailBean intergalExchangeOrderDetailBean, int position) {
        ImageView picIm = holder.getView(R.id.iv_goods);
        holder.setText(R.id.title_name_tv, intergalExchangeOrderDetailBean.getTitle()).setText(R.id.tv_integral, String.format("%d积分", intergalExchangeOrderDetailBean.getNeed_integral()));
        GlideUtils.showMediumPic(mContext, picIm, intergalExchangeOrderDetailBean.getFace_pic());
    }
}
