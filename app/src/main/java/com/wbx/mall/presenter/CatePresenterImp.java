package com.wbx.mall.presenter;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.CateInfo2;
import com.wbx.mall.model.CateModelImp;
import com.wbx.mall.presenter.inter.CatePresenter;
import com.wbx.mall.view.CateView;

public class CatePresenterImp implements CatePresenter {
    CateView cateView;
    CateModelImp cateModelImp;

    public CatePresenterImp(CateView cateView) {
        this.cateView = cateView;
        cateModelImp=new CateModelImp();
    }

    @Override
    public void getCate(String login_token, String shop_id) {
        cateModelImp.getCate(login_token, shop_id, new OnNetListener() {
            @Override
            public void onSuccess(Object o) {
                cateView.getCate((CateInfo2) o);
            }
        });
    }
}
