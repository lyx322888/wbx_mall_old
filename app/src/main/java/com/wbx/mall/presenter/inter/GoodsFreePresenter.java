package com.wbx.mall.presenter.inter;

import java.util.HashMap;

public interface GoodsFreePresenter {
    void getBuygreen(String login_token,String cate_id,String shop_id);
    void getShop(String login_token,String shop_id);
}
