package com.leisurely.spread.UI.activity.money;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.adapter.RechargeAdapter;
import com.leisurely.spread.UI.adapter.WithdrawalAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Recharge;
import com.leisurely.spread.model.bean.Withdrawal;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 充值提现记录
 */
public class MoneyHistoryLogActivity extends BaseActivity {
    public static final int TYPE_RECHARGE = 0;
    public static final int TYPE_WITHDRAWAL = 1;
    private XclModel xclModel;

    private LinearLayout back;


    private PullableListView listView;
    private PullToRefreshLayout pull_refresh_lay;
    private final int pageSize = 12;
    private int pageNum = 1;

    private List<Recharge> recharges;
    private List<Withdrawal> withdrawals;
    private int type;// 0 充值 1提现

    private TextView txtActionTitle;
    private TextView recharge;
    private TextView withdrawal;

    private RechargeAdapter rechargeAdapter;
    private WithdrawalAdapter withdrawalAdapter;
    private boolean isfirst = true;


    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.money_log_activity);

        back = findViewById(R.id.back);
        txtActionTitle = findViewById(R.id.txtActionTitle);
        recharge = findViewById(R.id.recharge);
        withdrawal = findViewById(R.id.withdrawal);
        listView = findViewById(R.id.lv_listview);
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);
        ((TextView) pull_refresh_lay.findViewById(R.id.state_tv)).setTextColor(Color.BLACK);
        ((TextView) pull_refresh_lay.findViewById(R.id.loadstate_tv)).setTextColor(Color.BLACK);

        recharges = new ArrayList<>();
        withdrawals = new ArrayList<>();
        rechargeAdapter = new RechargeAdapter(this, recharges);
        withdrawalAdapter = new WithdrawalAdapter(this, withdrawals);

//        adapter = new MoneyAdapter(this, recharges);
//        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        recharge.setOnClickListener(this);
        withdrawal.setOnClickListener(this);
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
                    if (type == TYPE_RECHARGE) {
                        xclModel.rechargePage(pageNum, pageSize);
                    } else {
                        xclModel.queryPage(pageNum, pageSize);
                    }

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
                    if (type == TYPE_RECHARGE) {
                        xclModel.rechargePage(pageNum, pageSize);
                    } else {
                        xclModel.queryPage(pageNum, pageSize);
                    }
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        String title = getIntent().getStringExtra("title");
        if (StringUtil.isNotNull(title)) {
            setTitleName(title);
        }
        type = getIntent().getIntExtra("type", TYPE_RECHARGE);
        if (type == TYPE_RECHARGE) {
            changeType(0);
        } else {
            changeType(1);
        }
//        xclModel.queryMoney(SharedPreferencesUtil.readString("uid", "0"));

    }


    public void rechargePageSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            JSONArray jsonArray = json.getJSONObject("data").getJSONArray("data");
            if (jsonArray != null) {
                recharges = JSONArray.parseArray(JSONArray.toJSONString(jsonArray), Recharge.class);
                if (pageNum == 1) {
                    rechargeAdapter.changeList(recharges);
                } else {
                    rechargeAdapter.addList(recharges);
                }
            }

        } else {
            ToastUtil.showToast(json.getString("msg"));
        }


    }

    public void queryPageSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            JSONArray jsonArray = json.getJSONObject("data").getJSONArray("data");
            if (jsonArray != null) {
                withdrawals = JSONArray.parseArray(JSONArray.toJSONString(jsonArray), Withdrawal.class);
                if (pageNum == 1) {
                    withdrawalAdapter.changeList(withdrawals);
                } else {
                    withdrawalAdapter.addList(withdrawals);
                }
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }


    }

    private void changeType(int changeType) {
        if (changeType == 0) {
            if (type != TYPE_RECHARGE || isfirst) {
                listView.setAdapter(rechargeAdapter);
                pageNum = 1;
                txtActionTitle.setText("充值明细");
                recharge.setBackground(getResources().getDrawable(R.drawable.gray_d2d2d2_2dp));
//                recharge.setTextColor(getResources().getColor(R.color.white));
                withdrawal.setBackground(getResources().getDrawable(R.drawable.white_6dp));
//                withdrawal.setTextColor(getResources().getColor(R.color.color_3BA370));
                xclModel.rechargePage(pageNum, pageSize);
            }
            type = TYPE_RECHARGE;
        } else {
            if (type != TYPE_WITHDRAWAL || isfirst) {
                listView.setAdapter(withdrawalAdapter);
                pageNum = 1;
                txtActionTitle.setText("提现明细");
                recharge.setBackground(getResources().getDrawable(R.drawable.white_6dp));
//                recharge.setTextColor(getResources().getColor(R.color.color_3BA370));
                withdrawal.setBackground(getResources().getDrawable(R.drawable.gray_d2d2d2_2dp));
//                withdrawal.setTextColor(getResources().getColor(R.color.white));
                xclModel.queryPage(pageNum, pageSize);

                type = TYPE_WITHDRAWAL;
            }
        }
        isfirst = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recharge:

                changeType(TYPE_RECHARGE);
                break;
            case R.id.withdrawal:
                changeType(TYPE_WITHDRAWAL);
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
