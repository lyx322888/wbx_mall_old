package com.wbx.mall.bean;

import java.util.ArrayList;

/**
 * Created by Zero on 2018/4/3 0003.
 */

public class SalesManDataBean {

    /**
     * operation_money : 0
     * recommend_bonus : 0
     * recommend_star_arr : {"child_salesmans_count":1,"child_salesmans_shop_count":2,"child_salesmans_shop_today":0,"all_recommend":20000,"all_recommend_today":0}
     * recommend_leader_arr : {"child_salesmans_count":1,"child_salesmans_shop_count":0,"child_salesmans_shop_today":0,"all_recommend":0,"all_recommend_today":0}
     * recommend_all_arr : {"child_salesmans_shop_today":0,"all_recommend":20000,"city_shop_count":148}
     * reckon_seven_data : {"list_recommend_money":[0,10000,0,0,0,0,0],"list_operation_money":[0,0,0,0,0,0,0],"list_days":["03-27","03-26","03-25","03-24","03-23","03-22","03-21"]}
     */

    private int operation_money;
    private int recommend_bonus;
    private RecommendStarArrBean recommend_star_arr;
    private RecommendLeaderArrBean recommend_leader_arr;
    private RecommendAllArrBean recommend_all_arr;
    private ReckonSevenDataBean reckon_seven_data;

    public int getOperation_money() {
        return operation_money;
    }

    public void setOperation_money(int operation_money) {
        this.operation_money = operation_money;
    }

    public int getRecommend_bonus() {
        return recommend_bonus;
    }

    public void setRecommend_bonus(int recommend_bonus) {
        this.recommend_bonus = recommend_bonus;
    }

    public RecommendStarArrBean getRecommend_star_arr() {
        return recommend_star_arr;
    }

    public void setRecommend_star_arr(RecommendStarArrBean recommend_star_arr) {
        this.recommend_star_arr = recommend_star_arr;
    }

    public RecommendLeaderArrBean getRecommend_leader_arr() {
        return recommend_leader_arr;
    }

    public void setRecommend_leader_arr(RecommendLeaderArrBean recommend_leader_arr) {
        this.recommend_leader_arr = recommend_leader_arr;
    }

    public RecommendAllArrBean getRecommend_all_arr() {
        return recommend_all_arr;
    }

    public void setRecommend_all_arr(RecommendAllArrBean recommend_all_arr) {
        this.recommend_all_arr = recommend_all_arr;
    }

    public ReckonSevenDataBean getReckon_seven_data() {
        return reckon_seven_data;
    }

    public void setReckon_seven_data(ReckonSevenDataBean reckon_seven_data) {
        this.reckon_seven_data = reckon_seven_data;
    }

    public static class RecommendStarArrBean {
        /**
         * child_salesmans_count : 1
         * child_salesmans_shop_count : 2
         * child_salesmans_shop_today : 0
         * all_recommend : 20000
         * all_recommend_today : 0
         */

        private int child_salesmans_count;
        private int child_salesmans_shop_count;
        private int child_salesmans_shop_today;
        private int all_recommend;
        private int all_recommend_today;

        public int getChild_salesmans_count() {
            return child_salesmans_count;
        }

        public void setChild_salesmans_count(int child_salesmans_count) {
            this.child_salesmans_count = child_salesmans_count;
        }

        public int getChild_salesmans_shop_count() {
            return child_salesmans_shop_count;
        }

        public void setChild_salesmans_shop_count(int child_salesmans_shop_count) {
            this.child_salesmans_shop_count = child_salesmans_shop_count;
        }

        public int getChild_salesmans_shop_today() {
            return child_salesmans_shop_today;
        }

        public void setChild_salesmans_shop_today(int child_salesmans_shop_today) {
            this.child_salesmans_shop_today = child_salesmans_shop_today;
        }

        public int getAll_recommend() {
            return all_recommend;
        }

        public void setAll_recommend(int all_recommend) {
            this.all_recommend = all_recommend;
        }

        public int getAll_recommend_today() {
            return all_recommend_today;
        }

        public void setAll_recommend_today(int all_recommend_today) {
            this.all_recommend_today = all_recommend_today;
        }
    }

    public static class RecommendLeaderArrBean {
        /**
         * child_salesmans_count : 1
         * child_salesmans_shop_count : 0
         * child_salesmans_shop_today : 0
         * all_recommend : 0
         * all_recommend_today : 0
         */

        private int child_salesmans_count;
        private int child_salesmans_shop_count;
        private int child_salesmans_shop_today;
        private int all_recommend;
        private int all_recommend_today;

        public int getChild_salesmans_count() {
            return child_salesmans_count;
        }

        public void setChild_salesmans_count(int child_salesmans_count) {
            this.child_salesmans_count = child_salesmans_count;
        }

        public int getChild_salesmans_shop_count() {
            return child_salesmans_shop_count;
        }

        public void setChild_salesmans_shop_count(int child_salesmans_shop_count) {
            this.child_salesmans_shop_count = child_salesmans_shop_count;
        }

        public int getChild_salesmans_shop_today() {
            return child_salesmans_shop_today;
        }

        public void setChild_salesmans_shop_today(int child_salesmans_shop_today) {
            this.child_salesmans_shop_today = child_salesmans_shop_today;
        }

        public int getAll_recommend() {
            return all_recommend;
        }

        public void setAll_recommend(int all_recommend) {
            this.all_recommend = all_recommend;
        }

        public int getAll_recommend_today() {
            return all_recommend_today;
        }

        public void setAll_recommend_today(int all_recommend_today) {
            this.all_recommend_today = all_recommend_today;
        }
    }

    public static class RecommendAllArrBean {
        /**
         * child_salesmans_shop_today : 0
         * all_recommend : 20000
         * city_shop_count : 148
         */

        private int child_salesmans_shop_today;
        private int all_recommend;
        private int city_shop_count;

        public int getChild_salesmans_shop_today() {
            return child_salesmans_shop_today;
        }

        public void setChild_salesmans_shop_today(int child_salesmans_shop_today) {
            this.child_salesmans_shop_today = child_salesmans_shop_today;
        }

        public int getAll_recommend() {
            return all_recommend;
        }

        public void setAll_recommend(int all_recommend) {
            this.all_recommend = all_recommend;
        }

        public int getCity_shop_count() {
            return city_shop_count;
        }

        public void setCity_shop_count(int city_shop_count) {
            this.city_shop_count = city_shop_count;
        }
    }

    public static class ReckonSevenDataBean {
        private ArrayList<Integer> list_recommend_money;
        private ArrayList<Integer> list_operation_money;
        private ArrayList<String> list_days;

        public ArrayList<Integer> getList_recommend_money() {
            return list_recommend_money;
        }

        public void setList_recommend_money(ArrayList<Integer> list_recommend_money) {
            this.list_recommend_money = list_recommend_money;
        }

        public ArrayList<Integer> getList_operation_money() {
            return list_operation_money;
        }

        public void setList_operation_money(ArrayList<Integer> list_operation_money) {
            this.list_operation_money = list_operation_money;
        }

        public ArrayList<String> getList_days() {
            return list_days;
        }

        public void setList_days(ArrayList<String> list_days) {
            this.list_days = list_days;
        }
    }
}
