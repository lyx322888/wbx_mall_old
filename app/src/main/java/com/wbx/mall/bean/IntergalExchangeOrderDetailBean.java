package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/8/16.
 */

public class IntergalExchangeOrderDetailBean implements Serializable {

    /**
     * exchange_id : 11
     * face_pic : http://www.wbx365.com/attachs/2017/07/19/thumb_596f1eadc85aa.jpg
     * title : 微百姓抱枕
     * num : 2
     * need_integral : 40
     * is_send : 0
     * address : 福建省 厦门市 思明区 前埔前村133
     * name : 尹少辉
     * phone : 18659242510
     * express_company :
     * express_num :
     * create_time : 1500530147
     */

    private int exchange_id;
    private String face_pic;
    private String title;
    private int num;
    private int need_integral;
    private int is_send;
    private String address;
    private String name;
    private String phone;
    private String express_company;
    private String express_num;
    private int create_time;

    public int getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(int exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String getFace_pic() {
        return face_pic;
    }

    public void setFace_pic(String face_pic) {
        this.face_pic = face_pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNeed_integral() {
        return need_integral;
    }

    public void setNeed_integral(int need_integral) {
        this.need_integral = need_integral;
    }

    public int getIs_send() {
        return is_send;
    }

    public void setIs_send(int is_send) {
        this.is_send = is_send;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExpress_company() {
        return express_company;
    }

    public void setExpress_company(String express_company) {
        this.express_company = express_company;
    }

    public String getExpress_num() {
        return express_num;
    }

    public void setExpress_num(String express_num) {
        this.express_num = express_num;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }
}
