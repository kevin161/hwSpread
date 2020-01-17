package com.leisurely.spread.UI.activity.home;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.money.AuthAccountInfoActivity;
import com.leisurely.spread.UI.activity.money.AuthNameBindCardActivity;
import com.leisurely.spread.UI.activity.money.Recharge2Activity;
import com.leisurely.spread.UI.activity.money.Withdrawal2Activity;
import com.leisurely.spread.UI.activity.setting.ScanActivity;
import com.leisurely.spread.UI.adapter.HomeAdapter;
import com.leisurely.spread.UI.adapter.HomeViewPagerAda;
import com.leisurely.spread.UI.view.ContentViewPager;
import com.leisurely.spread.UI.view.MarqueeView;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Banner;
import com.leisurely.spread.model.bean.Commodity;
import com.leisurely.spread.model.bean.News;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.widget.permission.MyPermission;
import com.leisurely.spread.widget.permission.Permission;
import com.leisurely.spread.widget.permission.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeActivity extends BaseActivity {

    private static final int REQUEST_SCAN = 49373;
    private XclModel xclModel;

    private LinearLayout back;
    private LinearLayout add_lay;

    private long mPressedTime = 0;
    private List<Commodity> list;
    private ListView listView;
    private HomeAdapter adapter;
    private Commodity commodity;
    private RadioGroup radio_group;
    private boolean isFirst = true;
    private List<Banner> banners;
    private List<News> news;
    private List<String> items = new ArrayList<String>();


    private LinearLayout layoutRecharge, layoutTakeBack, layoutBindCard, layoutZxingImage;
    private LinearLayout gonggao_li;
    private TextView gonggao_li2, txtBindCard;
    private ContentViewPager view_page;
    private MarqueeView home_news;
    private LinearLayout home_dot;
    private ImageView imgGoods;

    private HomeViewPagerAda viewPageAda;

    private List<ImageView> mDots;//小点

    private Timer timer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentItem = view_page.getCurrentItem();
            currentItem++;
            view_page.setCurrentItem(currentItem);
        }
    };

    private boolean isBack;

    /**
     * 倒计时 方法
     */
    private void initTimer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(task, 3000, 3000);
    }


    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.home_activity);

        back = findViewById(R.id.back);
        add_lay = findViewById(R.id.add_lay);

        listView = findViewById(R.id.listview);
        radio_group = findViewById(R.id.radio_group);

        gonggao_li = findViewById(R.id.gonggao_li);
        gonggao_li2 = findViewById(R.id.gonggao_li2);
        home_news = findViewById(R.id.home_news);
        view_page = findViewById(R.id.view_page);
        home_dot = findViewById(R.id.home_dot);
        layoutRecharge = findViewById(R.id.layoutRecharge);
        layoutTakeBack = findViewById(R.id.layoutTakeBack);
        layoutBindCard = findViewById(R.id.layoutBindCard);
        layoutZxingImage = findViewById(R.id.layoutZxingImage);
        txtBindCard = findViewById(R.id.txtBindCard);
        imgGoods = findViewById(R.id.imgGoods);
        viewPageAda = new HomeViewPagerAda(new ArrayList<Banner>(), this);

        view_page.setAdapter(viewPageAda);
        view_page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (banners != null && banners.size() > 0) {
                    for (int i = 0; i < banners.size(); i++) {
                        // 将所有的圆点设置为为选中时候的图片
                        mDots.get(i).setImageResource(R.mipmap.home_banner_select);
//                        mDots.get(i).setImageResource(R.drawable.dot_select);
                    }
                    // 将被选中的图片中的圆点设置为被选中的时候的图片
                    mDots.get(position % banners.size()).setImageResource(R.mipmap.home_banner_unselect);
//                    mDots.get(position % banners.size()).setImageResource(R.drawable.dot_normal);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        adapter = new HomeAdapter(this, new ArrayList<Commodity>());
        listView.setAdapter(adapter);

        if (timer != null) {
            timer.cancel();
            initTimer();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        xclModel.queryIndexGoods();
        xclModel.checkUserAccount();
        new MyPermission(HomeActivity.this, Permission.INTERNET, new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            }

            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                ToastUtil.makeTextAndShow("未打开网络请求权限！");
            }
        }).request();
        if (isBack) {
            initTimer();
        }

        if ("1".equals(SharedPreferencesUtil.readString(SysConstants.IS_RISK_AUTH, "0"))) {
            txtBindCard.setText("绑卡信息");
        } else {
            txtBindCard.setText("实名绑卡");
        }
    }


    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        gonggao_li.setOnClickListener(this);
        gonggao_li2.setOnClickListener(this);
        layoutRecharge.setOnClickListener(this);
        layoutTakeBack.setOnClickListener(this);
        layoutBindCard.setOnClickListener(this);
        layoutZxingImage.setOnClickListener(this);
        imgGoods.setOnClickListener(this);

    }

    @Override
    protected void initData() {
//        goods =new Commodity.Goods();
        list = new ArrayList<>();
        xclModel = new XclModel(this);
    }

    public void queryIndexGoodsSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            JSONArray jsonArray = json.getJSONArray("data");
            if (jsonArray != null) {
                list = JSONArray.parseArray(JSONArray.toJSONString(jsonArray), Commodity.class);

                adapter.changeList(list);
//                radio_group.removeAllViews();
//                for (final Commodity commodity : list) {
////                    TextView textView = addText(commodity);
////                    textView.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View view) {
////
////                        }
////                    });
////                    add_lay.addView(textView);
////                    final RadioButton radioButton = addRadioButton(commodity);
////                    radio_group.addView(radioButton);
//                    if (isFirst) {
//                        isFirst = false;
//                        this.commodity = commodity;
//
//                    }
//                    if (this.commodity.getTypeName().equals(commodity.getTypeName())) {
////                        radioButton.setChecked(true);
//                        if (commodity.getGoods() == null) {
//                            break;
//                        }
//                        adapter.changeList(commodity.getGoods());
//                    }
//
////                    radioButton.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View view) {
////
////                            radioButton.setChecked(true);
////                            HomeActivity.this.commodity = commodity;
////                            adapter.changeList(commodity.getGoods());
////                            listView.setSelection(0);
//////                                showToast(getResources().getString(R.string.toast_system_exit));
////                            mPressedTime = System.currentTimeMillis();
////
////
////                        }
////                    });
//                }


            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
        if (isBack) {
            xclModel.getNewsList(1, 10, "NOTICE");
        } else {
            xclModel.getNewsList(1, 10, "NOTICE");
        }
        if (banners == null || banners.size() == 0) {
            xclModel.getCarouselList();
        }
    }

    public void getCarouselListSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            JSONArray jsonArray = json.getJSONArray("data");
            if (jsonArray != null) {
                banners = JSONArray.parseArray(JSONArray.toJSONString(jsonArray), Banner.class);
                initDots();
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    public void getNewsListSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            JSONArray jsonArray = json.getJSONArray("data");
            if (jsonArray != null) {
                news = JSONArray.parseArray(JSONArray.toJSONString(jsonArray), News.class);
                if (news != null && news.size() > 0) {
                    items.clear();
                    for (News n : news) {
                        items.add(n.getSubject());
                    }
                    home_news.startWithList(items);

                    home_news.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position, TextView textView) {

                            Intent intent = new Intent();
                            intent.setClass(HomeActivity.this, NewsDatailActivity.class);
                            intent.putExtra("id", news.get(position).getId());
                            startActivity(intent);
                        }
                    });
                }
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }

    }

    private void initDots() {
        home_dot.removeAllViews();

        mDots = new ArrayList<ImageView>();// 底部圆点集合的初始化
        if (banners != null && banners.size() > 0) {
            if (banners.size() > 1) {
                for (int i = 0; i < banners.size(); i++) {// 根据界面数量动态添加圆点
                    ImageView imageView = new ImageView(this);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(40, 40));// 设置ImageView的宽度和高度
                    imageView.setPadding(12, 12, 12, 12);// 设置圆点的Padding，与周围的距离
                    imageView.setImageResource(R.mipmap.home_banner_select);// 设置图片
//                    imageView.setImageResource(R.drawable.dot_select);// 设置图片
                    mDots.add(imageView);// 将该图片添加到圆点集合中
                    home_dot.addView(imageView);// 将图片添加到LinearLayout中
                }
//                mDots.get(0).setImageResource(R.drawable.dot_normal);
                mDots.get(0).setImageResource(R.mipmap.home_banner_unselect);
                initTimer();
                viewPageAda.setUrllist(banners);
                viewPageAda.notifyDataSetChanged();
                view_page.setCurrentItem(banners.size() * 100);
            }

            viewPageAda.setUrllist(banners);
            viewPageAda.notifyDataSetChanged();
        }

    }

    public void detail(String id) {
        startActivity(new Intent(this, GoodsDetailActivity.class).putExtra("id", id));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back:
                new MyPermission(this, Permission.CAMERA, new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        openScan();
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        ToastUtil.makeTextAndShow("未打开相机权限！");
                    }
                }).request();
                break;
            case R.id.gonggao_li:
                if (SharedPreferencesUtil.readBoolean(SysConstants.ISLOGIN, false)) {
                    startActivity(new Intent(this, PersonCenterActivity.class));
                } else {
                    startActivityForResult(new Intent(this, RegisterOrLoginActivity.class), 110);
                }
                break;
            case R.id.gonggao_li2:
                startActivity(new Intent(this, NewsActivity.class));
                break;

            case R.id.layoutRecharge:
                if (SharedPreferencesUtil.isLogin()) {
                    if (SharedPreferencesUtil.isAuthBindCard()) {
                        startActivity(new Intent(getBaseContext(), Recharge2Activity.class));
                    } else {
                        ToastUtil.makeTextAndShow("请先进行实名开户");
                        startActivityForResult(new Intent(this, AuthNameBindCardActivity.class), 1111);
                    }
                } else {
                    startActivityForResult(new Intent(this, RegisterOrLoginActivity.class), 110);
                }


                break;
            case R.id.layoutTakeBack:
                if (SharedPreferencesUtil.isLogin()) {
                    if (SharedPreferencesUtil.isAuthBindCard()) {
                        startActivity(new Intent(getBaseContext(), Withdrawal2Activity.class));
                    } else {
                        ToastUtil.makeTextAndShow("请先进行实名开户");
                        startActivityForResult(new Intent(this, AuthNameBindCardActivity.class), 1111);
                    }
                } else {
                    startActivityForResult(new Intent(this, RegisterOrLoginActivity.class), 110);
                }

                break;
            case R.id.layoutBindCard:
                if (SharedPreferencesUtil.isLogin()) {
                    if (SharedPreferencesUtil.isAuthBindCard()) {
                        startActivity(new Intent(this, AuthAccountInfoActivity.class));
                    } else {
                        startActivityForResult(new Intent(this, AuthNameBindCardActivity.class), 1111);
                    }
                } else {
                    startActivityForResult(new Intent(this, RegisterOrLoginActivity.class), 110);
                }

                break;
            case R.id.layoutZxingImage:
                startActivity(new Intent(this, MyExtension2Activity.class));
                break;
            case R.id.imgGoods:
                startActivity(new Intent(this, GoodIntroduceActivity.class));

                break;


            default:
                break;
        }
    }

    private void startActivityAfterLoginChecked(Intent intent) {
        if (SharedPreferencesUtil.isLogin()) {
            startActivity(intent);
        } else {
            startActivityForResult(new Intent(this, RegisterOrLoginActivity.class), 110);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 110:
                case 1111:
                    if (SharedPreferencesUtil.isLogin()) {
                        if (SharedPreferencesUtil.isAuthBindCard()) {
                            //已实名
                            txtBindCard.setText("绑卡信息");
                        }
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void showUpdateDialog() {
        final View view = LayoutInflater.from(this).inflate(R.layout.update_dialog, null);
        final Dialog backDialog = new Dialog(this, R.style.officeDialog);
        backDialog.setContentView(view);
        view.findViewById(R.id.imgUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backDialog.dismiss();
            }
        });
        backDialog.show();
        backDialog.setCancelable(false);
        backDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
        isBack = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
        isBack = true;
    }

    /**
     * 打开系统默认扫描界面
     */
    public void openScan() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1001);
        } else { //有权限直接调用系统相机拍照
            startActivityForResult(new Intent(this, ScanActivity.class), REQUEST_SCAN);
//            Intent intent = new Intent(this, CaptureActivity.class);
//            startActivityForResult(intent, REQUEST_SCAN);
        }


