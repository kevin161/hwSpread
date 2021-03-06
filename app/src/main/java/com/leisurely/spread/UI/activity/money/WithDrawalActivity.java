package com.leisurely.spread.UI.activity.money;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.ForgetPayPwdActivity;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Bank;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.util.WheelViewUtils;

import java.util.ArrayList;
import java.util.List;


public class WithDrawalActivity extends BaseActivity {

    private XclModel xclModel;

    private LinearLayout back;
    private TextView bankcard;
    private EditText amount;
    private EditText pwd;
    private TextView commit;
    private List<Bank> list;
    private List<String> banks;
    private String id;
    private TextView forget_pwd;
    private double maxAmount;
    private TextView withdrawal_detail;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.withdrawal_activity);

        back = findViewById(R.id.back);
        bankcard = findViewById(R.id.bankcard);
        amount = findViewById(R.id.amount);
        pwd = findViewById(R.id.pwd);
        commit = findViewById(R.id.commit);
        forget_pwd = findViewById(R.id.forget_pwd);
        withdrawal_detail = findViewById(R.id.withdrawal_detail);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        bankcard.setOnClickListener(this);
        commit.setOnClickListener(this);
        forget_pwd.setOnClickListener(this);
        withdrawal_detail.setOnClickListener(this);

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String cashString = amount.getText().toString();
                if (StringUtil.isNotNull(cashString)) {
                    try {
                        if (cashString.length() > 1 && cashString.indexOf("0") == 0 && (cashString.indexOf(".") > 1
                                || cashString.indexOf(".") == -1)) {
                            amount.setText(cashString.substring(1));
                            cashString = amount.getText().toString();
                        }

                        if (cashString.indexOf(".") > 0) {
                            if (cashString.indexOf(".") < (cashString.length() - 3)) {
                                amount.setText(cashString.substring(0, cashString.length() - 1));
                            }
                            if (cashString.indexOf(".") != cashString.lastIndexOf(".")) {
                                amount.setText(cashString.substring(0, cashString.length() - 1));
                            }
                        } else if (cashString.indexOf(".") == 0) {
                            amount.setText("0.");
                        }
                        double cash = Double.valueOf(cashString);
                        if (cash > maxAmount) {
                            amount.setText((TextUtil.get2Double(maxAmount)));
                        }
                    } catch (Exception e) {
                        if (cashString.length() > 1) {
                            amount.setText(cashString.substring(0, cashString.length() - 1));
                        } else {
//                        moneyout_et.setText("");
                            amount.setText("0.");
                        }
                    }
                    amount.setSelection(amount.getText().toString().length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        xclModel.queryCards();
        String title = getIntent().getStringExtra("title");
        if (StringUtil.isNotNull(title)) {
            setTitleName(title);
        }
        String maxAmountStr = getIntent().getStringExtra("maxAmount");
        if (StringUtil.isNotNull(maxAmountStr)) {
            maxAmount = Double.parseDouble(maxAmountStr);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                if (list != null && list.size() > 0) {
                    String result = validate();
                    if (StringUtil.isNullOrEmpty(result)) {
                        xclModel.withdraw(id, amount.getText().toString(), pwd.getText().toString());
                    } else {
                        ToastUtil.showToast(result);
                    }
                } else {
                    ToastUtil.showToast("请先绑定银行卡");
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.bankcard:
                if (list != null && list.size() > 0) {
                    int index = 0;
                    int position = 0;
                    for (Bank bank : list) {
                        if (bankcard.getText().toString().contains(bank.getCardNum())) {
                            position = index;
                        }
                        index++;
                    }
                    WheelViewUtils.alertBottomWheelOption(this, banks, position, new WheelViewUtils.OnWheelViewClick() {
                        @Override
                        public void onClick(View view, final int index) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    bankcard.setText(banks.get(index));
                                }
                            });
                        }
                    });
                } else {
                    ToastUtil.showToast("请先绑定银行卡");
                }
                break;
            case R.id.forget_pwd:
                startActivity(new Intent(this, ForgetPayPwdActivity.class)
                        .putExtra("type", 1).putExtra("title", "忘记资金密码"));
                break;
            case R.id.withdrawal_detail:
                startActivity(new Intent(this, MoneyHistoryLogActivity.class)
                        .putExtra("type", 1).putExtra("title", "提现"));
                break;
            default:
                break;
        }
    }

    public void withdrawSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            ToastUtil.showToast("提现已提交");
            finish();
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    public void queryCardsSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            list = JSONArray.parseArray(JSONArray.toJSONString(json.getJSONArray("data")), Bank.class);
            if (list != null && list.size() > 0) {
                banks = new ArrayList<>();
                for (Bank bank : list) {
                    banks.add(bank.getBankName() + " (" + bank.getCardNum() + ")");
                }
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    @NonNull
    private String validate() {

        id = "";
        for (Bank bank : list) {
            if (bankcard.getText().toString().contains(bank.getCardNum())) {
                id = bank.getId();
            }
        }
        if (StringUtil.isNullOrEmpty(id)) {
            return "请选择银行卡";
        }

        if (StringUtil.isNullOrEmpty(amount.getText().toString())) {
            return "请输入金额";
        }
        if (StringUtil.isNullOrEmpty(pwd.getText().toString())) {
            return "请输入资金密码";
        }


        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
