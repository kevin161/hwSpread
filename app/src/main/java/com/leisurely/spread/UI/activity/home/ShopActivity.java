package com.leisurely.spread.UI.activity.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.leisurely.spread.model.bean.Area;
import com.leisurely.spread.model.bean.City;
import com.leisurely.spread.model.bean.Provinces;
import com.leisurely.spread.model.bean.Shop;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.util.WheelViewUtils;

import java.util.ArrayList;
import java.util.List;


public class ShopActivity extends BaseActivity {

    private XclModel xclModel;


    private PullableListView listView;
    private PullToRefreshLayout pull_refresh_lay;
    private final int pageSize = 10;
    private int pageNum = 1;

    private List<Shop> list;
    private ShopAdapter adapter;

    private ImageView head;

    private String provinceCode, cityCode, areaCode, fullName;


    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.shop_activity);


        listView = findViewById(R.id.lv_listview);
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);
        head = findViewById(R.id.head);
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
        xclModel.getShopList(pageNum, pageSize, provinceCode, cityCode, areaCode, fullName);
    }

    @Override
    protected void initListener() {
        pull_refresh_lay.setOnRefreshListener(new MyListener());
        head.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
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
                    xclModel.getShopList(pageNum, pageSize, provinceCode, cityCode, areaCode, fullName);
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
                    xclModel.getShopList(pageNum, pageSize, provinceCode, cityCode, areaCode, fullName);
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
            case R.id.head:
                showDialog();
                break;
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


    private TextView provinces_text, city_text, area_text;

    public void showDialog() {
        final PopupWindow popupWindow = new PopupWindow();
        View view = LayoutInflater.from(this).inflate(R.layout.show_shop_select, null);

        provinceCode = "";
        cityCode = "";
        areaCode = "";
        LinearLayout all = view.findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        provinces_text = view.findViewById(R.id.provinces_text);
        provinces_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xclModel.getProvinces();
            }
        });

        city_text = view.findViewById(R.id.city_text);
        city_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isNullOrEmpty(provinceCode)) {
                    ToastUtil.showToast("请先选择省份");
                } else {
                    xclModel.getCities(provinceCode);
                }
            }
        });

        area_text = view.findViewById(R.id.area_text);
        area_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isNullOrEmpty(provinceCode)) {
                    ToastUtil.showToast("请先选择省份");
                } else if (StringUtil.isNullOrEmpty(cityCode)) {
                    ToastUtil.showToast("请先选择城市");
                } else {
                    xclModel.getAreas(cityCode);
                }
            }
        });

        final EditText fullname_text = view.findViewById(R.id.fullname_text);


        final TextView search = view.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                xclModel.getShopList(pageNum, pageSize, provinceCode, cityCode, areaCode, fullname_text.getText().toString());
            }
        });


        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0), Gravity.CENTER, 0, 0);
    }

    public void updateShouCangSuccess(JSONObject json) {
        dialog.dismiss();
        if (json.getBoolean("success")) {
            ToastUtil.showToast("操作成功");
            list.get(index).setKeep(!list.get(index).getKeep());
            adapter.notifyDataSetChanged();
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    List<Provinces> provinces;

    public void getProvincesSuccess(JSONObject json) {
        if (json.getBoolean("success")) {

            provinces = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONArray("data")), Provinces.class);

            WheelViewUtils.alertBottomWheelOption(this, provinces, index, new WheelViewUtils.OnWheelViewClick() {
                @Override
                public void onClick(View view, final int index) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            provinces_text.setText(provinces.get(index).getProvince());
                            provinceCode = provinces.get(index).getProvinceid();
                        }
                    });
                }
            });
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    List<City> citys;

    public void getCitiesSuccess(JSONObject json) {
        if (json.getBoolean("success")) {

            citys = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONArray("data")), City.class);

            WheelViewUtils.alertBottomWheelOption(this, citys, index, new WheelViewUtils.OnWheelViewClick() {
                @Override
                public void onClick(View view, final int index) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            city_text.setText(citys.get(index).getCity());
                            cityCode = citys.get(index).getCityid();
                        }
                    });
                }
            });
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    List<Area> areas;

    public void getAreasSuccess(JSONObject json) {
        if (json.getBoolean("success")) {

            areas = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONArray("data")), Area.class);

            WheelViewUtils.alertBottomWheelOption(this, areas, index, new WheelViewUtils.OnWheelViewClick() {
                @Override
                public void onClick(View view, final int index) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            area_text.setText(areas.get(index).getArea());
                            areaCode = areas.get(index).getAreaid();
                        }
                    });
                }
            });
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
