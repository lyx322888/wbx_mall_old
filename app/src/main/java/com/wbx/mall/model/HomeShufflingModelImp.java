package com.wbx.mall.model;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.HomeShufflingData;
import com.wbx.mall.model.inter.HomeShufflingModel;
import com.wbx.mall.utils.RetrofitUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeShufflingModelImp implements HomeShufflingModel {
    @Override
    public void getHomeShuffling(String login_token,int city_id,final OnNetListener onNetListener) {
        RetrofitUtils.getInstance().server().getIndexCountData(login_token,city_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeShufflingData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HomeShufflingData homeShufflingData) {
                        if (homeShufflingData!=null){
                            onNetListener.onSuccess(homeShufflingData);
                        }

                    }
                });
    }
}
