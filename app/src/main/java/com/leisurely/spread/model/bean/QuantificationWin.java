package com.leisurely.spread.model.bean;

/**
 * Created by Administrator on 2018/12/12.
 */

public class QuantificationWin {

    private String id ;
    private String userid;
    private String its;
    private long createtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIts() {
        return its;
    }

    public void setIts(String its) {
        this.its = its;
    }

    public long getCreatetime() {
        return createtime*1000;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
}
