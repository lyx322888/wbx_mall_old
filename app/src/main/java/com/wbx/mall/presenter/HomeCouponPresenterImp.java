package com.wbx.mall.presenter;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.HomeCouponBean;
import com.wbx.mall.model.HomeCouponModelImp;
import com.wbx.mall.presenter.inter.HomeCouponPresenter;
import com.wbx.mall.view.HomeCouponView;

public class HomeCouponPresenterImp implements HomeCouponPresenter {
    HomeCouponView homeCouponView;
    HomeCouponModelImp modelImp;

    public HomeCouponPresenterImp(HomeCouponView homeCouponView) {
        this.homeCouponView = homeCouponView;
        modelImp = new HomeCouponModelImp();
    }

    @Override
    public void getHomeCoupon(int city_id) {
        modelImp.getHomeCoupon(city_id, new OnNetListener() {
            @Override
            public void onSuccess(Object o) {
                homeCouponView.getHomeCoupon((HomeCouponBean) o);
            }
        });
    }
}
