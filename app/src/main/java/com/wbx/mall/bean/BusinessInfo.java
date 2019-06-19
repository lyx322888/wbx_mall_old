package com.wbx.mall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wushenghui on 2017/7/20.
 */

public class BusinessInfo  implements Serializable{

    /**
     * area_id : 66
     * area_name : 思明
     * business : [{"business_id":54,"business_name":"思明商圈"}]
     */

    private int area_id;
    private String area_name;
    private List<BusinessBean> business;

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public List<BusinessBean> getBusiness() {
        return business;
    }

    public void setBusiness(List<BusinessBean> business) {
        this.business = business;
    }

    public static class BusinessBean {
        /**
         * business_id : 54
         * business_name : 思明商圈
         */

        private int business_id;
        private String business_name;

        public int getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(int business_id) {
            this.business_id = business_id;
        }

        public String getBusiness_name() {
            return business_name;
        }

        public void setBusiness_name(String business_name) {
            this.business_name = business_name;
        }
    }
}
