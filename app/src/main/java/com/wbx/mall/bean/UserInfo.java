package com.wbx.mall.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by wushenghui on 2017/7/10.
 */

public class UserInfo implements Serializable {
    private int user_id;
    private String face;
    private String nickname;
    private String account;
    private String mobile;
    private int money;
    private int integral;
    private int subsidy_money;
    private String icon;
    private String rank_name;
    private int buygreens_dfk_count;
    private int buygreens_dfh_count;
    private int buygreens_dsh_count;
    private int rank_id;
    private String cashBankNum;
    private String cashBankName;
    private String bankRealName;
    private String bankBranch;
    private String hx_username;
    private int is_salesman;
    private int is_sign;
    private int deposit;
    private int rank;
    private int is_rank_pay;
    private String rank_audit;
    private int dfk_count;
    private int dfh_count;
    private int dsh_count;
    private int dtk_count;
    private int ywc_count;

    public int getIs_rank_pay() {
        return is_rank_pay;
    }

    public void setIs_rank_pay(int is_rank_pay) {
        this.is_rank_pay = is_rank_pay;
    }

    public int getIs_sign() {
        return is_sign;
    }

    public void setIs_sign(int is_sign) {
        this.is_sign = is_sign;
    }

    public int getDfk_count() {
        return dfk_count;
    }

    public void setDfk_count(int dfk_count) {
        this.dfk_count = dfk_count;
    }

    public int getDfh_count() {
        return dfh_count;
    }

    public void setDfh_count(int dfh_count) {
        this.dfh_count = dfh_count;
    }

    public int getDsh_count() {
        return dsh_count;
    }

    public void setDsh_count(int dsh_count) {
        this.dsh_count = dsh_count;
    }

    public int getDtk_count() {
        return dtk_count;
    }

    public void setDtk_count(int dtk_count) {
        this.dtk_count = dtk_count;
    }

    public int getYwc_count() {
        return ywc_count;
    }

    public void setYwc_count(int ywc_count) {
        this.ywc_count = ywc_count;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank_audit() {
        if (TextUtils.isEmpty(rank_audit)) {
            return 0;
        } else {
            return Integer.valueOf(rank_audit);
        }
    }

    public void setRank_audit(String rank_audit) {
        this.rank_audit = rank_audit;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getIs_salesman() {
        return is_salesman;
    }

    public void setIs_salesman(int is_salesman) {
        this.is_salesman = is_salesman;
    }

    public String getHx_username() {
        return hx_username;
    }

    public void setHx_username(String hx_username) {
        this.hx_username = hx_username;
    }

    public int getRank_id() {
        return rank_id;
    }

    public void setRank_id(int rank_id) {
        this.rank_id = rank_id;
    }

    public String getCashBankNum() {
        return cashBankNum;
    }

    public void setCashBankNum(String cashBankNum) {
        this.cashBankNum = cashBankNum;
    }

    public String getCashBankName() {
        return cashBankName;
    }

    public void setCashBankName(String cashBankName) {
        this.cashBankName = cashBankName;
    }

    public String getBankRealName() {
        return bankRealName;
    }

    public void setBankRealName(String bankRealName) {
        this.bankRealName = bankRealName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getSubsidy_money() {
        return subsidy_money;
    }

    public void setSubsidy_money(int subsidy_money) {
        this.subsidy_money = subsidy_money;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRank_name() {
        return rank_name;
    }

    public void setRank_name(String rank_name) {
        this.rank_name = rank_name;
    }

    public int getBuygreens_dfk_count() {
        return buygreens_dfk_count;
    }

    public void setBuygreens_dfk_count(int buygreens_dfk_count) {
        this.buygreens_dfk_count = buygreens_dfk_count;
    }

    public int getBuygreens_dfh_count() {
        return buygreens_dfh_count;
    }

    public void setBuygreens_dfh_count(int buygreens_dfh_count) {
        this.buygreens_dfh_count = buygreens_dfh_count;
    }

    public int getBuygreens_dsh_count() {
        return buygreens_dsh_count;
    }

    public void setBuygreens_dsh_count(int buygreens_dsh_count) {
        this.buygreens_dsh_count = buygreens_dsh_count;
    }

    public int getBuygreens_dtk_count() {
        return buygreens_dtk_count;
    }

    public void setBuygreens_dtk_count(int buygreens_dtk_count) {
        this.buygreens_dtk_count = buygreens_dtk_count;
    }

    public int getBuygreens_ywc_count() {
        return buygreens_ywc_count;
    }

    public void setBuygreens_ywc_count(int buygreens_ywc_count) {
        this.buygreens_ywc_count = buygreens_ywc_count;
    }

    public int getShop_dfk_count() {
        return shop_dfk_count;
    }

    public void setShop_dfk_count(int shop_dfk_count) {
        this.shop_dfk_count = shop_dfk_count;
    }

    public int getShop_dfh_count() {
        return shop_dfh_count;
    }

    public void setShop_dfh_count(int shop_dfh_count) {
        this.shop_dfh_count = shop_dfh_count;
    }

    public int getShop_dsh_count() {
        return shop_dsh_count;
    }

    public void setShop_dsh_count(int shop_dsh_count) {
        this.shop_dsh_count = shop_dsh_count;
    }

    public int getShop_dtk_count() {
        return shop_dtk_count;
    }

    public void setShop_dtk_count(int shop_dtk_count) {
        this.shop_dtk_count = shop_dtk_count;
    }

    public int getShop_ywc_count() {
        return shop_ywc_count;
    }

    public void setShop_ywc_count(int shop_ywc_count) {
        this.shop_ywc_count = shop_ywc_count;
    }

    private int buygreens_dtk_count;
    private int buygreens_ywc_count;
    private int shop_dfk_count;
    private int shop_dfh_count;
    private int shop_dsh_count;
    private int shop_dtk_count;
    private int shop_ywc_count;

}
