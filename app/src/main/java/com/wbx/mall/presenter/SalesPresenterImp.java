package com.wbx.mall.presenter;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.SalesDetailBean;
import com.wbx.mall.model.SalesModelImp;
import com.wbx.mall.presenter.inter.SalesPresenter;
import com.wbx.mall.view.SalesView;

public class SalesPresenterImp implements SalesPresenter {
    SalesView salesView;
    SalesModelImp salesModelImp;

    public SalesPresenterImp(SalesView salesView) {
        this.salesView = salesView;
        salesModelImp = new SalesModelImp();
    }

    @Override
    public void getSales(String login_token) {
        salesModelImp.getSales(login_token, new OnNetListener() {
            @Override
            public void onSuccess(Object o) {
                salesView.getSales((SalesDetailBean) o);
            }
        });
    }
}
