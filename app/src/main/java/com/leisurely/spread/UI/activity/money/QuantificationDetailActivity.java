package com.leisurely.spread.UI.activity.money;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.adapter.QuantificationDetailAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Quantification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/11.
 */

public class QuantificationDetailActivity extends BaseActivity {

    private XclModel xclModel;
    private QuantificationDetailAdapter adapter;
    private List<Quantification> list;
    private final int pageSize = 10;
    private int pageNum = 1;

    private PullToRefreshLayout pull_refresh_lay;
    private PullableListView listView;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_chongbi_detail);
        setTitleName("量化明细");
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);
        listView = findViewById(R.id.lv_listview);
        findViewById(R.id.bc).setBackgroundColor(getResources().getColor(R.color.color_464A53));

        list = new ArrayList<>();
        adapter = new QuantificationDetailAdapter(this, list);
        listView.setAdapter(adapter);

        ((TextView) pull_refresh_lay.findViewById(R.id.state_tv)).setTextColor(getResources().getColor(R.color.light));
        ((TextView) pull_refresh_lay.findViewById(R.id.loadstate_tv)).setTextColor(getResources().getColor(R.color.light));
    }

    @Override
    protected void initData() {
        super.initData();
        xclModel = new XclModel(this);
        xclModel.investmentList(pageNum, pageSize);

    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    pageNum = 1;
                    xclModel.investmentList(pageNum, pageSize);
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
                    xclModel.investmentList(pageNum, pageSize);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }

    @Override
    protected void initListener() {
        pull_refresh_lay.setOnRefreshListener(new MyListener());
    }

    public void investmentListSuccess(List<Quantification> bean) {
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
            default:
                break;
        }
    }


}
