package com.wbx.mall.model.inter;

import com.wbx.mall.api.OnNetListener;

public interface SoftwareModel {
    void getSoftware(String login_token, OnNetListener onNetListener);
}
