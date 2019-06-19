package com.wbx.mall.bean;

import java.util.List;

public class SeckillInfo {
    /**
     * goods_id : 59906
     * title : 白水杜康 N88 帝王黄 浓香型白酒 52度 500ml
     * photo : http://www.wbx365.com/attachs/2018/03/14/5aa87a8b37677.jpg
     * price : 300
     * is_attr : 0
     * nature : []
     * sold_num : 0
     * goods_attr : [{"attr_id":3042,"goods_id":60889,"attr_name":"大","price":8800,"mall_price":700,"sales_promotion_price":0,"num":0,"type":"shop","loss":0,"seckill_price":10,"seckill_num":49,"limitations_num":5,"is_seckill":0,"casing_price":600,"shop_member_price":0,"is_shop_member":0},{"attr_id":3043,"goods_id":60889,"attr_name":"中","price":7900,"mall_price":600,"sales_promotion_price":0,"num":0,"type":"shop","loss":0,"seckill_price":20,"seckill_num":50,"limitations_num":5,"is_seckill":0,"casing_price":500,"shop_member_price":0,"is_shop_member":0}]
     */

    private int goods_id;
    private String title;
    private String photo;
    private int price;
    private int is_attr;
    private int sold_num;
    private List<?> nature;
    private List<GoodsAttrBean> goods_attr;

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

    public int getIs_attr() {
        return is_attr;
    }

    public void setIs_attr(int is_attr) {
        this.is_attr = is_attr;
    }

    public int getSold_num() {
        return sold_num;
    }

    public void setSold_num(int sold_num) {
        this.sold_num = sold_num;
    }

    public List<?> getNature() {
        return nature;
    }

    public void setNature(List<?> nature) {
        this.nature = nature;
    }

    public List<GoodsAttrBean> getGoods_attr() {
        return goods_attr;
    }

    public void setGoods_attr(List<GoodsAttrBean> goods_attr) {
        this.goods_attr = goods_attr;
    }

    public static class GoodsAttrBean {
        /**
         * attr_id : 3042
         * goods_id : 60889
         * attr_name : 大
         * price : 8800
         * mall_price : 700
         * sales_promotion_price : 0
         * num : 0
         * type : shop
         * loss : 0
         * seckill_price : 10
         * seckill_num : 49
         * limitations_num : 5
         * is_seckill : 0
         * casing_price : 600
         * shop_member_price : 0
         * is_shop_member : 0
         */

        private int attr_id;
        private int goods_id;
        private String attr_name;
        private int price;
        private int mall_price;
        private int sales_promotion_price;
        private int num;
        private String type;
        private int loss;
        private int seckill_price;
        private int seckill_num;
        private int limitations_num;
        private int is_seckill;
        private int casing_price;
        private int shop_member_price;
        private int is_shop_member;

        public int getAttr_id() {
            return attr_id;
        }

        public void setAttr_id(int attr_id) {
            this.attr_id = attr_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getAttr_name() {
            return attr_name;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
        }

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

        public int getSales_promotion_price() {
            return sales_promotion_price;
        }

        public void setSales_promotion_price(int sales_promotion_price) {
            this.sales_promotion_price = sales_promotion_price;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getLoss() {
            return loss;
        }

        public void setLoss(int loss) {
            this.loss = loss;
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

        public int getLimitations_num() {
            return limitations_num;
        }

        public void setLimitations_num(int limitations_num) {
            this.limitations_num = limitations_num;
        }

        public int getIs_seckill() {
            return is_seckill;
        }

        public void setIs_seckill(int is_seckill) {
            this.is_seckill = is_seckill;
        }

        public int getCasing_price() {
            return casing_price;
        }

        public void setCasing_price(int casing_price) {
            this.casing_price = casing_price;
        }

        public int getShop_member_price() {
            return shop_member_price;
        }

        public void setShop_member_price(int shop_member_price) {
            this.shop_member_price = shop_member_price;
        }

        public int getIs_shop_member() {
            return is_shop_member;
        }

        public void setIs_shop_member(int is_shop_member) {
            this.is_shop_member = is_shop_member;
        }
    }
}
