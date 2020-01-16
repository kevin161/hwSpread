package com.leisurely.spread.model.bean;

/**
 * Created by Administrator on 2018/12/29.
 */

public class OrderDetail {

    private String id;
    private String orderno;
    private String number;
    private String amount;
    private int paytype;
    //0=交易中,1=已完成,2=已取消
    private int status;
    private long addtime;
    private Merchant merchant;
    private String cny;
    private String its;
    private long createtime;
    private String buyername;
    private String buyerprice;
    private String price;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getPaytype() {
        return paytype;
    }

    public void setPaytype(int paytype) {
        this.paytype = paytype;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getAddtime() {
        return addtime * 1000;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getCny() {
        return cny;
    }

    public void setCny(String cny) {
        this.cny = cny;
    }

    public String getIts() {
        return its;
    }

    public void setIts(String its) {
        this.its = its;
    }

    public long getCreatetime() {
        return createtime * 1000;
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

    public String getBuyerprice() {
        return buyerprice;
    }

    public void setBuyerprice(String buyerprice) {
        this.buyerprice = buyerprice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public class Merchant {
        private String name;
        private String price;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}


