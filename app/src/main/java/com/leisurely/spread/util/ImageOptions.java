package com.leisurely.spread.util;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.view.BgImageViewAware;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


/**
 * imageloader加载的图片操作
 */
public class ImageOptions {

    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.app_icon).showImageOnFail(R.mipmap.app_icon)
            .cacheInMemory(true).cacheOnDisk(true).build();

    public static DisplayImageOptions optionsOriginal = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.app_icon).showImageOnFail(R.mipmap.app_icon)
            .imageScaleType(ImageScaleType.NONE).cacheInMemory(false).cacheOnDisk(true).build();

    public static DisplayImageOptions noLoadingOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
            .cacheOnDisk(true).build();

    //设置默认加载图片的加载操作
    public static DisplayImageOptions myOptions(int defaultImage) {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(defaultImage).showImageOnFail(defaultImage)
                .imageScaleType(ImageScaleType.NONE).cacheInMemory(false).cacheOnDisk(true).build();
    }

    //直接调用显示用
    public static void showImage(String url, ImageView img) {
        ImageLoader.getInstance().displayImage(url, img, noLoadingOptions);
    }

    //直接调用显示用
    public static void showImageRound(String url, ImageView img,DisplayImageOptions options) {
        ImageLoader.getInstance().displayImage(url, img, options);
    }

    public static DisplayImageOptions getRoundOptions(int radiu) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(radiu))
                .showImageForEmptyUri(R.mipmap.app_icon).showImageOnFail(R.mipmap.app_icon)
                .cacheInMemory(true).cacheOnDisk(true).build();

        return options;

    }



    /**
     * 加载网络图片设置为ImageView背景
     *
     * @param url
     * @param view
     */
    public static void loadImageToBackground(String url, ImageView view) {
        ImageLoader.getInstance().displayImage(url, new BgImageViewAware(view),noLoadingOptions);
    }
}
