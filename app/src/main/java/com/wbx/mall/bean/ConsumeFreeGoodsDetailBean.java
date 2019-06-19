package com.wbx.mall.bean;

import java.util.List;

public class ConsumeFreeGoodsDetailBean {
    /**
     * user_free_activity : {"id":8,"need_participants":6,"sponsor_user_id":13620,"surplus_time":924133,"forever":0,"count_activity_users":0,"surplus_users":6,"is_pay":0}
     * activity_users : []
     * shop : {"shop_id":1423,"logo":"http://imgs.wbx365.com/FkW-M_yLJrS5fZBQafv32W4XsWSW","shop_name":"尚尚","notice":"这是一个商铺系统网页版添加的公告！！！","is_renzheng":1,"lat":"24.490177","lng":"118.183613","distance":"8m","is_favorites":1}
     * goods : {"title":"斑马","photo":"http://imgs.wbx365.com/FgTls_enB9tHcOtEE9R_TUlegIQM","price":1,"is_consume_free":1,"consume_free_price":10000,"consume_free_create_time":1544769116,"consume_free_duration":500,"shop_id":1423,"consume_free_amount":6,"free_goods_peoples":4}
     */

    private UserFreeActivityBean user_free_activity;
    private ShopBean shop;
    private GoodsBean goods;
    private List<ActivityUsers> activity_users;

    public UserFreeActivityBean getUser_free_activity() {
        return user_free_activity;
    }

    public void setUser_free_activity(UserFreeActivityBean user_free_activity) {
        this.user_free_activity = user_free_activity;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public List<ActivityUsers> getActivity_users() {
        return activity_users;
    }

    public void setActivity_users(List<ActivityUsers> activity_users) {
        this.activity_users = activity_users;
    }

    public static class ActivityUsers {
        private String face;
        private String nickname;

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }

    public static class UserFreeActivityBean {
        /**
         * id : 8
         * need_participants : 6
         * sponsor_user_id : 13620
         * surplus_time : 924133
         * forever : 0
         * count_activity_users : 0
         * surplus_users : 6
         * is_pay : 0
         */

        private String id;
        private int need_participants;
        private int sponsor_user_id;
        private long surplus_time;
        private int forever;
        private int count_activity_users;
        private int surplus_users;
        private int is_pay;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getNeed_participants() {
            return need_participants;
        }

        public void setNeed_participants(int need_participants) {
            this.need_participants = need_participants;
        }

        public int getSponsor_user_id() {
            return sponsor_user_id;
        }

        public void setSponsor_user_id(int sponsor_user_id) {
            this.sponsor_user_id = sponsor_user_id;
        }

        public long getSurplus_time() {
            return surplus_time;
        }

        public void setSurplus_time(long surplus_time) {
            this.surplus_time = surplus_time;
        }

        public int getForever() {
            return forever;
        }

        public void setForever(int forever) {
            this.forever = forever;
        }

        public int getCount_activity_users() {
            return count_activity_users;
        }

        public void setCount_activity_users(int count_activity_users) {
            this.count_activity_users = count_activity_users;
        }

        public int getSurplus_users() {
            return surplus_users;
        }

        public void setSurplus_users(int surplus_users) {
            this.surplus_users = surplus_users;
        }

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }
    }

    public static class ShopBean {
        /**
         * shop_id : 1423
         * logo : http://imgs.wbx365.com/FkW-M_yLJrS5fZBQafv32W4XsWSW
         * shop_name : 尚尚
         * notice : 这是一个商铺系统网页版添加的公告！！！
         * is_renzheng : 1
         * lat : 24.490177
         * lng : 118.183613
         * distance : 8m
         * is_favorites : 1
         */

        private int shop_id;
        private String logo;
        private String shop_name;
        private String notice;
        private int is_renzheng;
        private String lat;
        private String lng;
        private String distance;
        private int is_favorites;

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public int getIs_renzheng() {
            return is_renzheng;
        }

        public void setIs_renzheng(int is_renzheng) {
            this.is_renzheng = is_renzheng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getIs_favorites() {
            return is_favorites;
        }

        public void setIs_favorites(int is_favorites) {
            this.is_favorites = is_favorites;
        }
    }

    public static class GoodsBean {
        /**
         * title : 斑马
         * photo : http://imgs.wbx365.com/FgTls_enB9tHcOtEE9R_TUlegIQM
         * price : 1
         * is_consume_free : 1
         * consume_free_price : 10000
         * consume_free_create_time : 1544769116
         * consume_free_duration : 500
         * shop_id : 1423
         * consume_free_amount : 6
         * free_goods_peoples : 4
         */

        private String title;
        private String photo;
        private int price;
        private int is_consume_free;
        private int consume_free_price;
        private int consume_free_create_time;
        private int consume_free_duration;
        private int shop_id;
        private int consume_free_amount;
        private int free_goods_peoples;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getIs_consume_free() {
            return is_consume_free;
        }

        public void setIs_consume_free(int is_consume_free) {
            this.is_consume_free = is_consume_free;
        }

        public int getConsume_free_price() {
            return consume_free_price;
        }

        public void setConsume_free_price(int consume_free_price) {
            this.consume_free_price = consume_free_price;
        }

        public int getConsume_free_create_time() {
            return consume_free_create_time;
        }

        public void setConsume_free_create_time(int consume_free_create_time) {
            this.consume_free_create_time = consume_free_create_time;
        }

        public int getConsume_free_duration() {
            return consume_free_duration;
        }

        public void setConsume_free_duration(int consume_free_duration) {
            this.consume_free_duration = consume_free_duration;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getConsume_free_amount() {
            return consume_free_amount;
        }

        public void setConsume_free_amount(int consume_free_amount) {
            this.consume_free_amount = consume_free_amount;
        }

        public int getFree_goods_peoples() {
            return free_goods_peoples;
        }

        public void setFree_goods_peoples(int free_goods_peoples) {
            this.free_goods_peoples = free_goods_peoples;
        }
    }
}
