package com.leisurely.spread.model.enums;

/**
 * Created by jbuy on 2018/4/26.
 */

public enum DeliveryChannelEnum {

    JIYUN(1, "集运"), EMS(2, "EMS"), TNT_S(3, "TNT"), DPEX(4, "DPEX"),
    FEDEX_BAG(5, "FedEx"), FEDEX_BOX(6, "FedEx"), SF(7, "顺丰速运"),
    TNT_N(8, "TNT"), TNT_B(9, "TNT"), DPEX_B(10, "DPEX"), DHL_N(11, "DHL"),
    DHL_B(12, "DHL"), DHL_E(13, "DHL"), DISMANTLING(19, "拆拍"), WAIT_SLELCT(20, "待选渠道");

    private int id;

    private String name;

    DeliveryChannelEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // 普通方法
    public static String getName(int id) {
        for (DeliveryChannelEnum e : DeliveryChannelEnum.values()) {
            if (e.getId() == id) {
                return e.name;
            }
        }
        return null;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
