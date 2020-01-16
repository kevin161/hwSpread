package com.leisurely.spread.UI.activity.money;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.adapter.ITSLogAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.OrderDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/24.
 */

public class ITSLogActivity extends BaseActivity {

    private XclModel xclModel;

    private final int pageSize = 10;
    private int pageNum = 1;
    private List<OrderDetail> list;
    private PullToRefreshLayout pull_refresh_lay;
    private PullableListView listView;

    private TextView buy;
    private TextView buy_line;
    private TextView sell;
    private TextView sell_line;

    private ITSLogAdapter adapter;

    private int type;//0 buy 1 sell
    private int index;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_its_log);
        setTitleName("ITS交易");

        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);
        listView = findViewById(R.id.lv_listview);
        buy = findViewById(R.id.buy);
        sell = findViewById(R.id.sell);
        buy_line = findViewById(R.id.buy_line);
        sell_line = findViewById(R.id.sell_line);

        list = new ArrayList<>();
        adapter = new ITSLogAdapter(this, list);
        listView.setAdapter(adapter);

        ((TextView) pull_refresh_lay.findViewById(R.id.state_tv)).setTextColor(getResources().getColor(R.color.light));
        ((TextView) pull_refresh_lay.findViewById(R.id.loadstate_tv)).setTextColor(getResources().getColor(R.color.light));

    }

    @Override
    protected void initData() {
        super.initData();
        xclModel = new XclModel(this);
        getData();
    }

    @Override
    protected void initListener() {
        buy.setOnClickListener(this);
        sell.setOnClickListener(this);
        pull_refresh_lay.setOnRefreshListener(new MyListener());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                if (type == 0) {
                    if (list.get(position).getStatus() == 0) {
                        startActivityForResult(new Intent(ITSLogActivity.this, BuyDetailActivity.class)
                                .putExtra("id", list.get(position).getId())
                                .putExtra("from", 1), 1111);
                    }
                } else if (type == 1) {
                    if (list.get(position).getStatus() == 0) {
                        startActivityForResult(new Intent(ITSLogActivity.this, SellDetailActivity.class)
                                .putExtra("id", list.get(position).getId()), 1111);
                    }
                }
            }
        });
    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    pageNum = 1;
                    getData();
//                    xclModel.rechargeList(pageNum, pageSize);
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
                public void handleMessage(Message msg) {
                    pageNum++;
                    getData();
//                    xclModel.rechargeList(pageNum, pageSize);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }

    private void changeType() {
        if (type == 0) {
            buy.setTextColor(getResources().getColor(R.color.color_DCB981));
            buy_line.setBackground(getResources().getDrawable(R.color.color_DCB981));
            sell.setTextColor(getResources().getColor(R.color.white));
            sell_line.setBackground(getResources().getDrawable(R.color.transparent));
        } else if (type == 1) {
            buy.setTextColor(getResources().getColor(R.color.white));
            buy_line.setBackground(getResources().getDrawable(R.color.transparent));
            sell.setTextColor(getResources().getColor(R.color.color_DCB981));
            sell_line.setBackground(getResources().getDrawable(R.color.color_DCB981));
        }
        getData();
    }

    public void getDataSuccess(List<OrderDetail> bean) {
        if (pageNum == 1) {
            list = bean;
            adapter.changeList(bean);
        } else {
            adapter.addList(bean);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buy:
                if (type != 0) {
                    type = 0;
                    pageNum = 1;
                    changeType();
                }
                break;
            case R.id.sell:
                if (type != 1) {
                    type = 1;
                    pageNum = 1;
                    changeType();
                }
                break;
            default:
                break;
        }
    }

    private void getData() {
        if (type == 0) {
            xclModel.orderList(pageNum, pageSize);
        } else if (type == 1) {
            xclModel.sellLogList(pageNum, pageSize);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNum = 1;
        getData();
    }
}
