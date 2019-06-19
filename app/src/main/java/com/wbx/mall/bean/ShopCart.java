package com.wbx.mall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wushenghui on 2018/1/31.
 */

public class ShopCart implements Serializable {
    private List<GoodsInfo2> goods_cart;

    /**
     * since_money : 0
     * shop_name : 阿火重庆鸡公煲
     * photo : http://imgs.wbx365.com/FufNcAHWnymNkJolXFRNmOWkPNqM
     * shop_id : 1395
     * grade_id : 20
     * logistics : 0
     * full_reduce : 0
     * coupon_money : 0
     * all_money : 5800
     * is_open : 1
     * score : 5
     */

    private int since_money;
    private String shop_name;
    private String photo;
    private int shop_id;
    private int grade_id;
    private int logistics;
    private int full_reduce;
    private int coupon_money;
    private int all_money;
    private int is_open;
    private int score;
    private boolean isCheck;
    private int num;



    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public List<GoodsInfo2> getGoods_cart() {
        return goods_cart;
    }

    public void setGoods_cart(List<GoodsInfo2> goods_cart) {
        this.goods_cart = goods_cart;
    }

    public int getSince_money() {
        return since_money;
    }

    public void setSince_money(int since_money) {
        this.since_money = since_money;
    }
    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(int grade_id) {
        this.grade_id = grade_id;
    }

    public int getLogistics() {
        return logistics;
    }

    public void setLogistics(int logistics) {
        this.logistics = logistics;
    }

    public int getFull_reduce() {
        return full_reduce;
    }

    public void setFull_reduce(int full_reduce) {
        this.full_reduce = full_reduce;
    }

    public int getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(int coupon_money) {
        this.coupon_money = coupon_money;
    }

    public int getAll_money() {
        return all_money;
    }

    public void setAll_money(int all_money) {
        this.all_money = all_money;
    }

    public int getIs_open() {
        return is_open;
    }

    public void setIs_open(int is_open) {
        this.is_open = is_open;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
