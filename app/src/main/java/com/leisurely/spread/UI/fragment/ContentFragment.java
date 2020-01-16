package com.leisurely.spread.UI.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.pager.HomePager;
import com.leisurely.spread.UI.activity.pager.PersonalCenterPager;
import com.leisurely.spread.UI.activity.pager.QuantificationPager;
import com.leisurely.spread.UI.adapter.MypageAdapter;
import com.leisurely.spread.UI.view.LazyViewPager;
import com.leisurely.spread.UI.view.MyViewPager;
import com.leisurely.spread.framework.base.MyBaseFragment;
import com.leisurely.spread.framework.tabManager.BasePager;
import com.leisurely.spread.framework.tabManager.TabManager;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends MyBaseFragment {

    private MyViewPager vp_content_pagers;
    private CommonTabLayout mTabLayout_2;
    private List<BasePager> pagers;

    private TabManager tab;
    public static int currtnepostion = 0;
    public static int currtSize = 0;
//    private ImageView iv_bottom;
//    private ViewGroup.MarginLayoutParams parama;

    private List<String> titles;
    private List<Integer> mIconUnselectIds;
    private List<Integer> mIconSelectIds;


    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.content_fragment, null);
        vp_content_pagers = view.findViewById(R.id.vp_content_pagers);
        mTabLayout_2 = view.findViewById(R.id.mTabLayout_2);
//        iv_bottom = (ImageView) view.findViewById(R.id.iv_bottom);
//        parama = (ViewGroup.MarginLayoutParams) iv_bottom.getLayoutParams();
//        parama.bottomMargin = DensityUtil.dip2px(mActivity, 20);
//        iv_bottom.setLayoutParams(parama);
        initTab();


        return view;
    }

    private void initTab() {
        titles = new ArrayList<>();
        mIconUnselectIds = new ArrayList<>();
        mIconSelectIds = new ArrayList<>();

        titles.add("首页");
        mIconUnselectIds.add(R.mipmap.home_icon);
        mIconSelectIds.add(R.mipmap.home_sle_icon);

        titles.add("店铺");
        mIconUnselectIds.add(R.mipmap.dianpu_icon);
        mIconSelectIds.add(R.mipmap.dianpu_sle_icon);

//        titles.add("分享");
//        mIconUnselectIds.add(R.mipmap.sharebutton_icon);
//        mIconSelectIds.add(R.mipmap.sharebutton__sle_icon);

        titles.add("我的");
        mIconUnselectIds.add(R.mipmap.me_icon);
        mIconSelectIds.add(R.mipmap.me_sle_icon);


        tab = new TabManager(titles, mIconUnselectIds, mIconSelectIds);
    }

    @Override
    protected void initData() {
        // 准备数据
        pagers = new ArrayList<>();
        pagers.add(new HomePager(mActivity));
        pagers.add(new QuantificationPager(mActivity));
//        pagers.add(new SharePager(mActivity));
        pagers.add(new PersonalCenterPager(mActivity));
        // 更新Adapter
        vp_content_pagers.setAdapter(new MypageAdapter(pagers));
        // 监听ViewPager
        vp_content_pagers.setOnPageChangeListener(new MyOnPageChangeListener());
        pagers.get(0).initData();
        vp_content_pagers.setCurrentItem(0, false);
        tl_2();
        currtSize = 0;
        setMessage(currtSize);
//        iv_bottom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currtnepostion == 1) {
//                    ((AwardPager) pagers.get(currtnepostion)).startNewCommodityCode();
//
//                }
//            }
//        });
    }

    class MyOnPageChangeListener implements LazyViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
//            if (position != 1 && position != 2) {
                mTabLayout_2.setCurrentTab(position);
                // 当选中某一页时，才加载数据
                pagers.get(position).initData();
//            HomePager.isInitList = false;
//            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }

    public void setMessage(int num) {
        //两位数
        mTabLayout_2.showMsg(0, num);
        mTabLayout_2.setMsgMargin(0, -25, 6);
    }

    private void tl_2() {
        mTabLayout_2.setTabData(tab.getTab());
        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    currtSize = 0;
                    setMessage(currtSize);
                }
//                setImageVible(position);
//                if (position != 1 && position != 2) {
                    currtnepostion = position;
                    vp_content_pagers.setCurrentItem(position, false);
//                }
            }

            @Override
            public void onTabReselect(int position) {
//                setImageVible(position);
//                if (position != 1 && position != 2) {
                    currtnepostion = position;
                    vp_content_pagers.setCurrentItem(position, false);
//                }
            }
        });

    }

    public void onEventMainThread() {
        if (currtnepostion != 0) {
            currtSize = currtSize + 1;
            setMessage(currtSize);
        }

    }

//    public void setImageVible(int position) {
//        if (position == 1) {
//            iv_bottom.setVisibility(View.VISIBLE);
//        } else {
//            iv_bottom.setVisibility(View.GONE);
//        }
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if (pagers.get(currtnepostion) instanceof PersonalCenterPager) {
//            try {
//                // 上传文件后续动作
//                if (((PersonalCenterPager) pagers.get(currtnepostion)).getmImageSelectUtil().onActivityResult(requestCode, resultCode, data)) {
//                    ((PersonalCenterPager) pagers.get(currtnepostion)).uploadLogoSuccess();
//                    return;
//                }
//            } catch (Exception e) {
////                Log.e("上传头像", e.getMessage(), e);
//            }
//        }

        switch (requestCode) { //resultCode为回传的标记，
//            case 1111:
//                if (pagers.get(currtnepostion) instanceof PersonalCenterPager) {
//                    ((PersonalCenterPager) pagers.get(currtnepostion)).onActivityResult(requestCode, resultCode, data);
//                }else if(pagers.get(currtnepostion) instanceof  HomePager){
//                    ((HomePager) pagers.get(currtnepostion)).onActivityResult(requestCode, resultCode, data);
//                }
//                break;
            default:
                break;
        }
    }

    public void initViewPager() {
//        ((HomePager) pagers.get(0)).tianjiaView();
    }


    public void setCurrentTab(int position) {
//        if (position != 1 && position != 2) {
            mTabLayout_2.setCurrentTab(position);
            vp_content_pagers.setCurrentItem(position, false);
            pagers.get(position).initData();
            currtnepostion = position;
//        }
    }


//    public void setVisibility(boolean isShow) {
//        ViewGroup.LayoutParams lp =     mTabLayout_2.getLayoutParams();
//        if (isShow) {
//            if(!show) {
//                mTabLayout_2.setLayoutParams(new ViewGroup.LayoutParams(lp.width, buttomHeight));
//                show = true;
//            }
//        } else {
//            if(show) {
//                buttomHeight = lp.height;
//                mTabLayout_2.setLayoutParams(new ViewGroup.LayoutParams(lp.width, 0));
//                show = false;
//            }
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        pagers.get(currtnepostion).onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        pagers.get(currtnepostion).onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        pagers.get(currtnepostion).onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pagers.get(currtnepostion).onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
