package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/7/17.
 */

public class PaymentInfo implements Serializable {
    /**
     * payment_id : 4
     * name : 余额支付
     * logo : money.png
     * code : money
     * mobile_logo : money_mobile.png
     * contents : 余额支付是最方便快捷的
     * content : 如果您没有网银，可以货到付款
     */
    private int payment_id;
    private String name;
    private String logo;
    private String code;
    private String mobile_logo;
    private String contents;
    private String content;
    private boolean isChecked;

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile_logo() {
        return mobile_logo;
    }

    public void setMobile_logo(String mobile_logo) {
        this.mobile_logo = mobile_logo;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
