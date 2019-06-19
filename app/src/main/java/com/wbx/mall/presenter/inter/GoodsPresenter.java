package com.wbx.mall.presenter.inter;

public interface GoodsPresenter {
    void getGoods(String shop_id, String login_token, int page, int num,int cate_id);
}
