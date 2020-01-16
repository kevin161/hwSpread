package com.leisurely.spread.model.bean;

/**
 * Created by Administrator on 2018/12/24.
 */

public class ITS {

    private String id;

    private String name;

    //标签逗号隔开
    private String label;

    //库存
    private String stock;

    private double minimum;

    private double maximum;

    private double price;

    //支付方式逗号隔开:1=支付宝,2=微信支付,3=银行卡支付
    private String paymentdata;

    //	成交量
    private String volume;

//	成交率
    private String rate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPaymentdata() {
        return paymentdata;
    }

    public void setPaymentdata(String paymentdata) {
        this.paymentdata = paymentdata;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
