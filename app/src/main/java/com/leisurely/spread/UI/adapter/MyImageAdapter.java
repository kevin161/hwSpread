package com.leisurely.spread.UI.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import com.github.chrisbanes.photoview.PhotoView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.util.ImageOptions;

/**
 * Created by jbuy on 2018/8/15.
 */

public class MyImageAdapter extends PagerAdapter {
    public static final String TAG = MyImageAdapter.class.getSimpleName();
    private List<String> imageUrls;
    private BaseActivity activity;

    public MyImageAdapter(List<String> imageUrls, BaseActivity activity) {
        this.imageUrls = imageUrls;
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final String url = imageUrls.get(position);
        final PhotoView photoView = new PhotoView(activity);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageOptions.showImage(url, photoView);
            }
        });
        container.addView(photoView);

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        return photoView;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}