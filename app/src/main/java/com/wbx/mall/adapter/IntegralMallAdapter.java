package com.wbx.mall.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.IntegralGoodsBean;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

/**
 * Created by wushenghui on 2017/8/14.
 * 积分商城
 */

public class IntegralMallAdapter extends BaseAdapter<IntegralGoodsBean.GoodsBean, Context> {

    public IntegralMallAdapter(List<IntegralGoodsBean.GoodsBean> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_integral_mall;
    }

    @Override
    public void convert(BaseViewHolder holder, IntegralGoodsBean.GoodsBean goodsInfo, int position) {
        ImageView picIm = holder.getView(R.id.iv_goods);
        GlideUtils.showMediumPic(mContext, picIm, goodsInfo.getFace_pic());
        holder.setText(R.id.tv_title, goodsInfo.getTitle()).setText(R.id.tv_integral, String.valueOf(goodsInfo.getIntegral()));
    }
}
