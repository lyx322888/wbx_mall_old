package com.wbx.mall.bean;

import java.util.List;

public class IndexCountBean {

    private OrderBean order;
    private List<OrderBean> activity_user;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public List<OrderBean> getActivity_user() {
        return activity_user;
    }

    public void setActivity_user(List<OrderBean> activity_user) {
        this.activity_user = activity_user;
    }

    public static class OrderBean {
        private long create_time;
        private String title;
        private String nickname;
        private String face;

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }

}
