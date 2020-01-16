package com.leisurely.spread.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by jbuy on 2018/4/17.
 */

public class CustomerService {

    @JSONField(name = "customer_service")
    private String id;
    private String number;
    @JSONField(name = "nickname")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
