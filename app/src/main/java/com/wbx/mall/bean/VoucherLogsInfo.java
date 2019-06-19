package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/11/20.
 */

public class VoucherLogsInfo implements Serializable {
    private String name;
    private String card_key;
    private int value;
    private String end_date;
    private String used_time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard_key() {
        return card_key;
    }

    public void setCard_key(String card_key) {
        this.card_key = card_key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getUsed_time() {
        return used_time;
    }

    public void setUsed_time(String used_time) {
        this.used_time = used_time;
    }
}
