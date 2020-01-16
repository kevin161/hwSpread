package com.leisurely.spread.framework.httprequest;

import java.util.HashMap;

/**
 * 网络请求参数对象
 * @author xcl
 * create at 2015/12/3 16:02
 */
public class HttpRequestParams {
    //http header部分参数
    public HashMap<String,String> headers = new HashMap<String,String>();
    //http body部分参数
    public HashMap<String,String> Bodys = new HashMap<String,String>();

    public void setHeaders(String key, String value)
    {
        headers.put(key,value);
    }

    public void setBodys(String key, String value)
    {
        Bodys.put(key,value);
    }

    public HashMap<String,String> getHeaders()
    {
        return headers;
    }

    public HashMap<String,String> getBodys()
    {
        return Bodys;
    }

}
