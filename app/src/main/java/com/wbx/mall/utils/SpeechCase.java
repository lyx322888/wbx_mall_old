package com.wbx.mall.utils;

import java.util.Map;

/**
 * Created by wushenghui on 2017/12/29.
 */

public class SpeechCase {
    private String name;

    private String fileName;

    private Map<String, Object> params;

    public SpeechCase(String name, String fileName, Map<String, Object> params) {
        this.name = name;
        this.fileName = fileName;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
