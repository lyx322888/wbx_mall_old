package com.wbx.mall.bean;

import java.util.List;

public class SplitRecordBean {
    private List<SplitBean> data;

    public List<SplitBean> getData() {
        return data;
    }

    public void setData(List<SplitBean> data) {
        this.data = data;
    }

    public static class SplitBean {
        private long subpackage_time;//分包时间
        private String subpackage_type;//分包类型
        private String subpackage_num;//软件套数
        private String face;//头像
        private String phone;//帐号|手机号

        public long getSubpackage_time() {
            return subpackage_time;
        }

        public void setSubpackage_time(long subpackage_time) {
            this.subpackage_time = subpackage_time;
        }

        public String getSubpackage_type() {
            return subpackage_type;
        }

        public void setSubpackage_type(String subpackage_type) {
            this.subpackage_type = subpackage_type;
        }

        public String getSubpackage_num() {
            return subpackage_num;
        }

        public void setSubpackage_num(String subpackage_num) {
            this.subpackage_num = subpackage_num;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
