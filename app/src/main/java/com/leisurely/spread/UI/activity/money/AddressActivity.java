package com.leisurely.spread.UI.activity.money;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.order.PlaceOrderActivity;
import com.leisurely.spread.UI.adapter.AddressAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Address;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/12/11.
 */

public class AddressActivity extends BaseActivity {

    public static final String TYPE_ADDRESS = "subType";
    public static final int TYPE_SELECT_ADDRESS = 100;
    private XclModel xclModel;


    private List<Address> list;

    private TextView add, toAdd,actionTitle;
    private AddressAdapter adapter;
    private Dialog backDialog;
    private int index;
    private LinearLayout back;
    private Dialog deleteDialog;
    private Address address;
    private RelativeLayout layoutNoAddress;
    private LinearLayout layoutHaveAddress;
    private final int pageSize = 10;
    private int pageNum = 1;


    private PullToRefreshLayout pull_refresh_lay;
    private PullableListView listView;
    private boolean isAdd = false;
    private int subType;// 进入页面的目的   100 选择地址

    @Override
    protected void initView() {
        setContentView(R.layout.activity_address);

        listView = findViewById(R.id.lv_listview);
        actionTitle = findViewById(R.id.actionTitle);
        add = findViewById(R.id.add);
        toAdd = findViewById(R.id.toAdd);
        back = findViewById(R.id.back);
        layoutNoAddress = findViewById(R.id.layoutNoAddress);
        layoutHaveAddress = findViewById(R.id.layoutHaveAddress);
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);
        listView = findViewById(R.id.lv_listview);

        list = new ArrayList<>();
        adapter = new AddressAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        xclModel = new XclModel(this);
        subType = getIntent().getIntExtra(TYPE_ADDRESS, 0);
        if (subType==TYPE_SELECT_ADDRESS){
            actionTitle.setText("选择收货地址");
            adapter.setOnItemClickListener(new AddressAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(Address address) {
                    Intent intent = new Intent();
                    intent.putExtra(PlaceOrderActivity.GOODS_BUY_ADDRESS,address);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        xclModel.findAddressPage(pageNum, pageSize);

    }


    @Override
    protected void initListener() {
        add.setOnClickListener(this);
        toAdd.setOnClickListener(this);
        back.setOnClickListener(this);
        pull_refresh_lay.setOnRefreshListener(new MyListener());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toAdd:
            case R.id.add:
//                address = null;
//                showDialog();
                startActivity(new Intent(getBaseContext(), AddUpdateAddressActivity.class));
                break;
            case R.id.back:
                setResult(RESULT_OK, new Intent());
                finish();
                break;
            default:
                break;
        }
    }

    public void deleteBank(String id, int position, Dialog deleteDialog) {
        this.deleteDialog = deleteDialog;
        index = position;
        xclModel.deleteAddress(id);
//        xclModel.deleteBankCard(id);
    }


    public void findAddressPageSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            JSONArray jsonArray = json.getJSONObject("data").getJSONArray("result");
            if (jsonArray != null) {
                list = JSONArray.parseArray(JSONArray.toJSONString(jsonArray), Address.class);
                if (pageNum == 1 || isAdd) {
                    adapter.changeList(list);
                    if (list.size() == 0) {
                        layoutHaveAddress.setVisibility(View.GONE);
                        layoutNoAddress.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        layoutHaveAddress.setVisibility(View.VISIBLE);
                        layoutNoAddress.setVisibility(View.GONE);
                    }
                    isAdd = false;
                } else {
                    adapter.addList(list);
                }
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }


    }


    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.add_address_dialog, null);
        backDialog = new Dialog(this, R.style.officeDialog);
        backDialog.setCancelable(true);
        backDialog.setContentView(view);
        ImageView close = view.findViewById(R.id.close);
        final EditText name = view.findViewById(R.id.name);
        final TextView title = view.findViewById(R.id.title);
        final EditText address_detail = view.findViewById(R.id.address_detail);
        final EditText phone = view.findViewById(R.id.phone);
        final EditText city = view.findViewById(R.id.city);


        if (address == null) {
            title.setText("新增地址");
        } else {
            title.setText("修改地址");
            name.setText(address.getUserName());
            address_detail.setText(address.getAddress());
            phone.setText(address.getPhone());
            city.setText(address.getProvince());
        }
//        setEditTextInhibitInputSpeChat(bank_name);
//        setEditTextInhibitInputSpeChat(sub_bank_name);
        address_detail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {

                } else {
                    address_detail.setSelection(0);
                }
            }
        });
        city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {

                } else {
                    city.setSelection(0);
                }
            }
        });

//        name.setText(SharedPreferencesUtil.readString("realname", ""));
        TextView commit = view.findViewById(R.id.commit);
