package com.wbx.mall.bean;

import java.util.List;

public class IndexShopBean {
    /**
     * shop_id : 1443
     * grade_id : 15
     * shop_name : 果然鲜果园
     * tel : 12345678910
     * addr : 虎仔山路45号厦门大学电子出版社(望海路)
     * photo : http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I
     * is_renzheng : 0
     * view : 2863
     * peisong_fanwei : 飞机器，莫非你李经理，莫得
     * score : 0
     * since_money : 0
     * notice : 44444444444444444444
     * lat : 24.490268
     * lng : 118.18362
     * ele_since_money : 0
     * is_open : 1
     * shop_status : 1
     * distance : 6m
     * coupon : []
     * full_money_reduce : [{"id":191,"full_money":600,"reduce_money":200,"shop_id":1443,"is_delete":0},{"id":193,"full_money":2200,"reduce_money":300,"shop_id":1443,"is_delete":0},{"id":194,"full_money":5500,"reduce_money":500,"shop_id":1443,"is_delete":0},{"id":195,"full_money":3300,"reduce_money":300,"shop_id":1443,"is_delete":0}]
     * often_shop : []
     * all_shop_assess : [{"create_time":1542078315,"message":"路飞","pics":["http://imgs.wbx365.com/17750120560comment15420783140"],"grade":5,"nickname":null,"face":"http://imgs.wbx365.com/177501205601537950236"},{"create_time":1540799500,"message":"咯女弄","pics":["http://imgs.wbx365.com/FsWlFx1SqbDmY1Mq4QcUwh96uRQM"],"grade":5,"nickname":"我是鱼***","face":"http://imgs.wbx365.com/FhA_YMVYp_jX6KeV49TNU6nWWy7E"},{"create_time":1540534160,"message":"1111","pics":[],"grade":5,"nickname":"杜帅***","face":"http://imgs.wbx365.com/Fih_wrKbWhfv4FmWyjoYLkPohVfo"},{"create_time":1540523592,"message":"本田","pics":["http://imgs.wbx365.com/FkoD4JWBiJ3XvbmN85260ObqJX0U","http://imgs.wbx365.com/Fk2fXo1GxxHDTOGuaT1ayBXvtWhD","http://imgs.wbx365.com/FvX6SZxCMguvUHhYI14y2g3nrB8X","http://imgs.wbx365.com/FsIFIlxgofvw0a3gyUsUAvmuq9W6","http://imgs.wbx365.com/FmCifLMKboDGQ6HyXRS03JBUEKuH","http://imgs.wbx365.com/FpxqaNDL7m9FwSeZcV-WFaW8uDJp"],"grade":5,"nickname":"老猪高***","face":"http://wx.qlogo.cn/mmopen/uGibN1kvGKKlYtoR6iaEKvHfCicqYDkGHZh9LU9AkNN4QT7K8ppUTjf5ox9j3rQPpia8dic0SIRjwBQ3pJEKuia9tC5hWyYtkJgjQ9/0"},{"create_time":1540430740,"message":"考虑考虑","pics":["http://imgs.wbx365.com/Fk2fXo1GxxHDTOGuaT1ayBXvtWhD"],"grade":5,"nickname":"老猪高***","face":"http://wx.qlogo.cn/mmopen/uGibN1kvGKKlYtoR6iaEKvHfCicqYDkGHZh9LU9AkNN4QT7K8ppUTjf5ox9j3rQPpia8dic0SIRjwBQ3pJEKuia9tC5hWyYtkJgjQ9/0"}]
     * all_free_goods : []
     * seckill_goods : []
     * goods : []
     */

