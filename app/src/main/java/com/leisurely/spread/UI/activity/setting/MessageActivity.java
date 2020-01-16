package com.leisurely.spread.UI.activity.setting;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.adapter.MessageAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Message;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jbuy on 2018/10/22.
 */

public class MessageActivity extends BaseActivity {

    private final int pageSize = 20;
    private int pageNum = 1;

    private XclModel xclModel;

    private PullableListView listView;
    private PullToRefreshLayout pull_refresh_lay;
    private MessageAdapter adapter;
    private List<Message> list;
    private View back;

    @Override
    protected void initView() {
        setContentView(R.layout.message_activity);
        setTitleName("系统公告");
        listView = findViewById(R.id.lv_listview);
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);
        back = findViewById(R.id.imageView_left_title_bar);

        list = new ArrayList<>();
        adapter = new MessageAdapter(this, list);
        listView.setAdapter(adapter);

        ((TextView) pull_refresh_lay.findViewById(R.id.state_tv)).setTextColor(Color.BLACK);
        ((TextView) pull_refresh_lay.findViewById(R.id.loadstate_tv)).setTextColor(Color.BLACK);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(null);
        back.setOnClickListener(this);
        pull_refresh_lay.setOnRefreshListener(new MyListener());
    }

    @Override
    protected void initData() {
        super.initData();
        xclModel = new XclModel(this);
        xclModel.inform(pageNum, pageSize);
    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(android.os.Message msg) {
                    pageNum = 1;
                    xclModel.inform(pageNum, pageSize);
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
                    xclModel.inform(pageNum, pageSize);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }

    public void informSuccess(List<Message> list) {

        if (pageNum != 1) {
            adapter.addList(list);
        } else {
//            communitys.add(0, new Community());
            adapter.changeList(list);
        }
        this.list = adapter.getList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_left_title_bar:
//                if (toMain) {
//                    startActivity(new Intent(this, MainActivity.class));
//                }
                finish();
                break;
            default:
                break;
        }
    }

}
