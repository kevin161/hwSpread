package com.leisurely.spread.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

/**
 * Created by jbuy on 2018/4/23.
 */

public class MyPackage {

    private int id;

    private String expressform;

    private String expressimg;

    private long datetime;

    @JSONField(name = "package_name")
    private String packageName;

    private int state;//('0' = "待入库","2" = "已入库"

    private int warehouseid;

    @JSONField(name = "warehouseid_text")
    private String warehouseName;

    @JSONField(name = "count_state1")
    private int allCount;

    @JSONField(name = "count_state2")
    private int waitCount;

    @JSONField(name = "count_state3")
    private int doneCount;

    @JSONField(name = "count_warehouse1")
    private int jiangsuCount;

    @JSONField(name = "count_warehouse2")
    private int guangzhouCount;

    @JSONField(name = "dismantling_status")
    private int dismantlingStatus;//拆拍状态:0=未申请,1=已申请待处理,2=已完成

    @JSONField(name = "image_arr")
    private ArrayList<String> images;//拆拍图(有可能为空字符串)

    private boolean isSelect;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpressform() {
        return expressform;
    }

    public void setExpressform(String expressform) {
        this.expressform = expressform;
    }

    public long getDatetime() {
        return datetime * 1000;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(int warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(int waitCount) {
        this.waitCount = waitCount;
    }

    public int getDoneCount() {
        return doneCount;
    }

    public void setDoneCount(int doneCount) {
        this.doneCount = doneCount;
    }

    public int getJiangsuCount() {
        return jiangsuCount;
    }

    public void setJiangsuCount(int jiangsuCount) {
        this.jiangsuCount = jiangsuCount;
    }

    public int getGuangzhouCount() {
        return guangzhouCount;
    }

    public void setGuangzhouCount(int guangzhouCount) {
        this.guangzhouCount = guangzhouCount;
    }

    public int getDismantlingStatus() {
        return dismantlingStatus;
    }

    public void setDismantlingStatus(int dismantlingStatus) {
        this.dismantlingStatus = dismantlingStatus;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getExpressimg() {
        return expressimg;
    }

    public void setExpressimg(String expressimg) {
        this.expressimg = expressimg;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
