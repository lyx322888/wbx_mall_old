package com.wbx.mall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wushenghui on 2018/1/15.
 */

public class IndexInfo implements Serializable{

    private List<AdBean> ad;
    private List<NewsBean> news;
    private List<SeckillGoodsBean> seckill_goods;
    private List<SalesGoodsBean> sales_goods;

    public List<AdBean> getAd() {
        return ad;
    }

    public void setAd(List<AdBean> ad) {
        this.ad = ad;
    }

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    public List<SeckillGoodsBean> getSeckill_goods() {
        return seckill_goods;
    }

    public void setSeckill_goods(List<SeckillGoodsBean> seckill_goods) {
        this.seckill_goods = seckill_goods;
    }

    public List<SalesGoodsBean> getSales_goods() {
        return sales_goods;
    }

    public void setSales_goods(List<SalesGoodsBean> sales_goods) {
        this.sales_goods = sales_goods;
    }

    public static class AdBean {
        /**
         * ad_id : 178
         * link_url : #
         * photo : http://www.wbx365.com/attachs/2017/06/10/593b9b7be1e4c.png
         */

        private int ad_id;
        private String link_url;
        private String photo;

        public int getAd_id() {
            return ad_id;
        }

        public void setAd_id(int ad_id) {
            this.ad_id = ad_id;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }

    public static class NewsBean {
        /**
         * news_id : 0
         * title : 圣诞不孤单，我们一起过
         * grade_id :
         */

        private int news_id;
        private String title;
        private String grade_id;

        public int getNews_id() {
            return news_id;
        }

        public void setNews_id(int news_id) {
            this.news_id = news_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(String grade_id) {
            this.grade_id = grade_id;
        }
    }

    public static class SeckillGoodsBean {
        /**
         * product_id : 33913
         * product_name : 土特产一包
         * seckill_price : 1800
         * is_attr : 0
         * photo : http://imgs.wbx365.com/Fk6HOl1K0ycy6LtBT--e5jdNTsge
         * price : 2000
         * shop : {"since_money":3000,"shop_name":"鲜土生鲜超市","photo":"http://imgs.wbx365.com/FvaB-FCHKyLWKRbsrZbyYTYmdoFK","shop_id":1511,"is_open":1,"score":5}
         */

        private int product_id;
        private String product_name;
        private int seckill_price;
        private int is_attr;
        private String photo;
        private int price;
        private IndexInfo.SeckillGoodsBean.ShopBean shop;

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public int getSeckill_price() {
            return seckill_price;
        }

        public void setSeckill_price(int seckill_price) {
            this.seckill_price = seckill_price;
        }

        public int getIs_attr() {
            return is_attr;
        }

        public void setIs_attr(int is_attr) {
            this.is_attr = is_attr;
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

        public IndexInfo.SeckillGoodsBean.ShopBean getShop() {
            return shop;
        }

        public void setShop(IndexInfo.SeckillGoodsBean.ShopBean shop) {
            this.shop = shop;
        }

        public static class ShopBean {
            /**
             * since_money : 3000
             * shop_name : 鲜土生鲜超市
             * photo : http://imgs.wbx365.com/FvaB-FCHKyLWKRbsrZbyYTYmdoFK
             * shop_id : 1511
             * is_open : 1
             * score : 5
             */

            private int since_money;
            private String shop_name;
            private String photo;
            private String shop_id;
            private int grade_id;
            private int is_open;
            private int score;

            public int getSince_money() {
                return since_money;
            }

            public void setSince_money(int since_money) {
                this.since_money = since_money;
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

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public int getIs_open() {
                return is_open;
            }

            public int getGrade_id() {
                return grade_id;
            }

            public void setGrade_id(int grade_id) {
                this.grade_id = grade_id;
            }

            public void setIs_open(int is_open) {
                this.is_open = is_open;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }
        }
    }

    public static class SalesGoodsBean {
        /**
         * product_id : 29341
         * product_name : 黑土猪腊肠
         * sales_promotion_price : 4580
         * is_attr : 0
         */

        private int product_id;
        private String product_name;
        private int sales_promotion_price;
        private String is_attr;
        private String photo;
        private ShopInfo2 shop;
        private int price;
        private int mall_price;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getMall_price() {
            return mall_price;
        }

        public void setMall_price(int mall_price) {
            this.mall_price = mall_price;
        }

        public ShopInfo2 getShop() {
            return shop;
        }

        public void setShop(ShopInfo2 shop) {
            this.shop = shop;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public int getSales_promotion_price() {
            return sales_promotion_price;
        }

        public void setSales_promotion_price(int sales_promotion_price) {
            this.sales_promotion_price = sales_promotion_price;
        }

        public String getIs_attr() {
            return is_attr;
        }

        public void setIs_attr(String is_attr) {
            this.is_attr = is_attr;
        }
    }
}
