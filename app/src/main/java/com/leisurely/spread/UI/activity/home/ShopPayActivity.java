package com.leisurely.spread.UI.activity.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.setting.ScanActivity;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.widget.permission.MyPermission;
import com.leisurely.spread.widget.permission.Permission;
import com.leisurely.spread.widget.permission.PermissionListener;

import java.util.List;


public class ShopPayActivity extends BaseActivity {

    private XclModel xclModel;

    private RelativeLayout head_bc;

    private String name, id;
    private TextView name_text;
    private EditText shop_id;
    private TextView scan;
    private TextView count;
    private TextView add;
    private TextView plus;
    private TextView yue;
    private TextView price_text;
    private TextView all_text;
    private double amount;
    private double price;
    private TextView commit;

    private int maxLimit, minLimit;
    private int num = 1;

    private RelativeLayout lay;
    private LinearLayout lay2;
    private TextView shop_id2;
    private RelativeLayout name_re;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.shop_pay_activity);

        head_bc = findViewById(R.id.head_bc);
        head_bc.setBackgroundColor(getResources().getColor(R.color.transparent));
        setTitleName("茶贝支付");

        id = getIntent().getStringExtra("id");

        name_text = findViewById(R.id.name);
        shop_id = findViewById(R.id.shop_id);
        commit = findViewById(R.id.commit);
        scan = findViewById(R.id.scan);
        count = findViewById(R.id.count);
        add = findViewById(R.id.add);
        yue = findViewById(R.id.yue);
        plus = findViewById(R.id.plus);
        price_text = findViewById(R.id.price_text);
        all_text = findViewById(R.id.all_text);


        lay = findViewById(R.id.lay);
        lay2 = findViewById(R.id.lay2);
        shop_id2 = findViewById(R.id.shop_id2);
        name_re = findViewById(R.id.name_re);

    }

    @Override
    public void onResume() {
        super.onResume();


//        xclModel.queryMemberInfo();
    }

    @Override
    protected void initListener() {
        commit.setOnClickListener(this);
        add.setOnClickListener(this);
        plus.setOnClickListener(this);
        scan.setOnClickListener(this);
        shop_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (shop_id.getText().toString().length() == 10) {
                    shop_id.setSelection(shop_id.getText().toString().length());
                    id = shop_id.getText().toString();
                    xclModel.getShopDetail(id);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                all_text.setText(TextUtil.get2Double(price * num) + "茶贝");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        xclModel.getAccount("CBJ");
        if (StringUtil.isNotNull(id)) {
            shop_id.setText(id);
            lay.setVisibility(View.GONE);
            shop_id.setVisibility(View.GONE);
            lay2.setVisibility(View.VISIBLE);
            shop_id2.setText(id);
            name_re.setBackground(getResources().getDrawable(R.drawable.btn_3ba370_6dp));
        }
//        xclModel.queryMemberInfo();
//        xclModel.getShopList(pageNum, pageSize, provinceCode,cityCode,areaCode,fullName);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.commit:
                if (StringUtil.isNullOrEmpty(id)) {
                    ToastUtil.showToast("请输入店铺ID或扫描店铺二维码");
                    return;
                }
                if (maxLimit <= 0) {
                    ToastUtil.showToast("请输入正确的店铺ID或扫描店铺二维码");
                    return;
                }
                if (!SharedPreferencesUtil.readBoolean(SysConstants.ISLOGIN, false)) {
                    Intent intent = new Intent();
                    intent.setClass(this, RegisterOrLoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (price * num > amount) {
                    ToastUtil.showToast("茶贝余额不足");
                } else {
                    startActivityForResult(new Intent(this, ShopPay2Activity.class)
                            .putExtra("amount", TextUtil.get2Double(price * num))
                            .putExtra("name", name)
                            .putExtra("id", id)
                            .putExtra("num", String.valueOf(num)), 1111);
                }
                break;
            case R.id.add:
                if (StringUtil.isNullOrEmpty(id)) {
                    ToastUtil.showToast("请输入店铺ID或扫描店铺二维码");
                    return;
                }
                if (num + 1 > maxLimit) {
                    ToastUtil.showToast("超出最大数量");
                    return;
                }
                num++;
                count.setText(String.valueOf(num));
                break;
            case R.id.plus:
                if (StringUtil.isNullOrEmpty(id)) {
                    ToastUtil.showToast("请输入店铺ID或扫描店铺二维码");
                    return;
                }
                if (num - 1 < minLimit) {
                    ToastUtil.showToast("小于最小数量");
                    return;
                }
                num--;
                count.setText(String.valueOf(num));
                break;
            case R.id.scan:
                new MyPermission(this, Permission.CAMERA, new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        openScan();
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        ToastUtil.makeTextAndShow("未打开相机权限！");
                    }
                }).request();
                break;
            default:
                break;
        }
    }

    private static final int REQUEST_SCAN = 49374;

    /**
     * 打开系统默认扫描界面
     */
    public void openScan() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1001);
        } else { //有权限直接调用系统相机拍照
            startActivityForResult(new Intent(this, ScanActivity.class), REQUEST_SCAN);
//            Intent intent = new Intent(this, CaptureActivity.class);
//            startActivityForResult(intent, REQUEST_SCAN);
        }


//        if (Build.VERSION.SDK_INT > 22) {
//            if (ContextCompat.checkSelfPermission(this,
//                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                //先判断有没有权限 ，没有就在这里进行权限的申请
//                ActivityCompat.requestPermissions(this,
//                        new String[]{android.Manifest.permission.CAMERA},1001);
//                Intent intent = new Intent(Settings.ACTION_SETTINGS);
//                startActivity(intent);
//            } else {
//                startActivityForResult(new Intent(this, ScanActivity.class), REQUEST_SCAN);
//            }
//        } else {
//            startActivityForResult(new Intent(this, ScanActivity.class), REQUEST_SCAN);
//
//        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SCAN && resultCode == RESULT_OK) {
            String code = data.getStringExtra("result");
            if (StringUtil.isNotNull(code)) {
                try {
                    if (code.length() == 10 && TextUtil.isNumber(code)) {
                        id = code;
                        shop_id.setText(id);
                    } else {
                        ToastUtil.showToast("请扫描正确的店铺二维码");
                    }
                } catch (Exception e) {
                    ToastUtil.showToast("请扫描正确的店铺二维码");
                }
            } else {
                ToastUtil.showToast("请扫描正确的店铺二维码");
            }
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == 1111) {
                Intent it = new Intent();
                setResult(RESULT_OK, it);
                finish();
            }
        }
    }

    public void getAccountSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            if (json.getJSONObject("data") != null) {
                amount = json.getJSONObject("data").getDoubleValue("available");
                yue.setText(TextUtil.get2Double(amount));
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    public void getShopDetailSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            if (json.getJSONObject("data") != null) {
                name = json.getJSONObject("data").getString("aliasName");
                name_text.setText(name);
                price = json.getJSONObject("data").getDoubleValue("price");
                maxLimit = json.getJSONObject("data").getIntValue("maxLimit");
                minLimit = json.getJSONObject("data").getIntValue("minLimit");
                num = minLimit;
                price_text.setText(price + "茶贝/人");
                count.setText(minLimit + "");
            } else {
                ToastUtil.showToast("店铺不存在,请确认店铺ID是否正确");
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
