package com.leisurely.spread.UI.activity.pager;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.leisurely.spread.UI.activity.home.GoodsDetailActivity;
import com.leisurely.spread.UI.activity.home.MainActivity;
import com.leisurely.spread.UI.activity.home.NewsActivity;
import com.leisurely.spread.UI.activity.home.NewsDatailActivity;
import com.leisurely.spread.UI.activity.home.PersonCenterActivity;
import com.leisurely.spread.UI.activity.home.RegisterOrLoginActivity;
import com.leisurely.spread.UI.adapter.HomeAdapter;
import com.leisurely.spread.UI.adapter.HomeViewPagerAda;
import com.leisurely.spread.UI.view.ContentViewPager;
import com.leisurely.spread.UI.view.MarqueeView;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.tabManager.BasePager;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Banner;
import com.leisurely.spread.model.bean.Commodity;
import com.leisurely.spread.model.bean.News;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePager extends BasePager implements View.OnClickListener {

    private XclModel xclModel;

    private LinearLayout back;
    private LinearLayout add_lay;

    private long mPressedTime = 0;
    private List<Commodity> list;
    private ListView listView;
    private HomeAdapter adapter;
    private Commodity commodity2;
    private RadioGroup radio_group;
    private boolean isFirst = true;
    private List<Banner> banners;
    private List<News> news;
    private List<String> items = new ArrayList<String>();


    private LinearLayout gonggao_li;
    private TextView gonggao_li2;
    private ContentViewPager view_page;
    private MarqueeView home_news;
    private LinearLayout home_dot;

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

    public HomePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {

        rootView = mInflater.inflate(R.layout.home_activity, null);


        back = rootView.findViewById(R.id.back);
        add_lay = rootView.findViewById(R.id.add_lay);

        listView = rootView.findViewById(R.id.listview);
        radio_group = rootView.findViewById(R.id.radio_group);

        gonggao_li = rootView.findViewById(R.id.gonggao_li);
        gonggao_li2 = rootView.findViewById(R.id.gonggao_li2);
        home_news = rootView.findViewById(R.id.home_news);
        view_page = rootView.findViewById(R.id.view_page);
        home_dot = rootView.findViewById(R.id.home_dot);
        viewPageAda = new HomeViewPagerAda(new ArrayList<Banner>(), (MainActivity) mContext);

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
                        mDots.get(i).setImageResource(R.drawable.dot_select);
                    }
                    // 将被选中的图片中的圆点设置为被选中的时候的图片
                    mDots.get(position % banners.size()).setImageResource(R.drawable.dot_normal);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        adapter = new HomeAdapter((MainActivity)mContext, new ArrayList<Commodity>());
        listView.setAdapter(adapter);

        if (timer != null) {
            timer.cancel();
            initTimer();
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        xclModel.queryIndexGoods();
        if (isBack) {
            initTimer();
        }

    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        xclModel = new XclModel((MainActivity) mContext);
    }

    private void initListener() {
        back.setOnClickListener(this);
        gonggao_li.setOnClickListener(this);
        gonggao_li2.setOnClickListener(this);
    }

    public void queryIndexGoodsSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            JSONArray jsonArray = json.getJSONArray("data");
            if (jsonArray != null) {
                list = JSONArray.parseArray(JSONArray.toJSONString(jsonArray), Commodity.class);
                radio_group.removeAllViews();
                for (final Commodity commodity : list) {
//                    TextView textView = addText(commodity2);
//                    textView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                        }
//                    });
//                    add_lay.addView(textView);
                    final RadioButton radioButton = addRadioButton(commodity);
                    radio_group.addView(radioButton);
                    if (isFirst) {
                        isFirst = false;
                        this.commodity2 = commodity;

                    }
                    if (this.commodity2.getTypeName().equals(commodity.getTypeName())) {
                        radioButton.setChecked(true);
                        adapter.changeList(commodity.getGoods());
                    }

                    radioButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            radioButton.setChecked(true);
                            commodity2 = commodity;
                            adapter.changeList(commodity.getGoods());
                            listView.setSelection(0);
//                                showToast(getResources().getString(R.string.toast_system_exit));
                            mPressedTime = System.currentTimeMillis();


                        }
                    });
                }


            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
        if (isBack) {
            xclModel.getNewsList(1, 10, "NOTICE");
        } else {
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
        xclModel.getNewsList(1, 10, "NOTICE");
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
                            intent.setClass(mContext, NewsDatailActivity.class);
                            intent.putExtra("id", news.get(position).getId());
                            mContext.startActivity(intent);
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
                    ImageView imageView = new ImageView(mContext);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(40, 40));// 设置ImageView的宽度和高度
                    imageView.setPadding(12, 12, 12, 12);// 设置圆点的Padding，与周围的距离
                    imageView.setImageResource(R.drawable.dot_select);// 设置图片
                    mDots.add(imageView);// 将该图片添加到圆点集合中
                    home_dot.addView(imageView);// 将图片添加到LinearLayout中
                }
                mDots.get(0).setImageResource(R.drawable.dot_normal);
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
        mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class).putExtra("id", id));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back:
                if (SharedPreferencesUtil.readBoolean(SysConstants.ISLOGIN, false)) {
                    mContext.startActivity(new Intent(mContext, PersonCenterActivity.class));
                } else {
                    mContext.startActivity(new Intent(mContext, RegisterOrLoginActivity.class));
                }
                break;
            case R.id.gonggao_li:
            case R.id.gonggao_li2:
                mContext.startActivity(new Intent(mContext, NewsActivity.class));
                break;
            default:
                break;
        }
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mPressedTime) > 2000) {
                ToastUtil.showToast(mContext.getResources().getString(R.string.toast_system_exit));
                mPressedTime = System.currentTimeMillis();
            } else {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(startMain);
//                System.exit(0);
//                finish();
//                closeWPS(getPackageName());
//                ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//                manager.restartPackage(getPackageName());
//                    android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private RadioButton addRadioButton(Commodity commodity) {
        RadioButton radioButton = (RadioButton) LayoutInflater.from(mContext).inflate(R.layout.home_radiobutton, null);

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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
