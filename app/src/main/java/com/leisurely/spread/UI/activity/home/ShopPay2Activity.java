package com.leisurely.spread.UI.activity.home;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;


public class ShopPay2Activity extends BaseActivity {

    private XclModel xclModel;

    private RelativeLayout head_bc;

    private String name, id, amount, num;
    private TextView name_text;

    private TextView commit;
    private TextView amount_text;
    private TextView shop_id;
    private TextView number;
    private EditText pwd;


    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.shop_pay2_activity);

        head_bc = findViewById(R.id.head_bc);
        head_bc.setBackgroundColor(getResources().getColor(R.color.color_3BA370));
        setTitleName("密码支付");

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        amount = getIntent().getStringExtra("amount");
        num = getIntent().getStringExtra("num");

        name_text = findViewById(R.id.name);
        amount_text = findViewById(R.id.amount_text);
        commit = findViewById(R.id.commit);
        shop_id = findViewById(R.id.shop_id);
        number = findViewById(R.id.number);
        pwd = findViewById(R.id.pwd);

        name_text.setText(name);
        number.setText(num + "人");
        shop_id.setText("店铺ID: " + id);
        amount_text.setText(amount);
    }

    @Override
    public void onResume() {
        super.onResume();
//        xclModel.queryMemberInfo();
    }

    @Override
    protected void initListener() {
        commit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
//        xclModel.queryMemberInfo();
//        xclModel.getShopList(pageNum, pageSize, provinceCode,cityCode,areaCode,fullName);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                if (StringUtil.isNotNull(pwd.getText().toString())) {
                    xclModel.shopPurchase(id, num, amount, pwd.getText().toString());
                }else {
                    ToastUtil.showToast("交易密码不能为空");
                }
                break;
            default:
                break;
        }
    }

    public void shopPurchaseSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            Intent it = new Intent();
            setResult(RESULT_OK, it);
            ToastUtil.showToast("支付成功");
            finish();
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
