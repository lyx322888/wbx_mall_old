package com.wbx.mall.bean;

/**
 * @author Zero
 * @date 2018/7/6
 */
public class WithdrawRecordBean {
    /**
     * cash_id : 107
     * money : 20
     * commission : 10
     * bank_name :
     * bank_num : ***
     * bank_realname :
     * addtime : 1524207721
     * status : 已提现
     * type : 业务抽成，
     */

    private int cash_id;
    private int money;
    private int commission;
    private String bank_name;
    private String bank_num;
    private String bank_realname;
    private int addtime;
    private int status;
    private String type;

    public int getCash_id() {
        return cash_id;
    }

    public void setCash_id(int cash_id) {
        this.cash_id = cash_id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
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

    public String getBank_realname() {
        return bank_realname;
    }

    public void setBank_realname(String bank_realname) {
        this.bank_realname = bank_realname;
    }

    public int getAddtime() {
        return addtime;
    }

    public void setAddtime(int addtime) {
        this.addtime = addtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
