package com.wbx.mall.model.inter;

import com.wbx.mall.api.OnNetListener;

public interface HomeCouponModel {
    void getHomeCoupon(int city_id, OnNetListener onNetListener);
}
