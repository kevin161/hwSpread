package com.leisurely.spread.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by jbuy on 2018/5/5.
 */

public class ForeignCurrency {
    @JSONField(name = "zhesuan")
    private double convert;

    @JSONField(name = "hui_in")
    private double abouchement;

    @JSONField(name = "hui_out")
    private double remit;

    @JSONField(name = "time")
    private String time;

    @JSONField(name = "day")
    private String day;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "chao_out")
    private double chaoOut;

    @JSONField(name = "chao_in")
    private double chaoIn;

    @JSONField(name = "code")
    private String code;

    private double rate;

    private int drawable;

    private int logo;

    public double getConvert() {
        return convert;
    }

    public void setConvert(double convert) {
        this.convert = convert;
    }

    public double getAbouchement() {
        return abouchement;
    }

    public void setAbouchement(double abouchement) {
        this.abouchement = abouchement;
    }

    public double getRemit() {
        return remit;
    }

    public void setRemit(double remit) {
        this.remit = remit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getChaoOut() {
        return chaoOut;
    }

    public void setChaoOut(double chaoOut) {
        this.chaoOut = chaoOut;
    }

    public double getChaoIn() {
        return chaoIn;
    }

    public void setChaoIn(double chaoIn) {
        this.chaoIn = chaoIn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
