package com.wbx.mall.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopInfo2 implements Serializable {

    /**
     * shop_id : 1259
     * grade_id : 15
     * shop_name : 呆呆水果店
     * tel : 18206062707
     * addr : 大地方
     * photo : http://www.wbx365.com/attachs/2017/09/01/thumb_59a90b4cb2c97.jpg
     * is_renzheng : 0
     * view : 667
     * peisong_fanwei :
     * score : 5
     * since_money : 0
     * lat : 24.484941
     * lng : 118.197181
     * is_subscribe : 0
     * ele_since_money : 0
     * is_open : 1
     * shop_status : 1
     * is_redpacket : 0
     * full_money_reduce : []
     * is_jisuda : 1
     * sold_num : 1389
     * goods : [{"product_id":29414,"product_name":"菠萝","photo":"http://www.wbx365.com/attachs/2017/11/15/thumb_5a0c05e6e8184.jpg","price":300,"is_seckill":1,"seckill_price":300,"seckill_num":100,"is_consume_free":0,"consume_free_price":0,"consume_free_num":0,"consume_free_create_time":0,"consume_free_duration":999999999,"is_share_free":0,"share_free_price":0,"share_free_num":0,"share_free_create_time":0,"share_free_duration":0,"is_accumulate_free":0,"is_attr":0,"nature":[],"seckill_start_time":1525417620,"seckill_end_time":1830323220,"sales_promotion_is":0,"sales_promotion_price":0,"free_goods_type":"goods","goods_id":29414,"sold_num":0,"free_goods_peoples":0,"free_goods_peoples_face":[],"shop_id":1259,"grade_id":15}]
     * d : 39m
     * is_exceed : 0
     */

    private int shop_id;
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
    private int is_favorites;
    private int sold_num;
    private String d;
    private int is_exceed;
    private int is_shop_member_user;
    private List<GoodsInfo2> goods;
    private List<CouponInfo> coupon;
    private List<FullInfo> full_money_reduce;
    private String notice;
    private int is_buy;
    private String hx_username;
    private double logistics;//配送价
    private int consumption_money;
    private ArrayList<String> photos;//环境图
    private int goods_num;
    private String business_time;
    private String qr_url;
    private String small_routine_photo;


    private String audit_name;
    private String audit_addr;
    private String zhucehao;
    private String audit_end_date;
    private String hygiene_photo;
    private String audit_photo;//资质照片

    public String getAudit_photo() {
        return audit_photo;
    }

    public void setAudit_photo(String audit_photo) {
        this.audit_photo = audit_photo;
    }

    public String getAudit_name() {
        return audit_name;
    }

    public void setAudit_name(String audit_name) {
        this.audit_name = audit_name;
    }

    public String getAudit_addr() {
        return audit_addr;
    }

    public void setAudit_addr(String audit_addr) {
        this.audit_addr = audit_addr;
    }

    public String getZhucehao() {
        return zhucehao;
    }

    public void setZhucehao(String zhucehao) {
        this.zhucehao = zhucehao;
    }

    public String getAudit_end_date() {
        return audit_end_date;
    }

    public void setAudit_end_date(String audit_end_date) {
        this.audit_end_date = audit_end_date;
    }

    public String getHygiene_photo() {
        return hygiene_photo;
    }

    public void setHygiene_photo(String hygiene_photo) {
        this.hygiene_photo = hygiene_photo;
    }

    public String getQr_url() {
        return qr_url;
    }

    public void setQr_url(String qr_url) {
        this.qr_url = qr_url;
    }

    public String getBusiness_time() {
        return business_time;
    }

    public void setBusiness_time(String business_time) {
        this.business_time = business_time;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public int getConsumption_money() {
        return consumption_money;
    }

    public void setConsumption_money(int consumption_money) {
        this.consumption_money = consumption_money;
    }

    public double getLogistics() {
        return logistics;
    }

    public void setLogistics(double logistics) {
        this.logistics = logistics;
    }

    public String getHx_username() {
        return hx_username;
    }

    public void setHx_username(String hx_username) {
        this.hx_username = hx_username;
    }

    public int getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(int is_buy) {
        this.is_buy = is_buy;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getIs_favorites() {
        return is_favorites;
    }

    public void setIs_favorites(int is_favorites) {
        this.is_favorites = is_favorites;
    }

    public int getIs_shop_member_user() {
        return is_shop_member_user;
    }

    public void setIs_shop_member_user(int is_shop_member_user) {
        this.is_shop_member_user = is_shop_member_user;
    }

    public List<GoodsInfo2> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsInfo2> goods) {
        this.goods = goods;
    }

    public List<CouponInfo> getCoupon() {
        return coupon;
    }

    public void setCoupon(List<CouponInfo> coupon) {
        this.coupon = coupon;
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

    public String getSmall_routine_photo() {
        return small_routine_photo;
    }

    public void setSmall_routine_photo(String small_routine_photo) {
        this.small_routine_photo = small_routine_photo;
    }

    public List<FullInfo> getFull_money_reduce() {
        return full_money_reduce;
    }

    public void setFull_money_reduce(List<FullInfo> full_money_reduce) {
        this.full_money_reduce = full_money_reduce;
    }

}
