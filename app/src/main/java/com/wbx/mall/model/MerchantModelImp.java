package com.wbx.mall.model;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.ListShopDataBean;
import com.wbx.mall.model.inter.MerchantModel;
import com.wbx.mall.utils.RetrofitUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MerchantModelImp implements MerchantModel {
    @Override
    public void getMerchant(String login_token, final OnNetListener onNetListener) {
        RetrofitUtils.getInstance().server().getMerchant(login_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListShopDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ListShopDataBean listShopDataBean) {
                        if (listShopDataBean.getData() != null) {
                            onNetListener.onSuccess(listShopDataBean);
                        }

                    }
                });
    }
}
