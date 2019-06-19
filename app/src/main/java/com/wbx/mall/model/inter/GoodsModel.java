package com.wbx.mall.model.inter;

import com.wbx.mall.api.OnNetListener;

public interface GoodsModel {
    void getGoods(String shop_id, String login_token, int page, int num,int cate_id, OnNetListener onNetListener);
}
