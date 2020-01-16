package com.leisurely.spread.okhttp.okhttp.builder;

import com.leisurely.spread.okhttp.okhttp.OkHttpUtils;
import com.leisurely.spread.okhttp.okhttp.request.OtherRequest;
import com.leisurely.spread.okhttp.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
