package com.leisurely.spread.framework.base;

/**
 * 外层数据对象
 * @author xcl
 * create at 2015/12/2 16:15
 */
public class BaseBean {

    private String responseReq;
    private String responseTime;
    private String message;
    private String code;
    private String accountStr;
    private Object responseBody;


    public String getResponseReq() {
        return responseReq;
    }

    public void setResponseReq(String responseReq) {
        this.responseReq = responseReq;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccountStr() {
        return accountStr;
    }

    public void setAccountStr(String accountStr) {
        this.accountStr = accountStr;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

}
