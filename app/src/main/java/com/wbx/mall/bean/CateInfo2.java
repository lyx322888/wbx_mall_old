package com.wbx.mall.bean;

import java.io.Serializable;

public class CateInfo2 implements Serializable {


    /**
     * cate_id : 0
     * cate_name : 全部
     * cate_num : 0
     */

    private int cate_id;
    private String cate_name;
    private int cate_num;
    private int buy_num;

    public int getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(int buy_num) {
        this.buy_num = buy_num;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public int getCate_num() {
        return cate_num;
    }

    public void setCate_num(int cate_num) {
        this.cate_num = cate_num;
    }
}
