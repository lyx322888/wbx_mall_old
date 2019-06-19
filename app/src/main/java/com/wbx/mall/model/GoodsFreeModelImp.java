package com.wbx.mall.model;

import android.util.Log;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.bean.BuygreensGoodsBean;
import com.wbx.mall.bean.ListgoodsBean;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.bean.ShopGoodsBean;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.bean.ShopInfo3;
import com.wbx.mall.model.inter.GoodsFreeModel;
import com.wbx.mall.utils.RetrofitUtils;

import java.util.HashMap;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoodsFreeModelImp implements GoodsFreeModel {
    @Override
    public void getBuygreen(String shop_id, String cate_id, String login_token, final OnNetListener onNetListener) {

        RetrofitUtils.getInstance().server().getVegetable(shop_id, cate_id, login_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BuygreensGoodsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BuygreensGoodsBean buygreensGoodsBean) {
                        if (buygreensGoodsBean.getData().getSeckill_goods() != null&&buygreensGoodsBean.getData().getShop_detail().getGrade_id()==AppConfig.StoreGrade.MARKET) {
                            onNetListener.onSuccess(buygreensGoodsBean);
                        }

                    }
                });
    }

    @Override
    public void getShop(final String shop_id, String login_token, final OnNetListener onNetListener) {
        RetrofitUtils.getInstance().server().getShop(shop_id, login_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShopGoodsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ShopGoodsBean shopGoodsBean) {
                        if (shopGoodsBean.getState()==1&&shopGoodsBean.getData().getDetail().getGrade_id()!=AppConfig.StoreGrade.MARKET) {
                            onNetListener.onSuccess(shopGoodsBean);
                        }

                    }
                });
    }
}
