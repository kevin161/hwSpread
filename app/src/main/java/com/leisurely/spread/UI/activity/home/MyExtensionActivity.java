package com.leisurely.spread.UI.activity.home;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.adapter.LevelAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Level;
import com.leisurely.spread.util.ImageUtil;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class MyExtensionActivity extends BaseActivity {

    private XclModel xclModel;

    private TextView count;
    private ImageView qrcode;
    private TextView qrcode_text;
    private TextView copy_qrcode_text;
    private TextView phone_text;
    private TextView copy_phone_text;

    private LinearLayout back;
    private LinearLayout detail;
    private String qrcodeStr;

    private LinearLayout lianjie_li;
    private LinearLayout tongji_li;
    private LinearLayout listview_li;

    private TextView name;
    private TextView phone;
    private TextView user_id;
    private TextView user_id2;
    private TextView tongji_count;
    private TextView youxiao_count;
    private TextView one_sum;
    private TextView total_trade;
    private TextView commission_sum;
    private TextView pay_amount_sum;
    private TextView amount_sum;

    private RelativeLayout lianjie_re;
    private RelativeLayout tongji_re;
    private RelativeLayout zhijie_re;
    private RelativeLayout jianjie_re;

    private TextView lianjie_text;
    private TextView lianjie_line;
    private TextView tongji_text;
    private TextView tongji_line;
    private TextView zhijie_text;
    private TextView zhijie_line;
    private TextView jianjie_text;
    private TextView jianjie_line;


    private PullableListView listView;
    private PullToRefreshLayout pull_refresh_lay;
    private final int pageSize = 10;
    private int pageNum = 1;

    private LevelAdapter adapter;
    private List<Level> list;


    public static int type;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.activity_my_money);
        count = findViewById(R.id.count);
        qrcode = findViewById(R.id.qrcode);
        qrcode_text = findViewById(R.id.qrcode_text);
        copy_qrcode_text = findViewById(R.id.copy_qrcode_text);
        phone_text = findViewById(R.id.phone_text);
        copy_phone_text = findViewById(R.id.copy_phone_text);
        back = findViewById(R.id.back);
        detail = findViewById(R.id.detail);
        lianjie_li = findViewById(R.id.lianjie_li);
        tongji_li = findViewById(R.id.tongji_li);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        user_id = findViewById(R.id.user_id);
        user_id2 = findViewById(R.id.user_id2);
        tongji_count = findViewById(R.id.tongji_count);
        youxiao_count = findViewById(R.id.youxiao_count);
        one_sum = findViewById(R.id.one_sum);
        total_trade = findViewById(R.id.total_trade);
        commission_sum = findViewById(R.id.commission_sum);
        pay_amount_sum = findViewById(R.id.pay_amount_sum);
        amount_sum = findViewById(R.id.amount_sum);
        lianjie_re = findViewById(R.id.lianjie_re);
        tongji_re = findViewById(R.id.tongji_re);
        lianjie_text = findViewById(R.id.lianjie_text);
        lianjie_line = findViewById(R.id.lianjie_line);
        tongji_text = findViewById(R.id.tongji_text);
        tongji_line = findViewById(R.id.tongji_line);

        listview_li = findViewById(R.id.listview_li);
        zhijie_re = findViewById(R.id.zhijie_re);
        jianjie_re = findViewById(R.id.jianjie_re);
        zhijie_text = findViewById(R.id.zhijie_text);
        zhijie_line = findViewById(R.id.zhijie_line);
        jianjie_text = findViewById(R.id.jianjie_text);
        jianjie_line = findViewById(R.id.jianjie_line);


        listView = findViewById(R.id.lv_listview);
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);

        list = new ArrayList<>();
        adapter = new LevelAdapter(this, list);
        listView.setAdapter(adapter);

        ((TextView) pull_refresh_lay.findViewById(R.id.state_tv)).setTextColor(getResources().getColor(R.color.light));
        ((TextView) pull_refresh_lay.findViewById(R.id.loadstate_tv)).setTextColor(getResources().getColor(R.color.light));

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        copy_qrcode_text.setOnClickListener(this);
        copy_phone_text.setOnClickListener(this);
        back.setOnClickListener(this);
        detail.setOnClickListener(this);
        tongji_re.setOnClickListener(this);
        lianjie_re.setOnClickListener(this);
        zhijie_re.setOnClickListener(this);
        jianjie_re.setOnClickListener(this);
        pull_refresh_lay.setOnRefreshListener(new MyListener());
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        count.setText("￥" + "0");
        xclModel.commission();
        xclModel.registerQrCode();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copy_qrcode_text:
                if (StringUtil.isNotNull(qrcodeStr)) {
                    TextUtil.onClickCopy(qrcodeStr, this);
                }
                break;
            case R.id.copy_phone_text:
                if (StringUtil.isNotNull(phone_text.getText().toString())) {
                    TextUtil.onClickCopy(phone_text.getText().toString(), this);
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.detail:
                startActivity(new Intent(this, CommissionActivity.class));
                break;
            case R.id.tongji_re:
                if (type != 1) {
                    type = 1;
                    changeType();
                }
                break;
            case R.id.lianjie_re:
                if (type != 0) {
                    type = 0;
                    changeType();
                }
                break;
            case R.id.zhijie_re:
                if (type != 2) {
                    type = 2;
                    changeType();
                }
                break;
            case R.id.jianjie_re:
                if (type != 3) {
                    type = 3;
                    changeType();
                }
                break;
            default:
                break;
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) { // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView); // listItem.measure(0, 0);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(android.os.Message msg) {
                    pageNum = 1;
                    if (type == 2) {
                        xclModel.queryLowerLevel(pageNum, pageSize);
                    } else {
                        xclModel.queryIndirectLowerLevel(pageNum, pageSize);
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
                    if (type == 2) {
                        xclModel.queryLowerLevel(pageNum, pageSize);
                    } else {
                        xclModel.queryIndirectLowerLevel(pageNum, pageSize);
                    }
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }


    public void getLowerSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            list = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONObject("data").getJSONArray("data")), Level.class);
            if (pageNum == 1) {
                adapter.changeList(list);
            } else {
                adapter.addList(list);
            }
