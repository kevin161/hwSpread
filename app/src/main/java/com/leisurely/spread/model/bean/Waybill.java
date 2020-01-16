package com.leisurely.spread.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by jbuy on 2018/8/30.
 */

public class Waybill {

    private String id;

    @JSONField(name = "orders_sn")
    private String orderSn;

    @JSONField(name = "add_time")
    private long addTime;

    @JSONField(name = "pay_money")
    private double money;

    @JSONField(name = "order_id")
    private String orderIds;

    private int status;//2=已完成,3=已取消

    private int count;

    private List<OldOrder> oldOrder;

    private boolean isEvaluate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public long getAddTime() {
        return addTime*1000;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OldOrder> getOldOrder() {
        return oldOrder;
    }

    public void setOldOrder(List<OldOrder> oldOrder) {
        this.oldOrder = oldOrder;
    }

    public boolean isEvaluate() {
        return isEvaluate;
    }

    public void setEvaluate(boolean evaluate) {
        isEvaluate = evaluate;
    }
}
