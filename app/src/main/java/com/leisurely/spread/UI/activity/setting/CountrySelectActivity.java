package com.leisurely.spread.UI.activity.setting;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.adapter.CountryAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Country;


public class CountrySelectActivity extends BaseActivity {

    private final int pageSize = 50;
    private int pageNum=1;
    private XclModel xclModel;

    private CountryAdapter adapter;
    private List<Country> list;

    private PullableListView listView;
    private PullToRefreshLayout pull_refresh_lay;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.activity_country);
        setTitleName(getResources().getString(R.string.choose_country));

        listView = findViewById(R.id.lv_listview);
        list = new ArrayList<>();
        adapter = new CountryAdapter(this, list);
        listView.setAdapter(adapter);

        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);
        ((TextView) pull_refresh_lay.findViewById(R.id.state_tv)).setTextColor(getResources().getColor(R.color.light));
        ((TextView) pull_refresh_lay.findViewById(R.id.loadstate_tv)).setTextColor(getResources().getColor(R.color.light));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("country", list.get(position).getName());
                intent.putExtra("abbr", list.get(position).getAbbr());
                intent.putExtra("code", list.get(position).getCode());
                intent.putExtra("countryId",list.get(position).getId());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        pull_refresh_lay.setOnRefreshListener(new MyListener());
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        xclModel.getCountry(pageNum,pageSize);
    }

    public void getCountrySuccess(List<Country> list) {
        if(pageNum==1){
            this.list = list;
            adapter.changeList(list);
        }else{
//            this.list.addAll(list);
            adapter.addList(list);
        }
//        ToastUtil.showToast(this.list.size() +":"+ list.size()+"");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    pageNum = 1;
                    xclModel.getCountry(pageNum,pageSize);
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
                    xclModel.getCountry(pageNum,pageSize);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
