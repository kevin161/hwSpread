package com.leisurely.spread.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2018/12/29.
 */

public class SellDetail {

    private String id;
    private String orderno;
    @JSONField(name ="cny")
    private String amount;
    private String buyerprice;
@JSONField(name = "its")
    private String num;

private long createtime;

private String buyername;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBuyerprice() {
        return buyerprice;
    }

    public void setBuyerprice(String buyerprice) {
        this.buyerprice = buyerprice;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public long getCreatetime() {
        return createtime *1000;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }
}


