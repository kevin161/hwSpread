package com.leisurely.spread.model.bean;

/**
 * Created by Administrator on 2018/12/29.
 */

public class PayWay {

    private int id;

    private String userid;

    private int type;//支付方式 1 支付宝 2微信 3银行卡

    private String name;

    private String account;//账号

    private String image;

    private String bankdeposit;

    private String bankbranch;

    private long createtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBankdeposit() {
        return bankdeposit;
    }

    public void setBankdeposit(String bankdeposit) {
        this.bankdeposit = bankdeposit;
    }

    public String getBankbranch() {
        return bankbranch;
    }

    public void setBankbranch(String bankbranch) {
        this.bankbranch = bankbranch;
    }

    public long getCreatetime() {
        return createtime * 1000;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
}
