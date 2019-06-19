package com.wbx.mall.model.inter;

import com.wbx.mall.api.OnNetListener;

public interface CateModel {
    void getCate(String login_token, String shop_id, OnNetListener onNetListener);
}
