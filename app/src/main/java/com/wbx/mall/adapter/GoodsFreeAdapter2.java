package com.wbx.mall.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.ShopGoodsBean;
import com.wbx.mall.utils.GlideUtils;

import java.util.List;

public class GoodsFreeAdapter2 extends BaseQuickAdapter<ShopGoodsBean.DataBean.SeckillGoodsBean, BaseViewHolder> {


    public GoodsFreeAdapter2(int layoutResId, @Nullable List<ShopGoodsBean.DataBean.SeckillGoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopGoodsBean.DataBean.SeckillGoodsBean item) {

            GlideUtils.showMediumPic(mContext, (ImageView) helper.getView(R.id.goods_pic_im), item.getPhoto().contains("http://") ? item.getPhoto().contains("wbx365888") ? item.getPhoto() + "?imageView2/1/w/200/h/200" : item.getPhoto() : "http://www.wbx365.com" + item.getPhoto());
            TextView tv_price = helper.getView(R.id.tv_price);
            tv_price.setText("¥:"+item.getPrice() / 100 + "");
            TextView tv_title = helper.getView(R.id.tv_title);
            tv_title.setText(item.getTitle());


    }
}
