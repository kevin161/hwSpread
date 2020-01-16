package com.leisurely.spread.model.bean;

public class Zijin {

    private String asset;
    private int type;//收支类型： 0-	收入1-	支出

    private String affectMoney;
    private String createTime;
    private String operateWayName;

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAffectMoney() {
        return affectMoney;
    }

    public void setAffectMoney(String affectMoney) {
        this.affectMoney = affectMoney;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOperateWayName() {
        return operateWayName;
    }

    public void setOperateWayName(String operateWayName) {
        this.operateWayName = operateWayName;
    }
}
