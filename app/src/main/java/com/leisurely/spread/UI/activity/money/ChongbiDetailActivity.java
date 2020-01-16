package com.leisurely.spread.UI.activity.money;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.adapter.ChongbiDetailAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Chongbi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/11.
 */

public class ChongbiDetailActivity extends BaseActivity {

    private XclModel xclModel;
    private ChongbiDetailAdapter adapter;
    private List<Chongbi> list;
    private final int pageSize = 10;
    private int pageNum = 1;

    private PullToRefreshLayout pull_refresh_lay;
    private PullableListView listView;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_chongbi_detail);
        setTitleName("充值记录");
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);
        listView = findViewById(R.id.lv_listview);



    }

    @Override
    protected void initData() {
        super.initData();list = new ArrayList<>();
        adapter = new ChongbiDetailAdapter(this, list);
        listView.setAdapter(adapter);

        ((TextView) pull_refresh_lay.findViewById(R.id.state_tv)).setTextColor(getResources().getColor(R.color.light));
        ((TextView) pull_refresh_lay.findViewById(R.id.loadstate_tv)).setTextColor(getResources().getColor(R.color.light));
        xclModel = new XclModel(this);
        xclModel.rechargeList(pageNum, pageSize);

    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    pageNum = 1;
                    xclModel.rechargeList(pageNum, pageSize);
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
                    xclModel.rechargeList(pageNum, pageSize);
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

    public void rechargeListSuccess(List<Chongbi> bean) {
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
