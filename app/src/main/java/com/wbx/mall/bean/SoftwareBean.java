package com.wbx.mall.bean;

import java.util.List;

public class SoftwareBean {

    private List<SalesmanDataBean> salesman_data;
    private List<ShopsBean> shops;

    public List<SalesmanDataBean> getSalesman_data() {
        return salesman_data;
    }

    public void setSalesman_data(List<SalesmanDataBean> salesman_data) {
        this.salesman_data = salesman_data;
    }

    public List<ShopsBean> getShops() {
        return shops;
    }

    public void setShops(List<ShopsBean> shops) {
        this.shops = shops;
    }

    public static class SalesmanDataBean {
        /**
         * software_num : 5
         * all_shop_money : 5000
         * type : 480
         */

        private int software_num;
        private int all_shop_money;
        private int type;

        public int getSoftware_num() {
            return software_num;
        }

        public void setSoftware_num(int software_num) {
            this.software_num = software_num;
        }

        public int getAll_shop_money() {
            return all_shop_money;
        }

        public void setAll_shop_money(int all_shop_money) {
            this.all_shop_money = all_shop_money;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class ShopsBean {
        /**
         * shop_id : 289
         * grade_id : 15
         * shop_name : 万友水果便利店
         * photo : http://www.wbx365.com/attachs/2017/04/15/thumb_58f1814fd7a8b.jpg
         * create_time : 1492170750
         * user_id : 1505
         * shop_grade : 6
         * receipts : 5000
         * mobile : 13809514475
         */

        private int shop_id;
        private int grade_id;
        private String shop_name;
        private String photo;
        private int create_time;
        private int user_id;
        private int shop_grade;
        private int receipts;
        private String mobile;

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

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getShop_grade() {
            return shop_grade;
        }

        public void setShop_grade(int shop_grade) {
            this.shop_grade = shop_grade;
        }

        public int getReceipts() {
            return receipts;
        }

        public void setReceipts(int receipts) {
            this.receipts = receipts;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
