package com.wbx.mall.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.BuygreensGoodsBean;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

public class GoodsFreeAdapter extends BaseQuickAdapter<BuygreensGoodsBean.DataBean.SeckillGoodsBean, BaseViewHolder> {
    private boolean isShopMemberUser;
//    private AddSkWidget.OnAddClick onAddClick;

    public GoodsFreeAdapter(int layoutResId, @Nullable List<BuygreensGoodsBean.DataBean.SeckillGoodsBean> data) {
        super(layoutResId, data);
//        this.onAddClick = onAddClick;
    }

    @Override
    protected void convert(BaseViewHolder helper, BuygreensGoodsBean.DataBean.SeckillGoodsBean item) {
            GlideUtils.showMediumPic(mContext, (ImageView) helper.getView(R.id.goods_pic_im), item.getPhoto().contains("http://") ? item.getPhoto().contains("wbx365888") ? item.getPhoto() + "?imageView2/1/w/200/h/200" : item.getPhoto() : "http://www.wbx365.com" + item.getPhoto());
            TextView tv_price = helper.getView(R.id.tv_price);
            tv_price.setText("¥:"+item.getPrice() / 100 + "");
            TextView tv_title = helper.getView(R.id.tv_title);
            tv_title.setText(item.getProduct_name());
//        AddSkWidget addWidget = helper.getView(R.id.addwidget);
//        addWidget.setData(onAddClick,item);
    }
}
