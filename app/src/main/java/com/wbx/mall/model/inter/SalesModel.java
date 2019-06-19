package com.wbx.mall.model.inter;

import com.wbx.mall.api.OnNetListener;

public interface SalesModel {
    void getSales(String login_token, OnNetListener onNetListener);
}
