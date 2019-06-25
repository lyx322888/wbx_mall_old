package com.wbx.mall.adapter;

import android.content.Context;
import android.content.res.TypedArray;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.PaymentInfo;

import java.util.List;

/**
 * Created by wushenghui on 2017/5/31.
 */

public class PayWayAdapter extends BaseAdapter<PaymentInfo,Context> {
    public PayWayAdapter(List<PaymentInfo> dataList, Context context) {
        super(dataList, context);
    }
    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_pay_way;
    }

    @Override
    public void convert(BaseViewHolder holder, PaymentInfo paymentBean, int position) {
        TypedArray ar = mContext.getResources().obtainTypedArray(R.array.pay_way_array);

        holder.setText(R.id.pay_mode_name_tv,paymentBean.getName()).setText(R.id.pay_mode_step_tv,paymentBean.getContents()).setImageResource(R.id.pay_mode_im, ar.getResourceId(position, 0)).setCheck(R.id.pay_mode_cb,paymentBean.isChecked());
    }
}
