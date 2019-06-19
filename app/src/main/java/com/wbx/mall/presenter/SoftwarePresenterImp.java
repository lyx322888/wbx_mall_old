package com.wbx.mall.presenter;

import com.wbx.mall.api.OnNetListener;
import com.wbx.mall.bean.SoftwareInfo;
import com.wbx.mall.model.SoftwareModelImp;
import com.wbx.mall.presenter.inter.SoftwarePresenter;
import com.wbx.mall.view.SoftwareView;

public class SoftwarePresenterImp implements SoftwarePresenter {
    SoftwareView softwareView;
    SoftwareModelImp softwareModelImp;

    public SoftwarePresenterImp(SoftwareView softwareView) {
        this.softwareView = softwareView;
        softwareModelImp = new SoftwareModelImp();
    }

    @Override
    public void getSoftware(String login_token) {
        softwareModelImp.getSoftware(login_token, new OnNetListener() {
            @Override
            public void onSuccess(Object o) {
                softwareView.getSoftware((SoftwareInfo) o);
            }
        });
    }
}
