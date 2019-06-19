package com.wbx.mall.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/7 0007.
 */

public class WinRecordBean {

    /**
     * list : [{"money":25,"can_use":0}]
     * rule : 1、订单满30元及以上（不包含运费）可获得1次抽奖机会
     * 2、订单收货完成后即可使用奖励金
     * 3、下单时，若有可使用奖励金将自动使用奖励金
     * 4、奖励金不可提现，只可于下单抵用
     * 5、本活动最终解释权归微百姓（厦门）科技有限公司所有
     */

    private String rule;
    private List<ListBean> list;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * money : 25
         * can_use : 0
         */

        private int money;
        private int can_use;
        private int is_use;

        public int getIs_use() {
            return is_use;
        }

        public void setIs_use(int is_use) {
            this.is_use = is_use;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getCan_use() {
            return can_use;
        }

        public void setCan_use(int can_use) {
            this.can_use = can_use;
        }
    }
}
