package com.wbx.mall.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalesDetailBean {

    /**
     * msg : 成功
     * state : 1
     * data : {"salesman":{"name":"周永","480_software_num":10,"2080_software_num":20,"face":"http://imgs.wbx365.com/152608791621553222810","operation_money":199980000,"login_time":1529921076,"yesterday_sales_money":0,"yesterday_sales_num":0,"rank_name":"二星"},"shops":[{"shop_id":1481,"grade_id":20,"shop_name":"杨小姐的店","photo":"http://imgs.wbx365.com/FiMvLDgGiMPMWkeTiV11X04v5Nex","create_time":1542004859,"user_id":13664,"shop_grade":6,"mobile":"18344983321","grade_name":"通用版","money":48000}]}
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
        /**
         * salesman : {"name":"周永","480_software_num":10,"2080_software_num":20,"face":"http://imgs.wbx365.com/152608791621553222810","operation_money":199980000,"login_time":1529921076,"yesterday_sales_money":0,"yesterday_sales_num":0,"rank_name":"二星"}
         * shops : [{"shop_id":1481,"grade_id":20,"shop_name":"杨小姐的店","photo":"http://imgs.wbx365.com/FiMvLDgGiMPMWkeTiV11X04v5Nex","create_time":1542004859,"user_id":13664,"shop_grade":6,"mobile":"18344983321","grade_name":"通用版","money":48000}]
         */

        private SalesmanBean salesman;
        private List<ShopsBean> shops;

        public SalesmanBean getSalesman() {
            return salesman;
        }

        public void setSalesman(SalesmanBean salesman) {
            this.salesman = salesman;
        }

        public List<ShopsBean> getShops() {
            return shops;
        }

        public void setShops(List<ShopsBean> shops) {
            this.shops = shops;
        }

        public static class SalesmanBean {
            /**
             * name : 周永
             * 480_software_num : 10
             * 2080_software_num : 20
             * face : http://imgs.wbx365.com/152608791621553222810
             * operation_money : 199980000
             * login_time : 1529921076
             * yesterday_sales_money : 0
             * yesterday_sales_num : 0
             * rank_name : 二星
             */

            private String name;
            @SerializedName("480_software_num")
            private int _$480_software_num;
            @SerializedName("2080_software_num")
            private int _$2080_software_num;
            private String face;
            private int operation_money;
            private int login_time;
            private int yesterday_sales_money;
            private int yesterday_sales_num;
            private String rank_name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int get_$480_software_num() {
                return _$480_software_num;
            }

            public void set_$480_software_num(int _$480_software_num) {
                this._$480_software_num = _$480_software_num;
            }

            public int get_$2080_software_num() {
                return _$2080_software_num;
            }

            public void set_$2080_software_num(int _$2080_software_num) {
                this._$2080_software_num = _$2080_software_num;
            }

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }

            public int getOperation_money() {
                return operation_money;
            }

            public void setOperation_money(int operation_money) {
                this.operation_money = operation_money;
            }

            public int getLogin_time() {
                return login_time;
            }

            public void setLogin_time(int login_time) {
                this.login_time = login_time;
            }

            public int getYesterday_sales_money() {
                return yesterday_sales_money;
            }

            public void setYesterday_sales_money(int yesterday_sales_money) {
                this.yesterday_sales_money = yesterday_sales_money;
            }

            public int getYesterday_sales_num() {
                return yesterday_sales_num;
            }

            public void setYesterday_sales_num(int yesterday_sales_num) {
                this.yesterday_sales_num = yesterday_sales_num;
            }

            public String getRank_name() {
                return rank_name;
            }

            public void setRank_name(String rank_name) {
                this.rank_name = rank_name;
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
             * mobile : 18344983321
             * grade_name : 通用版
             * money : 48000
             */

            private int shop_id;
            private int grade_id;
            private String shop_name;
            private String photo;
            private int create_time;
            private int user_id;
            private int shop_grade;
            private String mobile;
            private String grade_name;
            private int money;

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

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getGrade_name() {
                return grade_name;
            }

            public void setGrade_name(String grade_name) {
                this.grade_name = grade_name;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }
        }
    }
}
