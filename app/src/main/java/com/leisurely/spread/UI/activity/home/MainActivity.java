package com.leisurely.spread.UI.activity.home;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.fragment.ContentFragment;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.framework.tabManager.TabManager;
import com.leisurely.spread.util.SharedPreferencesUtil;

import java.lang.reflect.Method;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity {
    private static final String MAIN_TAG = "main";
    private FragmentManager fragmentManager;
    private long mPressedTime = 0;
    private String order;
    private String mId;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
//        PushManager.startWork(getApplicationContext(),
//                PushConstants.LOGIN_TYPE_API_KEY, SysConstants.API_KEY);
//        isGrantExternalRW(this);
        initFragment();
        mId = JPushInterface.getRegistrationID(this);

//        model.checkVersion();
    }

    @Override
    protected void initListener() {
        registerEventBus();
    }

    @Override
    protected void initData() {
        super.initData();
        sContext = this;
        if (TextUtils.isEmpty(mId)) {
            mId = SharedPreferencesUtil.readString("deviceId", "001");
        }

//        model.onbinddevices(mId);
    }

    private void initFragment() {
        // 获取Fragment管理器
        fragmentManager = getSupportFragmentManager();
        // 开启事务
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        ContentFragment cf = new ContentFragment();
        beginTransaction.replace(R.id.fl_content, cf, MAIN_TAG);

        // 提交事务
        beginTransaction.commit();
    }

    // 对外提供获取ContentFragment的方法
    public ContentFragment getContentFragment() {
        return (ContentFragment) fragmentManager.findFragmentByTag(MAIN_TAG);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        order = intent.getStringExtra("index");
        toPage(order);
    }

    private void toPage(String order) {
        if ("order".equals(order)) {
            getContentFragment().setCurrentTab(TabManager.OrderPisition);
        } else if ("wallet".equals(order)) {
            getContentFragment().setCurrentTab(TabManager.WalletPisition);
        }

    }

//    public void checkVersion(String result) {
//        JsonObject json = new JsonParser().parse(result).getAsJsonObject();
//        new UpdateUtil(MainActivity.this).checkUpdate(false, json);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            getContentFragment().onActivityResult(requestCode, resultCode, data);
        } else {
            if (requestCode == 1) {
                getContentFragment().onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if ((System.currentTimeMillis() - mPressedTime) > 2000) {
//                showToast(getResources().getString(R.string.toast_system_exit));
//                mPressedTime = System.currentTimeMillis();
//            } else {
//                Intent startMain = new Intent(Intent.ACTION_MAIN);
//                startMain.addCategory(Intent.CATEGORY_HOME);
//                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(startMain);
////                System.exit(0);
////                finish();
////                closeWPS(getPackageName());
////                ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
////                manager.restartPackage(getPackageName());
////                    android.os.Process.killProcess(android.os.Process.myPid());
//            }
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }

    private void closeWPS(String packageName){try{
        ActivityManager m= (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        Method method = m.getClass().getMethod("forceStopPackage", String.class);
        method.setAccessible(true);
        method.invoke(m, packageName);
        finish();
    }catch(Exception e){
        e.printStackTrace();
    }
    }



    @Override
    public void onEvent(Object messageEvent) {
//        if ((messageEvent instanceof String) && "order".equals(messageEvent.toString())) {
//            getContentFragment().onEventMainThread();
//        }else if ("bind_device_Id".equals(messageEvent.toString())){//防止重新登录后，没有绑定id
//            if (TextUtils.isEmpty(mId)) {
//                mId = SharedPreferencesUtil.readString("deviceId", "001");
//            }
//            model.onbinddevices(mId);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        model = null;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (model == null) {
//            model = new XclModel(this);
//        }
        if (sContext == null) {
            sContext = MainActivity.this;
        }
    }

    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        //授权成功后的逻辑
                        if (ContentFragment.currtnepostion == 0) {
                            getContentFragment().initViewPager();
                        }
//                    } else {
//                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_CODE);
                    }
                }
            }
        }
    }

}
