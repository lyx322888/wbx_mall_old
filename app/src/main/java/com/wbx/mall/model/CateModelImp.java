package com.wbx.mall.model;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.CateInfo2;
import com.wbx.mall.model.inter.CateModel;
import com.wbx.mall.utils.RetrofitUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CateModelImp implements CateModel {
    @Override
    public void getCate(String login_token, String shop_id, final OnNetListener onNetListener) {
        RetrofitUtils.getInstance().server().getCate(login_token, shop_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CateInfo2>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CateInfo2 cateInfo2) {
                        if (cateInfo2 != null) {
                            onNetListener.onSuccess(cateInfo2);
                        }
                    }
                });
    }
}
