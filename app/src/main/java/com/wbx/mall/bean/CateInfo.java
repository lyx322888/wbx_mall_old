package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/7/11.
 */

public class CateInfo implements Serializable {
    private String cate_id;
    private String cate_name;
    private int buy_num;

    public CateInfo(String cate_id, String cate_name) {
        this.cate_id = cate_id;
        this.cate_name = cate_name;
    }

    public int getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(int buy_num) {
        this.buy_num = buy_num;
    }

    public CateInfo() {
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }
}
