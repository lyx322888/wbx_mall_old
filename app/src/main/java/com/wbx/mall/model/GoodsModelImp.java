package com.wbx.mall.model;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.model.inter.GoodsModel;
import com.wbx.mall.utils.RetrofitUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoodsModelImp implements GoodsModel {
    @Override
    public void getGoods(String shop_id, String login_token, int page, int num, int cate_id,final OnNetListener onNetListener) {
        RetrofitUtils.getInstance().server().getGoods(shop_id,login_token,page,num,cate_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GoodsInfo2>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GoodsInfo2 goodsInfo2) {
                        if (goodsInfo2!=null){
                            onNetListener.onSuccess(goodsInfo2);
                        }

                    }
                });
    }
}
