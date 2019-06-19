package com.wbx.mall.bean;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/7/10.
 */

public class LocationInfo extends BaseIndexPinyinBean implements Serializable {
    private double lat;
    private double lng;
    private int city_id;
    private String name;
    private String first_letter;
    private String addressName;

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    private String selectCityName;
    private int selectCityId;

    private boolean isSelect;//是否选择

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getSelectCityName() {
        return selectCityName;
    }

    public void setSelectCityName(String selectCityName) {
        this.selectCityName = selectCityName;
    }

    public int getSelectCityId() {
        return selectCityId;
    }

    public void setSelectCityId(int selectCityId) {
        this.selectCityId = selectCityId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    @Override
    public String getTarget() {
        return first_letter;
    }
}
