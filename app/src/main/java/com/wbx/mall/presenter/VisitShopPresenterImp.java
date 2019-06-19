package com.wbx.mall.presenter;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.VisitShopBean;
import com.wbx.mall.model.VisitShopModelImp;
import com.wbx.mall.presenter.inter.VisitShopPresenter;
import com.wbx.mall.view.VisitShopView;

public class VisitShopPresenterImp implements VisitShopPresenter {
    VisitShopView visitShopView;
    VisitShopModelImp visitShopModelImp;

    public VisitShopPresenterImp(VisitShopView visitShopView) {
        this.visitShopView = visitShopView;
        visitShopModelImp = new VisitShopModelImp();
    }

    @Override
    public void getVisitShop(String login_token, int page, int num, String lat, String lng) {
        visitShopModelImp.getVisitShop(login_token, page, num, lat, lng, new OnNetListener() {
            @Override
            public void onSuccess(Object o) {
                visitShopView.getVisitShop((VisitShopBean) o);
            }
        });
    }
}
