package com.leisurely.spread.UI.activity.setting;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.pager.PhotoViewPager;
import com.leisurely.spread.UI.adapter.MyImageAdapter;
import com.leisurely.spread.framework.base.BaseActivity;

/**
 * Created by jbuy on 2018/8/15.
 */

public class PhotoViewActivity extends BaseActivity {

    private PhotoViewPager mViewPager;
    private int currentPosition;
    private MyImageAdapter adapter;
    private TextView mTvImageCount;
    private TextView mTvSaveImage;
    private List<String> urls;

    @Override
    protected void initView() {
        setContentView(R.layout.picture_slither);
        mViewPager = findViewById(R.id.viewPage);
        mTvImageCount =  findViewById(R.id.count);
    }


    @Override
    protected void initListener() {
        mViewPager.setOnClickListener(this);

    }

    protected void initData() {

        Intent intent = getIntent();
        currentPosition = intent.getIntExtra("currentPosition", 0);
//        HomeQuestionListModel.DataBeanX DataBean = ((HomeQuestionListModel.DataBeanX) intent.getSerializableExtra("questionlistdataBean"));
        urls = JSONArray.parseArray(getIntent().getStringExtra("urls"),String.class);

        adapter = new MyImageAdapter(urls, this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition, false);
        mTvImageCount.setText(currentPosition+1 + "/" + urls.size());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                mTvImageCount.setText(currentPosition + 1 + "/" + urls.size());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewPage:
                finish();
                break;

            default:
                break;
        }
    }
}