//            setListViewHeightBasedOnChildren(listView);
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    public void getIndirectLowerSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            list = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONObject("data").getJSONArray("data")), Level.class);
            if (pageNum == 1) {
                adapter.changeList(list);
            } else {
                adapter.addList(list);
            }
//            setListViewHeightBasedOnChildren(listView);
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    public void getQrcodeSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            phone_text.setText(json.getString("resultSet"));
            qrcodeStr = json.getString("data");
            qrcode_text.setText(qrcodeStr);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageUtil.createQRcodeImage(qrcodeStr, qrcode);
                }
            });
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }

    }

    public void getCommissionSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            count.setText(json.getString("data"));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageUtil.createQRcodeImage(qrcodeStr, qrcode);
                }
            });
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }

    }

    private void changeType() {

        if (type == 0) {
            lianjie_li.setVisibility(View.VISIBLE);
            tongji_li.setVisibility(View.GONE);
            listview_li.setVisibility(View.GONE);
            lianjie_line.setVisibility(View.VISIBLE);
            lianjie_text.setTextColor(getResources().getColor(R.color.color_3BA370));
            tongji_text.setTextColor(getResources().getColor(R.color.white));
            zhijie_text.setTextColor(getResources().getColor(R.color.white));
            jianjie_text.setTextColor(getResources().getColor(R.color.white));
            tongji_line.setVisibility(View.GONE);
            zhijie_line.setVisibility(View.GONE);
            jianjie_line.setVisibility(View.GONE);
        } else if (type == 1) {
            tongji_li.setVisibility(View.VISIBLE);
            lianjie_li.setVisibility(View.GONE);
            listview_li.setVisibility(View.GONE);
            tongji_line.setVisibility(View.VISIBLE);
            tongji_text.setTextColor(getResources().getColor(R.color.color_3BA370));
            lianjie_text.setTextColor(getResources().getColor(R.color.white));
            zhijie_text.setTextColor(getResources().getColor(R.color.white));
            jianjie_text.setTextColor(getResources().getColor(R.color.white));
            lianjie_line.setVisibility(View.GONE);
            zhijie_line.setVisibility(View.GONE);
            jianjie_line.setVisibility(View.GONE);
            xclModel.getTongji();
        } else if (type == 2) {
            lianjie_li.setVisibility(View.GONE);
            tongji_li.setVisibility(View.GONE);
            listview_li.setVisibility(View.VISIBLE);
            lianjie_line.setVisibility(View.GONE);
            lianjie_text.setTextColor(getResources().getColor(R.color.white));
            tongji_text.setTextColor(getResources().getColor(R.color.white));
            zhijie_text.setTextColor(getResources().getColor(R.color.color_3BA370));
            jianjie_text.setTextColor(getResources().getColor(R.color.white));
            tongji_line.setVisibility(View.GONE);
            zhijie_line.setVisibility(View.VISIBLE);
            jianjie_line.setVisibility(View.GONE);
            pageNum = 1;
            xclModel.queryLowerLevel(pageNum, pageSize);
        } else {
            lianjie_li.setVisibility(View.GONE);
            tongji_li.setVisibility(View.GONE);
            listview_li.setVisibility(View.VISIBLE);
            lianjie_line.setVisibility(View.GONE);
            lianjie_text.setTextColor(getResources().getColor(R.color.white));
            tongji_text.setTextColor(getResources().getColor(R.color.white));
            zhijie_text.setTextColor(getResources().getColor(R.color.white));
            jianjie_text.setTextColor(getResources().getColor(R.color.color_3BA370));
            tongji_line.setVisibility(View.GONE);
            zhijie_line.setVisibility(View.GONE);
            jianjie_line.setVisibility(View.VISIBLE);
            pageNum = 1;
            xclModel.queryIndirectLowerLevel(pageNum, pageSize);
        }
    }

    public void getTongjiSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            json = json.getJSONObject("data");
            user_id.setText(json.getString("uid"));
            user_id2.setText(json.getString("twoLevelSum"));
            phone.setText(json.getString("user_phone"));
            total_trade.setText("总交易额 ：" + json.getString("total_trade"));
            name.setText(SharedPreferencesUtil.readString("name", json.getString("name")));
            pay_amount_sum.setText(json.getString("payAmountSum"));
            amount_sum.setText(json.getString("amountSum"));
            commission_sum.setText(json.getString("commissionSum"));
            tongji_count.setText("分红会员数量 ：" + json.getString("lowerLevelSum"));
            one_sum.setText(json.getString("oneLevelSum"));
            youxiao_count.setText(json.getString("effectiveLevelSum"));

        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
