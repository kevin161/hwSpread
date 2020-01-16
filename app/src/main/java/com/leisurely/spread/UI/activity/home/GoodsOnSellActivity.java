package com.leisurely.spread.UI.activity.home;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.adapter.GoodsOnSellAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Commodity;
import com.leisurely.spread.model.bean.GoodsDetail;
import com.leisurely.spread.model.bean.Order;
import com.leisurely.spread.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V3.0
 * @FileName: com.leisurely.spread.UI.activity.home.GoodsOnSellActivity.java
 * @author: GYZ
 * asia161@qq.com
 * @date: 2020-01-09 12:37
 */
public class GoodsOnSellActivity extends BaseActivity {

    private XclModel xclModel;
    private Commodity goods;
    private LinearLayout layoutBack;
    private TextView txtTitle;

    private List<GoodsDetail> list;
    private GoodsOnSellAdapter adapter;
    private PullableListView listView;
    private PullToRefreshLayout pull_refresh_lay;
    private final int pageSize = 10;
    private int pageNum = 1;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_goods_on_sell);
        layoutBack = findViewById(R.id.layoutBack);
        txtTitle = findViewById(R.id.txtTitle);
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);
        listView = findViewById(R.id.lv_listview);
        list = new ArrayList<>();
        adapter = new GoodsOnSellAdapter(this, list);
        listView.setAdapter(adapter);

        ((TextView) pull_refresh_lay.findViewById(R.id.state_tv)).setTextColor(getResources().getColor(R.color.light));
        ((TextView) pull_refresh_lay.findViewById(R.id.loadstate_tv)).setTextColor(getResources().getColor(R.color.light));
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        goods = (Commodity) getIntent().getSerializableExtra("goods");
        txtTitle.setText(goods.getTypeName());
        xclModel.queryGoodsList(pageNum, pageSize, goods.getType());
    }

    @Override
    public void onResume() {
        super.onResume();
//        pageNum = 1;
//        xclModel.queryGoodsList(pageNum, pageSize, goods.getType());
    }
    @Override
    protected void initListener() {
        layoutBack.setOnClickListener(this);
        pull_refresh_lay.setOnRefreshListener(new  MyListener());
    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(android.os.Message msg) {
                    pageNum = 1;
                    xclModel.queryGoodsList(pageNum, pageSize, goods.getType());
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
                    xclModel.queryGoodsList(pageNum, pageSize, goods.getType());
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
            case R.id.layoutBack:
                finish();
                break;
        }
    }


    public void queryGoodsListSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            list = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONObject("data").getJSONArray("result")), GoodsDetail.class);
            if (pageNum == 1) {
                adapter.changeList(list);
            } else {
                adapter.addList(list);
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }
}
