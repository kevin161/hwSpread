package com.leisurely.spread.model.enums;

/**
 * Created by jbuy on 2018/4/26.
 */

public enum PayMethodEnum {

    BALANCE(2, "余额"), WECHAT(0, "微信"), ALIPAY(1, "支付宝"),
    BANK_CARD(4, "银行卡");

    private int id;

    private String name;

    PayMethodEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
