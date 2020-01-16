package com.leisurely.spread.framework.httprequest;

import android.content.Context;
import android.widget.ImageView;

/**
 * 网络图片加载对象规范
 * @author xcl
 * create at 2015/12/3 16:21
 */
public interface ImageLoad {
    /**
     * 网络图片加载
     * @param context 上下文对象
     * @param view  图片关联控件（加载图片的控件 一般为ImageView）
     * @param url http图片地址
     * @return 图片加载工具类对象
     */
    public Object loadImage(Context context, ImageView view, String url);

    /**
     * 网络图片加载
     * @param context 上下文对象
     * @param view  图片关联控件（加载图片的控件 一般为ImageView）
     * @param url http图片地址
     * @param defaultImg 默认的图片
     * @return 图片加载工具类对象
     */
    public Object loadImage(Context context, ImageView view, String url, int defaultImg);

    /**
     * 网络图片加载
     * @param context 上下文对象
     * @param view  图片关联控件（加载图片的控件 一般为ImageView）
     * @param url http图片地址
     * @param defaultImg 默认的图片
     * @param failedImg 失败后加载的图片
     * @return 图片加载工具类对象
     */
    public Object loadImage(Context context, ImageView view, String url, int defaultImg, int failedImg);

    /**
     * 网络图片加载
     * @param context 上下文对象
     * @param view  图片关联控件（加载图片的控件 一般为ImageView）
     * @param url http图片地址
     * @param defaultImg 默认的图片
     * @param failedImg 失败后加载的图片
     * @param callback 图片加载回调
     * @return 图片加载工具类对象
     */
    public Object loadImage(Context context, ImageView view, String url, int defaultImg, int failedImg, ImageCallback callback);
}
