package com.wbx.mall.bean;

import java.util.List;

public class DispatchTimeBean {
    private List<TimeBean> data;

    public List<TimeBean> getData() {
        return data;
    }

    public void setData(List<TimeBean> data) {
        this.data = data;
    }

    public static class TimeBean {
        private String day;
        private String week;
        private String date;
        private List<String> time;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<String> getTime() {
            return time;
        }

        public void setTime(List<String> time) {
            this.time = time;
        }
    }
}