    private String shop_id;
    private int grade_id;
    private String shop_name;
    private String tel;
    private String addr;
    private String photo;
    private int is_renzheng;
    private int view;
    private String peisong_fanwei;
    private int score;
    private int since_money;
    private String notice;
    private String lat;
    private String lng;
    private String ele_since_money;
    private String is_open;
    private int shop_status;
    private String distance;
    private List<CouponInfo> coupon;
    private List<FullMoneyReduceBean> full_money_reduce;
    private List<OftenShop> often_shop;
    private List<AllShopAssessBean> all_shop_assess;
    private List<?> all_free_goods;
    private List<GoodsInfo2> sales_goods;
    private List<GoodsBean> goods;
    private boolean isShowFreeRecord = false;

    public boolean isShowFreeRecord() {
        return isShowFreeRecord;
    }

    public void setShowFreeRecord(boolean showFreeRecord) {
        isShowFreeRecord = showFreeRecord;
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

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getIs_renzheng() {
        return is_renzheng;
    }

    public void setIs_renzheng(int is_renzheng) {
        this.is_renzheng = is_renzheng;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getPeisong_fanwei() {
        return peisong_fanwei;
    }

    public void setPeisong_fanwei(String peisong_fanwei) {
        this.peisong_fanwei = peisong_fanwei;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSince_money() {
        return since_money;
    }

    public void setSince_money(int since_money) {
        this.since_money = since_money;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
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

    public String getEle_since_money() {
        return ele_since_money;
    }

    public void setEle_since_money(String ele_since_money) {
        this.ele_since_money = ele_since_money;
    }

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public int getShop_status() {
        return shop_status;
    }

    public void setShop_status(int shop_status) {
        this.shop_status = shop_status;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<CouponInfo> getCoupon() {
        return coupon;
    }

    public void setCoupon(List<CouponInfo> coupon) {
        this.coupon = coupon;
    }

    public List<FullMoneyReduceBean> getFull_money_reduce() {
        return full_money_reduce;
    }

    public void setFull_money_reduce(List<FullMoneyReduceBean> full_money_reduce) {
        this.full_money_reduce = full_money_reduce;
    }

    public List<OftenShop> getOften_shop() {
        return often_shop;
    }

    public void setOften_shop(List<OftenShop> often_shop) {
        this.often_shop = often_shop;
    }

    public List<AllShopAssessBean> getAll_shop_assess() {
        return all_shop_assess;
    }

    public void setAll_shop_assess(List<AllShopAssessBean> all_shop_assess) {
        this.all_shop_assess = all_shop_assess;
    }

    public List<?> getAll_free_goods() {
        return all_free_goods;
    }

    public void setAll_free_goods(List<?> all_free_goods) {
        this.all_free_goods = all_free_goods;
    }

    public List<GoodsInfo2> getSales_goods() {
        return sales_goods;
    }

    public void setSales_goods(List<GoodsInfo2> sales_goods) {
        this.sales_goods = sales_goods;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class OftenShop {

        /**
         * visit_time : 1545361651
         * photo : http://imgs.wbx365.com/FkW-M_yLJrS5fZBQafv32W4XsWSW
         * shop_name : 尚尚
         * shop_id : 1423
         * grade_id : 20
         * lat : 24.490177
         * lng : 118.183613
         * distance : 8m
         * is_shop_favorites : 1
         */

        private int visit_time;
        private String photo;
        private String shop_name;
        private String shop_id;
        private int grade_id;
        private String lat;
        private String lng;
        private String distance;
        private int is_shop_favorites;

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

        public int getIs_shop_favorites() {
            return is_shop_favorites;
        }

        public void setIs_shop_favorites(int is_shop_favorites) {
            this.is_shop_favorites = is_shop_favorites;
        }
    }

    public static class FullMoneyReduceBean {
        /**
         * id : 191
         * full_money : 600
         * reduce_money : 200
         * shop_id : 1443
         * is_delete : 0
         */

        private int id;
        private int full_money;
        private int reduce_money;
        private int shop_id;
        private int is_delete;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFull_money() {
            return full_money;
        }

        public void setFull_money(int full_money) {
            this.full_money = full_money;
        }

        public int getReduce_money() {
            return reduce_money;
        }

        public void setReduce_money(int reduce_money) {
            this.reduce_money = reduce_money;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }
    }

    public static class GoodsBean {

        /**
         * goods_id : 60898
         * title : 斑马
         * photo : http://imgs.wbx365.com/FgTls_enB9tHcOtEE9R_TUlegIQM
         * price : 1
         * is_seckill : 0
         * seckill_price : 0
         * seckill_num : 0
         * is_consume_free : 1
         * consume_free_price : 10000
         * is_share_free : 0
         * share_free_price : 0
         * is_accumulate_free : 1
         * is_attr : 0
         * nature : []
         * seckill_start_time : 0
         * seckill_end_time : 0
         * sales_promotion_is : 0
         * sales_promotion_price : 0
         * free_goods_type : consume_free
         * free_goods_peoples : 0
         * free_goods_peoples_face : []
         */

        private String goods_id;
        private String title;
        private String photo;
        private int price;
        private int is_seckill;
        private int seckill_price;
        private int seckill_num;
        private int is_consume_free;
        private int consume_free_price;
        private int is_share_free;
        private int share_free_price;
        private int is_accumulate_free;
        private int is_attr;
        private int seckill_start_time;
        private int seckill_end_time;
        private int sales_promotion_is;
        private int sales_promotion_price;
        private String free_goods_type;
        private int free_goods_peoples;
        private List<?> nature;
        private List<String> free_goods_peoples_face;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
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

        public int getSeckill_price() {
            return seckill_price;
        }

        public void setSeckill_price(int seckill_price) {
            this.seckill_price = seckill_price;
        }

        public int getSeckill_num() {
            return seckill_num;
        }

        public void setSeckill_num(int seckill_num) {
            this.seckill_num = seckill_num;
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

        public int getIs_attr() {
            return is_attr;
        }

        public void setIs_attr(int is_attr) {
            this.is_attr = is_attr;
        }

        public int getSeckill_start_time() {
            return seckill_start_time;
        }

        public void setSeckill_start_time(int seckill_start_time) {
            this.seckill_start_time = seckill_start_time;
        }

        public int getSeckill_end_time() {
            return seckill_end_time;
        }

        public void setSeckill_end_time(int seckill_end_time) {
            this.seckill_end_time = seckill_end_time;
        }

        public int getSales_promotion_is() {
            return sales_promotion_is;
        }

        public void setSales_promotion_is(int sales_promotion_is) {
            this.sales_promotion_is = sales_promotion_is;
        }

        public int getSales_promotion_price() {
            return sales_promotion_price;
        }

        public void setSales_promotion_price(int sales_promotion_price) {
            this.sales_promotion_price = sales_promotion_price;
        }

        public String getFree_goods_type() {
            return free_goods_type;
        }

        public void setFree_goods_type(String free_goods_type) {
            this.free_goods_type = free_goods_type;
        }

        public int getFree_goods_peoples() {
            return free_goods_peoples;
        }

        public void setFree_goods_peoples(int free_goods_peoples) {
            this.free_goods_peoples = free_goods_peoples;
        }

        public List<?> getNature() {
            return nature;
        }

        public void setNature(List<?> nature) {
            this.nature = nature;
        }

        public List<String> getFree_goods_peoples_face() {
            return free_goods_peoples_face;
        }

        public void setFree_goods_peoples_face(List<String> free_goods_peoples_face) {
            this.free_goods_peoples_face = free_goods_peoples_face;
        }
    }

    public static class AllShopAssessBean {
        /**
         * create_time : 1542078315
         * message : 路飞
         * pics : ["http://imgs.wbx365.com/17750120560comment15420783140"]
         * grade : 5
         * nickname : null
         * face : http://imgs.wbx365.com/177501205601537950236
         */

        private int create_time;
        private String message;
        private int grade;
        private String nickname;
        private String face;
        private List<String> pics;

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
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

        public List<String> getPics() {
            return pics;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }
    }
}
