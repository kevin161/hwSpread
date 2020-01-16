package com.leisurely.spread.framework.base;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.leisurely.spread.UI.activity.home.RegisterOrLoginActivity;
import com.leisurely.spread.UI.activity.setting.LoginActivity;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.httprequest.HttpCallback;
import com.leisurely.spread.framework.httprequest.HttpRequestParams;
import com.leisurely.spread.framework.httprequest.MyHttp;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;


/**
 * 数据模型基类
 *
 * @author xcl
 *         create at 2015/12/2 14:37
 */
public class BaseModel implements HttpCallback {


    protected BaseActivity baseActivity;
    Boolean isShowDialog = false;
    private String token;
    private String tag;


    protected void request(String request, BaseActivity baseActivity, String url, HashMap<String, String> header, Object params, String tag) {
        HashMap<String, String> bodys = new HashMap<>();
        bodys.put("body", JSONObject.toJSONString(params));
        request(true, request, baseActivity, url, header, bodys, tag);
    }

    protected void request(String request, BaseActivity baseActivity, String url, HashMap<String, String> params, String tag) {
        request(false, request, baseActivity, url, params, tag);
    }

    protected void request(Boolean isShowDialog, String request, BaseActivity baseActivity, String url, String tag) {
        request(isShowDialog, request, baseActivity, url, new HashMap<String, String>(), new HashMap<String, String>(), tag);
    }

    protected void request(Boolean isShowDialog, String request, BaseActivity baseActivity, String url, HashMap<String, String> params, String tag) {
        request(isShowDialog, request, baseActivity, url, new HashMap<String, String>(), params, tag);
    }

    protected void request(Boolean isShowDialog, String request, BaseActivity baseActivity, String url,
                           HashMap<String, String> header, HashMap<String, String> params, String tag) {
        this.baseActivity = baseActivity;
        this.isShowDialog = isShowDialog;
        this.tag = tag;
//        header.put("business-version", SysConstants.VERSION);
        Log.e("http","------------------------------");
        for (Map.Entry<String,String> a :params.entrySet()){
            Log.e("http",a.getKey()+"="+a.getValue());
        }
        MyHttp.getHttpRequest().onRequest(request, url, createHttpRequestParams(header, params), tag, this);
    }

    protected void request(Boolean isShowDialog, String request, BaseActivity baseActivity, String url,
                           HashMap<String, String> header, HashMap<String, String> params, String tag, String filePath) {
        this.baseActivity = baseActivity;
        this.isShowDialog = isShowDialog;
        this.tag = tag;
//        header.put("business-version", SysConstants.VERSION);
        MyHttp.getHttpRequest().onRequest(request, url, createHttpRequestParams(header, params), tag, this, filePath);
    }

//    protected void requestList(Boolean isShowDialog, String request, BaseActivity baseActivity, String url, List params, String tag) {
//        requestList(isShowDialog, request, baseActivity, url, new HashMap<String, String>(), params, tag);
//    }
//
//    protected void requestList(Boolean isShowDialog, String request, BaseActivity baseActivity, String url,
// HashMap<String, String> header, List params, String tag) {
//        this.baseActivity = baseActivity;
//        this.isShowDialog = true;
//        this.tag = tag;
//        if (header == null) {
//            header = new HashMap<>();
//        }
//        HashMap<String, String> bodys = new HashMap<>();
//        if (request.equals(OkHttp.METHOD.POSTSTRING)) {
//            bodys.put("body", GsonUtil.getJson(params));
//        }
//        MyHttp.getHttpRequest().onRequest(request, url, createHttpRequestParams(header, bodys), tag, this);
//    }

    @Override
    public void onStart(String url, String tag) {
        if (isShowDialog && baseActivity != null) {
            baseActivity.showProgressDialog("努力加载中");
        }
    }

    @Override
    public void onSuccess(String url, JSON response, String tag) {


    }

    @Override
    public void onqueryError(int statuscode, int code, String message, String tag) {
        if ("refashToken".equals(tag)) {

        } else if (statuscode == 401) {
            ToastUtil.showToast("登录失效,请重新登录");
            baseActivity.startActivity(new Intent(baseActivity, RegisterOrLoginActivity.class));
            SharedPreferencesUtil.saveBoolean(SysConstants.ISLOGIN, false);
        } else {
            if (!TextUtils.isEmpty(message))
                ToastUtil.showToast(message);
        }
    }

    protected void onResult(String url, String result, String tag) {
    }

    //请求为非1000的情况
    protected void queryError(String tag) {

    }

    @Override
    public void onFail(String url, Object error, String tag) {
        queryError(tag);
        ToastUtil.showToast("网络错误,请稍后重试。。");
    }

    @Override
    public void onEnd(String url, String tag) {
        if (isShowDialog && baseActivity != null) {
            baseActivity.dismissProgressDialog();
            isShowDialog = false;
        }
    }


    /**
     * map参数集合转http请求参数集合
     *
     * @param params
     * @return
     */
    protected HttpRequestParams createHttpRequestParams(HashMap<String, String> header, HashMap<String, String> params) {

        HttpRequestParams httpRequestParams = new HttpRequestParams();
        if (header != null) {
            httpRequestParams.headers = header;
//            String token = homeModel.gettoken();
            if (!TextUtils.isEmpty(token)) {
                httpRequestParams.headers.put("token", "Bearer " + token);
            }
            if (StringUtil.isNotNull(SharedPreferencesUtil.readString(SysConstants.CLIENTID, ""))) {
                httpRequestParams.headers.put(SysConstants.CLIENTID, SharedPreferencesUtil.readString(SysConstants.CLIENTID, ""));
            }
        }
        if (params != null && !params.isEmpty()) {
            httpRequestParams.Bodys = params;
        }
        return httpRequestParams;
    }

}
