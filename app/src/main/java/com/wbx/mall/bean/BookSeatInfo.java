package com.wbx.mall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wushenghui on 2017/12/22.
 */

public class BookSeatInfo implements Serializable {

    /**
     * reserve_table_id : 2
     * name : 周杰222
     * mobile : 13328320893
     * number : 5
     * subscribe_money : 0
     * note : 不要辣
     * type : 1
     * shop_id : 1388
     * user_id : 2855
     * order_id : 0
     * create_time : 1513749284
     * reserve_time : 1513664380
     * status : 0
     */

    private int reserve_table_id;
    private String name;
    private String mobile;
    private int number;
    private int subscribe_money;
    private String note;
    private int type;
    private int shop_id;
    private int user_id;
    private int order_id;
    private int create_time;
    private int reserve_time;
    private String table_number;
    private int status;
    private String shop_name;
    private String photo;
    private int full_money_reduce;
    private int coupon_money;
    private List<GoodsInfo2> order_goods;
    private String tel;

    public String getTable_number() {
        return table_number;
    }

    public void setTable_number(String table_number) {
        this.table_number = table_number;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<GoodsInfo2> getOrder_goods() {
        return order_goods;
    }

    public void setOrder_goods(List<GoodsInfo2> order_goods) {
        this.order_goods = order_goods;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getFull_money_reduce() {
        return full_money_reduce;
    }

    public void setFull_money_reduce(int full_money_reduce) {
        this.full_money_reduce = full_money_reduce;
    }

    public int getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(int coupon_money) {
        this.coupon_money = coupon_money;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getReserve_table_id() {
        return reserve_table_id;
    }

    public void setReserve_table_id(int reserve_table_id) {
        this.reserve_table_id = reserve_table_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSubscribe_money() {
        return subscribe_money;
    }

    public void setSubscribe_money(int subscribe_money) {
        this.subscribe_money = subscribe_money;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(int reserve_time) {
        this.reserve_time = reserve_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
