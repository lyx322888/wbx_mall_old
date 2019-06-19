package com.wbx.mall.model.inter;

import com.wbx.mall.api.OnNetListener;

public interface HomeShufflingModel {
    void getHomeShuffling(String login_token,int city_id,OnNetListener onNetListener);
}
