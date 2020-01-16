package com.leisurely.spread.UI.activity.stock;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.MainActivity;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.util.SharedPreferencesUtil;


public class AppstartNavigationActivity extends BaseActivity {

    private ViewPager mViewPager;
    //导航页图片资源
//    private int[] guides = new int[]{R.mipmap.navigation_1,
//            R.mipmap.navigation_2, R.mipmap.navigation_3};
    private List<View> mViewList;//view集合
    private int selectIndex;//当前位置

    @Override
    protected void initView() {
        setContentView(R.layout.activity_appstartnavigation);
        mViewPager = findViewById(R.id.navigation_view);
        initWithPageGuideMode();
    }

    /**
     * 程序导航页效果
     */
    public void initWithPageGuideMode() {
        List<View> mList = new ArrayList<View>();
        LayoutInflater inflat = LayoutInflater.from(this);
        //先添加一个最左侧空的view
        View item;
//        for (int index : guides) {
//            item = inflat.inflate(R.layout.pageguide, null);
//            item.setBackgroundResource(index);
//            mList.add(item);
//        }

        MyViewPageAdapter adapter = new MyViewPageAdapter(mList);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(adapter);
        mViewPager.setCurrentItem(0);

        //为了滑动流畅，使用setOnTouchListener，注意，viewpager的setOnclickListener无效，其事件会被里面的子view消费
        mViewPager.setOnTouchListener(onTouchListener);
    }

    /**
     * 内部类，继承PagerAdapter，当然你也可以直接 new PageAdapter
     */
    class MyViewPageAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        public MyViewPageAdapter(List<View> views) {
            mViewList = views;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position), 0);
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            selectIndex = position;
        }

    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        private VelocityTracker mVelocityTracker = null;
        private float mX;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mX = event.getX();
                    if (mVelocityTracker == null) {
                        mVelocityTracker = VelocityTracker.obtain();
                    } else {
                        mVelocityTracker.clear();
                    }
                    mVelocityTracker.addMovement(event);
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (mVelocityTracker == null) {
                        mVelocityTracker = VelocityTracker.obtain();
                    }
                    mVelocityTracker.addMovement(event);
                    mVelocityTracker.computeCurrentVelocity(1000);
                    break;

                case MotionEvent.ACTION_UP:
                    if (mVelocityTracker != null) {
                        mVelocityTracker.computeCurrentVelocity(1000);
                        int velocityX = (int) mVelocityTracker.getXVelocity();
                        mVelocityTracker.recycle();
                        mVelocityTracker = null;
                        if (velocityX < -600 || event.getX() == mX) {// 跳到下一个Activity
                            if (selectIndex == mViewList.size() - 1) {
                                SharedPreferencesUtil.saveString("version", SysConstants.VERSION);
                                Intent intent = new Intent(AppstartNavigationActivity.this, MainActivity.class);
                                startActivity(intent);
                                AppstartNavigationActivity.this.finish();
                            }
                        }
                    }
                    break;

                case MotionEvent.ACTION_CANCEL:
                    if (mVelocityTracker != null) {
                        mVelocityTracker.recycle();
                        mVelocityTracker = null;
                    }
                    break;
            }
            return false;
        }
    };

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
