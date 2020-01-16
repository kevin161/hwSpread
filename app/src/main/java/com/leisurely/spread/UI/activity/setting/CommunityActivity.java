package com.leisurely.spread.UI.activity.setting;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.adapter.CommunityAdapter;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Community;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/12.
 */

public class CommunityActivity extends BaseActivity {

    private XclModel xclModel;

    private TextView share_count;
    private TextView community_count;
    private TextView count;
    private ListView listview;
    private CommunityAdapter adapter;
    private List<Community> list;
    private ScrollView scrollview;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_community);
        setTitleName("我的社区");
        share_count = findViewById(R.id.share_count);
        community_count = findViewById(R.id.community_count);
        count = findViewById(R.id.count);
        listview = findViewById(R.id.listview);
        scrollview = findViewById(R.id.scrollview);
        list = new ArrayList<>();
        adapter = new CommunityAdapter(this,list);
        listview.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        super.initData();
        xclModel = new XclModel(this);
        xclModel.community();
    }

    public void communitySuccess(JSONObject json){
        share_count.setText(json.getString("direct"));
        community_count.setText(json.getString("number"));
        count.setText(json.getString("share"));
        list= JSONArray.parseArray(JSONArray.toJSONString(json.getJSONArray("list")),Community.class);
        adapter.changeList(list);
        setListViewHeightBasedOnChildren(listview);
        scrollview.smoothScrollTo(0,20);
        scrollview.setFocusable(true);
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
    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
