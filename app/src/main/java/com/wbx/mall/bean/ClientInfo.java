package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/7/20.
 */

public class ClientInfo implements Serializable{
    /**
     * user_id : 2448
     * money : 540
     * account : 13459234796
     * reg_time : 1492916492
     * nickname : sdfsdf
     * shop_name : 乐果鲜
     */

    private int user_id;
    private int money;
    private String account;
    private int reg_time;
    private String nickname;
    private String shop_name;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getReg_time() {
        return reg_time;
    }

    public void setReg_time(int reg_time) {
        this.reg_time = reg_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
}
