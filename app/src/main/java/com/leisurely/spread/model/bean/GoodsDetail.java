package com.leisurely.spread.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GoodsDetail implements Parcelable {
//    	        "number": 1,
//                "lbtImg": ["http://114.67.176.178/hbf/uploadimg//image/1578368238574.jpg", "http://114.67.176.178/hbf/uploadimg//image/1578368244815.jpg", "http://114.67.176.178/hbf/uploadimg//image/1578368253148.jpg"],
//                "price": 59880.00,
//                "rewardType": 1,
//                "name": "老村长888号",
//                "surplusStock": 998,
//                "id": 1,
//                "units": "箱",
//                "type": 1,
//                "defaultImgUrl": "http://114.67.176.178/hbf/uploadimg//image/1578368238574.jpg"


    private int number;//商品数量
    //    认购类型：       1：代理商 2：分销商
    private int rewardType;
    private List<String> lbtImg;
    private String price;
    private String name;
    private String surplusStock;//剩余数量
    private String units;//商品单位
    private int id;
    private int type;//商品类型
    private String defaultImgUrl;//默认首页图片

    private String totalStock;
    private String goodDetail;
    private String goodParameter;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDefaultImgUrl() {
        return defaultImgUrl;
    }

    public void setDefaultImgUrl(String defaultImgUrl) {
        this.defaultImgUrl = defaultImgUrl;
    }

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

    public String getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(String totalStock) {
        this.totalStock = totalStock;
    }

    public String getSurplusStock() {
        return surplusStock;
    }

    public void setSurplusStock(String surplusStock) {
        this.surplusStock = surplusStock;
    }

    public String getGoodDetail() {
        return goodDetail;
    }

    public void setGoodDetail(String goodDetail) {
        this.goodDetail = goodDetail;
    }

    public String getGoodParameter() {
        return goodParameter;
    }

    public void setGoodParameter(String goodParameter) {
        this.goodParameter = goodParameter;
    }

    public List<String> getLbtImg() {
        return lbtImg;
    }

    public void setLbtImg(List<String> lbtImg) {
        this.lbtImg = lbtImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.number);
        dest.writeInt(this.rewardType);
        dest.writeStringList(this.lbtImg);
        dest.writeString(this.price);
        dest.writeString(this.name);
        dest.writeString(this.surplusStock);
        dest.writeString(this.units);
        dest.writeInt(this.id);
        dest.writeInt(this.type);
        dest.writeString(this.defaultImgUrl);
        dest.writeString(this.totalStock);
        dest.writeString(this.goodDetail);
        dest.writeString(this.goodParameter);
    }

    public GoodsDetail() {
    }

    protected GoodsDetail(Parcel in) {
        this.number = in.readInt();
        this.rewardType = in.readInt();
        this.lbtImg = in.createStringArrayList();
        this.price = in.readString();
        this.name = in.readString();
        this.surplusStock = in.readString();
        this.units = in.readString();
        this.id = in.readInt();
        this.type = in.readInt();
        this.defaultImgUrl = in.readString();
        this.totalStock = in.readString();
        this.goodDetail = in.readString();
        this.goodParameter = in.readString();
    }

    public static final Parcelable.Creator<GoodsDetail> CREATOR = new Parcelable.Creator<GoodsDetail>() {
        @Override
        public GoodsDetail createFromParcel(Parcel source) {
            return new GoodsDetail(source);
        }

        @Override
        public GoodsDetail[] newArray(int size) {
            return new GoodsDetail[size];
        }
    };
}
