package com.leisurely.spread.UI.activity.home;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;


public class ModifyPasswordActivity extends BaseActivity {

    private XclModel xclModel;

    private EditText old_password;//旧密码
    private EditText new_password;//新密码
    private EditText repeat_new_password;//确认新密码
    private TextView confirm_modift;//确认修改
    private ImageView back;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.activity_modify_password);
        setTitleName(getResources().getString(R.string.modify_pwd));
        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        repeat_new_password = findViewById(R.id.repeat_new_password);
        confirm_modift = findViewById(R.id.confirm_modift);
        back = findViewById(R.id.back);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        confirm_modift.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_modift://确认修改
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.editPassword(old_password.getText().toString(), new_password.getText().toString());
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

    public void modifySuccess(){
        ToastUtil.showToast(getResources().getString(R.string.modify_success));
        finish();
    }

    @NonNull
    private String validate() {
        if (StringUtil.isNullOrEmpty(old_password.getText().toString())) {
            return "请输入旧密码";
        }
        if (StringUtil.isNullOrEmpty(new_password.getText().toString())) {
            return "请输入新密码";
        }
        if (new_password.getText().toString().length() > 20 || new_password.getText().toString().length() < 6) {
            return "新密码长度不符合,请输入6-20位";
        }
        if (StringUtil.isNullOrEmpty(repeat_new_password.getText().toString())) {
            return "请输入重复新密码";
        }
        if (!new_password.getText().toString().equals(repeat_new_password.getText().toString())) {
            return "新密码两次输入不一致,请检查";
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
