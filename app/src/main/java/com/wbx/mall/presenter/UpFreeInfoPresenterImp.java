package com.wbx.mall.presenter;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.NewFreeInfoBean2;
import com.wbx.mall.model.UpFreeInfoModelImp;
import com.wbx.mall.presenter.inter.UpFreeInfoPresenter;
import com.wbx.mall.view.UpFreeInfoView;

public class UpFreeInfoPresenterImp implements UpFreeInfoPresenter {
    UpFreeInfoView upFreeInfoView;
    UpFreeInfoModelImp upFreeInfoModelImp;

    public UpFreeInfoPresenterImp(UpFreeInfoView upFreeInfoView) {
        this.upFreeInfoView = upFreeInfoView;
        upFreeInfoModelImp=new UpFreeInfoModelImp();
    }

    @Override
    public void getUpFreeInfo() {
        upFreeInfoModelImp.getUpFreeInfo(new OnNetListener() {
            @Override
            public void onSuccess(Object o) {
                upFreeInfoView.getUpFreeInfo((NewFreeInfoBean2) o);
            }
        });
    }
}
