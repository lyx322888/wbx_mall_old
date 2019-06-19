package com.wbx.mall.bean;

import java.util.List;

public class ListShopDataBean {


    /**
     * msg : 成功
     * state : 1
     * data : {"shops":[{"shop_id":1443,"grade_id":15,"shop_name":"果然鲜果园","photo":"http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I","view":3171,"turnover":460233,"order_people":8,"order_num":43},{"shop_id":1444,"grade_id":19,"shop_name":"厦门张旺福装饰工程有限公司","photo":"http://imgs.wbx365.com/Ft4qZAqddubSlqdOMKEYnXDz0Wqj","view":64,"turnover":0,"order_people":0,"order_num":0}],"all_order_num":43,"all_order_people":8,"all_turnover":460233}
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
         * shops : [{"shop_id":1443,"grade_id":15,"shop_name":"果然鲜果园","photo":"http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I","view":3171,"turnover":460233,"order_people":8,"order_num":43},{"shop_id":1444,"grade_id":19,"shop_name":"厦门张旺福装饰工程有限公司","photo":"http://imgs.wbx365.com/Ft4qZAqddubSlqdOMKEYnXDz0Wqj","view":64,"turnover":0,"order_people":0,"order_num":0}]
         * all_order_num : 43
         * all_order_people : 8
         * all_turnover : 460233
         */

        private int all_order_num;
        private int all_order_people;
        private int all_turnover;
        private List<ShopsBean> shops;

        public int getAll_order_num() {
            return all_order_num;
        }

        public void setAll_order_num(int all_order_num) {
            this.all_order_num = all_order_num;
        }

        public int getAll_order_people() {
            return all_order_people;
        }

        public void setAll_order_people(int all_order_people) {
            this.all_order_people = all_order_people;
        }

        public int getAll_turnover() {
            return all_turnover;
        }

        public void setAll_turnover(int all_turnover) {
            this.all_turnover = all_turnover;
        }

        public List<ShopsBean> getShops() {
            return shops;
        }

        public void setShops(List<ShopsBean> shops) {
            this.shops = shops;
        }

        public static class ShopsBean {
            /**
             * shop_id : 1443
             * grade_id : 15
             * shop_name : 果然鲜果园
             * photo : http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I
             * view : 3171
             * turnover : 460233
             * order_people : 8
             * order_num : 43
             */

            private int shop_id;
            private int grade_id;
            private String shop_name;
            private String photo;
            private int view;
            private int turnover;
            private int order_people;
            private int order_num;

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

            public int getView() {
                return view;
            }

            public void setView(int view) {
                this.view = view;
            }

            public int getTurnover() {
                return turnover;
            }

            public void setTurnover(int turnover) {
                this.turnover = turnover;
            }

            public int getOrder_people() {
                return order_people;
            }

            public void setOrder_people(int order_people) {
                this.order_people = order_people;
            }

            public int getOrder_num() {
                return order_num;
            }

            public void setOrder_num(int order_num) {
                this.order_num = order_num;
            }
        }
    }
}
