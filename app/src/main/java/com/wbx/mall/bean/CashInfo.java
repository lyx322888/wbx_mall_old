package com.wbx.mall.bean;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/7/18.
 */

public class CashInfo implements Serializable {
    /**
     * account : 15153391847
     * money : 50678
     * nickname : hello
     * bank_name : 工商银行
     * bank_num : 15010000256458894
     * bank_branch : 观音山支行
     * bank_realname : 周杰伦
     * cash_second : 100
     * cash_money : 200
     * cash_money_big : 50000
     * cash_commission : 0.16
     */

    private String account;
    private double money;
    private String nickname;
    private String bank_name;
    private String bank_num;
    private String bank_branch;
    private String bank_realname;
    private String cash_second;
    private String cash_money;
    private String cash_money_big;
    private String cash_commission;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_num() {
        return bank_num;
    }

    public void setBank_num(String bank_num) {
        this.bank_num = bank_num;
    }

    public String getBank_branch() {
        return bank_branch;
    }

    public void setBank_branch(String bank_branch) {
        this.bank_branch = bank_branch;
    }

    public String getBank_realname() {
        return bank_realname;
    }

    public void setBank_realname(String bank_realname) {
        this.bank_realname = bank_realname;
    }

    public String getCash_second() {
        return cash_second;
    }

    public void setCash_second(String cash_second) {
        this.cash_second = cash_second;
    }

    public String getCash_money() {
        return cash_money;
    }

    public void setCash_money(String cash_money) {
        this.cash_money = cash_money;
    }

    public String getCash_money_big() {
        return cash_money_big;
    }

    public void setCash_money_big(String cash_money_big) {
        this.cash_money_big = cash_money_big;
    }

    public String getCash_commission() {
        return cash_commission;
    }

    public void setCash_commission(String cash_commission) {
        this.cash_commission = cash_commission;
    }
}
