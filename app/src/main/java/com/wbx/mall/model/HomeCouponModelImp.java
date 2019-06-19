package com.wbx.mall.model;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.HomeCouponBean;
import com.wbx.mall.model.inter.HomeCouponModel;
import com.wbx.mall.utils.RetrofitUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeCouponModelImp implements HomeCouponModel {

    @Override
    public void getHomeCoupon(int city_id, final OnNetListener onNetListener) {
        Map<String, String> map = new HashMap<>();
        map.put("city_id", city_id + "");
        RetrofitUtils.getInstance().server().getIndexCoupon(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeCouponBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HomeCouponBean homeCouponBean) {
                        if (homeCouponBean!=null){
                            onNetListener.onSuccess(homeCouponBean);
                        }

                    }
                });
    }
}
