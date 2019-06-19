package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/7/27.
 */

public class SpecInfo implements Serializable {
    private int price;
    private int mall_price;
    private String goods_id;
    private String attr_id;
    private int num;
    private String type;
    private int sales_promotion_price;
    private String attr_name;
    private int is_seckill;
    private int sales_promotion_is;
    private int limitations_num;
    private int inventory_num;
    private int casing_price;
    private int seckill_price;
    private int is_shop_member;
    private int shop_member_price;

    public int getSales_promotion_is() {
        return sales_promotion_is;
    }

    public void setSales_promotion_is(int sales_promotion_is) {
        this.sales_promotion_is = sales_promotion_is;
    }

    public int getInventory_num() {
        return inventory_num;
    }

    public void setInventory_num(int inventory_num) {
        this.inventory_num = inventory_num;
    }

    public int getIs_shop_member() {
        return is_shop_member;
    }

    public void setIs_shop_member(int is_shop_member) {
        this.is_shop_member = is_shop_member;
    }

    public int getShop_member_price() {
        return shop_member_price;
    }

    public void setShop_member_price(int shop_member_price) {
        this.shop_member_price = shop_member_price;
    }

    public int getSeckill_price() {
        return seckill_price;
    }

    public void setSeckill_price(int seckill_price) {
        this.seckill_price = seckill_price;
    }

    public int getIs_seckill() {
        return is_seckill;
    }

    public void setIs_seckill(int is_seckill) {
        this.is_seckill = is_seckill;
    }

    public int getLimitations_num() {
        return limitations_num;
    }

    public void setLimitations_num(int limitations_num) {
        this.limitations_num = limitations_num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMall_price() {
        return mall_price;
    }

    public void setMall_price(int mall_price) {
        this.mall_price = mall_price;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSales_promotion_price() {
        return sales_promotion_price;
    }

    public void setSales_promotion_price(int sales_promotion_price) {
        this.sales_promotion_price = sales_promotion_price;
    }

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public int getCasing_price() {
        return casing_price;
    }

    public void setCasing_price(int casing_price) {
        this.casing_price = casing_price;
    }
}
