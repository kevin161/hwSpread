package com.leisurely.spread.UI.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;


public class ItemShopViewpager extends RelativeLayout {

    private ImageView home_banner;
    View view;
    private BaseActivity mcontext;
    private TextView home_banner_title, home_banner_en;

    public ItemShopViewpager(BaseActivity context) {
        super(context);
        mcontext = context;
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_homeviewpager, this);

        home_banner = (ImageView) view.findViewById(R.id.home_banner);
        home_banner_en = (TextView) view.findViewById(R.id.home_banner_en);
        home_banner_title = (TextView) view.findViewById(R.id.home_banner_title);
    }

    public void set(final String banner) {
        mcontext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //设置图片圆角角度
                RoundedCorners roundedCorners= new RoundedCorners(20);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

                Glide.with(mcontext).load(banner).apply(options).into(home_banner);
//                ImageOptions.showImageRound(banner.getImage(), home_banner, ImageOptions.getRoundOptions(20));
            }
        });


    }

}