//        if (Build.VERSION.SDK_INT > 22) {
//            if (ContextCompat.checkSelfPermission(this,
//                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                //先判断有没有权限 ，没有就在这里进行权限的申请
//                ActivityCompat.requestPermissions(this,
//                        new String[]{android.Manifest.permission.CAMERA},1001);
//                Intent intent = new Intent(Settings.ACTION_SETTINGS);
//                startActivity(intent);
//            } else {
//                startActivityForResult(new Intent(this, ScanActivity.class), REQUEST_SCAN);
//            }
//        } else {
//            startActivityForResult(new Intent(this, ScanActivity.class), REQUEST_SCAN);
//
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if ((System.currentTimeMillis() - mPressedTime) > 2000) {
//                showToast(getResources().getString(R.string.toast_system_exit));
//                mPressedTime = System.currentTimeMillis();
//            } else {
//                Intent startMain = new Intent(Intent.ACTION_MAIN);
//                startMain.addCategory(Intent.CATEGORY_HOME);
//                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(startMain);
//
//            }
//            return true;
//        }

        return false;
    }

    private RadioButton addRadioButton(Commodity commodity) {
        RadioButton radioButton = (RadioButton) LayoutInflater.from(this).inflate(R.layout.home_radiobutton, null);

//        radioButton.setTextColor(getResources().getColor(R.color.light));
//        radioButton.setTextSize(18);
//        textView.setBackground(mContext.getResources().getDrawable(R.drawable.dcb981_6dp_side));
        radioButton.setText(commodity.getTypeName());
//        radioButton.setTextAppearance(R.style.Beatbtn);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins(0, 0, 100, 0);//4个参数按顺序分别是左上右下
//        radioButton.setLayoutParams(layoutParams);
//        radioButton.setPadding(4, 2, 4, 2);
        return radioButton;
    }

    public void checkUserAccountSuccess(JSONObject response) {
        if ("1".equals(response.getJSONObject("status").getString("success"))) {
            if ("0".equals(response.getString("result"))) {
                SharedPreferencesUtil.saveString(SysConstants.IS_RISK_AUTH, "0");
            } else if ("1".equals(response.getString("result"))) {
                SharedPreferencesUtil.saveString(SysConstants.IS_RISK_AUTH, "1");
            }
        } else {
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
