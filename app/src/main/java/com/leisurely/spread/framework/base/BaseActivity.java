
package com.leisurely.spread.framework.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.control.CustomProgressDialog;
import com.leisurely.spread.util.ActivityUtil;
import com.leisurely.spread.util.SoftHideKeyBoardUtil;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.util.ViewUtil;


/**
 * activity基类
 *
 * @author xcl
 *         create at 2015/12/3 15:56
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {
    protected Context sContext;
    protected View mView;
    //加载提示dialog
    private volatile CustomProgressDialog progressDialog = null;
    public static Bundle savedInstanceState;
    private boolean isRegisterEventBus = false;
    private boolean mIsDismess = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        sContext = this;
        super.onCreate(savedInstanceState);
        initView();
        setBackDefaultListener();
        initListener();
        initData();
        SoftHideKeyBoardUtil.assistActivity(this);
        // 在当前的activity中注册广播，这个广播主要用于释放所有在栈内的activity
        IntentFilter filter = new IntentFilter();
        filter.addAction("ExitApp");
        this.registerReceiver(this.broadcastReceiver, filter);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }

        Log.i("ActivityName", "所在Activity = " + this.getClass().getSimpleName());//方便熟悉代码，打印每个进入的activity
    }

    //这个方法特意拿出来，不必每个activity都注册，消耗内存，有需要直接子类调用
    protected void registerEventBus(){
        EventBus.getDefault().register(this);
        isRegisterEventBus = true;
    }

    // 声明一个订阅方法，用于接收事件
    @Subscribe
    public void onEvent(Object messageEvent) {

    }

//    protected void setStatusBar() {
//        View view = findViewById(R.id.status_high);
//        int status = DisplayUtil.getStatusBarHeight();
//        int h=status;
//        if(status==0){
//             h = DisplayUtil.dip2px(20, this);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            //透明状态栏
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            if (view != null) {
//                //动态设置代替导航栏view的高度
//                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
//                params.height = h;
//                view.setLayoutParams(params);
//            }
//            isbigto19 = true;
//        } else {
//            //小于4.4去掉多加的那个view
//            if (view != null) {
//                view.setVisibility(View.GONE);
//            }
//            isbigto19 = false;
//        }
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化控件监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 展示dialog
     *
     * @param word
     */
    public void showProgressDialog(String word) {

        if (progressDialog == null) {
            progressDialog = new CustomProgressDialog(this);
        }
        progressDialog.setMessage(word);
        progressDialog.setCancelable(false);
        if (!isFinishing()){
            progressDialog.show();
            mIsDismess = false;
        }
    }

    /**
     * 关闭dialog
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && !isFinishing() && !mIsDismess){
            progressDialog.dismiss();
            mIsDismess = true;
        }
    }

    /**
     * 展示toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        ToastUtil.showToast(msg);
    }


    /**
     * 设置view的单击监听
     *
     * @param id view的id
     */
    protected void setClickListener(int id) {
        View view = findViewById(id);
        if (view != null) {
            setClickListener(view);
        }
    }


    /**
     * 设置view的单击监听
     *
     * @param view
     */
    protected void setClickListener(View view) {
        if (view != null) {
            view.setOnClickListener(this);
        }

    }

    /**----------------------------------titlebar 通用方法---------------------------------*/

    /**
     * 设置标题栏默认返回事件
     */
    public void setBackDefaultListener() {

        View imageView = findViewById(R.id.imageView_left_title_bar);
        if (imageView != null) {
            imageView.setVisibility(View.VISIBLE);
            ((ImageView) imageView).setImageResource(R.mipmap.back_icon);
        }
        setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTranColor() {
        View principal = findViewById(R.id.principal);
        if (principal != null) {
            principal.setBackgroundColor(getResources().getColor(R.color.transparent));
            //  principal.setBackground(getResources().getDrawable(R.drawable.titlebar));
        }
        View textView_title_title_bar = findViewById(R.id.textView_title_title_bar);
        if (textView_title_title_bar != null) {
            ((TextView) textView_title_title_bar).setTextColor(getResources().getColor(R.color.black));
        }
    }

    public void setDefaultColor() {
        View principal = findViewById(R.id.principal);
        if (principal != null) {
            principal.setBackgroundColor(getResources().getColor(R.color.title_bar));
        }
        View textView_title_title_bar = findViewById(R.id.textView_title_title_bar);
        if (textView_title_title_bar != null) {
            ((TextView) textView_title_title_bar).setTextColor(getResources().getColor(R.color.bg_white));
        }
    }

    /**
     * 设置标题栏自定义返回事件
     */
    public void setBackListener(View.OnClickListener listener) {

        mView = findViewById(R.id.layout_left_title_bar);
        if (mView != null) {
            if (listener != null) {
                mView.setOnClickListener(listener);
            }
        }
    }

    /**
     * 返回键
     */
    public void cancelBackButton() {
        View view = findViewById(R.id.layout_left_title_bar);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 创建标题栏右侧按钮
     *
     * @param res_img         按钮图片
     * @param onClickListener 按钮事件
     */
    public void setTitleBarLeftButton(int res_img, View.OnClickListener onClickListener) {
        View view = findViewById(R.id.layout_left_title_bar);
        View imageView = view.findViewById(R.id.imageView_left_title_bar);
        if (imageView != null) {
            view.setVisibility(View.VISIBLE);
            ((ImageView) imageView).setImageResource(res_img);
            view.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置左侧状态栏图标
     *
     * @param res_img,width,height
     * @author yangcw
     * @created at 2016/8/22 18:07
     */
    public void setTitleBarLeftButtonBg(int res_img, int padding) {
        View view = findViewById(R.id.layout_left_title_bar);
        View imageView = view.findViewById(R.id.imageView_left_title_bar);
        imageView.setPadding(padding, padding, padding, padding);
        if (imageView != null) {
            view.setVisibility(View.VISIBLE);
            ((ImageView) imageView).setImageResource(res_img);
        }
    }

    /**
     * 创建标题栏右侧按钮
     *
     * @param bitmap          按钮图片
     * @param onClickListener 按钮事件
     */
    public void setTitleBarLeftButton(Bitmap bitmap, View.OnClickListener onClickListener) {
        View view = findViewById(R.id.layout_left_title_bar);
        View imageView = view.findViewById(R.id.imageView_left_title_bar);
        if (imageView != null) {
            imageView.setVisibility(View.VISIBLE);
            ((ImageView) imageView).setImageBitmap(bitmap);
            view.setOnClickListener(onClickListener);
        }
    }


    /**
     * 创建标题栏右侧文本按钮
     *
     * @param str             按钮文本
     * @param onClickListener 按钮事件
     */
    public void setTitleBarLeftTextButton(String str, View.OnClickListener onClickListener) {
        setTitleBarLeftTextButton(str, 0, 0, onClickListener);
    }

    /**
     * 创建标题栏右侧文本按钮
     *
     * @param str             按钮文本
     * @param onClickListener 按钮事件
     */
    public void setTitleBarLeftTextButton(String str, int color, int size, View.OnClickListener onClickListener) {
        View view = findViewById(R.id.layout_left_title_bar);
        View imageView = view.findViewById(R.id.imageView_left_title_bar);
        if (imageView != null) {
            imageView.setVisibility(View.GONE);
        }
    }

    /**
     * 获取标题栏左侧按钮容器 左侧内容自定义
     *
     * @return LinearLayout
     */
    public View getTitleBarLeftLayout() {
        return findViewById(R.id.layout_left_title_bar);
    }

    /**
     * 设置标题栏标题
     *
     * @param title
     */
    public void setTitleName(String title) {
        View view = findViewById(R.id.textView_title_title_bar);
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setText(title);
            }

        }
    }

    /**
     * 设置标题栏标题
     *
     * @param title
     */
    public void setTitleName(String title, int colorid) {
        View view = findViewById(R.id.textView_title_title_bar);
        if (view != null) {
//            ((TextView) view).setTextColor(getResources().getColor(R.color.white));
            ((TextView) view).setText(title);
            ((TextView) view).setTextColor(getResources().getColor(colorid));
        }
    }


    public void setBlackTitleName(String title) {
        View view = findViewById(R.id.textView_title_title_bar);
        if (view != null) {
            ((TextView) view).setTextColor(getResources().getColor(R.color.black));
            ((TextView) view).setText(title);
        }
    }

    /**
     * 设置标题栏标题
     *
     * @param res_str
     */
    protected void setTitleName(int res_str) {
        View view = findViewById(R.id.textView_title_title_bar);
        if (view != null) {
            ((TextView) view).setText(getString(res_str));
        }
    }

    /**
     * 创建标题栏右侧按钮
     *
     * @param res_img         按钮图片
     * @param onClickListener 按钮事件
     */
    protected void setTitleBarRightButton(int res_img, View.OnClickListener onClickListener) {
//        View view = findViewById(R.id.imageView_right_title_bar);
//        if (view != null) {
//            view.setVisibility(View.VISIBLE);
//            ((ImageView) view).setImageResource(res_img);
//            view.setOnClickListener(onClickListener);
//        }
    }

    /**
     * 创建标题栏右侧按钮
     *
     * @param bitmap          按钮图片
     * @param onClickListener 按钮事件
     */
    protected void setTitleBarRightButton(Bitmap bitmap, View.OnClickListener onClickListener) {
//        View view = findViewById(R.id.imageView_right_title_bar);
//        if (view != null) {
//            view.setVisibility(View.VISIBLE);
//            ((ImageView) view).setImageBitmap(bitmap);
//            view.setOnClickListener(onClickListener);
//        }
    }


    /**
     * 创建标题栏右侧文本按钮
     *
     * @param str             按钮文本
     * @param onClickListener 按钮事件
     */
    protected void setTitleBarRightTextButton(String str, View.OnClickListener onClickListener) {
        setTitleBarRightTextButton(str, 0, 0, onClickListener);
    }

    /**
     * 创建标题栏右侧文本按钮
     *
     * @param str             按钮文本
     * @param onClickListener 按钮事件
     */
    protected void setTitleBarRightTextButton(String str, int color, int size, View.OnClickListener onClickListener) {
        View view = findViewById(R.id.textView_right_title_bar);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            ((TextView) view).setText(str);
            if (color != 0) {
                ((TextView) view).setTextColor(color);
            }
            if (size > 0) {
                ((TextView) view).setTextSize(size);
            }
            view.setOnClickListener(onClickListener);
        }
    }

    /**
     * 获取标题栏右侧按钮容器 右侧内容自定义
     *
     * @return LinearLayout
     */
    protected View getTitleBarRightLayout() {
        return findViewById(R.id.layout_right_title_bar);
    }


    /**
     * 退出应用程序
     * 通过android广播方式 关闭所有activity
     * 所有activity必须继承该类
     */
    public void exit() {
        Intent intent = new Intent();
        intent.setAction("ExitApp");
        this.sendBroadcast(intent);
        super.finish();
    }

    //退出程序广播监听
    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "ExitApp":
                    finish();
                    break;
                default:
                    break;
            }

        }
    };

    /**
     * ----------------------------友盟统计---------------------------------
     */
    @Override
    public void onResume() {
        super.onResume();
        // MFHomeActivity内嵌套Fragment，应在Fragment中统计页面
        // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        //if (!"NewHomeActivity".equals(ActivityUtil.getRunningActivityName())) {
//            Log.d("Running","-------onResume--------"+ActivityUtil.getRunningActivityName());
        //}
        //统计时长

    }

    @Override
    public void onPause() {
        hintKbTwo();
        super.onPause();
        // MFHomeActivity内嵌套Fragment，应在Fragment中统计页面
        // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        if (!"NewHomeActivity".equals(ActivityUtil.getRunningActivityName())) {
//            Log.d("Running","-------onPause--------"+ActivityUtil.getRunningActivityName());
        }
        //统计时长
    }

    //此方法只是关闭软键盘
    public void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当activity关闭之后，检查当前的对话框有没有关闭
        if (progressDialog != null) {
            progressDialog.dismissAndDestory();
            progressDialog = null;
        }
        //取消退出应用监听
        if (isRegisterEventBus){
            EventBus.getDefault().unregister(this); // 解绑
            isRegisterEventBus =false;
        }
        this.unregisterReceiver(this.broadcastReceiver);
        sContext = null;
    }

    /**
     * 监听触摸 触摸输入法以外区域 隐藏软键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 判断输入法是否可以隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                View view = ViewUtil.getViewAtActivity(this, (int) event.getX(), (int) event.getY());
                if (view instanceof EditText) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 强制弹出键盘
     */
    public void pulloutInput() {
        InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen) {
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        }

    }

    /*
     * 关闭键盘
     */
    public void closeInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && this.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
