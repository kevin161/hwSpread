package com.leisurely.spread.UI.activity.home;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.adapter.OrderAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Order;
import com.leisurely.spread.util.DateUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class OrderActivity extends BaseActivity {


    private XclModel xclModel;

    private LinearLayout back;
    private TextView history;
    private TextView order;
    private RelativeLayout history_re;
    private LinearLayout order_li;
    private TextView search;
    private EditText order_no_et;
    private TextView express_no;
    private TextView express_company;
    private TextView order_no;
    private TextView name;
    private TextView phone;
    private TextView good_name;
    private TextView creat_time;
    private TextView send_time;
    private TextView number;
    private TextView price;
    private TextView total_price;
    private TextView address;
    private LinearLayout search_order_li;
    private RelativeLayout order_detail_re;
    private TextView yifahuo;


    private PullableListView listView;
    private PullToRefreshLayout pull_refresh_lay;
    private final int pageSize = 10;
    private int pageNum = 1;

    private List<Order> list;
    private OrderAdapter adapter;
    private Order searchOrder;

    private int status = 1;

    private int type = 0;//0 历史查询  1 物流信息查询

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.order_activity);

        back = findViewById(R.id.back);
        address = findViewById(R.id.address);
        total_price = findViewById(R.id.total_price);
        price = findViewById(R.id.price);
        number = findViewById(R.id.number);
        send_time = findViewById(R.id.send_time);
        creat_time = findViewById(R.id.creat_time);
        good_name = findViewById(R.id.good_name);
        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        order_no = findViewById(R.id.order_no);
        express_company = findViewById(R.id.express_company);
        express_no = findViewById(R.id.express_no);
        order_no_et = findViewById(R.id.order_no_et);
        search = findViewById(R.id.search);
        order_li = findViewById(R.id.order_li);
        history_re = findViewById(R.id.history_re);
        order = findViewById(R.id.order);
        history = findViewById(R.id.history);
        listView = findViewById(R.id.lv_listview);
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);
        search_order_li = findViewById(R.id.search_order_li);
        order_detail_re = findViewById(R.id.order_detail_re);
        yifahuo = findViewById(R.id.commit);
        list = new ArrayList<>();
        adapter = new OrderAdapter(this, list);
        listView.setAdapter(adapter);

        ((TextView) pull_refresh_lay.findViewById(R.id.state_tv)).setTextColor(getResources().getColor(R.color.light));
        ((TextView) pull_refresh_lay.findViewById(R.id.loadstate_tv)).setTextColor(getResources().getColor(R.color.light));
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNum = 1;
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        history.setOnClickListener(this);
        order.setOnClickListener(this);
        search.setOnClickListener(this);
        yifahuo.setOnClickListener(this);
        pull_refresh_lay.setOnRefreshListener(new MyListener());
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        xclModel.getOrders(pageNum, pageSize, null);
    }

    private void changeType(int changeType) {
        if (changeType == 0) {
            if (type != 0) {
//                listView.setAdapter(adapter);
                pageNum = 1;
                history.setBackground(getResources().getDrawable(R.drawable.left_3ba370_20dp));
                history.setTextColor(getResources().getColor(R.color.white));
                order.setBackground(getResources().getDrawable(R.drawable.right_3ba370_20dp_side));
                order.setTextColor(getResources().getColor(R.color.color_3BA370));
//                xclModel.getOrders(pageNum,pageSize);
//                xclModel.offlinePage(pageNum, pageSize);
            }
            type = 0;
        } else {
            if (type != 1) {

                history.setBackground(getResources().getDrawable(R.drawable.left_3ba370_20dp_side));
                history.setTextColor(getResources().getColor(R.color.color_3BA370));
                order.setBackground(getResources().getDrawable(R.drawable.right_3ba370_20dp));
                order.setTextColor(getResources().getColor(R.color.white));
//                xclModel.queryPage(pageNum, pageSize);


            }
            type = 1;
        }
    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(android.os.Message msg) {
                    pageNum = 1;
                    xclModel.getOrders(pageNum, pageSize, null);
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
                    xclModel.getOrders(pageNum, pageSize, null);
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
            case R.id.back:
                finish();
                break;
            case R.id.order:
                changeType(1);
                history_re.setVisibility(View.GONE);
                order_li.setVisibility(View.VISIBLE);
                order_detail_re.setVisibility(View.GONE);
                search_order_li.setVisibility(View.GONE);
                order_no_et.setText("");
                break;
            case R.id.history:
                changeType(0);
                history_re.setVisibility(View.VISIBLE);
                order_li.setVisibility(View.GONE);
                break;
            case R.id.search:
                if (StringUtil.isNullOrEmpty(order_no_et.getText().toString())) {
                    ToastUtil.showToast("请输入订单编号");
                } else {
                    xclModel.searchOrder(order_no_et.getText().toString());
                }
                break;
            case R.id.commit:
                startActivity(new Intent(this, OrderFahuoActivity.class));
                break;
            default:
                break;
        }
    }

    public void getOrdersSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            list = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONObject("data").getJSONArray("result")), Order.class);
            if (pageNum == 1) {
                adapter.changeList(list);
            } else {
                adapter.addList(list);
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    public void searchOrderSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            searchOrder = JSONObject.parseObject(JSONObject.toJSONString(json.getJSONObject("data")), Order.class);
            if (searchOrder != null) {
                search_order_li.setVisibility(View.VISIBLE);
                order_detail_re.setVisibility(View.VISIBLE);
                order_no.setText(searchOrder.getOrderNo());
                address.setText(searchOrder.getAddress());
                creat_time.setText(DateUtil.getdata(searchOrder.getCreatedAt()));
                if (searchOrder.getSendTime() == 0) {
                    send_time.setText("未发货");
                    express_company.setText("未发货");
                    express_no.setText("未发货");
                } else {
                    send_time.setText(DateUtil.getdata(searchOrder.getSendTime()));
                    express_company.setText(searchOrder.getExpressCompany());
                    express_no.setText(searchOrder.getExpressNo());
                }
                good_name.setText(searchOrder.getGoodName());
                number.setText(searchOrder.getNumber());
                phone.setText(searchOrder.getPhone());
                price.setText(searchOrder.getPrice());
                total_price.setText(searchOrder.getTotalPrice());
                name.setText(searchOrder.getUserName());
            } else {
                ToastUtil.showToast("无记录");
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
