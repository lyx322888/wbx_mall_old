package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/10/12.
 */

public class CouponInfo implements Serializable {
    private int coupon_id;
    private int money;
    private int condition_money;
    private int shop_id;
    private int create_time;
    private int is_delete;
    private int start_time;
    private int end_time;
    private int is_receive;//是否领取

    public int getIs_receive() {
        return is_receive;
    }

    public void setIs_receive(int is_receive) {
        this.is_receive = is_receive;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCondition_money() {
        return condition_money;
    }

    public void setCondition_money(int condition_money) {
        this.condition_money = condition_money;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }
}
