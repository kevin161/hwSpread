package com.leisurely.spread.UI.activity.home;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.setting.MainAct;
import com.leisurely.spread.UI.adapter.NewsAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.News;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class NewsActivity extends BaseActivity {

    private XclModel xclModel;

    private LinearLayout back;

    private PullableListView listView;
    private PullToRefreshLayout pull_refresh_lay;
    private final int pageSize = 10;
    private int pageNum = 1;
    private List<News> list;
    private NewsAdapter adapter;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.news_activity);

        back = findViewById(R.id.back);
        listView = findViewById(R.id.lv_listview);
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);
        list = new ArrayList<>();
        adapter = new NewsAdapter(this, list);
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
        back.setOnClickListener(this);
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
                    getData();
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
    protected void initData() {
        xclModel = new XclModel(this);

        getData();
    }

    private void getData() {
        xclModel.getNewsList(pageNum, pageSize, "NOTICE");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }


    public void getNewsListSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            JSONArray jsonArray = json.getJSONArray("data");
            list = JSONArray.parseArray(JSONArray.toJSONString(jsonArray), News.class);
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
    protected void onDestroy() {
        super.onDestroy();
    }


}
