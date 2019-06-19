package com.wbx.mall.bean;

/**
 * Created by wushenghui on 2017/6/6.
 */

public class WxPayInfo {

    /**
     * appid : wx446e462418664fa0
     * mch_id : 1471943402
     * nonce_str : Xrj0jMnjoAQbm8tG
     * prepay_id : wx201706091154579b95201c2d0289882217
     * result_code : SUCCESS
     * return_code : SUCCESS
     * return_msg : OK
     * sign : E50D3F18CE7F74B52D596813418B08AB
     * trade_type : APP
     * time : 1496980497
     * two_sign : A7435F6425378F031E5F5DFBA3F3933D
     */

    private String appid;
    private String mch_id;
    private String nonce_str;
    private String prepay_id;
    private String result_code;
    private String return_code;
    private String return_msg;
    private String sign;
    private String trade_type;
    private int time;
    private String two_sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTwo_sign() {
        return two_sign;
    }

    public void setTwo_sign(String two_sign) {
        this.two_sign = two_sign;
    }
}
