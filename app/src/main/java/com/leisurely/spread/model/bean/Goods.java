package com.leisurely.spread.model.bean;

import java.util.List;

public class Goods {
    private String name;
    private String price;
    private List<String> goodImgs;
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

    public List<String> getGoodImgs() {
        return goodImgs;
    }

    public void setGoodImgs(List<String> goodImgs) {
        this.goodImgs = goodImgs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
