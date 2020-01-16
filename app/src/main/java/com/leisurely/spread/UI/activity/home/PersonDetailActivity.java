package com.leisurely.spread.UI.activity.home;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;


public class PersonDetailActivity extends BaseActivity {

    private XclModel xclModel;

    private EditText name;
    private EditText trueid;
    private TextView confirm_modift;//确认

    private String trueidStr;
    private String nameStr;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.activity_person_detail);
        setTitleName("个人资料");
        name = findViewById(R.id.name);
        trueid = findViewById(R.id.trueid);
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
        trueidStr = getIntent().getStringExtra("trueid");
        nameStr = getIntent().getStringExtra("name");
        if (StringUtil.isNotNull(trueidStr)) {
            name.setBackground(getResources().getDrawable(R.color.transparent));
            name.setEnabled(false);
            name.setFocusable(false);
            name.setKeyListener(null);
            name.setText(nameStr);
            trueid.setBackground(getResources().getDrawable(R.color.transparent));
            trueid.setClickable(false);
            trueid.setEnabled(false);
            trueid.setFocusable(false);
            trueid.setKeyListener(null);
            trueid.setText(trueidStr);
            confirm_modift.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_modift://确认修改
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.profile(name.getText().toString(), trueid.getText().toString());
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

    public void modifySuccess() {
        ToastUtil.showToast(getResources().getString(R.string.modify_success));
        finish();
    }

    @NonNull
    private String validate() {
        if (StringUtil.isNullOrEmpty(name.getText().toString())) {
            return "请输入姓名";
        }
        if (StringUtil.isNullOrEmpty(trueid.getText().toString())) {
            return "请输入身份证";
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
