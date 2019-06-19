package com.wbx.mall.view;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.BuygreensGoodsBean;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.bean.ListgoodsBean;
import com.wbx.mall.bean.ShopGoodsBean;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.bean.ShopInfo3;

import java.util.List;

public interface GoodsFreeView {
    void getBuygreen(BuygreensGoodsBean buygreensGoodsBean);

    void getShop(ShopGoodsBean shopGoodsBean);
}
