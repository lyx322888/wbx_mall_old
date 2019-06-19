package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * @author Zero
 * @date 2018/6/12
 */
public class IntegralGoodsDetailBean implements Serializable {

    /**
     * goods_id : 2
     * title : 盘子
     * face_pic : http://www.wbx365.com/attachs/2017/08/09/thumb_598ab8e69ab04.jpg
     * integral : 100
     * details : <p>盘子 盘子盘子盘子 盘子盘子盘子 盘子<img src="/attachs/editor/2017/08/09/1502263550174299.jpg" title="1502263550174299.jpg" alt="17e4d5a5f9d13bc3dc4cacab291e373a5f1a747e1932-nvjyEo_sq320.jpg"/></p>
     */

    private int goods_id;
    private String title;
    private String face_pic;
    private int integral;
    private String details;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFace_pic() {
        return face_pic;
    }

    public void setFace_pic(String face_pic) {
        this.face_pic = face_pic;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
