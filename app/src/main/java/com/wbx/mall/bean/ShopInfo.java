package com.wbx.mall.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wushenghui on 2017/7/10.
 */

public class ShopInfo implements Serializable {
    private Integer shop_id;
    private int grade_id;
    private String shop_name;
    private String tel;
    private String addr;
    private String photo;
    private Integer is_renzheng;
    private Integer view;
    private String peisong_fanwei;
    private int score;
    private int since_money;//起送价
    private int ele_since_money;//菜市场起送价
    private String d;
    private String qr_url;
    private String small_routine_photo;
    private String business_time;
    private String intro;
    private double logistics;//配送价
    private int is_open;//1是打烊
    private int sold_num;//总销量
    private String notice;
    private String details;
//    private List<GoodsInfo> goods;
    private List<GoodsInfo2> goods2;
    private ArrayList<String> photos;//环境图
    private String audit_photo;//资质照片
    private String hx_username;
    private String video;
    private List<FullInfo> full_money_reduce;
    private List<CouponInfo> coupon;
    private int is_favorites;
    private int is_subscribe;
    private int is_shop_member_user;
    private int is_buy;
    private int consumption_money;
    private int shop_status;
    private int is_exceed;
    private int goods_num;
    private String audit_name;
    private String audit_addr;
    private String zhucehao;
    private String audit_end_date;
    private String hygiene_photo;



    public int getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(int is_buy) {
        this.is_buy = is_buy;
    }

    public String getHygiene_photo() {
        return hygiene_photo;
    }

    public void setHygiene_photo(String hygiene_photo) {
        this.hygiene_photo = hygiene_photo;
    }

    public String getAudit_addr() {
        return audit_addr;
    }

    public void setAudit_addr(String audit_addr) {
        this.audit_addr = audit_addr;
    }

    public String getAudit_name() {
        return audit_name;
    }

    public void setAudit_name(String audit_name) {
        this.audit_name = audit_name;
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

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public int getIs_exceed() {
        return is_exceed;
    }

    public void setIs_exceed(int is_exceed) {
        this.is_exceed = is_exceed;
    }

    public int getShop_status() {
        return shop_status;
    }

    public void setShop_status(int shop_status) {
        this.shop_status = shop_status;
    }

    public int getConsumption_money() {
        return consumption_money;
    }

    public void setConsumption_money(int consumption_money) {
        this.consumption_money = consumption_money;
    }

    public int getIs_shop_member_user() {
        return is_shop_member_user;
    }

    public void setIs_shop_member_user(int is_shop_member_user) {
        this.is_shop_member_user = is_shop_member_user;
    }

    public int getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(int is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public int getIs_favorites() {
        return is_favorites;
    }

    public void setIs_favorites(int is_favorites) {
        this.is_favorites = is_favorites;
    }

    public List<CouponInfo> getCoupon() {
        return coupon;
    }

    public void setCoupon(List<CouponInfo> coupon) {
        this.coupon = coupon;
    }

    public List<FullInfo> getFull_money_reduce() {
        return full_money_reduce;
    }

    public void setFull_money_reduce(List<FullInfo> full_money_reduce) {
        this.full_money_reduce = full_money_reduce;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    private int month_num;//月销单量

    public int getMonth_num() {
        return month_num;
    }

    public void setMonth_num(int month_num) {
        this.month_num = month_num;
    }

    public String getSmall_routine_photo() {
        return small_routine_photo;
    }

    public void setSmall_routine_photo(String small_routine_photo) {
        this.small_routine_photo = small_routine_photo;
    }

    public String getHx_username() {
        return hx_username;
    }

    public void setHx_username(String hx_username) {
        this.hx_username = hx_username;
    }

    public String getAudit_photo() {
        return audit_photo;
    }

    public void setAudit_photo(String audit_photo) {
        this.audit_photo = audit_photo;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

//    public List<GoodsInfo> getGoods() {
//        return goods;
//    }
//
//    public void setGoods(List<GoodsInfo> goods) {
//        this.goods = goods;
//    }
    public List<GoodsInfo2> getGoods2() {
        return goods2;
    }

    public void setGoods2(List<GoodsInfo2> goods2) {
        this.goods2 = goods2;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getSold_num() {
        return sold_num;
    }

    public void setSold_num(int sold_num) {
        this.sold_num = sold_num;
    }

    public Integer getShop_id() {
        return shop_id;
    }

    public void setShop_id(Integer shop_id) {
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

    public Integer getIs_renzheng() {
        return is_renzheng;
    }

    public void setIs_renzheng(Integer is_renzheng) {
        this.is_renzheng = is_renzheng;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
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

    public int getEle_since_money() {
        return ele_since_money;
    }

    public void setEle_since_money(int ele_since_money) {
        this.ele_since_money = ele_since_money;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public double getLogistics() {
        return logistics;
    }

    public void setLogistics(double logistics) {
        this.logistics = logistics;
    }

    public int getIs_open() {
        return is_open;
    }

    public void setIs_open(int is_open) {
        this.is_open = is_open;
    }


}
