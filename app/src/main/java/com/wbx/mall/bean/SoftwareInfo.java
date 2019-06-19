package com.wbx.mall.bean;

import java.util.List;

public class SoftwareInfo {

    /**
     * msg : 成功
     * state : 1
     * data : {"salesman_data":[{"software_num":10,"all_shop_money":5000,"software_type":480},{"software_num":20,"all_shop_money":0,"software_type":2080}],"shops":[{"shop_id":1481,"grade_id":20,"shop_name":"杨小姐的店","photo":"http://imgs.wbx365.com/FiMvLDgGiMPMWkeTiV11X04v5Nex","create_time":1542004859,"user_id":13664,"shop_grade":6,"receipts":5000,"mobile":"18344983321"}]}
     */

    private String msg;
    private int state;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
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
             * software_num : 10
             * all_shop_money : 5000
             * software_type : 480
             */

            private int software_num;
            private int all_shop_money;
            private int software_type;

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

            public int getSoftware_type() {
                return software_type;
            }

            public void setSoftware_type(int software_type) {
                this.software_type = software_type;
            }
        }

        public static class ShopsBean {
            /**
             * shop_id : 1481
             * grade_id : 20
             * shop_name : 杨小姐的店
             * photo : http://imgs.wbx365.com/FiMvLDgGiMPMWkeTiV11X04v5Nex
             * create_time : 1542004859
             * user_id : 13664
             * shop_grade : 6
             * receipts : 5000
             * mobile : 18344983321
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
}
