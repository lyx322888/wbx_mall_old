package com.wbx.mall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wushenghui on 2017/7/13.
 */

public class OrderInfo implements Serializable {
    private int order_id;
    private int num;
    private int need_pay;
    private int logistics;
    private int status;
    private boolean isPhysical;//是否是实体店
    private List<GoodsInfo2> goods;
    private int is_daofu;
    private int total_price;
    private int casing_price;
    private int user_id;
    private int shop_id;
    private AddressInfo address;
    private int express_price;//运费
    private int create_time;
    private String message;
    private float lat;//配送地址维度
    private float lng;//配送地址经度
    private String tel;
    private String shop_name;

    private int full_money_reduce;

    private int coupon_money;
    private int user_subsidy_money;
    private int red_packet_money;
    private int discounts_all_money;

    public int getDiscounts_all_money() {
        return discounts_all_money;
    }

    public void setDiscounts_all_money(int discounts_all_money) {
        this.discounts_all_money = discounts_all_money;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getUser_subsidy_money() {
        return user_subsidy_money;
    }

    public void setUser_subsidy_money(int user_subsidy_money) {
        this.user_subsidy_money = user_subsidy_money;
    }

    public int getRed_packet_money() {
        return red_packet_money;
    }

    public void setRed_packet_money(int red_packet_money) {
        this.red_packet_money = red_packet_money;
    }

    public int getCasing_price() {
        return casing_price;
    }

    public void setCasing_price(int casing_price) {
        this.casing_price = casing_price;
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

    public double getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getExpress_price() {
        return express_price;
    }

    public void setExpress_price(int express_price) {
        this.express_price = express_price;
    }

    public AddressInfo getAddress() {
        return address;
    }

    public void setAddress(AddressInfo address) {
        this.address = address;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getIs_daofu() {
        return is_daofu;
    }

    public void setIs_daofu(int is_daofu) {
        this.is_daofu = is_daofu;
    }

    public boolean isPhysical() {
        return isPhysical;
    }

    public void setPhysical(boolean physical) {
        isPhysical = physical;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNeed_pay() {
        return need_pay;
    }

    public void setNeed_pay(int need_pay) {
        this.need_pay = need_pay;
    }

    public int getLogistics() {
        return logistics;
    }

    public void setLogistics(int logistics) {
        this.logistics = logistics;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<GoodsInfo2> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsInfo2> goods) {
        this.goods = goods;
    }
}
