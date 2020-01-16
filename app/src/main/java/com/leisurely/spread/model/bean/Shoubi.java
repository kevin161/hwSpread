package com.leisurely.spread.model.bean;

/**
 * Created by Administrator on 2018/12/12.
 */

public class Shoubi {
    private String number;
    private long addtime;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getAddtime() {
        return addtime*1000;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }
}
