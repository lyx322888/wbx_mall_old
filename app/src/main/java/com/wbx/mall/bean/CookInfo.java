package com.wbx.mall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wushenghui on 2017/8/24.
 */

public class CookInfo implements Serializable {
    private String classid;
    private String name;
    private String parentid;
    private List<CookInfo> list;

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public List<CookInfo> getList() {
        return list;
    }

    public void setList(List<CookInfo> list) {
        this.list = list;
    }
}
