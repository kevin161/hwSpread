package com.leisurely.spread.UI.activity.money;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.RegisterOrLoginActivity;
import com.leisurely.spread.UI.adapter.MoneyAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Money;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class MyMoneyActivity extends BaseActivity {

    private XclModel xclModel;

    private LinearLayout back;

    private TextView bingcard_li, txtRecharge, txtTakeBack;
    private TextView count;
    private String maxAmount;

    private PullableListView listView;
    private PullToRefreshLayout pull_refresh_lay;
    private final int pageSize = 12;
    private int pageNum = 1;

    private List<Money> list;
    private MoneyAdapter adapter;

    private int type = 1;//1 资金明细  2 奖金明细

    private TextView total_commission;
    private TextView bonus;
    private TextView frozen_bonus;
    private LinearLayout mingxi_li;
    private TextView mingxi_text;
    private TextView mingxi_line;
    private LinearLayout jiangjin_li;
    private TextView jiangjin_text;
    private TextView jiangjin_line;


    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.mymoney_activity);

        back = findViewById(R.id.back);
        bingcard_li = findViewById(R.id.bingcard_li);
        count = findViewById(R.id.count);
        listView = findViewById(R.id.lv_listview);
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);

        total_commission = findViewById(R.id.total_commission);
        bonus = findViewById(R.id.bonus);
        frozen_bonus = findViewById(R.id.frozen_bonus);
        mingxi_li = findViewById(R.id.mingxi_li);
        mingxi_text = findViewById(R.id.mingxi_text);
        mingxi_line = findViewById(R.id.mingxi_line);
        jiangjin_text = findViewById(R.id.jiangjin_text);
        jiangjin_line = findViewById(R.id.jiangjin_line);
        jiangjin_li = findViewById(R.id.jiangjin_li);
        txtRecharge = findViewById(R.id.txtRecharge);
        txtTakeBack = findViewById(R.id.txtTakeBack);


        ((TextView) pull_refresh_lay.findViewById(R.id.state_tv)).setTextColor(Color.BLACK);
        ((TextView) pull_refresh_lay.findViewById(R.id.loadstate_tv)).setTextColor(Color.BLACK);

        list = new ArrayList<>();
        adapter = new MoneyAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        xclModel.queryMoney(SharedPreferencesUtil.readString("uid", "0"));
        xclModel.moneyDetail(pageNum, pageSize, type);
        Log.e("tag","MyMoneyActivity-------isAuthBindCard="+SharedPreferencesUtil.isAuthBindCard());
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        mingxi_li.setOnClickListener(this);
        bingcard_li.setOnClickListener(this);
        jiangjin_li.setOnClickListener(this);
        txtRecharge.setOnClickListener(this);
        txtTakeBack.setOnClickListener(this);
        pull_refresh_lay.setOnRefreshListener(new MyListener());
    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(android.os.Message msg) {
                    pageNum = 1;
                    xclModel.moneyDetail(pageNum, pageSize, type);
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(android.os.Message msg) {
                    pageNum++;
                    xclModel.moneyDetail(pageNum, pageSize, type);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        if (SharedPreferencesUtil.isLogin()) {
            if ("1".equals(SharedPreferencesUtil.readString(SysConstants.IS_RISK_AUTH, "0"))) {
                bingcard_li.setText("绑卡信息");
            } else {
                bingcard_li.setText("去绑卡");
            }
        }
//        String title = getIntent().getStringExtra("title");
//        if (StringUtil.isNotNull(title)) {
//            setTitleName(title);
//        }

//        xclModel.queryMoney(SharedPreferencesUtil.readString("uid", "0"));

    }

    private void changeType() {

        if (type == 1) {
            mingxi_text.setTextColor(getResources().getColor(R.color.color_3BA370));
            mingxi_line.setVisibility(View.VISIBLE);
            jiangjin_text.setTextColor(getResources().getColor(R.color.light));
            jiangjin_line.setVisibility(View.GONE);
        } else {
            mingxi_text.setTextColor(getResources().getColor(R.color.light));
            mingxi_line.setVisibility(View.GONE);
            jiangjin_text.setTextColor(getResources().getColor(R.color.color_3BA370));
            jiangjin_line.setVisibility(View.VISIBLE);
        }

    }

    public void getMoneySuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            json = json.getJSONObject("data");
            maxAmount = json.getString("available");
            count.setText( maxAmount);
            bonus.setText(json.getString("bonus"));
            frozen_bonus.setText(json.getString("frozenBonus"));
            total_commission.setText(json.getString("totalCommission"));
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }


    }

    public void moneyDetailSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            list = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONObject("data").getJSONArray("result")), Money.class);
            if (pageNum == 1) {
                adapter.changeList(list);
            } else {
                adapter.addList(list);
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bingcard_li:
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

            case R.id.txtRecharge:
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
            case R.id.txtTakeBack:
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
            case R.id.back:
                finish();
                break;
            case R.id.mingxi_li:
                if (type != 1) {
                    type = 1;
                    changeType();
                    pageNum = 1;
                    xclModel.moneyDetail(pageNum, pageSize, type);
                }

                break;
            case R.id.jiangjin_li:
                if (type != 2) {
                    type = 2;
                    changeType();
                    pageNum = 1;
                    xclModel.moneyDetail(pageNum, pageSize, type);
                }
                break;
            default:
                break;
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
                            bingcard_li.setText("绑卡信息");
                        }
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
