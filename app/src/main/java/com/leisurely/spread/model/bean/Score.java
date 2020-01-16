package com.leisurely.spread.model.bean;

import com.leisurely.spread.model.enums.ScoreWay;

public class Score {
    private Integer scoreType;//积分类型: 1: 盈利积分, 2: 非盈利积分
    private String type;//操作类型(0-增加,1-减少)
    private int operateWay;
    private String affectMoney;
    private String systemTraceNo;
    private String memo;
    private long createdAt;

    public Integer getScoreType() {
        return scoreType;
    }

    public void setScoreType(Integer scoreType) {
        this.scoreType = scoreType;
    }

    public String isType() {
        return type;
    }

    public void setType(String type) {
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

    public String getSystemTraceNo() {
        return systemTraceNo;
    }

    public void setSystemTraceNo(String systemTraceNo) {
        this.systemTraceNo = systemTraceNo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
