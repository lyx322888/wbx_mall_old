package com.wbx.mall.bean;

public class FreeGoodsDetailBean {
    /**
     * free_goods : {"goods_id":60898,"title":"斑马","photo":"http://imgs.wbx365.com/FgTls_enB9tHcOtEE9R_TUlegIQM","price":1,"is_seckill":0,"is_consume_free":1,"consume_free_price":10000,"is_share_free":0,"share_free_price":0,"is_accumulate_free":1,"shop_id":1423,"consume_free_amount":6,"free_goods_all_users":4}
     * shop : {"shop_name":"尚尚","notice":"这是一个商铺系统网页版添加的公告！！！","is_renzheng":1,"logo":"http://imgs.wbx365.com/FkW-M_yLJrS5fZBQafv32W4XsWSW","lat":"24.490177","lng":"118.183613","distance":"8m"}
     */

    private FreeGoodsBean free_goods;
    private ShopBean shop;

    public FreeGoodsBean getFree_goods() {
        return free_goods;
    }

    public void setFree_goods(FreeGoodsBean free_goods) {
        this.free_goods = free_goods;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public static class FreeGoodsBean {
        /**
         * goods_id : 60898
         * title : 斑马
         * photo : http://imgs.wbx365.com/FgTls_enB9tHcOtEE9R_TUlegIQM
         * price : 1
         * is_seckill : 0
         * is_consume_free : 1
         * consume_free_price : 10000
         * is_share_free : 0
         * share_free_price : 0
         * is_accumulate_free : 1
         * shop_id : 1423
         * consume_free_amount : 6
         * free_goods_all_users : 4
         */

        private int goods_id;
        private String title;
        private String photo;
        private int price;
        private int is_seckill;
        private int is_consume_free;
        private int consume_free_price;
        private int is_share_free;
        private int share_free_price;
        private int is_accumulate_free;
        private int shop_id;
        private int consume_free_amount;
        private int share_free_amount;
        private int free_goods_all_users;

        public int getShare_free_amount() {
            return share_free_amount;
        }

        public void setShare_free_amount(int share_free_amount) {
            this.share_free_amount = share_free_amount;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

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

        public int getIs_seckill() {
            return is_seckill;
        }

        public void setIs_seckill(int is_seckill) {
            this.is_seckill = is_seckill;
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

        public int getIs_share_free() {
            return is_share_free;
        }

        public void setIs_share_free(int is_share_free) {
            this.is_share_free = is_share_free;
        }

        public int getShare_free_price() {
            return share_free_price;
        }

        public void setShare_free_price(int share_free_price) {
            this.share_free_price = share_free_price;
        }

        public int getIs_accumulate_free() {
            return is_accumulate_free;
        }

        public void setIs_accumulate_free(int is_accumulate_free) {
            this.is_accumulate_free = is_accumulate_free;
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

        public int getFree_goods_all_users() {
            return free_goods_all_users;
        }

        public void setFree_goods_all_users(int free_goods_all_users) {
            this.free_goods_all_users = free_goods_all_users;
        }
    }

    public static class ShopBean {
        /**
         * shop_name : 尚尚
         * notice : 这是一个商铺系统网页版添加的公告！！！
         * is_renzheng : 1
         * logo : http://imgs.wbx365.com/FkW-M_yLJrS5fZBQafv32W4XsWSW
         * lat : 24.490177
         * lng : 118.183613
         * distance : 8m
         */

        private String shop_name;
        private String notice;
        private int is_renzheng;
        private String logo;
        private String lat;
        private String lng;
        private String distance;

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

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
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
    }
}
