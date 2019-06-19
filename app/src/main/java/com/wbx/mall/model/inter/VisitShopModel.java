package com.wbx.mall.model.inter;

import com.wbx.mall.api.OnNetListener;

public interface VisitShopModel {
    void getVisitShop(String login_token, int page, int num, String lat, String lng, OnNetListener onNetListener);
}
