package com.leisurely.spread.framework.httprequest;

import com.alibaba.fastjson.JSON;

/**
 * http网络请求回调接口
 *
 * @author xcl
 *         create at 2015/12/3 15:35
 */
public interface HttpCallback {
    /**
     * 网络请求开始
     *
     * @param url http网络地址
     * @param tag 请求标记
     */
    public void onStart(String url, String tag);

    /**
     * 网络请求成功
     *
     * @param url      http网络地址
     * @param response 请求成功返回的结果
     * @param tag      请求标记
     */
    public void onSuccess(String url, JSON response, String tag);

    /**
     * 非硬件问题请求失败时给用户提示
     *
     * @param tag
     */
    public void onqueryError(int statuscode, int code, String message, String tag);

    /**
     * 网络请求失败
     *
     * @param url   http网络地址
     * @param error 请求错误对象
     * @param tag   请求标记
     */
    public void onFail(String url, Object error, String tag);

    /**
     * 网络请求结束
     *
     * @param url http网络地址
     * @param tag 请求标记
     */
    public void onEnd(String url, String tag);
}
