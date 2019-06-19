package com.wbx.mall.bean;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class IntelligentPayBean {
    /**
     * order_id : 12
     * shop_id : 3
     * create_time : 1520842007
     * money : 100
     * pay_type : money
     * shop_name : BBmale 妈乐旗舰店
     */

    private String order_id;
    private String shop_id;
    private int create_time;
    private int money;
    private String pay_type;
    private String shop_name;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
}
