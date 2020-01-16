package com.leisurely.spread.framework.httprequest;

/**
 * 网络请求工具工厂
 * @author xcl
 * create at 2015/12/3 16:31
 */
public class MyHttp {
    //http网络请求对象
    private static HttpRequest httpRequest;
    /**
     * 获取网络请求工具类对象 单例
     * @return 网络请求工具类对象
     */
    public static HttpRequest getHttpRequest()
    {
        if(httpRequest==null)
        {
            httpRequest = new OkHttp();
        }
        return  httpRequest;
    }

}
