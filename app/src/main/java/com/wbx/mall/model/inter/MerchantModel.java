package com.wbx.mall.model.inter;

import com.wbx.mall.api.OnNetListener;

public interface MerchantModel {
    void getMerchant(String login_token, OnNetListener onNetListener);
}
