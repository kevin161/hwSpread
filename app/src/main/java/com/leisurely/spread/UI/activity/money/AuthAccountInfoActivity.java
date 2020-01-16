package com.leisurely.spread.UI.activity.money;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.TextUtil;

/**
 * 实名账户信息
 *
 * @version V3.0
 * @FileName: com.leisurely.spread.UI.activity.money.AuthAccountInfoActivity.java
 * @author: GYZ
 * asia161@qq.com
 * @date: 2020-01-10 17:31
 */
public class AuthAccountInfoActivity extends BaseActivity {

    private XclModel xclModel;
    private LinearLayout back;
    private TextView txtType, txtName, txtBindCard, txtBankName, txtUserId, txtState;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_auth_account_info);
        back = findViewById(R.id.back);
        txtType = findViewById(R.id.txtType);
        txtName = findViewById(R.id.txtName);
        txtState = findViewById(R.id.txtState);
        txtUserId = findViewById(R.id.txtUserId);
        txtBindCard = findViewById(R.id.txtBindCard);
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
        String accountType = result.getString("acct_type");
        String userId = result.getString("mch_user_id");

        txtType.setText("1".equals(accountType) ? "个人开户" : "企业开户");
        txtName.setText(userName);
        txtBindCard.setText(TextUtil.addBlankInMiddle(bankCard));
        txtBankName.setText(bankName);
        txtUserId.setText(userId);


        String openStatus = response.getJSONObject("status").getString("message");
        if ("SUCCESS".equals(openStatus)) {
            txtState.setText("开户成功");
        } else if ("USER_NOT_APPLY".equals(openStatus)) {
            txtState.setText("用户未开户");
        } else if ("PENDING".equals(openStatus)) {
            txtState.setText("审核中");
        } else if ("FAIL".equals(openStatus)) {
            txtState.setText("审核失败");
        }
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
