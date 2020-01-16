package com.leisurely.spread.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2018/12/11.
 */

public class Chongbi {

    @JSONField(name = "number_actual")
    private String count;

    private long addtime;

    private int status;//0=未处理,1=成功,2=失败



    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public long getAddtime() {
        return addtime*1000;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
