package com.leisurely.spread.UI.activity.money;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.ForgetPayPwdActivity;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.widget.pwd.PayPassDialog;
import com.leisurely.spread.widget.pwd.PayPassView;

/**
 * @version V3.0
 * @FileName: com.leisurely.spread.UI.activity.money.Withdrawal2Activity.java
 * @author: GYZ
 * asia161@qq.com
 * @date: 2020-01-11 09:26
 */
public class Withdrawal2Activity extends BaseActivity {
    private XclModel xclModel;
    private View view;
    private TimeCount mTimeCount;
    private LinearLayout back;
    private EditText edtTakeBack, edtAuthCode;
    private TextView txtActionBarRight, txtBankName, txtUserName, txtBankNum, txtCanTakeBack, txtTakeAll, txtSendSms, txtSubmit;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_withdrawal);
        view = findViewById(R.id.view);
        back = findViewById(R.id.back);
        txtSubmit = findViewById(R.id.txtSubmit);
        txtBankNum = findViewById(R.id.txtBankNum);
        txtBankNum = findViewById(R.id.txtBankNum);
        txtTakeAll = findViewById(R.id.txtTakeAll);
        txtSendSms = findViewById(R.id.txtSendSms);
        edtTakeBack = findViewById(R.id.edtTakeBack);
        edtAuthCode = findViewById(R.id.edtAuthCode);
        txtBankName = findViewById(R.id.txtBankName);
        txtUserName = findViewById(R.id.txtUserName);
        txtCanTakeBack = findViewById(R.id.txtCanTakeBack);
        txtActionBarRight = findViewById(R.id.txtActionBarRight);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);

        String uid = SharedPreferencesUtil.readString("uid", null);
        if (TextUtils.isEmpty(uid)) {
            ToastUtil.makeTextAndShow("无法获取用户ID，请尝试重新登录");
            return;
        }
        xclModel.getUserMoneyInfo(uid);
        xclModel.getUserBankInfo();
        mTimeCount = new TimeCount(120000, 1000);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        txtSubmit.setOnClickListener(this);
        txtTakeAll.setOnClickListener(this);
        txtSendSms.setOnClickListener(this);
        txtActionBarRight.setOnClickListener(this);
        edtTakeBack.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    view.setBackgroundColor(Color.parseColor("#1A95FC"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#DBDBDB"));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.txtSubmit:
                String result = validate();
                if (TextUtils.isEmpty(result)) {
                    payDialog();
                } else {
                    ToastUtil.makeTextAndShow(result);
                }
                break;

            case R.id.txtTakeAll:
                edtTakeBack.setText(txtCanTakeBack.getText().toString());
                break;

            case R.id.txtSendSms:
                String userPhone = SharedPreferencesUtil.readString("userPhone", "");

                if (TextUtils.isEmpty(userPhone)) {
                    ToastUtil.showToast("无法获取到您的手机号码，请尝试重新登录");
                    return;
                }
                xclModel.getCaptcha(userPhone, "5");

                break;
            case R.id.txtActionBarRight:
                startActivity(new Intent(this, MoneyHistoryLogActivity.class)
                        .putExtra("type", MoneyHistoryLogActivity.TYPE_WITHDRAWAL));
                break;
        }
    }

    String amountStr, authCode;

    private String validate() {
        amountStr = edtTakeBack.getText().toString().trim();
        double amount = TextUtil.parseDouble(amountStr);
        double canTakeAmount = TextUtil.parseDouble(txtCanTakeBack.getText().toString());
        if (TextUtils.isEmpty(amountStr)) {
            return "请输入提现金额";
        }
        if (amount <= 5) {
            return "单笔提现金额应大于5元";
        }
        if (amount > canTakeAmount) {
            return "提现金额应小于可提现额度";
        }
        authCode = edtAuthCode.getText().toString();
        if (TextUtils.isEmpty(authCode)) {
            return "请输入验证码";
        }

        return null;
    }


    public void getCaptchaSuccess(JSONObject json) {
//        ToastUtil.showToast(JSONObject.toJSONString(json));
        mTimeCount.start();
        txtSendSms.setClickable(false);
    }

    public void getUserMoneyInfoSuccess(JSONObject response) {
        if (response.getBoolean("success")) {
            String dd = response.getJSONObject("data").getString("available");
            txtCanTakeBack.setText(dd);
        } else {
            ToastUtil.makeTextAndShow(response.getString("msg"));
        }
    }

    public void getUserBankInfoSuccess(JSONObject response) {

        if ("1".equals(response.getJSONObject("status").getString("success"))) {
            JSONObject result = response.getJSONObject("result");
            String name = result.getString("ex_bank_acct_name");
            String bankName = result.getString("real_bank_name");
            String bankAcct = result.getString("real_bank_acct");

            txtUserName.setText(name);
            txtBankName.setText(bankName);
            txtBankNum.setText(TextUtil.addBlankInMiddle(bankAcct));
        } else {
            ToastUtil.makeTextAndShow(response.getJSONObject("status").getString("message"));
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            txtSendSms.setText("发送验证码");
//            txtSendSms.setTextColor(Color.WHITE);
            txtSendSms.setClickable(true);
        }

        @Override
        public void onTick(long arg0) {
            long min = arg0 / 1000;
            txtSendSms.setText(min + "s");
            txtSendSms.setTextColor(getResources().getColor(R.color.color_969696));
            txtSendSms.setBackgroundResource(R.drawable.e2e2e2_2dp);
        }
    }

    public void withdrawSuccess(JSONObject json) {
        // TODO: 2020/1/11 todo guoyazhou 未测试
        if (json.getBoolean("success")) {
            ToastUtil.showToast("提现已提交");
            finish();
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    private void payDialog() {
        final PayPassDialog dialog = new PayPassDialog(this);
        dialog.getPayViewPass().setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        //6位输入完成回调
                        xclModel.withdraw(amountStr, passContent, authCode);
                        dialog.dismiss();
                    }

                    @Override
                    public void onPayClose() {
                        dialog.dismiss();
                        //关闭回调
                    }

                    @Override
                    public void onPayForget() {
                        dialog.dismiss();
                        //点击忘记密码回调

                        startActivity(new Intent(getBaseContext(), ForgetPayPwdActivity.class)
                                .putExtra("type", 1));
                    }
                });
        dialog.setOutColse(true);
    }
}
