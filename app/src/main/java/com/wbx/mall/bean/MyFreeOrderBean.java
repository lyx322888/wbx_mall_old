package com.wbx.mall.bean;

public class MyFreeOrderBean {
    /**
     * activity_id : 8
     * activity_type : 1
     * status : 0
     * need_participants : 6
     * title : 斑马
     * photo : http://imgs.wbx365.com/FgTls_enB9tHcOtEE9R_TUlegIQM
     * consume_free_create_time : 1544769116
     * consume_free_duration : 5000
     * count_activity_users : 2
     * surplus_users : 4
     * surplus_time : 16959005
     * forever : 0
     */

    private String activity_id;
    private int activity_type;
    private int status;
    private int need_participants;
    private String title;
    private String photo;
    private String goods_id;
    private String shop_id;
    private int grade_id;
    private int consume_free_create_time;
    private int consume_free_duration;
    private int count_activity_users;
    private int surplus_users;
    private long surplus_time;
    private int forever;
    private int is_lottery;
    private int is_gain;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public int getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(int grade_id) {
        this.grade_id = grade_id;
    }

    public int getIs_lottery() {
        return is_lottery;
    }

    public void setIs_lottery(int is_lottery) {
        this.is_lottery = is_lottery;
    }

    public int getIs_gain() {
        return is_gain;
    }

    public void setIs_gain(int is_gain) {
        this.is_gain = is_gain;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public int getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(int activity_type) {
        this.activity_type = activity_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNeed_participants() {
        return need_participants;
    }

    public void setNeed_participants(int need_participants) {
        this.need_participants = need_participants;
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
}
