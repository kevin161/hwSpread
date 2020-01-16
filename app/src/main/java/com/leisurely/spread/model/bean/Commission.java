package com.leisurely.spread.model.bean;

public class Commission {

    private String proceedsUid;
    private String payUid;
    private String orderId;
    private String memo;
    private String realCommission;
    private String commission;
    private long createAt;

    public String getProceedsUid() {
        return proceedsUid;
    }

    public void setProceedsUid(String proceedsUid) {
        this.proceedsUid = proceedsUid;
    }

    public String getPayUid() {
        return payUid;
    }

    public void setPayUid(String payUid) {
        this.payUid = payUid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRealCommission() {
        return realCommission;
    }

    public void setRealCommission(String realCommission) {
        this.realCommission = realCommission;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }
}
