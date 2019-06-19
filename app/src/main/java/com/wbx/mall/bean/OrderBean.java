package com.wbx.mall.bean;

import java.util.List;

/**
 * @author Zero
 * @date 2018/10/22
 */
public class OrderBean {
    private String goods_id;
    private String product_id;
    private int num;
    private String attr_id;
    private List<String> nature;
    private int activity_type;

    public int getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(int activity_type) {
        this.activity_type = activity_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public List<String> getNature() {
        return nature;
    }

    public void setNature(List<String> nature) {
        this.nature = nature;
    }
}
