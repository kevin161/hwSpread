package com.leisurely.spread.model.bean;

/**
 * Created by jbuy on 2018/7/24.
 */

public class Community {
    private String userid;
    private String money;
    private long createtime;
    private String mobile;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public long getCreatetime() {
        return createtime * 1000;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
