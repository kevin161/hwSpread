package com.leisurely.spread.model.enums;

/**
 * Created by jbuy on 2018/4/25.
 */

public enum OrderStatusEnum {

    CKECK_PENDING(0, "待审核"), NOT_AUDITED(1, "未通过审核"), PACKING(2, "打包中"), WAIT_PAY(3, "待支付"),
    WAIT_TRANSPORT(4, "待出库"), TRANSPORT(20, "转运中"), FINISH(30, "完成");

    private int id;

    private String name;

    OrderStatusEnum(int id, String name) {
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
