package com.wbx.mall.module.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.SplitRecordBean;
import com.wbx.mall.utils.DateUtil;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.ToastUitl;

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
        holder.setText(R.id.tv_type, bean.getSubpackage_type() + "(" + bean.getSubpackage_num() + "套)");
        GlideUtils.showSmallPic(mContext, (ImageView) holder.getView(R.id.iv_head), bean.getFace());
        holder.setText(R.id.tv_time, "分包日期：" + DateUtil.formatDateAndTime2(bean.getSubpackage_time()));
        View view =holder.getView(R.id.iv_phone);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(bean.getPhone())) {
                    ToastUitl.showShort("抱歉，暂未获取到联系方式");
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bean.getPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }
}
