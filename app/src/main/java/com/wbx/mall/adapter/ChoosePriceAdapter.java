package com.wbx.mall.adapter;

import android.content.Context;
import android.view.View;

import com.wbx.mall.R;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseViewHolder;
import com.wbx.mall.bean.PaymentInfo;

import java.util.List;

/**
 * Created by wushenghui on 2017/9/6.
 */

public class ChoosePriceAdapter extends BaseAdapter<PaymentInfo,Context> {
    private int selectItem = 0;

    public ChoosePriceAdapter(List<PaymentInfo> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_choose_price;
    }

    @Override
    public void convert(BaseViewHolder holder, PaymentInfo paymentInfo, int position) {
        View view = holder.getView(R.id.choose_bg_rl);
        View viewIm = holder.getView(R.id.select_im);
        if(position==selectItem){
            view.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.side_line_app_color));
            viewIm.setVisibility(View.VISIBLE);
        }else {
            view.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.side_line_gray_color));
            viewIm.setVisibility(View.GONE);
        }

    }
    public  void setSelectItem(int position){
     this.selectItem = position;
        notifyDataSetChanged();
    }
}
