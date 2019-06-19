package com.wbx.mall.presenter;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.HomeShufflingData;
import com.wbx.mall.model.HomeShufflingModelImp;
import com.wbx.mall.presenter.inter.HomeShufflingPresenter;
import com.wbx.mall.view.HomeShufflingView;

public class HomeShufflingPresenterImp implements HomeShufflingPresenter {
    HomeShufflingView homeShufflingView;
    HomeShufflingModelImp homeCouponPresenterImp;

    public HomeShufflingPresenterImp(HomeShufflingView homeShufflingView) {
        this.homeShufflingView = homeShufflingView;
        homeCouponPresenterImp = new HomeShufflingModelImp();
    }

    @Override
    public void getHomeShuffling(String login_token, int city_id) {
        homeCouponPresenterImp.getHomeShuffling(login_token, city_id, new OnNetListener() {
            @Override
            public void onSuccess(Object o) {
                homeShufflingView.getHomeShuffling((HomeShufflingData) o);
            }
        });
    }
}
