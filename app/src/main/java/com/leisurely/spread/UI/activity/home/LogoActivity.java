package com.leisurely.spread.UI.activity.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.setting.MainAct;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.widget.permission.MyPermission;
import com.leisurely.spread.widget.permission.Permission;
import com.leisurely.spread.widget.permission.PermissionListener;

import java.util.List;

public class LogoActivity extends BaseActivity {

    //    private XclModel xclModel;
    private ImageView imageView; //广告
    private LinearLayout linearLayout;//跳过广告
    private LinearLayout linearLayout2;//左下角广告字样
    private TextView durationText; //倒计时秒数

    private final String ADVERTISEMENT = "advertisement";
    private final String ADVERTISEMENT_INDEX = "advertisementIndex";
    public static final String ADVERTISEMENT_LOG = "advertisementLog";

    private long startTime;
    private long endTime;

    private long duration;

    private boolean isChange = true;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

//    private List<AdvertisementStoreListViewModel> list;
//    private int index;

//    private List<AdvertisementDeliveringLogViewModel> logs;
//
//    private TimeCount timeCount;

    @Override
    protected void initView() {
//        xclModel = new XclModel(this);
        setContentView(R.layout.activity_main);
//        imageView = (ImageView) findViewById(R.id.logo_image);
//        linearLayout = (LinearLayout) findViewById(R.id.logo_lay);
//        linearLayout2 = (LinearLayout) findViewById(R.id.logo_lay2);
//        durationText = (TextView) findViewById(R.id.logo_time);
    }

    @Override
    protected void initListener() {
//        linearLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (SysConstants.VERSION.equals(SharedPreferencesUtil.readString("version", ""))) {
                //加载过导航了 不是第一次启动app
                new MyPermission(LogoActivity.this, Permission.INTERNET, new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {

//                            xclModel.getAdvertisement();
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        ToastUtil.makeTextAndShow("未打开网络权限！");
//                            xclModel.getAdvertisement();
                    }
                }).request();
//                startActivity(new Intent(LogoActivity.this, HomeActivity.class));
//                if (SharedPreferencesUtil.readBoolean(SysConstants.ISLOGIN, false)) {
//                    startActivity(new Intent(LogoActivity.this, HomeActivity.class));
//                } else {
//                    startActivity(new Intent(LogoActivity.this, RegisterOrLoginActivity.class));
//                }
//                } else {//未加载过导航 是第一次启动app
//                    Intent it = new Intent(LogoActivity.this, AppstartNavigationActivity.class);
//                    LogoActivity.this.startActivity(it);
//                    LogoActivity.this.finish();
//                }

            }
        }, 300);

    }

    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通文件存储，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            if (isChange) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
            isChange = !isChange;
        } else {
            startActivity(new Intent(LogoActivity.this, MainAct.class));
//            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermission();
//        initData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.logo_lay:
//                closeAdvertisement();
//                timeCount.cancel();
//                break;
        }
    }

//    public void getAdvertisementSuccess(String result) {
//        String advertisementString = SharedPreferencesUtil.readString(ADVERTISEMENT, "");
//        index = SharedPreferencesUtil.readInt(ADVERTISEMENT_INDEX, 0);
//        if (StringUtil.isNullOrEmpty(result)) {
//            if (StringUtil.isNullOrEmpty(advertisementString)) {
//                finishThis();
//            } else {
//                list = (List<AdvertisementStoreListViewModel>) GsonUtil.getModel(advertisementString, new TypeToken<List<AdvertisementStoreListViewModel>>() {
//                }.getType());
//                for (int i = 0; i < list.size(); i++) {
//                    if (list.get(i).getPosition() != 4) {
//                        list.remove(i);
//                        i--;
//                    }
//                }
//                if (index >= list.size()) {
//                    index = 0;
//                }
//                if (list.size() > 0) {
//                    showAdvertisement(list.get(index).getUrl(), list.get(index).getDuration(), list.get(index).getId());
//                    SharedPreferencesUtil.saveInt(ADVERTISEMENT_INDEX, index + 1);
//                } else {
//                    finishThis();
//                }
//            }
//        } else {
//            if (isNewAdvertisement(result, advertisementString)) {
//                list = (List<AdvertisementStoreListViewModel>) GsonUtil.getModel(advertisementString, new TypeToken<List<AdvertisementStoreListViewModel>>() {
//                }.getType());
//            } else {
//                advertisementString = result;
//                SharedPreferencesUtil.saveString(ADVERTISEMENT, advertisementString);
//                list = (List<AdvertisementStoreListViewModel>) GsonUtil.getModel(advertisementString, new TypeToken<List<AdvertisementStoreListViewModel>>() {
//                }.getType());
//                index = 0;
//            }
//            for (int i = 0; i < list.size(); i++) {
//                if (list.get(i).getPosition() != 4) {
//                    list.remove(i);
//                    i--;
//                }
//            }
//            if (index >= list.size()) {
//                index = 0;
//            }
//            if (list.size() > 0) {
//
//                showAdvertisement(list.get(index).getUrl(), list.get(index).getDuration(), list.get(index).getId());
//                SharedPreferencesUtil.saveInt(ADVERTISEMENT_INDEX, index + 1);
//                final List<AdvertisementStoreListViewModel> advertisements = list;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (AdvertisementStoreListViewModel aslvm : advertisements) {
//                            try {
//                                saveAdvertisement(aslvm.getUrl(), aslvm.getId(), false);
//                            } catch (Exception e) {
//
//                            }
//                        }
//                    }
//                }).start();
//            } else {
//                finishThis();
//            }
//        }
//    }

