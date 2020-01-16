package com.leisurely.spread.framework.httprequest;

import android.graphics.Bitmap;
import android.view.View;

/**
 * 网络图片加载结果回调
 * @author xcl
 * create at 2015/12/3 16:04
 */
public interface ImageCallback {
    /**
     * 网络图片加载回调
     * @param url http图片地址
     * @param bitmap 图片对象
     * @param view 图片关联控件（加载图片的控件 一般为ImageView）
     */
    public void callback(String url, Bitmap bitmap, View view);
}
