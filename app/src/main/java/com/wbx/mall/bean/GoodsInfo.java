package com.wbx.mall.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wushenghui on 2017/7/11.
 */

public class GoodsInfo implements Serializable {

    /**
     * goods_id : 716
     * title : 丝婷持久定型喷雾
     * intro : null
     * photo : http://www.wbx365.com/attachs/2017/04/15/thumb_58f1dbdc5836d.jpg
     * price : 7500
     * mall_price : 7500
     * sales_promotion_price : 0
     * sales_promotion_is : 0
     * shopcate_id : 144
     */
    private String cart_id;
    private String product_id;
    private String goods_id;
    private String product_name;
    private String title;//实体店
    private String desc;
    private String intro;
    private String photo;
    private int price;
    private int mall_price;
    private int sales_promotion_price;
    private int sales_promotion_is;
    private String shopcate_id;
    private String cate_id;
    private int sold_num;
    private int month_num;
    private int num;
    private String shop_name;
    private String details;
    private int is_vs1;//1为认证商品
    private int is_vs2;//1为正品保障
    private int is_vs3;//1为假一赔十
    private int is_vs4;//1为当日送达
    private int is_vs5;//1为免运费
    private int is_vs6;//1为货到付款
    private int favorites;//收藏人数
    private List<PhotosInfo> pics;//轮播图
    private int favorites_id;
    private int is_attr;//是否多规格商品
    private int is_use_num;//是否开启库存
    private int inventory_num;//库存
    private String specName;//规格名称
    private String attr_id;//规格Id
    private String face_pic;//积分商城  图片
    private int integral;//兑换所需积分
    private String attr_name;

    private int is_seckill;//是否秒杀
    private int limitations_num;
    private int casing_price;//包装费
    private int selected;
    private int is_shop_member;//是否是会员商品
    private int shop_member_price;//会员价
    private List<String> goods_photo;//产品图
    private List<SpecInfo> goods_attr;
    private int original_price;//原价（用于获取购物车商品时显示原价与会员价的对比）
    private long seckill_start_time;
    private long seckill_end_time;
    private int total_price;
    private ArrayList<Nature> nature;
    private ArrayList<String> selected_nature_ids;
    private String nature_name;
    private HashMap<String, Integer> hmBuyNum = new HashMap<>();//key拼接规则：goodsId,attrId,natureId+natureId+natureId，例如100,5,1001+1002+1003
    private HashMap<String, Integer> cacheHmBuyNum = new HashMap<>();

    public HashMap<String, Integer> getCacheHmBuyNum() {
        return cacheHmBuyNum;
    }

    public void setCacheHmBuyNum(HashMap<String, Integer> cacheHmBuyNum) {
        this.cacheHmBuyNum = cacheHmBuyNum;
    }

    public HashMap<String, Integer> getHmBuyNum() {
        return hmBuyNum;
    }

    public void setHmBuyNum(HashMap<String, Integer> hmBuyNum) {
        this.hmBuyNum = hmBuyNum;
    }

    public String getNature_name() {
        return nature_name;
    }

    public void setNature_name(String nature_name) {
        this.nature_name = nature_name;
    }

    public ArrayList<String> getSelected_nature_ids() {
        return selected_nature_ids;
    }

    public void setSelected_nature_ids(ArrayList<String> selected_nature_ids) {
        this.selected_nature_ids = selected_nature_ids;
    }

    public ArrayList<Nature> getNature() {
        return nature;
    }

