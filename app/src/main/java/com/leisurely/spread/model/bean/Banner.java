package com.leisurely.spread.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by jbuy on 2018/8/8.
 */

public class Banner {

    private String title;
    @JSONField(name = "tocUrl")
    private String image;
    @JSONField(name = "resourceUrl")
    private String url;
    private String id ;
    private String level;
    private String memo;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
