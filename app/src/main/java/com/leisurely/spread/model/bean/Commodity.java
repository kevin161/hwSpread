package com.leisurely.spread.model.bean;

import java.io.Serializable;
import java.util.List;

public class Commodity implements Serializable {
    //    	"imgUrl": "http://114.67.176.178/hbf/uploadimg//image/1578368123535.jpg",
////                "isStatus": 1,
////                "isDelete": 1,
////                "id": 1,
////                "synopsis": "酒一般",
////                "sort": 1,
////                "typeName": "酒",
////                "type": 1
    private int type;
    private String typeName;
    private String imgUrl;
    private int isStatus;
    private int isDelete;
    private int sort;
    private String id;
    private String synopsis;


    private List<Goods> goods;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(int isStatus) {
        this.isStatus = isStatus;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
