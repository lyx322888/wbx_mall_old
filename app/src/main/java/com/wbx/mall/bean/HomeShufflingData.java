package com.wbx.mall.bean;

import java.util.List;

public class HomeShufflingData {


    /**
     * msg : 成功
     * state : 1
     * data : {"all_shop_view":26912,"count_all_shop":10,"all_free_people":178,"activity_user":[{"goods_id":60846,"create_time":1553067824,"shop_type":2,"user_id":13694,"nickname":"","face":"","title":"鱼豆腐"},{"goods_id":60888,"create_time":1550735766,"shop_type":2,"user_id":398,"nickname":"BBmale 王子","face":"http://wx.qlogo.cn/mmopen/Q3auHgzwzM7SjxkD7tpyBb84nVg26YhF9O2B0PplM5qFiahnwv7Yicgia884AibcbqWomhQOCtfkTku6ibms3DenBzA/0","title":"珍珠奶茶(单规格多属性)"}],"order":{"create_time":1553850924,"title":"珍珠奶茶(单规格多属性)","nickname":"在一起","face":"http://imgs.wbx365.com/152608791621553222810"}}
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
         * all_shop_view : 26912
         * count_all_shop : 10
         * all_free_people : 178
         * activity_user : [{"goods_id":60846,"create_time":1553067824,"shop_type":2,"user_id":13694,"nickname":"","face":"","title":"鱼豆腐"},{"goods_id":60888,"create_time":1550735766,"shop_type":2,"user_id":398,"nickname":"BBmale 王子","face":"http://wx.qlogo.cn/mmopen/Q3auHgzwzM7SjxkD7tpyBb84nVg26YhF9O2B0PplM5qFiahnwv7Yicgia884AibcbqWomhQOCtfkTku6ibms3DenBzA/0","title":"珍珠奶茶(单规格多属性)"}]
         * order : {"create_time":1553850924,"title":"珍珠奶茶(单规格多属性)","nickname":"在一起","face":"http://imgs.wbx365.com/152608791621553222810"}
         */

        private int all_shop_view;
        private int count_all_shop;
        private int all_free_people;
        private OrderBean order;
        private List<ActivityUserBean> activity_user;

        public int getAll_shop_view() {
            return all_shop_view;
        }

        public void setAll_shop_view(int all_shop_view) {
            this.all_shop_view = all_shop_view;
        }

        public int getCount_all_shop() {
            return count_all_shop;
        }

        public void setCount_all_shop(int count_all_shop) {
            this.count_all_shop = count_all_shop;
        }

        public int getAll_free_people() {
            return all_free_people;
        }

        public void setAll_free_people(int all_free_people) {
            this.all_free_people = all_free_people;
        }

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public List<ActivityUserBean> getActivity_user() {
            return activity_user;
        }

        public void setActivity_user(List<ActivityUserBean> activity_user) {
            this.activity_user = activity_user;
        }

        public static class OrderBean {
            /**
             * create_time : 1553850924
             * title : 珍珠奶茶(单规格多属性)
             * nickname : 在一起
             * face : http://imgs.wbx365.com/152608791621553222810
             */

            private int create_time;
            private String title;
            private String nickname;
            private String face;

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }
        }

        public static class ActivityUserBean {
            /**
             * goods_id : 60846
             * create_time : 1553067824
             * shop_type : 2
             * user_id : 13694
             * nickname :
             * face :
             * title : 鱼豆腐
             */

            private int goods_id;
            private int create_time;
            private int shop_type;
            private int user_id;
            private String nickname;
            private String face;
            private String title;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getShop_type() {
                return shop_type;
            }

            public void setShop_type(int shop_type) {
                this.shop_type = shop_type;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
