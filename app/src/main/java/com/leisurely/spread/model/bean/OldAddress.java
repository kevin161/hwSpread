package com.leisurely.spread.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by jbuy on 2018/4/21.
 */

public class OldAddress {

    private String id;

    private String state;

    @JSONField(name = "country_id")
    private String countryId;

    private String country;

    private String consignee;

    @JSONField(name = "country_mobile")
    private String countryMobile;

    private String mobile;

    private String postalcode;

    private String province;

    private String city;

    private String address;

    private String type;

    private String country2;

    private String consignee2;

    private String province2;

    private String city2;

    private String address2;

    @JSONField(serialize =  false)
    private OldAddress vice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCountryMobile() {
        return countryMobile;
    }

    public void setCountryMobile(String countryMobile) {
        this.countryMobile = countryMobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry2() {
        return country2;
    }

    public void setCountry2(String country2) {
        this.country2 = country2;
    }

    public String getConsignee2() {
        return consignee2;
    }

    public void setConsignee2(String consignee2) {
        this.consignee2 = consignee2;
    }

    public String getProvince2() {
        return province2;
    }

    public void setProvince2(String province2) {
        this.province2 = province2;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public OldAddress getVice() {
        return vice;
    }

    public void setVice(OldAddress vice) {
        this.vice = vice;
    }
}
