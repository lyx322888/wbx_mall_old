package com.wbx.mall.bean;

import java.util.List;

/**
 * @author Zero
 * @date 2018/9/21
 */
public class RefundProgressBean {

    /**
     * need_pay : 895
     * num : 1
     * goods : [{"total_price":1000,"num":1,"attr_id":0,"title":"智利车厘子","attr_name":""}]
     * status : 3
     * status_message : 商家退款中
     * order_track : [{"track_id":18,"order_id":5116,"order_type":1,"status":3,"status_message":"商家退款中","create_time":1537512784}]
     * apply_time : 1537512784
     */

    private int need_pay;
    private int num;
    private int status;
    private String status_message;
    private String pay_type;
    private String tel;
    private int apply_time;
    private int status_time;
    private List<GoodsBean> goods;
    private List<OrderTrackBean> order_track;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public int getStatus_time() {
        return status_time;
    }

    public void setStatus_time(int status_time) {
        this.status_time = status_time;
    }

    public int getNeed_pay() {
        return need_pay;
    }

    public void setNeed_pay(int need_pay) {
        this.need_pay = need_pay;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public int getApply_time() {
        return apply_time;
    }

    public void setApply_time(int apply_time) {
        this.apply_time = apply_time;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public List<OrderTrackBean> getOrder_track() {
        return order_track;
    }

    public void setOrder_track(List<OrderTrackBean> order_track) {
        this.order_track = order_track;
    }

    public static class GoodsBean {
        /**
         * total_price : 1000
         * num : 1
         * attr_id : 0
         * title : 智利车厘子
         * attr_name :
         */

        private int total_price;
        private int num;
        private int attr_id;
        private String title;
        private String attr_name;
        private String photo;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
            this.total_price = total_price;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getAttr_id() {
            return attr_id;
        }

        public void setAttr_id(int attr_id) {
            this.attr_id = attr_id;
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

    public static class OrderTrackBean {
        /**
         * track_id : 18
         * order_id : 5116
         * order_type : 1
         * status : 3
         * status_message : 商家退款中
         * create_time : 1537512784
         */

        private int track_id;
        private int order_id;
        private int order_type;
        private int status;
        private String status_message;
        private int create_time;

        public int getTrack_id() {
            return track_id;
        }

        public void setTrack_id(int track_id) {
            this.track_id = track_id;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatus_message() {
            return status_message;
        }

        public void setStatus_message(String status_message) {
            this.status_message = status_message;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }
}
