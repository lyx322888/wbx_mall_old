package com.wbx.mall.bean;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class BaseResponse<T> {

    /**
     * msg : 成功
     * state : 1
     * data : [{"order_id":12,"shop_id":3,"create_time":1520842007,"money":100,"pay_type":"money","shop_name":"BBmale 妈乐旗舰店"}]
     */

    private String msg;
    private int state;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
