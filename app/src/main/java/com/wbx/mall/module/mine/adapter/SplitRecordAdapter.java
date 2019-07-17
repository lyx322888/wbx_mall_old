package com.wbx.mall.module.mine.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.SplitRecordBean;
import com.wbx.mall.utils.DateUtil;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

/**
 * 分包记录
 */

public class SplitRecordAdapter extends BaseAdapter<SplitRecordBean.SplitBean, Context> {

    public SplitRecordAdapter(List<SplitRecordBean.SplitBean> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_split_record;
    }

    @Override
    public void convert(BaseViewHolder holder, final SplitRecordBean.SplitBean bean, final int position) {
        holder.setText(R.id.tv_phone, "分包账号：" + bean.getPhone());
        holder.setText(R.id.tv_type, bean.getSubpackage_type() + "(" + bean.getSubpackage_num() + ")");
        GlideUtils.showSmallPic(mContext, (ImageView) holder.getView(R.id.iv_head), bean.getFace());
        holder.setText(R.id.tv_time, "分包日期：" + DateUtil.formatDateAndTime2(bean.getSubpackage_time()));
    }
}
