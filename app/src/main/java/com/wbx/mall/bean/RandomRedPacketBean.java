package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * @author Zero
 * @date 2018/7/13
 */
public class RandomRedPacketBean implements Serializable {

    /**
     * user_red_packet_id : 6
     * receive_money : 1.63
     */

    private String user_red_packet_id;
    private float receive_money;

    public String getUser_red_packet_id() {
        return user_red_packet_id;
    }

    public void setUser_red_packet_id(String user_red_packet_id) {
        this.user_red_packet_id = user_red_packet_id;
    }

    public float getReceive_money() {
        return receive_money;
    }

    public void setReceive_money(float receive_money) {
        this.receive_money = receive_money;
    }
}
