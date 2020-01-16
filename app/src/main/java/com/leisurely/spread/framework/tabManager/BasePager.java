package com.leisurely.spread.framework.tabManager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;


public abstract class BasePager implements Litener {

    protected Boolean isfirst;//是否是第一次加载
    protected static Context mContext;// 给子类提供上下文对象
    public View rootView;// 每一个界面对象创建时，就把自己的布局创建出来，赋值给成员变量

    protected String tag = "";
    protected LayoutInflater mInflater;

    public BasePager(Context context) {
        this(context, null);
    }

    public BasePager(Context context, String tag) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.tag = tag;
        isfirst = true;

        rootView = initView();
    }

    //每一个界面都有xml--->view操作,生成布局操作
    public abstract View initView();

    // 子类更新布局，不必须实现
    public void initData() {

    }

    public View getRootView() {
        return rootView;
    }




    @Override
    public void onResume( ) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
