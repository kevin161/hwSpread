package com.leisurely.spread.model.enums;

/**
 * Created by jbuy on 2018/4/28.
 */

public enum WareHouseEnum {
    JIANGSU(1, "江苏仓"), GUANGZHOU(2, "广州仓");

    private int id;

    private String name;

    WareHouseEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static int getIdWithName(String name){
        for (WareHouseEnum wareHouseEnum:WareHouseEnum.values()){
            if(wareHouseEnum.getName().equals(name)){
                return wareHouseEnum.getId();
            }
        }
        return 0;
    }
}
