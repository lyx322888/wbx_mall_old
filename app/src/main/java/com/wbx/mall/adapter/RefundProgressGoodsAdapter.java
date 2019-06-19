package com.wbx.mall.adapter;


import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.RefundProgressBean;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

public class RefundProgressGoodsAdapter extends BaseQuickAdapter<RefundProgressBean.GoodsBean, BaseViewHolder> {
    private Context mcontext;

    public RefundProgressGoodsAdapter(Context context, @Nullable List<RefundProgressBean.GoodsBean> data) {
        super(R.layout.item_refund_progress_goods, data);
        mcontext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundProgressBean.GoodsBean data) {
        ImageView ivGoods = helper.getView(R.id.iv_goods);
        GlideUtils.showMediumPic(mcontext, ivGoods, data.getPhoto());
        helper.setText(R.id.tv_name, data.getTitle() + (TextUtils.isEmpty(data.getAttr_name()) ? "" : ("(" + data.getAttr_name() + ")")))
                .setText(R.id.tv_price, String.format("¥%.2f", data.getTotal_price() / data.getNum() / 100.00))
                .setText(R.id.tv_num, String.valueOf(data.getNum()));
    }
}
