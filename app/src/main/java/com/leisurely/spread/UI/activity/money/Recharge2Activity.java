package com.leisurely.spread.UI.activity.money;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.widget.pwd.PayPassDialog;
import com.leisurely.spread.widget.pwd.PayPassView;

/**
 * 充值页面
 *
 * @version V3.0
 * @FileName: com.leisurely.spread.UI.activity.money.AuthAccountInfoActivity.java
 * @author: GYZ
 * asia161@qq.com
 * @date: 2020-01-10 17:31
 */
public class Recharge2Activity extends BaseActivity {

    private XclModel xclModel;
    private LinearLayout back;
    private TextView txtActionBarRight, txtName, txtBandCard, txtBankName, txtCopy ;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_recharge);
        back = findViewById(R.id.back);
        txtCopy = findViewById(R.id.txtCopy);
        txtName = findViewById(R.id.txtName);
        txtActionBarRight = findViewById(R.id.txtActionBarRight);
        txtBandCard = findViewById(R.id.txtBandCard);
        txtBankName = findViewById(R.id.txtBankName);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        xclModel.openAcctResult();
    }

    public void openAcctResultAcctSuccess(JSONObject response) {
        JSONObject result = response.getJSONObject("result");
        String bankCard = result.getString("ex_acct_no");
        String userName = result.getString("ex_bank_acct_name");
        String bankName = result.getString("bank_name");

        txtName.setText(userName);
        txtBandCard.setText(TextUtil.addBlankInMiddle(bankCard));
        txtBankName.setText(bankName);

    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        txtCopy.setOnClickListener(this);
        txtActionBarRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
            finish();
            break;
            case R.id.txtCopy:
                TextUtil.onClickCopy(txtBandCard.getText().toString(), this,"复制账号成功");
            break;
            case R.id.txtActionBarRight:
                startActivity(new Intent(this, MoneyHistoryLogActivity.class)
                        .putExtra("type", MoneyHistoryLogActivity.TYPE_RECHARGE));
            break;


        }
    }
}
