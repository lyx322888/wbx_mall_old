package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2018/1/26.
 */

public class FindInfo implements Serializable {

    /**
     * title : 元本家蜜汁鸡腿
     * goods_id : 55537
     * shop_id : 1414
     * photo : http://imgs.wbx365.com/Fqf1I7O4iVKn0ZjShV4CJ5o8_0cC
     */

    private String title;
    private int goods_id;
    private int shop_id;
    private String photo;
    private int price;
    private ShopInfo2 shop;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ShopInfo2 getShop() {
        return shop;
    }

    public void setShop(ShopInfo2 shop) {
        this.shop = shop;
    }
}
