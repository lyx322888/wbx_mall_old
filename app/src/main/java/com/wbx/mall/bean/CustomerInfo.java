package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/10/23.
 */

public class CustomerInfo implements Serializable {

    private String face;
    private String nickname;
    private int rank;
    private int shop_order_num;
    private int recommend_bonus;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getShop_order_num() {
        return shop_order_num;
    }

    public void setShop_order_num(int shop_order_num) {
        this.shop_order_num = shop_order_num;
    }

    public int getRecommend_bonus() {
        return recommend_bonus;
    }

    public void setRecommend_bonus(int recommend_bonus) {
        this.recommend_bonus = recommend_bonus;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

}
