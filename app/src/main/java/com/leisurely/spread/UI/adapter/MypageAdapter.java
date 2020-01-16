package com.leisurely.spread.UI.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.leisurely.spread.framework.tabManager.BasePager;


public class MypageAdapter extends PagerAdapter {
    private List<BasePager> pagers;


    public MypageAdapter(List<BasePager> pagers) {
        this.pagers = pagers;
    }



    @Override
    public int getCount() {
        return pagers.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        /**
         * 根据不同的position，返回不同的布局
         *
         * 返回控件
         * if(position==0){
         *  home.initView();
         * }
         * if(position==1){
         * view.inflate();
         * }
         *
         * 更新数据
         * if(position==0){
         * home.initData();
         * }
         * if(position==1){
         * 更新控件
         * }
         *
         * new Home();
         * home.initView();
         * home.initData();
         *
         * Pager initView initData
         *
         * List<Pager> pagers = new ArrayList<Pager>();
         *
         * Pager pager = pagers.get(position)
         * pager.initView
         * pager.initData
         *
         */
        BasePager pager = pagers.get(position);
        // 把当前位置的pager对象身上的布局添加到ViewPager中
        View view = pager.getRootView();
        container.addView(view);
        // 把当前位置的pager对象的布局更新数据
//			pager.initData(); 避免预加载浪费流量
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}

