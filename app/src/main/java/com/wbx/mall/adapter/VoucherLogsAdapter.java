package com.wbx.mall.adapter;

import android.content.Context;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.VoucherLogsInfo;
import com.wbx.mall.utils.FormatUtil;

import java.util.List;

/**
 * Created by wushenghui on 2017/11/20.
 */

public class VoucherLogsAdapter extends BaseAdapter<VoucherLogsInfo,Context> {

    public VoucherLogsAdapter(List<VoucherLogsInfo> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_log;
    }

    @Override
    public void convert(BaseViewHolder holder, VoucherLogsInfo voucherLogsInfo, int position) {
       holder.setText(R.id.money_tv,String.format("卡名: %s",voucherLogsInfo.getName()))
               .setText(R.id.create_time_tv,String.format("金额: %.2f",voucherLogsInfo.getValue()/100.00))
               .setText(R.id.intro_tv,String.format("%s 至 %s", FormatUtil.stampToDate1(voucherLogsInfo.getUsed_time()),voucherLogsInfo.getEnd_date()));
    }
}
