package com.leisurely.spread.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by jbuy on 2018/4/16.
 */

public class User {

    private String id;

    @JSONField(name = "mobile")
    private String tellphone;

    @JSONField(name = "country_mobile")
    private String code;

    private String password;

    @JSONField(name = "captcha")
    private String verificationCode;

    @JSONField(name = "customer_service")
    private String customerservice;//客服号

    private String username;//用户名

    @JSONField(name = "img")
    private String headPortrait;

    private double balance;

    @JSONField(name = "couponnum")
    private int couponCount;

    private String recommend;

    private String avatar;

    private int score;

    private String level;

    private String userdate;

    private boolean wechat;

    private String trueid;

    private String its;//可用资产

//    private String investment;//量化资产

    private String gross;//总资产

    private String nickname;

    private String truename;

    private String serviceqq;

    private String servicewx;

//    private String fundpool;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTellphone() {
        return tellphone;
    }

    public void setTellphone(String tellphone) {
        this.tellphone = tellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustomerservice() {
        return customerservice;
    }

    public void setCustomerservice(String customerservice) {
        this.customerservice = customerservice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUserdate() {
        return userdate;
    }

    public void setUserdate(String userdate) {
        this.userdate = userdate;
    }

    public boolean isWechat() {
        return wechat;
    }

    public void setWechat(boolean wechat) {
        this.wechat = wechat;
    }

    public String getTrueid() {
        return trueid;
    }

    public void setTrueid(String trueid) {
        this.trueid = trueid;
    }

    public String getIts() {
        return its;
    }

    public void setIts(String its) {
        this.its = its;
    }


    public String getGross() {
        return gross;
    }

    public void setGross(String gross) {
        this.gross = gross;
    }

    public String getNickname() {
        return nickname;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getServiceqq() {
        return serviceqq;
    }

    public void setServiceqq(String serviceqq) {
        this.serviceqq = serviceqq;
    }

    public String getServicewx() {
        return servicewx;
    }

    public void setServicewx(String servicewx) {
        this.servicewx = servicewx;
    }

}

