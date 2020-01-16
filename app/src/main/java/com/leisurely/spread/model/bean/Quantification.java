package com.leisurely.spread.model.bean;

/**
 * Created by Administrator on 2018/12/11.
 */

public class Quantification {

    private String day;

    private String rate ;

    private String money;

    private long createtime;

    private int isend;//0：收益中；1：收益完成；2:已出局

    private String complete;//已收益多少

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public long getCreatetime() {
        return createtime*1000;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getIsend() {
        return isend;
    }

    public void setIsend(int isend) {
        this.isend = isend;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }
}
