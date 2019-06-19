package com.wbx.mall.model;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.SalesDetailBean;
import com.wbx.mall.model.inter.SalesModel;
import com.wbx.mall.utils.RetrofitUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SalesModelImp implements SalesModel {
    @Override
    public void getSales(String login_token, final OnNetListener onNetListener) {
        RetrofitUtils.getInstance().server().getSales(login_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SalesDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SalesDetailBean salesDetailBean) {
                        if (salesDetailBean.getData()!=null){
                            onNetListener.onSuccess(salesDetailBean);
                        }
                    }
                });
    }
}
