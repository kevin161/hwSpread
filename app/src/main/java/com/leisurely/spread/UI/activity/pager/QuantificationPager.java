package com.leisurely.spread.UI.activity.pager;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.MainActivity;
import com.leisurely.spread.UI.adapter.QuantificationAdapter;
import com.leisurely.spread.framework.tabManager.BasePager;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Quantification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuantificationPager extends BasePager implements View.OnClickListener {

    public static XclModel xclModel;
    private ListView listview;
    private List<Quantification> list;

    private QuantificationAdapter adapter;



    public QuantificationPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {

        rootView = mInflater.inflate(R.layout.activity_quantification, null);
        listview = rootView.findViewById(R.id.listview);

        list = new ArrayList<>();
        adapter = new QuantificationAdapter(mContext,list);
        listview.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void initData() {
        if (xclModel == null) {
            xclModel = new XclModel((MainActivity) mContext, this);
        }
        xclModel.addInvestmentList();
        initListener();
    }

    private void initListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = ((MainActivity)mContext).getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(mContext.getResources().getColor(R.color.white));
//            //底部导航栏
//            //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
//        }

        if (xclModel == null) {
            xclModel = new XclModel((MainActivity) mContext, this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }

    }

    public void getInvestmentListSuccess(JSONObject json){
        list = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONObject("data").getJSONArray("rule"))
                ,Quantification.class);
        Map<String,Object> map =  json.getJSONObject("data").getJSONObject("money");
            adapter.changeList(list,map);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            default:
                break;
        }
    }

}
