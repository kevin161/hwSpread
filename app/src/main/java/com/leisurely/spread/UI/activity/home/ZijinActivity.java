package com.leisurely.spread.UI.activity.home;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.adapter.ZijinAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Zijin;
import com.leisurely.spread.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class ZijinActivity extends BaseActivity {

    private XclModel xclModel;

    private RelativeLayout head;

    private PullableListView listView;
    private PullToRefreshLayout pull_refresh_lay;
    private final int pageSize = 10;
    private int pageNum = 1;

    private List<Zijin> list;
    private ZijinAdapter adapter;


    private int type;

    private TextView yue_text;
    private TextView yue;
    private String assertStr;

    private LinearLayout lay;
    private LinearLayout zhifu_li;
    private LinearLayout chaxun_li;
    private LinearLayout shoucang_li;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.zijin_activity);

        type = getIntent().getIntExtra("type", 0);

        listView = findViewById(R.id.lv_listview);
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);
        yue_text = findViewById(R.id.yue_text);
        yue = findViewById(R.id.yue);
        lay = findViewById(R.id.lay);
        zhifu_li = findViewById(R.id.zhifu_li);
        chaxun_li = findViewById(R.id.chaxun_li);
        shoucang_li = findViewById(R.id.shoucang_li);



        list = new ArrayList<>();
        adapter = new ZijinAdapter(this, list);
        listView.setAdapter(adapter);

        ((TextView) pull_refresh_lay.findViewById(R.id.state_tv)).setTextColor(getResources().getColor(R.color.light));
        ((TextView) pull_refresh_lay.findViewById(R.id.loadstate_tv)).setTextColor(getResources().getColor(R.color.light));
    }

    @Override
    public void onResume() {
        super.onResume();
        xclModel.getAccount(assertStr);
        getData();
//        xclModel.queryMemberInfo();
    }

    @Override
    protected void initListener() {
        pull_refresh_lay.setOnRefreshListener(new MyListener());
        zhifu_li.setOnClickListener(this);
        chaxun_li.setOnClickListener(this);
        shoucang_li.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);

        if (type == 0) {
            yue_text.setText("投资基金余额");
            assertStr = "TZJ";
            lay.setVisibility(View.GONE);
        } else if (type == 1) {
            yue_text.setText("通证基金余额");
            assertStr = "PSJ";
            lay.setVisibility(View.GONE);
        } else {
            yue_text.setText("茶贝余额");
            assertStr = "CBJ";
            lay.setVisibility(View.VISIBLE);
        }
        xclModel.getAccount(assertStr);
        getData();
//        xclModel.queryMemberInfo();
//        xclModel.getZijinList(pageNum, pageSize, provinceCode,cityCode,areaCode,fullName);
    }

    public void getAccountSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            yue.setText(json.getJSONObject("data").getString("available"));
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    private void getData() {

        xclModel.getAccountDetail(pageNum, pageSize, assertStr);
    }

    public void getAccountDetailSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            List<Zijin> list = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONObject("data").getJSONArray("result")), Zijin.class);
            if (pageNum == 1) {
                if(list==null || list.isEmpty()){
                    list.add(new Zijin());
                }
                adapter.changeList(list);
                this.list = list;
            } else {
                adapter.addList(list);
                this.list.addAll(list);
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(android.os.Message msg) {
                    pageNum = 1;
                    getData();
//                    xclModel.getScoreDetail(pageNum, pageSize);
//                    if (type == 0) {
//                        xclModel.offlinePage(pageNum, pageSize);
//                    } else {
//                        xclModel.queryPage(pageNum, pageSize);
//                    }

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
                    getData();
//                    xclModel.getScoreDetail(pageNum, pageSize);
//                    if (type == 0) {
//                        xclModel.offlinePage(pageNum, pageSize);
//                    } else {
//                        xclModel.queryPage(pageNum, pageSize);
//                    }
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhifu_li:
                startActivity(new Intent(this,ShopPayActivity.class));
                break;
            case R.id.chaxun_li:
                Intent it = new Intent();
                setResult(RESULT_OK, it);
                finish();
                break;
            case R.id.shoucang_li:
                startActivity(new Intent(this,ShouCangActivity.class));
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
