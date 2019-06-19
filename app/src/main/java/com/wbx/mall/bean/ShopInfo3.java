package com.wbx.mall.bean;

import java.util.HashMap;
import java.util.List;

public class ShopInfo3 {

    /**
     * shop_id : 721
     * grade_id : 20
     * shop_name : 晴天超市
     * tel : 13361698464
     * addr : 厦门大学
     * photo : http://www.wbx365.com/attachs/2017/09/01/thumb_59a90ea571410.png
     * is_renzheng : 0
     * view : 1627
     * peisong_fanwei : 额咯福特哦，URL喷壶闺女
     * score : 4
     * since_money : 550
     * lat : 24.490303
     * lng : 118.183624
     * is_subscribe : 1
     * ele_since_money : 0
     * is_open : 1
     * shop_status : 1
     * is_redpacket : 1
     * full_money_reduce : [{"full_money":8800,"reduce_money":600},{"full_money":5000,"reduce_money":500},{"full_money":1000,"reduce_money":100}]
     * is_jisuda : 1
     * sold_num : 1700
     * goods : [{"goods_id":60888,"title":"珍珠奶茶(单规格多属性)","photo":"http://imgs.wbx365.com/Fp1T81mh5rVG3_OZ8foDzBxER4u3","price":600,"is_seckill":0,"seckill_price":100,"seckill_num":22,"is_consume_free":1,"consume_free_price":600,"consume_free_num":50,"consume_free_create_time":1546825883,"consume_free_duration":999999999,"is_share_free":1,"share_free_price":0,"share_free_num":50,"share_free_create_time":1546825906,"share_free_duration":999999999,"is_accumulate_free":1,"is_attr":0,"nature":[],"seckill_start_time":1540869600,"seckill_end_time":1541042640,"sales_promotion_is":0,"sales_promotion_price":0,"free_goods_type":"share_free","sold_num":0,"free_goods_peoples":55,"free_goods_peoples_face":["http://wx.qlogo.cn/mmopen/Q3auHgzwzM7SjxkD7tpyBb84nVg26YhF9O2B0PplM5qFiahnwv7Yicgia884AibcbqWomhQOCtfkTku6ibms3DenBzA/0","http://wx.qlogo.cn/mmopen/Q3auHgzwzM7SjxkD7tpyBb84nVg26YhF9O2B0PplM5qFiahnwv7Yicgia884AibcbqWomhQOCtfkTku6ibms3DenBzA/0","http://wx.qlogo.cn/mmopen/Q3auHgzwzM7SjxkD7tpyBb84nVg26YhF9O2B0PplM5qFiahnwv7Yicgia884AibcbqWomhQOCtfkTku6ibms3DenBzA/0"],"shop_id":721,"grade_id":20},{"goods_id":59923,"title":"西红柿蛋汤","photo":"http://www.wbx365.com/attachs/2018/03/15/5aaa262a1a621.jpg","price":0,"is_seckill":0,"seckill_price":0,"seckill_num":0,"is_consume_free":1,"consume_free_price":0,"consume_free_num":50,"consume_free_create_time":1552289319,"consume_free_duration":80,"is_share_free":1,"share_free_price":0,"share_free_num":220,"share_free_create_time":1546844634,"share_free_duration":999999999,"is_accumulate_free":0,"is_attr":0,"nature":[],"seckill_start_time":0,"seckill_end_time":0,"sales_promotion_is":0,"sales_promotion_price":0,"free_goods_type":"share_free","sold_num":0,"free_goods_peoples":8,"free_goods_peoples_face":["http://imgs.wbx365.com/133283208931514873423","http://imgs.wbx365.com/152608791621553222810","http://imgs.wbx365.com/FsR9AxvdSCY72U4MxSkK9H1I9a-g"],"shop_id":721,"grade_id":20},{"goods_id":59921,"title":"牛尾萝卜汤啊","photo":"http://www.wbx365.com/attachs/2018/03/29/5abc42671a848.jpg","price":3500,"is_seckill":0,"seckill_price":0,"seckill_num":0,"is_consume_free":1,"consume_free_price":3500,"consume_free_num":50,"consume_free_create_time":1546997850,"consume_free_duration":999999999,"is_share_free":0,"share_free_price":0,"share_free_num":0,"share_free_create_time":0,"share_free_duration":999999999,"is_accumulate_free":0,"is_attr":0,"nature":[],"seckill_start_time":0,"seckill_end_time":0,"sales_promotion_is":1,"sales_promotion_price":1000,"free_goods_type":"consume_free","sold_num":0,"free_goods_peoples":1,"free_goods_peoples_face":["http://imgs.wbx365.com/152608791621553222810"],"shop_id":721,"grade_id":20}]
     * d : 9261.88km
     * is_exceed : 1
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
    private String lat;
    private String lng;
    private int is_subscribe;
    private int ele_since_money;
    private int is_open;
    private int shop_status;
    private int is_redpacket;
    private int is_jisuda;
    private int sold_num;
    private String d;
    private int is_exceed;
    private List<FullMoneyReduceBean> full_money_reduce;
    private List<GoodsBean> goods;
    private List<IndexShopBean.OftenShop> often_shop;
    private List<IndexShopBean.AllShopAssessBean> all_shop_assess;
    private List<?> all_free_goods;
    private List<GoodsInfo> sales_goods;
    private List<CouponInfo> coupon;

    public List<IndexShopBean.OftenShop> getOften_shop() {
        return often_shop;
    }

    public void setOften_shop(List<IndexShopBean.OftenShop> often_shop) {
        this.often_shop = often_shop;
    }

    public List<IndexShopBean.AllShopAssessBean> getAll_shop_assess() {
        return all_shop_assess;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public void setAll_shop_assess(List<IndexShopBean.AllShopAssessBean> all_shop_assess) {
        this.all_shop_assess = all_shop_assess;
    }

    public List<?> getAll_free_goods() {
        return all_free_goods;
    }

    public void setAll_free_goods(List<?> all_free_goods) {
        this.all_free_goods = all_free_goods;
    }

    public List<GoodsInfo> getSales_goods() {
        return sales_goods;
    }

    public void setSales_goods(List<GoodsInfo> sales_goods) {
        this.sales_goods = sales_goods;
    }

    public List<CouponInfo> getCoupon() {
        return coupon;
    }

    public void setCoupon(List<CouponInfo> coupon) {
        this.coupon = coupon;
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

    public int getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(int is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public int getEle_since_money() {
        return ele_since_money;
    }

    public void setEle_since_money(int ele_since_money) {
        this.ele_since_money = ele_since_money;
    }

    public int getIs_open() {
        return is_open;
    }

    public void setIs_open(int is_open) {
        this.is_open = is_open;
    }

    public int getShop_status() {
        return shop_status;
    }

    public void setShop_status(int shop_status) {
        this.shop_status = shop_status;
    }

    public int getIs_redpacket() {
        return is_redpacket;
    }

    public void setIs_redpacket(int is_redpacket) {
        this.is_redpacket = is_redpacket;
    }

    public int getIs_jisuda() {
        return is_jisuda;
    }

    public void setIs_jisuda(int is_jisuda) {
        this.is_jisuda = is_jisuda;
    }

    public int getSold_num() {
        return sold_num;
    }

    public void setSold_num(int sold_num) {
        this.sold_num = sold_num;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public int getIs_exceed() {
        return is_exceed;
    }

    public void setIs_exceed(int is_exceed) {
        this.is_exceed = is_exceed;
    }

    public List<FullMoneyReduceBean> getFull_money_reduce() {
        return full_money_reduce;
    }

    public void setFull_money_reduce(List<FullMoneyReduceBean> full_money_reduce) {
        this.full_money_reduce = full_money_reduce;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class FullMoneyReduceBean {
        /**
         * full_money : 8800
         * reduce_money : 600
         */

