package com.wbx.mall.model;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.VisitShopBean;
import com.wbx.mall.model.inter.VisitShopModel;
import com.wbx.mall.utils.RetrofitUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VisitShopModelImp implements VisitShopModel {
    @Override
    public void getVisitShop(String login_token, int page, int num, String lat, String lng, final OnNetListener onNetListener) {
        RetrofitUtils.getInstance().server().getVisit(login_token, page, num, lat, lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VisitShopBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VisitShopBean shopBean) {
                        if (shopBean != null) {
                            onNetListener.onSuccess(shopBean);
                        }
                    }
                });
    }
}
