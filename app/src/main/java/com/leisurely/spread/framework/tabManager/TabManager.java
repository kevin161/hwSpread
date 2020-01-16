package com.leisurely.spread.framework.tabManager;

import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: xcl
 * Date: 2016-07-22
 * Time: 13:15
 * 首页下面tab动态控制
 */
public class TabManager {

    public static int OrderPisition = 2;
    public static int WalletPisition = 1;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<String> mTitles ;
//    private String[] morderTitles = {"店内单", "配送单", "未接单", "配送中", "取消"};
    private List<Integer> mIconUnselectIds;
    private List<Integer> mIconSelectIds;

    public TabManager(List<String> mTitles, List<Integer> mIconUnselectIds, List<Integer> mIconSelectIds) {
        this.mTitles = mTitles;
        this.mIconUnselectIds = mIconUnselectIds;
        this.mIconSelectIds = mIconSelectIds;
    }

    public ArrayList<CustomTabEntity> getTab() {
        mTabEntities.clear();
        for (int i = 0; i < mTitles.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles.get(i), mIconSelectIds.get(i), mIconUnselectIds.get(i)));
        }
        return mTabEntities;
    }

//    public ArrayList<CustomTabEntity> getOrderTab() {
//        mTabEntities.clear();
//        for (int i = 0; i < morderTitles.length; i++) {
//            mTabEntities.add(new TabEntity(morderTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
//        }
//        return mTabEntities;
//    }

}
