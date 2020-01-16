package com.leisurely.spread.UI.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.setting.HtmlActivity;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.bean.Banner;
import com.leisurely.spread.util.StringUtil;


public class ItemHomeViewpager extends RelativeLayout {

    private ImageView home_banner;
    View view;
    private BaseActivity mcontext;
    private TextView home_banner_title, home_banner_en;

    public ItemHomeViewpager(BaseActivity context) {
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

    public void set(final Banner banner) {
        // TODO Auto-generated method stub
//		home_banner_title.setText(url.activity_name);
//		home_banner_en.setText(url.memo);
//		FinalBitmap.create(mcontext).display(home_banner, url);
        mcontext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //设置图片圆角角度
                RoundedCorners roundedCorners= new RoundedCorners(20);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

                Glide.with(mcontext).load(banner.getImage()).apply(options).into(home_banner);
//                ImageOptions.showImageRound(banner.getImage(), home_banner, ImageOptions.getRoundOptions(20));
            }
        });

        if (StringUtil.isNotNull(banner.getUrl())) {
            home_banner.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mcontext.startActivity(new Intent(mcontext, HtmlActivity.class)
                            .putExtra("url", banner.getUrl()));
                }
            });
        } else {
            home_banner.setOnClickListener(null);
        }
//		if (url.resource_url.length() > 0) {
//			view.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					Intent intent = new Intent();
//					intent.setClass(mcontext, BannerWebviewAct.class);
//					intent.putExtra("url", url);
////					intent.putExtra("activity_name", url.activity_name);
//					mcontext.startActivity(intent);
//				}
//			});
//		} else {
//			view.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//				}
//			});
//		}

    }

}
