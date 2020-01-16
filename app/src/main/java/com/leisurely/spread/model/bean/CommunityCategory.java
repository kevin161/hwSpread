package com.leisurely.spread.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by jbuy on 2018/7/24.
 */

public class CommunityCategory {


    private int id;

    @JSONField(name = "type_name")
    private String name;

    private boolean isSelect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
