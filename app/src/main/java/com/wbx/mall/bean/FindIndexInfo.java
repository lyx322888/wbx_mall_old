package com.wbx.mall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wushenghui on 2018/1/26.
 */

public class FindIndexInfo implements Serializable {
    private int res;
    private List<FindInfo> findInfoList;

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public List<FindInfo> getFindInfoList() {
        return findInfoList;
    }

    public void setFindInfoList(List<FindInfo> findInfoList) {
        this.findInfoList = findInfoList;
    }
}
