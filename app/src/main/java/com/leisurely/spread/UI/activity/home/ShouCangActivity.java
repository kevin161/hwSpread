package com.leisurely.spread.UI.activity.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.adapter.ShopAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Shop;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class ShouCangActivity extends BaseActivity {

    private XclModel xclModel;

    private RelativeLayout head;

    private PullableListView listView;
    private PullToRefreshLayout pull_refresh_lay;
    private final int pageSize = 10;
    private int pageNum = 1;

    private List<Shop> list;
    private ShopAdapter adapter;

    private String provinceCode, cityCode, areaCode, fullName;


    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.shoucang_activity);

        setTitleName("收藏店铺");
        RelativeLayout head_bc = findViewById(R.id.head_bc);
        head_bc.setBackgroundColor(getResources().getColor(R.color.color_3BA370));
        listView = findViewById(R.id.lv_listview);
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);

        list = new ArrayList<>();
        adapter = new ShopAdapter(this, list);
        listView.setAdapter(adapter);

        ((TextView) pull_refresh_lay.findViewById(R.id.state_tv)).setTextColor(getResources().getColor(R.color.light));
        ((TextView) pull_refresh_lay.findViewById(R.id.loadstate_tv)).setTextColor(getResources().getColor(R.color.light));
    }

    @Override
    public void onResume() {
        super.onResume();
//        xclModel.queryMemberInfo();

    }

    @Override
    protected void initListener() {
        pull_refresh_lay.setOnRefreshListener(new MyListener());
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        xclModel.getKeepShopList(pageNum, pageSize);
//        xclModel.queryMemberInfo();
//        xclModel.getShopList(pageNum, pageSize, provinceCode,cityCode,areaCode,fullName);
    }


    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(android.os.Message msg) {
                    pageNum = 1;
                    xclModel.getKeepShopList(pageNum, pageSize);
//                    xclModel.getScoreDetail(pageNum, pageSize);
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
                    xclModel.getKeepShopList(pageNum, pageSize);
//                    xclModel.getScoreDetail(pageNum, pageSize);
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

            default:
                break;
        }
    }

    private int index;

    public void getShopListSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            List<Shop> list = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONObject("data").getJSONArray("result")), Shop.class);
            if (pageNum == 1) {
                adapter.changeList(list);
                this.list = list;
            } else {
                adapter.addList(list);
                this.list.addAll(list);
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    private Dialog dialog;

    public void showDialogShoucang(final int status, String name, final String id, int position) {
        if (!SharedPreferencesUtil.readBoolean(SysConstants.ISLOGIN, false)) {
            Intent intent = new Intent();
            intent.setClass(this, RegisterOrLoginActivity.class);
            startActivity(intent);
            return;
        }
        index = position;
        View view = LayoutInflater.from(this).inflate(R.layout.view_alert, null);
        dialog = new Dialog(this, R.style.officeDialog);
        dialog.setCancelable(true);
        dialog.setContentView(view);

        TextView alert_title = view.findViewById(R.id.alert_title);
        alert_title.setText(name);
        TextView alert_content = view.findViewById(R.id.alert_content);
        alert_content.setText("是否确认已经签收到了货物!");
        alert_content.setVisibility(View.GONE);
        TextView text1 = view.findViewById(R.id.awardpage_button1);
        text1.setText("取消");
        text1.setTextColor(this.getResources().getColor(R.color.light));
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView text2 = view.findViewById(R.id.awardpage_button2);
        if (status == 1) {
            text2.setText("确认收藏");
        } else {
            text2.setText("取消收藏");
        }
        text2.setTextColor(this.getResources().getColor(R.color.color_3BA370));
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xclModel.updateShouCang(id, status);
            }
        });


        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                dialog.dismiss();
            }
        });
        dialog.show();
//        popupWindow.setContentView(view);
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setFocusable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.showAtLocation(((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0), Gravity.CENTER, 0, 0);
    }

    public void updateShouCangSuccess(JSONObject json) {
        dialog.dismiss();
        if (json.getBoolean("success")) {
            ToastUtil.showToast("操作成功");
            list.remove(index);
            adapter.notifyDataSetChanged();
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
