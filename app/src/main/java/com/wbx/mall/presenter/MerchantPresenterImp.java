package com.wbx.mall.presenter;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.ListShopDataBean;
import com.wbx.mall.model.MerchantModelImp;
import com.wbx.mall.presenter.inter.MerchantPresenter;
import com.wbx.mall.view.MerchantView;

public class MerchantPresenterImp implements MerchantPresenter {
    MerchantView merchantView;
    MerchantModelImp merchantModelImp;

    public MerchantPresenterImp(MerchantView merchantView) {
        this.merchantView = merchantView;
        merchantModelImp = new MerchantModelImp();
    }

    @Override
    public void getMerchant(String login_token) {
        merchantModelImp.getMerchant(login_token, new OnNetListener() {
            @Override
            public void onSuccess(Object o) {
                merchantView.getMerchant((ListShopDataBean) o);
            }
        });
    }
}
