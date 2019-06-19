package com.wbx.mall.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.VisitShopBean;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

public class VisitShopAdapter extends BaseAdapter<VisitShopBean.DataBean, Context> {

    public VisitShopAdapter(List<VisitShopBean.DataBean> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_visit_shop;
    }

    @Override
    public void convert(BaseViewHolder holder, VisitShopBean.DataBean dataBean, int position) {
        GlideUtils.showSmallPic(mContext, (ImageView) holder.getView(R.id.shop_photo), dataBean.getPhoto());
        TextView tvShopName = holder.getView(R.id.shop_name);
        tvShopName.setText(dataBean.getShop_name());
        TextView tvDistance = holder.getView(R.id.distance_tv);
        tvDistance.setText(dataBean.getDistance());
    }
}
