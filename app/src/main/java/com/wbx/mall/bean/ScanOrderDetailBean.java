package com.wbx.mall.bean;

import java.util.List;

/**
 * @author Zero
 * @date 2018/5/14 0014
 */
public class ScanOrderDetailBean {

    /**
     * create_time : 2018-04-28 20:51:41
     * out_trade_no : 201804282051412417982841
     * seat : 0
     * need_price : 0.30
     * people_num : 0
     * num : 0
     * status : 已付款
     * goods : [{"goods_id":59900,"num":2,"price":"0.10","total_price":"0.20","attr_id":0,"photo":"http://www.wbx365.com/attachs/2018/03/15/5aaa24c1d33e9.jpg","title":"泡椒田鸡","attr_name":null},{"goods_id":59901,"num":1,"price":"0.10","total_price":"0.10","attr_id":0,"photo":"http://www.wbx365.com/attachs/2018/03/15/5aaa24d662939.jpg","title":"啤酒鸭","attr_name":null}]
     */

    private String shop_name;
    private String create_time;
    private String out_trade_no;
    private String seat;
    private String need_price;
    private String shop_id;
    private int grade_id;
    private int people_num;
    private int food_count;
    private int eat_type;
    private String status;
    private String note;
    private List<GoodsBean> goods;

    public int getEat_type() {
        return eat_type;
    }

    public void setEat_type(int eat_type) {
        this.eat_type = eat_type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
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

    public int getFood_count() {
        return food_count;
    }

    public void setFood_count(int food_count) {
        this.food_count = food_count;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * goods_id : 59900
         * num : 2
         * price : 0.10
         * total_price : 0.20
         * attr_id : 0
         * photo : http://www.wbx365.com/attachs/2018/03/15/5aaa24c1d33e9.jpg
         * title : 泡椒田鸡
         * attr_name : null
         */

        private String goods_id;
        private int num;
        private String price;
        private String total_price;
        private int attr_id;
        private String photo;
        private String title;
        private String attr_name;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public int getAttr_id() {
            return attr_id;
        }

        public void setAttr_id(int attr_id) {
            this.attr_id = attr_id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAttr_name() {
            return attr_name;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
        }
    }
}
