package com.leisurely.spread.UI.activity.setting;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.ImageOptions;
import com.leisurely.spread.util.StringUtil;


public class ImageActivity extends BaseActivity {

    private ImageView image;
    private String title;
    private int imageId;
    private String type;
    private XclModel xclModel;

    /**
     * 初始化布局
     */
    protected void initView() {
        title = getIntent().getStringExtra("title");
        imageId = getIntent().getIntExtra("image", 0);
        type = getIntent().getStringExtra("type");

        setContentView(R.layout.activity_image);
        setTitleName(title);
        image = findViewById(R.id.image_li);

        if (StringUtil.isNotNull(type)) {
            xclModel = new XclModel(this);
        } else {
            image.setBackground(getResources().getDrawable(imageId));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {


    }

    public void getBannerSuccess(JSONObject jsonObject) {
        final String imageUrl = jsonObject.getString("image");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageOptions.loadImageToBackground(imageUrl, image);
            }
        });
        String url = jsonObject.getString("url");
        if (StringUtil.isNotNull(url)) {
            final String url1 = url;
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ImageActivity.this, HtmlActivity.class)
                            .putExtra("url", url1));
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
