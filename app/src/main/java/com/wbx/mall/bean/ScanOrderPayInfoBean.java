package com.wbx.mall.bean;

import java.util.List;

/**
 * @author Zero
 * @date 2018/5/15 0015
 */
public class ScanOrderPayInfoBean {


    /**
     * payment : [{"payment_id":4,"name":"余额支付","logo":"money.png","code":"money","mobile_logo":"money_mobile.png","contents":"余额支付是最方便快捷的"},{"payment_id":9,"name":"微信APP支付","logo":"icon_wechat_payechat_pay.png","code":"weixinapp","mobile_logo":"weixin_mobile.png","contents":"微信官方支付，安全、方便、快捷！"},{"payment_id":10,"name":"支付宝APP","logo":"alipay.png","code":"alipayapp","mobile_logo":"alipay_mobile.png","contents":"支付宝官方支付，安全、方便、快捷！"}]
     * order : {"create_time":"2018-05-15 13:45:08","out_trade_no":"2018051415274813620124353","seat":"1","need_price":"53.00","people_num":3,"status":0,"coupon_money":"0.00","full_money_reduce":"15.00","shop_id":1395,"grade_id":20,"goods":["http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e84c09b7d.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e84c09b7d.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e84c09b7d.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1fd0bb53.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg"]}
     */
    private FullMoneyReduceBean full_money_reduce;
    private CouponBean coupon;
    private OrderBean order;
    private List<PaymentInfo> payment;

    public FullMoneyReduceBean getFull_money_reduce() {
        return full_money_reduce;
    }

    public void setFull_money_reduce(FullMoneyReduceBean full_money_reduce) {
        this.full_money_reduce = full_money_reduce;
    }

    public CouponBean getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponBean coupon) {
        this.coupon = coupon;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public List<PaymentInfo> getPayment() {
        return payment;
    }

    public void setPayment(List<PaymentInfo> payment) {
        this.payment = payment;
    }

    public static class CouponBean {
        /**
         * money : 1500
         * condition_money : 15000
         */

        private int money;
        private int condition_money;

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
    }

    public static class FullMoneyReduceBean {
        /**
         * id : 60
         * full_money : 10000
         * reduce_money : 1000
         * shop_id : 1442
         * is_delete : 0
         */

        private int id;
        private int full_money;
        private int reduce_money;
        private int shop_id;
        private int is_delete;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFull_money() {
            return full_money;
        }

        public void setFull_money(int full_money) {
            this.full_money = full_money;
        }

        public int getReduce_money() {
            return reduce_money;
        }

        public void setReduce_money(int reduce_money) {
            this.reduce_money = reduce_money;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }
    }

    public static class OrderBean {
        /**
         * create_time : 2018-05-15 13:45:08
         * out_trade_no : 2018051415274813620124353
         * seat : 1
         * : 53.00
         * people_num : 3
         * status : 0
         * coupon_money : 0.00
         * full_money_reduce : 15.00
         * shop_id : 1395
         * grade_id : 20
         * goods : ["http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e84c09b7d.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e84c09b7d.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e84c09b7d.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1fd0bb53.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e6260d536.jpg","http://www.wbx365.com/attachs/2017/10/31/thumb_59f7e1357674c.jpg"]
         */

        private String create_time;
        private String out_trade_no;
        private String seat;
        private String need_price;
        private String total_price;
        private String note;
        private int people_num;
        private int status;
        private String coupon_money;
        private String full_money_reduce;
        private String casing_price;
        private String red_packet_money;
        private int shop_id;
        private int grade_id;
        private List<String> goods;

        public String getRed_packet_money() {
            return red_packet_money;
        }

        public void setRed_packet_money(String red_packet_money) {
            this.red_packet_money = red_packet_money;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getCasing_price() {
            return casing_price;
        }

        public void setCasing_price(String casing_price) {
            this.casing_price = casing_price;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getSeat() {
            return seat;
        }

        public void setSeat(String seat) {
            this.seat = seat;
        }

        public String getNeed_price() {
            return need_price;
        }

        public void setNeed_price(String need_price) {
            this.need_price = need_price;
        }

        public int getPeople_num() {
            return people_num;
        }

        public void setPeople_num(int people_num) {
            this.people_num = people_num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCoupon_money() {
            return coupon_money;
        }

        public void setCoupon_money(String coupon_money) {
            this.coupon_money = coupon_money;
        }

        public String getFull_money_reduce() {
            return full_money_reduce;
        }

        public void setFull_money_reduce(String full_money_reduce) {
            this.full_money_reduce = full_money_reduce;
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

        public List<String> getGoods() {
            return goods;
        }

        public void setGoods(List<String> goods) {
            this.goods = goods;
        }
    }
}