        private int full_money;
        private int reduce_money;

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
    }

    public static class GoodsBean {
        /**
         * goods_id : 60888
         * title : 珍珠奶茶(单规格多属性)
         * photo : http://imgs.wbx365.com/Fp1T81mh5rVG3_OZ8foDzBxER4u3
         * price : 600
         * is_seckill : 0
         * seckill_price : 100
         * seckill_num : 22
         * is_consume_free : 1
         * consume_free_price : 600
         * consume_free_num : 50
         * consume_free_create_time : 1546825883
         * consume_free_duration : 999999999
         * is_share_free : 1
         * share_free_price : 0
         * share_free_num : 50
         * share_free_create_time : 1546825906
         * share_free_duration : 999999999
         * is_accumulate_free : 1
         * is_attr : 0
         * nature : []
         * seckill_start_time : 1540869600
         * seckill_end_time : 1541042640
         * sales_promotion_is : 0
         * sales_promotion_price : 0
         * free_goods_type : share_free
         * sold_num : 0
         * free_goods_peoples : 55
         * free_goods_peoples_face : ["http://wx.qlogo.cn/mmopen/Q3auHgzwzM7SjxkD7tpyBb84nVg26YhF9O2B0PplM5qFiahnwv7Yicgia884AibcbqWomhQOCtfkTku6ibms3DenBzA/0","http://wx.qlogo.cn/mmopen/Q3auHgzwzM7SjxkD7tpyBb84nVg26YhF9O2B0PplM5qFiahnwv7Yicgia884AibcbqWomhQOCtfkTku6ibms3DenBzA/0","http://wx.qlogo.cn/mmopen/Q3auHgzwzM7SjxkD7tpyBb84nVg26YhF9O2B0PplM5qFiahnwv7Yicgia884AibcbqWomhQOCtfkTku6ibms3DenBzA/0"]
         * shop_id : 721
         * grade_id : 20
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
        private int consume_free_num;
        private int consume_free_create_time;
        private int consume_free_duration;
        private int is_share_free;
        private int share_free_price;
        private int share_free_num;
        private int share_free_create_time;
        private int share_free_duration;
        private int is_accumulate_free;
        private int is_attr;
        private int seckill_start_time;
        private int seckill_end_time;
        private int sales_promotion_is;
        private int sales_promotion_price;
        private String free_goods_type;
        private int sold_num;
        private int free_goods_peoples;
        private int shop_id;
        private int grade_id;
        private List<?> nature;
        private List<String> free_goods_peoples_face;
        private HashMap<String, Integer> hmBuyNum = new HashMap<>();//key拼接规则：goodsId,attrId,natureId+natureId+natureId，例如100,5,1001+1002+1003
        private HashMap<String, Integer> cacheHmBuyNum = new HashMap<>();


        public HashMap<String, Integer> getHmBuyNum() {
            return hmBuyNum;
        }

        public void setHmBuyNum(HashMap<String, Integer> hmBuyNum) {
            this.hmBuyNum = hmBuyNum;
        }

        public HashMap<String, Integer> getCacheHmBuyNum() {
            return cacheHmBuyNum;
        }

        public void setCacheHmBuyNum(HashMap<String, Integer> cacheHmBuyNum) {
            this.cacheHmBuyNum = cacheHmBuyNum;
        }

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

        public int getConsume_free_num() {
            return consume_free_num;
        }

        public void setConsume_free_num(int consume_free_num) {
            this.consume_free_num = consume_free_num;
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

        public int getShare_free_num() {
            return share_free_num;
        }

        public void setShare_free_num(int share_free_num) {
            this.share_free_num = share_free_num;
        }

        public int getShare_free_create_time() {
            return share_free_create_time;
        }

        public void setShare_free_create_time(int share_free_create_time) {
            this.share_free_create_time = share_free_create_time;
        }

        public int getShare_free_duration() {
            return share_free_duration;
        }

        public void setShare_free_duration(int share_free_duration) {
            this.share_free_duration = share_free_duration;
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

        public int getSold_num() {
            return sold_num;
        }

        public void setSold_num(int sold_num) {
            this.sold_num = sold_num;
        }

        public int getFree_goods_peoples() {
            return free_goods_peoples;
        }

        public void setFree_goods_peoples(int free_goods_peoples) {
            this.free_goods_peoples = free_goods_peoples;
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
}
