package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/11/1.
 */

public class FullInfo implements Serializable {
    private int shop_id;
    private int reduce_money;
    private int id;
    private int is_delete;
    private int full_money;

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getReduce_money() {
        return reduce_money;
    }

    public void setReduce_money(int reduce_money) {
        this.reduce_money = reduce_money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public int getFull_money() {
        return full_money;
    }

    public void setFull_money(int full_money) {
        this.full_money = full_money;
    }
}
