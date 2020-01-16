package com.leisurely.spread.util;//package com.leisurely.xxy.util;
//
//import android.app.Dialog;
//import android.graphics.drawable.Animatable;
//import android.net.Uri;
//import android.support.annotation.Nullable;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.controller.BaseControllerListener;
//import com.facebook.drawee.controller.ControllerListener;
//import com.facebook.drawee.drawable.ScalingUtils;
//import com.facebook.drawee.generic.GenericDraweeHierarchy;
//import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
//import com.facebook.drawee.interfaces.DraweeController;
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.facebook.imagepipeline.image.ImageInfo;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ScheduledExecutorService;
//
//import com.leisurely.xxy.R;
//import com.leisurely.xxy.framework.base.BaseActivity;
//
///**
// * Created by jbuy on 2018/4/25.
// */
//
//public class PictureSlither {
//
//    private List<String> imageUris = new ArrayList<>();
//
//    private BaseActivity context;
//
//    private ViewPager viewPager;
//    private TextView count;
//
//    private Dialog dialog;
//
//    private float mPosX;
//    private float mPosY;
//    private float mCurPosX;
//    private float mCurPosY;
//
//    private SimpleDraweeView[] simpleDraweeViews;
//
//    private int currentItem;
////    private ScheduledExecutorService executor;
//
//    private int size;
//
//    private final int MULTIPLE = 100;
//
//    public PictureSlither(BaseActivity context) {
//        this.context = context;
//    }
//
//    public void init(List<String> imageUris, ViewPager viewPager, Dialog dialog, TextView count) {
//        this.imageUris = imageUris;
//        this.viewPager = viewPager;
//        this.dialog = dialog;
//        this.count = count;
//        size = imageUris.size();
//        initTextViews(initSize());
//        initViewPager();
//        count.setText((currentItem % size + 1) + "/" + size);
//    }
//
//
////    private void startAutoScroll() {
////        stopAutoScroll();
////
////        executor = Executors.newSingleThreadScheduledExecutor();
////        Runnable command = new Runnable() {
////            @Override
////            public void run() {
////                selectNextItem();
////            }
////
////            private void selectNextItem() {
////                ((BaseActivity) context).runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        viewPager.setCurrentItem(++currentItem);
////                    }
////                });
////            }
////        };
////        int delay = 2;
////        int period = 2;
////        TimeUnit timeUnit = TimeUnit.SECONDS;
////        executor.scheduleAtFixedRate(command, delay, period, timeUnit);
////    }
//
////    private void stopAutoScroll() {
////        if (executor != null) {
////            executor.shutdownNow();
////        }
////    }
//
//    private void initViewPager() {
//
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//
//                    case MotionEvent.ACTION_DOWN:
//                        mPosX = event.getX();
//                        mPosY = event.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        mCurPosX = event.getX();
//                        mCurPosY = event.getY();
//
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        if (mCurPosX - mPosX > 0
//                                && (Math.abs(mCurPosX - mPosX) > 25)) {
//                            //向左滑動
////                            tiShi(mContext,"向左");
//                            viewPager.setCurrentItem(--currentItem);
//                            count.setText((currentItem % size + 1) + "/" + size);
//                            return false;
//                        } else if (mCurPosX - mPosX < 0
//                                && (Math.abs(mCurPosX - mPosX) > 25)) {
//                            //向右滑动
//                            viewPager.setCurrentItem(++currentItem);
//                            count.setText((currentItem % size + 1) + "/" + size);
//                            return false;
////                            tiShi(mContext,"向右");
//                        } else if (mCurPosY - mPosY > 0
//                                && (Math.abs(mCurPosY - mPosY) > 25)) {
//                            //向下滑動
////                            tiShi(mContext,"向下");
//                            return false;
//                        } else if (mCurPosY - mPosY < 0
//                                && (Math.abs(mCurPosY - mPosY) > 25)) {
//                            //向上滑动
////                            tiShi(mContext,"向上");
//                            return false;
//                        } else {
//                            dialog.dismiss();
//                        }
//                        break;
//                }
//                return true;
//            }
//        });
//
//        viewPager.setAdapter(new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return imageUris.size() * MULTIPLE; // 取一个大数字
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//                return view == object;
//            }
//
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                SimpleDraweeView t = simpleDraweeViews[position % simpleDraweeViews.length];
//                t.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(context.getResources())
////                        .setFadeDuration(3000)
//                        .setPlaceholderImage(R.mipmap.refreshing)
//                        .setPlaceholderImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
////                        .setProgressBarImage(new ProgressBarDrawable()) // 显示进度条（Fresco自带的进度条）
//                        .build();
//                // 设置图片圆角
////                RoundingParams roundingParams = new RoundingParams();
////                roundingParams.setRoundAsCircle(false); // 不将图片剪切成圆形
////                roundingParams.setCornersRadius(200);
////                hierarchy.setRoundingParams(roundingParams);
//                // 为SimpleDraweeView设置属性
//                t.setHierarchy(hierarchy);
//                container.addView(t);
//
//                Uri uri = Uri.parse(imageUris.get(position % simpleDraweeViews.length));
//                t.setImageURI(uri);
//
//                setControllerListener(t,imageUris.get(position % simpleDraweeViews.length),context.getResources().getDisplayMetrics().widthPixels);
//
//                return t;
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                container.removeView((View) object);
//            }
//        });
//
////        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
////            @Override
////            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////
////            }
////
////            @Override
////            public void onPageSelected(int position) {
////                currentItem = position;
////
////                startAutoScroll(); // 手动切换完成后恢复自动播放
////            }
////
////            @Override
////            public void onPageScrollStateChanged(int state) {
////
////            }
////        });
//
//        currentItem = imageUris.size() * MULTIPLE / 2; // 取一个中间的大数字, 防止接近边界
//        viewPager.setCurrentItem(currentItem);
//    }
//
//    private void initTextViews(int size) {
//        SimpleDraweeView[] tvs = new SimpleDraweeView[size];
//
//        for (int i = 0; i < tvs.length; i++) {
//            tvs[i] = new SimpleDraweeView(context);
//            tvs[i].getHierarchy().setPlaceholderImage(R.mipmap.refreshing);
//            tvs[i].getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
//            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            tvs[i].setLayoutParams(layoutParams);
//            simpleDraweeViews = tvs;
//        }
//    }
//
//    public static void setControllerListener(final SimpleDraweeView simpleDraweeView, String imagePath, final int imageWidth) {
//        final ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
//        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
//            @Override
//            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
//                if (imageInfo == null) {
//                    return;
//                }
//                int height = imageInfo.getHeight();
//                int width = imageInfo.getWidth();
//                if(width>imageWidth){
//                    layoutParams.width = imageWidth;
//                    layoutParams.height = (int) ((float) (imageWidth * height) / (float) width);
//                }
//                simpleDraweeView.setLayoutParams(layoutParams);
//            }
//
//            @Override
//            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
//                Log.d("TAG", "Intermediate image received");
//            }
//
//            @Override
//            public void onFailure(String id, Throwable throwable) {
//                throwable.printStackTrace();
//            }
//        };
//        DraweeController controller = Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener).setUri(Uri.parse(imagePath)).build();
//        simpleDraweeView.setController(controller);
//    }
//
//    private int initSize() {
//        List<String> list = new ArrayList<>();
//        if (imageUris.size() == 1) {
//            list.add(imageUris.get(0));
//            list.add(imageUris.get(0));
//            list.add(imageUris.get(0));
//            list.add(imageUris.get(0));
//            list.add(imageUris.get(0));
//            list.add(imageUris.get(0));
//            imageUris = list;
//        } else if (imageUris.size() < 4) {
//
//            for (String s : imageUris) {
//                list.add(s);
//            }
//            for (String s : imageUris) {
//                list.add(s);
//            }
//            imageUris = list;
//        } else {
//
//        }
//        return imageUris.size();
//    }
//
//}
