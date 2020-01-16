package com.leisurely.spread.UI.activity.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.PayWay;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;


public class AddCardActivity extends BaseActivity {

    private XclModel xclModel;

    private EditText name;
    private EditText cardid;
    private EditText bankdeposit;
    private EditText bankbranch;
    private TextView confirm_modift;//确认

    private String cardidStr;
    private String nameStr;

    private int id;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.activity_add_card);
        id = getIntent().getIntExtra("id", 0);
        if (id > 0) {
            setTitleName("修改银行卡");
        } else {
            setTitleName("添加银行卡");
        }
        name = findViewById(R.id.name);
        cardid = findViewById(R.id.cardid);
        bankdeposit = findViewById(R.id.bankdeposit);
        bankbranch = findViewById(R.id.bankbranch);
        confirm_modift = findViewById(R.id.confirm_modift);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        confirm_modift.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        if (id > 0) {
            xclModel.userpayway(String.valueOf(id));
        }
    }

    public void userpaywaySuccess(PayWay payWay) {
        name.setText(payWay.getName());
        cardid.setText(payWay.getAccount());
        bankdeposit.setText(payWay.getBankdeposit());
        if (StringUtil.isNotNull(payWay.getBankbranch())) {
            bankbranch.setText(payWay.getBankbranch());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_modift://确认修改
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.savepayway(String.valueOf(id), 3, cardid.getText().toString(), null,
                            name.getText().toString(), bankdeposit.getText().toString(), bankbranch.getText().toString());
                } else {
                    ToastUtil.showToast(result);
                }
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    public void modifySuccess(int id) {
//        ToastUtil.showToast("提交成功");
        Intent it = new Intent();
        it.putExtra("msg", bankdeposit.getText().toString() + " " + cardid.getText().toString());
        it.putExtra("id",id);
        setResult(RESULT_OK, it);
        finish();
    }

    @NonNull
    private String validate() {
        if (StringUtil.isNullOrEmpty(name.getText().toString())) {
            return "请输入姓名";
        }
        if (StringUtil.isNullOrEmpty(cardid.getText().toString())) {
            return "请输入银行卡号";
        }
        if (StringUtil.isNullOrEmpty(bankdeposit.getText().toString())) {
            return "请输入开户银行";
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
