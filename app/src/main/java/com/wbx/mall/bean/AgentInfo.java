package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/9/7.
 */

public class AgentInfo implements Serializable {
    private int salesman_id;
    private int deposit;//押金
    private int join_fee;//加盟费用
    private String identity_card_front;//身份证正面
    private String identity_card_con;//身份证反面
    private int invest_fee;//三星投资金额
    private int rank;//业务员等级
    private String name;
    private String phone;
    private String identity_card_no;
    private int audit;//0未审核 1通过 2 拒绝
    private int commision;//推荐奖励
    private  int operation_money;//业务提成
    private String xh_share_link;
    private String referee_account;

    public String getReferee_account() {
        return referee_account;
    }

    public void setReferee_account(String referee_account) {
        this.referee_account = referee_account;
    }

    public String getXh_share_link() {
        return xh_share_link;
    }

    public void setXh_share_link(String xh_share_link) {
        this.xh_share_link = xh_share_link;
    }

    public int getSalesman_id() {
        return salesman_id;
    }

    public void setSalesman_id(int salesman_id) {
        this.salesman_id = salesman_id;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getJoin_fee() {
        return join_fee;
    }

    public void setJoin_fee(int join_fee) {
        this.join_fee = join_fee;
    }

    public String getIdentity_card_front() {
        return identity_card_front;
    }

    public void setIdentity_card_front(String identity_card_front) {
        this.identity_card_front = identity_card_front;
    }

    public String getIdentity_card_con() {
        return identity_card_con;
    }

    public void setIdentity_card_con(String identity_card_con) {
        this.identity_card_con = identity_card_con;
    }

    public int getInvest_fee() {
        return invest_fee;
    }

    public void setInvest_fee(int invest_fee) {
        this.invest_fee = invest_fee;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentity_card_no() {
        return identity_card_no;
    }

    public void setIdentity_card_no(String identity_card_no) {
        this.identity_card_no = identity_card_no;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public int getCommision() {
        return commision;
    }

    public void setCommision(int commision) {
        this.commision = commision;
    }

    public int getOperation_money() {
        return operation_money;
    }

    public void setOperation_money(int operation_money) {
        this.operation_money = operation_money;
    }
}
