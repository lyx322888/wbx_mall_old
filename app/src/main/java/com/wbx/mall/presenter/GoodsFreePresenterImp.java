package com.wbx.mall.presenter;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.BuygreensGoodsBean;
import com.wbx.mall.bean.ListgoodsBean;
import com.wbx.mall.bean.ShopGoodsBean;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.bean.ShopInfo3;
import com.wbx.mall.model.GoodsFreeModelImp;
import com.wbx.mall.presenter.inter.GoodsFreePresenter;
import com.wbx.mall.view.GoodsFreeView;

import java.util.List;

public class GoodsFreePresenterImp implements GoodsFreePresenter {
    GoodsFreeView goodsFreeView;
    GoodsFreeModelImp modelImp;

    public GoodsFreePresenterImp(GoodsFreeView goodsFreeView) {
        this.goodsFreeView = goodsFreeView;
        modelImp = new GoodsFreeModelImp();
    }

    @Override
    public void getBuygreen(String shop_id,String cate_id,String login_token) {
        modelImp.getBuygreen(shop_id, cate_id, login_token,new OnNetListener() {
            @Override
            public void onSuccess(Object o) {
                goodsFreeView.getBuygreen((BuygreensGoodsBean) o);
            }
        });
    }

    @Override
    public void getShop( String shop_id,String login_token) {
        modelImp.getShop( shop_id,login_token, new OnNetListener() {
            @Override
            public void onSuccess(Object o) {
                goodsFreeView.getShop((ShopGoodsBean) o);
            }
        });
    }
}
