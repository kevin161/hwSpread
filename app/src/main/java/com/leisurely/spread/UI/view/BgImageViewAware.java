package com.leisurely.spread.UI.view;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leisurely.spread.UI.activity.money.PayDetailActivity;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * Created by jbuy on 2018/9/7.
 */

public class BgImageViewAware extends ImageViewAware {

    private ImageView imageView;

    public BgImageViewAware(ImageView imageView) {
        this(imageView, true);
        this.imageView = imageView;
    }

    public BgImageViewAware(ImageView imageView, boolean checkActualViewSize) {
        super(imageView, checkActualViewSize);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean setImageBitmap(Bitmap bitmap) {
        //重写父类方法，将图片设为背景
        if (Looper.myLooper() == Looper.getMainLooper()) {
            if (imageView != null) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.width= imageView.getWidth();
                layoutParams.height =imageView.getWidth()* height/width ;
                imageView.setLayoutParams(layoutParams);
//                imageView.setImageBitmap(null);
//                imageView.setBackground(new BitmapDrawable(bitmap));
                imageView.setImageBitmap(bitmap);
                PayDetailActivity.bitmap =bitmap;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setImageDrawable(Drawable drawable) {
        return super.setImageDrawable(drawable);
    }

}
