package com.wbx.mall.model.inter;

import com.wbx.mall.api.OnNetListener;

import java.util.HashMap;

public interface GoodsFreeModel {
    void getBuygreen(String shop_id,String cate_id,String login_token,OnNetListener onNetListener1);

    void getShop(String shop_id,String login_token,OnNetListener onNetListener2);
}
