package com.wbx.mall.bean;

import java.util.List;

public class VisitShopBean {

    /**
     * msg : 成功
     * state : 1
     * data : [{"visit_time":1560216934,"photo":"http://vrzff.com/attachs/2017/09/01/thumb_59a90b4cb2c97.jpg","shop_name":"呆呆水果店","shop_id":1259,"grade_id":15,"is_shop_favorites":1,"distance":"1米"},{"visit_time":1560161349,"photo":"http://imgs.wbx365.com/FufNcAHWnymNkJolXFRNmOWkPNqM","shop_name":"阿火重庆鸡公煲","shop_id":1395,"grade_id":20,"is_shop_favorites":1,"distance":"1米"},{"visit_time":1559729440,"photo":"http://imgs.wbx365.com/FkW-M_yLJrS5fZBQafv32W4XsWSW","shop_name":"尚尚","shop_id":1423,"grade_id":20,"is_shop_favorites":1,"distance":"1米"},{"visit_time":1559729424,"photo":"http://www.wbx365.com/attachs/2017/09/01/thumb_59a90ea571410.png","shop_name":"晴天超市","shop_id":721,"grade_id":20,"is_shop_favorites":1,"distance":"1米"},{"visit_time":1559729364,"photo":"http://imgs.wbx365.com/15060722565busine15046022780","shop_name":"天天乐平价超市","shop_id":724,"grade_id":19,"is_shop_favorites":1,"distance":"1米"},{"visit_time":1559729308,"photo":"http://imgs.wbx365.com/18206062707busine15364001860","shop_name":"老猪高黑土猪肉专卖店","shop_id":586,"grade_id":15,"is_shop_favorites":1,"distance":"1米"},{"visit_time":1559615189,"photo":"http://imgs.wbx365.com/FpXuGAmLF6qFnYoz6DUUpL8ozKE5","shop_name":"小熊水果店","shop_id":1371,"grade_id":15,"is_shop_favorites":1,"distance":"1米"}]
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
         * visit_time : 1560216934
         * photo : http://vrzff.com/attachs/2017/09/01/thumb_59a90b4cb2c97.jpg
         * shop_name : 呆呆水果店
         * shop_id : 1259
         * grade_id : 15
         * is_shop_favorites : 1
         * distance : 1米
         */

        private int visit_time;
        private String photo;
        private String shop_name;
        private int shop_id;
        private int grade_id;
        private int is_shop_favorites;
        private String distance;

        public int getVisit_time() {
            return visit_time;
        }

        public void setVisit_time(int visit_time) {
            this.visit_time = visit_time;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
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

        public int getIs_shop_favorites() {
            return is_shop_favorites;
        }

        public void setIs_shop_favorites(int is_shop_favorites) {
            this.is_shop_favorites = is_shop_favorites;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}
