package com.wbx.mall.presenter;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.model.GoodsModelImp;
import com.wbx.mall.presenter.inter.GoodsPresenter;
import com.wbx.mall.view.GoodsView;

public class GoodsPresenterImp implements GoodsPresenter {
    GoodsView goodsView;
    GoodsModelImp goodsModelImp;

    public GoodsPresenterImp(GoodsView goodsView) {
        this.goodsView = goodsView;
        goodsModelImp=new GoodsModelImp();
    }

    @Override
    public void getGoods(String shop_id, String login_token, int page, int num,int cate_id) {
        goodsModelImp.getGoods(shop_id, login_token, page, num,cate_id,new OnNetListener() {
            @Override
            public void onSuccess(Object o) {
                goodsView.getGoods((GoodsInfo2) o);
            }
        });
    }
}
