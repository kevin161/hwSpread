package com.leisurely.spread.framework.httprequest;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.okhttp.OkHttpUtils;
import com.leisurely.spread.okhttp.callback.MapCallback;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.SignUtils;
import com.leisurely.spread.util.StringUtil;

import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * http网络请求和网络图片加载工具
 *
 * @author xcl
 *         create at 2015/12/3 16:02
 */
public class OkHttp implements HttpRequest {

    /*-----------------------网络接口请求---------------------------*/

    @Override
    public void onRequest(String request, String url, HttpRequestParams params, HttpCallback callback) {
        onRequest(request, url, params, null, callback, null);
    }

    @Override
    public void onRequest(String request, String url, HttpRequestParams params, String tag, HttpCallback callback) {
        doRequest(request, url, params, tag, callback, null);
    }

    public void onRequest(String request, String url, HttpRequestParams params, String tag, HttpCallback callback, String filePath) {
        doRequest(request, url, params, tag, callback, filePath);
    }

    /*----------------------------------XUtils http部分----------------------------*/
    public void doRequest(String request, String url, HttpRequestParams params, String tag, HttpCallback callback, String filePath) {
        String body = "";
        if (params.getBodys().containsKey("body")) {
            body = params.getBodys().get("body");
        }
        switch (request) {
            case METHOD.GET:
                String urls = url + SignUtils.getUrl(params.getBodys());
                OkHttpUtils.get().url(urls).headers(params.getHeaders()).build().execute(new OkHttpCallBack(urls, tag, callback));
                break;
            case METHOD.POST:
                if (StringUtil.isNotNull(body)) {
                    OkHttpUtils.post().url(url).params((Map<String, String>) JSONObject.parse(body))
                            .headers(params.getHeaders()).build().execute(new OkHttpCallBack(url, tag, callback));
                } else {
                    OkHttpUtils.post().url(url).params(params.getBodys()).headers(params.getHeaders()).build()
                            .execute(new OkHttpCallBack(url, tag, callback));
                }
                break;
            case METHOD.POSTSTRING:
                OkHttpUtils
                        .postString()
                        .url(url)
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .content(body).headers(params.getHeaders())
                        .build().execute(new OkHttpCallBack(url, tag, callback));
                break;
            case METHOD.PATCH:
                OkHttpUtils.patch().url(url).requestBody(RequestBody.create(MediaType.parse(params.getHeaders().get("Content-Type")), body))
                        .headers(params.getHeaders()).build().execute(new OkHttpCallBack(url, tag, callback));
                break;
            case METHOD.PUT:
                OkHttpUtils.put().url(url).requestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body))
                        .headers(params.getHeaders()).build().execute(new OkHttpCallBack(url, tag, callback));
                break;
            case METHOD.DELETE:
                OkHttpUtils.delete().url(url).headers(params.getHeaders()).build().execute(new OkHttpCallBack(url, tag, callback));
                break;
            case METHOD.PUTSTRING:
                OkHttpUtils.put().url(url).requestBody(body).headers(params.getHeaders()).build().execute(new OkHttpCallBack(url, tag, callback));
                break;
            case METHOD.POSTFILE:
                OkHttpUtils.post().url(url).build().execute(new OkHttpCallBack(url, tag, callback), url, filePath,
                        SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
                break;
        }
    }

    /**
     * XUtils http请求回调
     */
    private class OkHttpCallBack extends MapCallback {

        private String tag;
        private String url;
        private HttpCallback callback;

        public OkHttpCallBack(String url, String tag, HttpCallback callback) {
            this.tag = tag;
            this.url = url;
            this.callback = callback;
            callback.onStart(url, tag);
        }

        @Override
        public void queryError(int statuscode, int code, String message, int id) {
            super.queryError(statuscode, code, message, id);
            callback.onqueryError(statuscode, code, message, tag);
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            callback.onFail(url, e, tag);
        }

        @Override
        public void onResponse(JSONObject response, int id) {
            callback.onSuccess(url, response, tag);
        }

        @Override
        public void onAfter(int id) {
            super.onAfter(id);
            callback.onEnd(url, tag);
        }
    }


    public static class METHOD {
        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String POSTSTRING = "POSTSTRING";
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PUTSTRING = "PUTSTRING";
        public static final String PATCH = "PATCH";
        public static final String POSTFILE = "POSTFILE";
    }
}
