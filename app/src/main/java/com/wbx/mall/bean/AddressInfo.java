package com.wbx.mall.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/7/17.
 */

public class AddressInfo implements Serializable {

    private int id;
    @JSONField(name = "default")
    private int defaultX;
    private String xm;
    private String tel;
    private String area_str;
    private String info;
    private String lat;
    private String lng;
    private String province_name;
    private String city_name;
    private String area_name;
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public double getLat() {
        if (TextUtils.isEmpty(lat)) {
            return 0;
        } else {
            return Double.valueOf(lat);
        }
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public double getLng() {
        if (TextUtils.isEmpty(lng)) {
            return 0;
        } else {
            return Double.valueOf(lng);
        }
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDefaultX() {
        return defaultX;
    }

    public void setDefaultX(int defaultX) {
        this.defaultX = defaultX;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getArea_str() {
        return area_str;
    }

    public void setArea_str(String area_str) {
        this.area_str = area_str;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
