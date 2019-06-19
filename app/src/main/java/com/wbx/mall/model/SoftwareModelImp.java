package com.wbx.mall.model;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.SoftwareInfo;
import com.wbx.mall.model.inter.SoftwareModel;
import com.wbx.mall.utils.RetrofitUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SoftwareModelImp implements SoftwareModel {
    @Override
    public void getSoftware(String login_token, final OnNetListener onNetListener) {
        RetrofitUtils.getInstance().server().getSoftware(login_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SoftwareInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SoftwareInfo softwareInfo) {
                        if (softwareInfo.getMsg().equals("成功")){
                            onNetListener.onSuccess(softwareInfo);
                        }
                    }
                });
    }
}
