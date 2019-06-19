package com.wbx.mall.bean;

import java.util.List;

public class HomeCouponBean {


    /**
     * msg : 成功
     * state : 1
     * data : [{"condition_money":1000,"money":100,"shop_id":586,"grade_id":15,"shop_name":"老猪高黑土猪肉专卖店"},{"condition_money":2000,"money":500,"shop_id":721,"grade_id":20,"shop_name":"晴天超市"},{"condition_money":1000,"money":100,"shop_id":724,"grade_id":19,"shop_name":"天天乐平价超市"}]
     */

    private String msg;
    private int state;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * condition_money : 1000
         * money : 100
         * shop_id : 586
         * grade_id : 15
         * shop_name : 老猪高黑土猪肉专卖店
         */

        private int condition_money;
        private int money;
        private String shop_id;
        private int grade_id;
        private String shop_name;
        private List<GoodsInfo2> goods;

        public List<GoodsInfo2> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsInfo2> goods) {
            this.goods = goods;
        }

        public int getCondition_money() {
            return condition_money;
        }

        public void setCondition_money(int condition_money) {
            this.condition_money = condition_money;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public int getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(int grade_id) {
            this.grade_id = grade_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }
    }
}
