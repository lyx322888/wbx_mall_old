package com.wbx.mall.model;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.NewFreeInfoBean2;
import com.wbx.mall.model.inter.UpFreeInfoModel;
import com.wbx.mall.utils.RetrofitUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UpFreeInfoModelImp implements UpFreeInfoModel {

    @Override
    public void getUpFreeInfo(final OnNetListener onNetListener) {
        RetrofitUtils.getInstance().server().getAllFreeInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewFreeInfoBean2>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewFreeInfoBean2 newFreeInfoBean) {
                        if (newFreeInfoBean!=null){
                            onNetListener.onSuccess(newFreeInfoBean);
                        }
                    }
                });
    }
}
