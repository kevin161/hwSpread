package com.leisurely.spread.model.bean;

/**
 * 支行数据类型
 */
public class BankSub {

    private String bank_id;
    private String branch_name;
    private String open_bank_add;
    private String bank_no;

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getOpen_bank_add() {
        return open_bank_add;
    }

    public void setOpen_bank_add(String open_bank_add) {
        this.open_bank_add = open_bank_add;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }
}
