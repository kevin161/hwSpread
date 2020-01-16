package com.leisurely.spread.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by jbuy on 2018/4/24.
 */

public class OldOrder {

    private int id;

    @JSONField(name = "unique_id")
    private String uniqueId;

    private double allcost;

    @JSONField(name  ="number_box")
    private int boxNumber;

    @JSONField(name = "add_time")
    private long time;

    @JSONField(name = "above_images")
    private String aboveImages;


    @JSONField(name = "images")
    private String innerImages;

    @JSONField(name = "zh_name")
    private String countryName;

    @JSONField(name = "name")
    private String warehouseName;

    private String wname;//渠道名称


    private List<Box> box ; //箱子数据集

    @JSONField(name = "actual_weight")
    private double actualWeight;

    @JSONField(name = "is_confirmation")
    private int confirmationType;

    @JSONField(name = "channelid")
    private String channelId;

    @JSONField(name = "sheet_images")
    private String sheetImages;

    private boolean isSelect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public double getAllcost() {
        return allcost;
    }

    public void setAllcost(double allcost) {
        this.allcost = allcost;
    }

    public int getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(int boxNumber) {
        this.boxNumber = boxNumber;
    }

    public long getTime() {
        return time*1000;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAboveImages() {
        return aboveImages;
    }

    public void setAboveImages(String aboveImages) {
        this.aboveImages = aboveImages;
    }

    public String getInnerImages() {
        return innerImages;
    }

    public void setInnerImages(String innerImages) {
        this.innerImages = innerImages;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public List<Box> getBox() {
        return box;
    }

    public void setBox(List<Box> box) {
        this.box = box;
    }

    public double getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(double actualWeight) {
        this.actualWeight = actualWeight;
    }

    public int getConfirmationType() {
        return confirmationType;
    }

    public void setConfirmationType(int confirmationType) {
        this.confirmationType = confirmationType;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getSheetImages() {
        return sheetImages;
    }

    public void setSheetImages(String sheetImages) {
        this.sheetImages = sheetImages;
    }
}
