package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/5/25.
 */

public class SearchInfo implements Serializable {
    private int type;
    private String historys;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHistorys() {
        return historys;
    }

    public void setHistorys(String historys) {
        this.historys = historys;
    }
}
