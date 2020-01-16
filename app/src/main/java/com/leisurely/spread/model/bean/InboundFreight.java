package com.leisurely.spread.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by jbuy on 2018/10/31.
 */

public class InboundFreight {

    @JSONField(name = "country_name")
    private String name;

    private String images;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
