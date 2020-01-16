package com.leisurely.spread.model.bean;

public class Withdrawal {

    private String userId;
    private String orderNo;
    private String amount;
    private String fee;
    private String success_money;
//    0.待审核
//1.审核通过
//2.支付处理中
//3.提现成功
//4.提现失败
//5.审核不通过
    private int status;
    private String memos;
    private long createdAt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSuccess_money() {
        return success_money;
    }

    public void setSuccess_money(String success_money) {
        this.success_money = success_money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMemos() {
        return memos;
    }

    public void setMemos(String memos) {
        this.memos = memos;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
