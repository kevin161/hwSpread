package com.leisurely.spread.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class Money {

    private String uid;
    private int type;//操作类型(0-增加,1-减少)
    private int operateWay;//操作方式1: 冻结 2: 解冻 3: 充值 4: 提现
    private String affectMoney;
    private long createdAt;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOperateWay() {
        return operateWay;
    }

    public void setOperateWay(int operateWay) {
        this.operateWay = operateWay;
    }

    public String getAffectMoney() {
        return affectMoney;
    }

    public void setAffectMoney(String affectMoney) {
        this.affectMoney = affectMoney;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