//        backDialog.setCancelable(false);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backDialog.cancel();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result4 = validate(name, phone, city, address_detail);
                if (StringUtil.isNullOrEmpty(result4)) {
//                    xclModel.saveOrUpdateAddress(address.getId(), address.getUserName(),
//                            address.getPhone(), address.getProvince(), address.getAddress());

                } else {
                    ToastUtil.showToast(result4);
                }
            }
        });


        backDialog.show();
    }

    public void showAlert(final Address bank, final int position) {
        View layout = LayoutInflater.from(this).inflate(R.layout.view_alert, null);
        deleteDialog = new Dialog(this, R.style.officeDialog);
        deleteDialog.setCancelable(true);
        deleteDialog.setContentView(layout);

        TextView alert_title = layout.findViewById(R.id.alert_title);
        alert_title.setText("删除地址");
        TextView alert_content = layout.findViewById(R.id.alert_content);
//        alert_content.setText("是否确认删除此地址?");

        TextView text1 = layout.findViewById(R.id.awardpage_button1);
        text1.setText("取消");
        text1.setTextColor(Color.parseColor("#9C9B9B"));
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });
        TextView text2 = layout.findViewById(R.id.awardpage_button2);
        text2.setText("确定");
        text2.setTextColor(Color.parseColor("#FF3B3D"));
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBank(bank.getId(), position, deleteDialog);
                deleteDialog.dismiss();
            }
        });
        deleteDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                deleteDialog.dismiss();
            }
        });
        deleteDialog.show();
    }

    public void showChange(final Address bank, final int position) {
        View layout = LayoutInflater.from(this).inflate(R.layout.view_alert, null);
        deleteDialog = new Dialog(this, R.style.officeDialog);
        deleteDialog.setCancelable(true);
        deleteDialog.setContentView(layout);

        TextView alert_title = layout.findViewById(R.id.alert_title);
        alert_title.setText("确认");
        TextView alert_content = layout.findViewById(R.id.alert_content);
        alert_content.setText("是否确认修改为默认地址?");

        TextView text1 = layout.findViewById(R.id.awardpage_button1);
        text1.setText("取消");
        text1.setTextColor(this.getResources().getColor(R.color.light));
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });
        TextView text2 = layout.findViewById(R.id.awardpage_button2);
        text2.setText("确认");
        text2.setTextColor(this.getResources().getColor(R.color.color_1490D8));
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AddressActivity.this.deleteDialog = deleteDialog;
//                deleteBank(bank.getId(), position, deleteDialog);
                xclModel.defaultAddress(bank.getId());
                deleteDialog.dismiss();
            }
        });
        deleteDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                deleteDialog.dismiss();
            }
        });
        deleteDialog.show();
    }

    public void saveOrUpdateAddressSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            if (StringUtil.isNullOrEmpty(address.getId())) {
                ToastUtil.showToast("添加成功");
//            xclModel.queryCards();
//                list.add(address);

                xclModel.findAddressPage(1, adapter.getCount() + 1);
                isAdd = true;
            } else {
                ToastUtil.showToast("修改成功");
                list.remove(index);
                list.add(index, address);
            }
            adapter.changeList(list);
            address = null;
            backDialog.cancel();
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    public void deleteAddressSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            deleteDialog.cancel();
            ToastUtil.showToast("删除成功");
            list.remove(index);
            adapter.changeList(list);
            if (list.size() == 0) {
                layoutHaveAddress.setVisibility(View.GONE);
                layoutNoAddress.setVisibility(View.VISIBLE);
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    public void changeSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            deleteDialog.cancel();
            ToastUtil.showToast("设置默认地址成功");
//            list.remove(index);

            for (int i = 0; i < list.size(); i++) {
                Address address = list.get(i);
                if (this.address.getId().equals(address.getId())) {
                    address.setIsStatus(1);
                    list.remove(index);
                    list.add(0, address);
                } else {
                    address.setIsStatus(0);
                }
            }
            adapter.changeList(list);
//            xclModel.findAddressPage(1, list.size() + 1);

        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    public void changeStatus(Address address, int position) {
        this.address = address;
        index = position;
        showChange(address, position);
    }

    public void edit(Address address, int position) {
        this.address = address;
        index = position;
        Intent intent = new Intent(getBaseContext(),AddUpdateAddressActivity.class);
        intent.putExtra("address",address);
        startActivity(intent);
//        showDialog();
    }

    @NonNull
    private String validate(EditText name, EditText phone, EditText city, EditText address_detail) {

        if (StringUtil.isNullOrEmpty(name.getText().toString())) {
            return "请输入联系人";
        }
        if (StringUtil.isNullOrEmpty(phone.getText().toString())) {
            return "请输入联系方式";
        }
        if (TextUtil.isMobileNumber(phone.getText().toString())) {
            return "请输入正确的手机号";
        }
        if (StringUtil.isNullOrEmpty(city.getText().toString())) {
            return "请输入省市区";
        }
        if (StringUtil.isNullOrEmpty(address_detail.getText().toString())) {
            return "请输入详细地址";
        }
        if (address == null) {
            address = new Address();
        }
        address.setUserName(name.getText().toString());
        address.setAddress(address_detail.getText().toString());
        address.setPhone(phone.getText().toString());
        address.setProvince(city.getText().toString());
        return "";
    }


    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    pageNum = 1;
                    xclModel.findAddressPage(pageNum, pageSize);
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
                    xclModel.findAddressPage(pageNum, pageSize);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }


    /**
     * 禁止EditText表情
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[a-zA-Z0-9!@#%*()_+=`~\\u4E00-\\u9FA5]+";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (!matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK, new Intent());
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
