package com.leisurely.spread.UI.activity.money;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.adapter.MoneyAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Money;
import com.leisurely.spread.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 推广明细页
 *
 * @version V3.0
 * @FileName: com.leisurely.spread.UI.activity.money.MyRecommenderActivity.java
 * @author: GYZ
 * asia161@qq.com
 * @date: 2020-01-12 12:06
 */
public class MyRecommenderActivity extends BaseActivity {
    private XclModel xclModel;

    private LinearLayout back;
    private TextView txtUserId, txtUserPhone;
    private PullableListView listView;
    private PullToRefreshLayout pull_refresh_lay;
    private final int pageSize = 12;
    private int pageNum = 1;
    private List<Money> list;
    private MoneyAdapter adapter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_recommender);
        back = findViewById(R.id.back);
        txtUserId = findViewById(R.id.txtUserId);
        txtUserPhone = findViewById(R.id.txtUserPhone);
        listView = findViewById(R.id.lv_listview);
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);
        ((TextView) pull_refresh_lay.findViewById(R.id.state_tv)).setTextColor(Color.BLACK);
        ((TextView) pull_refresh_lay.findViewById(R.id.loadstate_tv)).setTextColor(Color.BLACK);

        list = new ArrayList<>();
        adapter = new MoneyAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        xclModel.queryMemberInfo();
        xclModel.queryIndirectLowerLevel(pageNum, pageSize);


    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        pull_refresh_lay.setOnRefreshListener(new MyListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }


    public void getUserSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            json = json.getJSONObject("data");
            String inviteId = json.getString("inviteId");
            String invitePhone = json.getString("invitePhone");
            txtUserId.setText(inviteId);
            txtUserPhone.setText(invitePhone);
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
                    xclModel.queryIndirectLowerLevel(pageNum, pageSize);
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
                    xclModel.queryIndirectLowerLevel(pageNum, pageSize);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }
}
