package com.leisurely.spread.model.enums;

/**
 * Created by jbuy on 2018/4/28.
 */

public enum YZMEnum {
    REGISTER("register", "注册"), FORGETPWD("forgetpwd", "忘记密码"), CHANGEMOBILE("changemobile", "修改手机号")
    , CHANGEEMAIL("changeemail", "绑定邮箱"), RESETPWD("resetpwd", "修改密码"), THIRDREG("thirdreg", "第三方注册")
    ,RESETPAYPWD("paymobile","修改支付密码"),RECHARGE("recharge","充币"),WITHDRAWAL("withdrawal","提币");

    private String id;

    private String name;

    YZMEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
