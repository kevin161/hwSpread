package com.leisurely.spread.okhttp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.leisurely.spread.okhttp.builder.GetBuilder;
import com.leisurely.spread.okhttp.builder.HeadBuilder;
import com.leisurely.spread.okhttp.builder.OtherRequestBuilder;
import com.leisurely.spread.okhttp.builder.PostFileBuilder;
import com.leisurely.spread.okhttp.builder.PostFormBuilder;
import com.leisurely.spread.okhttp.builder.PostStringBuilder;
import com.leisurely.spread.okhttp.callback.Callback;
import com.leisurely.spread.okhttp.request.RequestCall;
import com.leisurely.spread.okhttp.utils.Platform;
import com.leisurely.spread.util.StringUtil;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 30_000L;
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }

        mPlatform = Platform.get();
    }


    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }


    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }
                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            response.body().byteStream()));
                    for (String s = reader.readLine(); s != null; s = reader.readLine()) {
                        builder.append(s);
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        String result = builder.toString();
                        String message = "";
                        int code = 0;
                        //   sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        if (StringUtil.isNotNull(result)) {
                            JSONObject json = JSON.parseObject(result);
                            message = json.getString("msg");
                            code = json.getIntValue("code");
                        }
                        sendFailErrorCallback(response.code(), code, message, finalCallback, id);
                        return;
                    }
//                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(builder.toString(), finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }

        });
    }


    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");

    private final OkHttpClient client = new OkHttpClient();

    public String upLoadFile(String url, String filePath, String token, String fileName) {
        try {
            // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
             RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("title", "Square Logo")
                    .addFormDataPart("file", fileName + ".jpg",
                            RequestBody.create(MEDIA_TYPE_PNG, new File(filePath)))
                    .addFormDataPart("token", token)
                    .build();

            Request request = new Request.Builder()
                    .header("Authorization", "Bearer " + token)
                    .url(url)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            } else {
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        response.body().byteStream()));
                for (String s = reader.readLine(); s != null; s = reader.readLine()) {
                    builder.append(s);
                }
                return builder.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void postFile(final String url,String token, File file,final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("file", filename, body)
                    .addFormDataPart("token", token);

        }
//        if (map != null) {
//            // map 里面是请求中所需要的 key 和 value
//            Set<Map.Entry<String, String>> entries = map.entrySet();
//            for (Map.Entry entry : entries) {
//                String key = String.valueOf(entry.getKey());
//                String value = String.valueOf(entry.getValue());
//                requestBody.addFormDataPart(key,value);
//            }
//        }
        Request request = new Request.Builder().addHeader("token",token).url(url).post(requestBody.build()).build();
        // readTimeout("请求超时时间" , 时间单位);
        mOkHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }
                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            response.body().byteStream()));
                    for (String s = reader.readLine(); s != null; s = reader.readLine()) {
                        builder.append(s);
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        String result = builder.toString();
                        String message = "";
                        int code = 0;
                        //   sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        if (StringUtil.isNotNull(result)) {
                            JSONObject json = JSON.parseObject(result);
                            message = json.getString("msg");
                            code = json.getIntValue("code");
                        }
                        sendFailErrorCallback(response.code(), code, message, finalCallback, id);
                        return;
                    }
//                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(builder.toString(), finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });

    }

    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }


    private void sendFailErrorCallback(final int statuscode, final int code, final String message, final Callback callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.queryError(statuscode, code, message, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) return;
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                String s = (String) object;
                JSON json;
                if(s.indexOf("[")==0){
                    json = JSON.parseObject("{\"data\":"+s+"}");
                }else{
                    json = JSON.parseObject(s);
                }
                callback.onResponse(json, id);
                callback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }

}