    public void setNature(ArrayList<Nature> nature) {
        this.nature = nature;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getInventory_num() {
        return inventory_num;
    }

    public void setInventory_num(int inventory_num) {
        this.inventory_num = inventory_num;
    }

    public long getSeckill_start_time() {
        return seckill_start_time;
    }

    public void setSeckill_start_time(long seckill_start_time) {
        this.seckill_start_time = seckill_start_time;
    }

    public long getSeckill_end_time() {
        return seckill_end_time;
    }

    public void setSeckill_end_time(long seckill_end_time) {
        this.seckill_end_time = seckill_end_time;
    }

    public int getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(int original_price) {
        this.original_price = original_price;
    }

    public List<SpecInfo> getGoods_attr() {
        return goods_attr;
    }

    public List<String> getGoods_photo() {
        return goods_photo;
    }

    public void setGoods_photo(List<String> goods_photo) {
        this.goods_photo = goods_photo;
    }

    public void setGoods_attr(List<SpecInfo> goods_attr) {
        this.goods_attr = goods_attr;
    }

    public int getIs_shop_member() {
        return is_shop_member;
    }

    public void setIs_shop_member(int is_shop_member) {
        this.is_shop_member = is_shop_member;
    }

    public int getShop_member_price() {
        return shop_member_price;
    }

    public void setShop_member_price(int shop_member_price) {
        this.shop_member_price = shop_member_price;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public int getCasing_price() {
        return casing_price;
    }

    public void setCasing_price(int casing_price) {
        this.casing_price = casing_price;
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

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public String getFace_pic() {
        return face_pic;
    }

    public void setFace_pic(String face_pic) {
        this.face_pic = face_pic;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public int getIs_attr() {
        return is_attr;
    }

    public void setIs_attr(int is_attr) {
        this.is_attr = is_attr;
    }

    public int getIs_use_num() {
        return is_use_num;
    }

    public void setIs_use_num(int is_use_num) {
        this.is_use_num = is_use_num;
    }

    public int getFavorites_id() {
        return favorites_id;
    }

    public void setFavorites_id(int favorites_id) {
        this.favorites_id = favorites_id;
    }

    public String getDetails() {
        return details;
    }

    public int getIs_vs1() {
        return is_vs1;
    }

    public int getIs_vs2() {
        return is_vs2;
    }

    public int getIs_vs3() {
        return is_vs3;
    }

    public int getIs_vs4() {
        return is_vs4;
    }

    public int getIs_vs5() {
        return is_vs5;
    }

    public int getIs_vs6() {
        return is_vs6;
    }

    public int getFavorites() {
        return favorites;
    }

    public List<PhotosInfo> getPics() {
        return pics;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setIs_vs1(int is_vs1) {
        this.is_vs1 = is_vs1;
    }

    public void setIs_vs2(int is_vs2) {
        this.is_vs2 = is_vs2;
    }

    public void setIs_vs3(int is_vs3) {
        this.is_vs3 = is_vs3;
    }

    public void setIs_vs4(int is_vs4) {
        this.is_vs4 = is_vs4;
    }

    public void setIs_vs5(int is_vs5) {
        this.is_vs5 = is_vs5;
    }

    public void setIs_vs6(int is_vs6) {
        this.is_vs6 = is_vs6;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public void setPics(List<PhotosInfo> pics) {
        this.pics = pics;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSold_num() {
        return sold_num;
    }

    public void setSold_num(int sold_num) {
        this.sold_num = sold_num;
    }

    public int getMonth_num() {
        return month_num;
    }

    public void setMonth_num(int month_num) {
        this.month_num = month_num;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public int getSales_promotion_is() {
        return sales_promotion_is;
    }

    public void setSales_promotion_is(int sales_promotion_is) {
        this.sales_promotion_is = sales_promotion_is;
    }

    public String getShopcate_id() {
        return shopcate_id;
    }

    public void setShopcate_id(String shopcate_id) {
        this.shopcate_id = shopcate_id;
    }


    public static class Nature implements Serializable {
        private String item_id;
        private String item_name;
        private List<Nature_attr> nature_arr;

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public List<Nature_attr> getNature_arr() {
            return nature_arr;
        }

        public void setNature_arr(List<Nature_attr> nature_arr) {
            this.nature_arr = nature_arr;
        }
    }

    public static class Nature_attr implements Serializable {
        private String nature_id;
        private String nature_name;

        public String getNature_id() {
            return nature_id;
        }

        public void setNature_id(String nature_id) {
            this.nature_id = nature_id;
        }

        public String getNature_name() {
            return nature_name;
        }

        public void setNature_name(String nature_name) {
            this.nature_name = nature_name;
        }
    }
}
