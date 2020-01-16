package com.leisurely.spread.UI.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.GoodsDetailActivity;
import com.leisurely.spread.util.ImageOptions;



public class ItemGooddetailViewpager extends RelativeLayout {

	private ImageView home_banner;
	View view;
	private GoodsDetailActivity mcontext;
	private TextView home_banner_title,home_banner_en;

	public ItemGooddetailViewpager(GoodsDetailActivity context) {
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

	public void set(final String url) {
		// TODO Auto-generated method stub
//		home_banner_title.setText(url.activity_name);
//		home_banner_en.setText(url.memo);
//		FinalBitmap.create(mcontext).display(home_banner, url);
		mcontext.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageOptions.showImage(url, home_banner);
			}
		});
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
