package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/7/18.
 */

public class LogInfo implements Serializable {
    /**
     * money : -3080
     * intro : 余额支付196
     * create_time : 1494815474
     */

    private int money;
    private String intro;
    private int create_time;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }
}
