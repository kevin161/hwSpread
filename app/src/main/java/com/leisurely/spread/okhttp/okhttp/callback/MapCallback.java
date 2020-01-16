package com.leisurely.spread.okhttp.okhttp.callback;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class MapCallback extends Callback<JSONObject> {
    @Override
    public JSONObject parseNetworkResponse(Response response, int id) throws IOException {
        JSONObject hashMap = new JSONObject();
        String link = response.header("Link");
        if (!TextUtils.isEmpty(link)) {
            HashMap<String, String> headermap=spitHeader(link);
            if (!headermap.isEmpty()){
                hashMap.put("header",headermap);
            }
        }

        String headerString = response.header("Location");
        String bodyString = response.body().string();
        hashMap.put("headerString",headerString);
        hashMap.put("body",bodyString);
        Log.i("TyAndroid","请求返回数据-->"+ bodyString);
        return hashMap;
    }

    /**
     * <http://192.168.10.10:8083/api/v1/shopCommodities/search?pageSize=2&shopId=1&categoryId=10&pageNo=2>; rel="next",
     * <http://192.168.10.10:8083/api/v1/shopCommodities/search?pageSize=2&shopId=1&categoryId=10&pageNo=3>; rel="last"
     * @param link
     */
    private HashMap<String, String> spitHeader(String link) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String[] names = link.split(",");
        for (String value : names) {
            String[] key = value.split(";");
            if (key.length == 2) {
                hashMap.put(key[1], key[0].substring(1, key[0].length() - 1));
            }
        }
        return hashMap;
    }


}