//    private boolean isNewAdvertisement(String result, String advertisementString) {
//        if (StringUtil.isNullOrEmpty(advertisementString)) {
//            return false;
//        }
//        List<AdvertisementStoreListViewModel> list1 = (List<AdvertisementStoreListViewModel>) GsonUtil.getModel(result, new TypeToken<List<AdvertisementStoreListViewModel>>() {
//        }.getType());
//        List<AdvertisementStoreListViewModel> list2 = (List<AdvertisementStoreListViewModel>) GsonUtil.getModel(advertisementString, new TypeToken<List<AdvertisementStoreListViewModel>>() {
//        }.getType());
//
//        if (list1.size() != list2.size()) {
//            return false;
//        }
//        int i = 0;
//        for (AdvertisementStoreListViewModel a : list1) {
//            for (AdvertisementStoreListViewModel b : list2) {
//                if (a.getId().equals(b.getId())) {
//                    i++;
//                    break;
//                }
//            }
//        }
//
//        return list1.size() == i;
//    }

//    private void showAdvertisement(String url, int duration, String id) {
//        try {
//            this.duration = duration;
//            bitmap = saveAdvertisement(url, id, true);
//            if (bitmap != null) {
//                advertisement();
//            }
//        } catch (Exception e) {
//            closeAdvertisement();
//        }
//    }

//    private void advertisement() {
//        startTime = DateUtil.currentimeLong();
//        imageView.setImageBitmap(bitmap);
//        linearLayout.setVisibility(View.VISIBLE);
//        linearLayout2.setVisibility(View.VISIBLE);
//        timeCount = new TimeCount(duration, 200);
//        timeCount.start();
//    }

//    /**
//     * @param url1   图片url
//     * @param id     广告id
//     * @param change 是否改变图片
//     * @return
//     * @throws Exception
//     */
//    private Bitmap saveAdvertisement(final String url1, final String id, final boolean change) throws Exception {
//        if (FileUtil.fileIsExists(SysConstants.DOWNLOAD_PATH + id + ".png")) {
//            FileInputStream fis = new FileInputStream(SysConstants.DOWNLOAD_PATH + id + ".png");
//            bitmap = BitmapFactory.decodeStream(fis);
//        }
//
//        if (bitmap == null) {
//            new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        //创建一个url对象
//                        URL url = new URL(url1);
//                        //打开URL对应的资源输入流
//                        InputStream is = url.openStream();
//                        //从InputStream流中解析出图片
//                        bitmap = BitmapFactory.decodeStream(is);
//                        if (bitmap == null) {
//                            finishThis();
//                            return;
//                        }
//                        ImageUtils.savaImage(bitmap, SysConstants.DOWNLOAD_PATH, id);
//                        //  imageview.setImageBitmap(bitmap);
//                        //发送消息，通知UI组件显示图片
//                        if (change) {
//                            handler.sendEmptyMessage(0x9527);
//                        }
//                        //关闭输入流
//                        is.close();
//                    }catch (Exception e) {
//                        if(change){
//                            finishThis();
//                        }
//                        e.printStackTrace();
//                    }
//                }
//            }.start();
//        }
//
//        return bitmap;
//    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

//    class TimeCount extends CountDownTimer {
//        public TimeCount(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        @Override
//        public void onFinish() {
//            closeAdvertisement();
//        }
//
//        @Override
//        public void onTick(long arg0) {
//            long min = arg0 / 1000;
//            durationText.setText(String.valueOf(min));
//        }
//
//    }

//    private void closeAdvertisement() {
//        endTime = DateUtil.currentimeLong();
//
//        String advertisementLog = SharedPreferencesUtil.readString(ADVERTISEMENT_LOG, "[]");
//        logs = (List<AdvertisementDeliveringLogViewModel>) GsonUtil.getModel(advertisementLog, new TypeToken<List<AdvertisementDeliveringLogViewModel>>() {
//        }.getType());
//
//        boolean isNew = true;
//        TimeSpan timeSpan = new TimeSpan();
//        timeSpan.setStartTime(startTime);
//        timeSpan.setEndTime(endTime);
//
//        if (logs.size() > 0) {
//            for (AdvertisementDeliveringLogViewModel am : logs) {
//                if (am.getMaterialId().equals(list.get(index).getAdvertisementMaterialId())) {
//                    List<TimeSpan> timeSpens = am.getDeliveryTimeSpans() != null ? am.getDeliveryTimeSpans() : new ArrayList<TimeSpan>();
//                    timeSpens.add(timeSpan);
//                    isNew = false;
//                    break;
//                }
//            }
//        }
//
//        if (isNew) {
//            AdvertisementDeliveringLogViewModel adlvm = new AdvertisementDeliveringLogViewModel();
//            List<TimeSpan> timeSpens = new ArrayList<TimeSpan>();
//            timeSpens.add(timeSpan);
//            adlvm.setDeliveryTimeSpans(timeSpens);
//            adlvm.setMaterialId(list.get(index).getAdvertisementMaterialId());
//            logs.add(adlvm);
//        }
//
//        xclModel.saveAdvertisementLog(logs);
//
//    }

//    public void getAdvertisementLogResult(boolean success) {
//        if (success) {
//            SharedPreferencesUtil.saveString(ADVERTISEMENT_LOG, "[]");
//        } else {
//            SharedPreferencesUtil.saveString(ADVERTISEMENT_LOG, GsonUtil.getJson(logs));
//        }
//        finishThis();
//    }

    private void finishThis() {
//        if (SharedPreferencesUtil.readBoolean(SysConstants.ISLOGIN, false)) {
//            new homeModel().refashToken();
//            startActivity(new Intent(LogoActivity.this, MainActivity.class));
//        } else {
//            startActivity(new Intent(LogoActivity.this, LoginActivity.class));
//        }
//        LogoActivity.this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }
}
