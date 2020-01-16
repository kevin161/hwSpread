package com.leisurely.spread.UI.activity.home;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;


public class NickNameActivity extends BaseActivity {

    private XclModel xclModel;

    private LinearLayout back;
    private EditText nickname;
    private TextView commit;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.nickname_activity);

        back = findViewById(R.id.back);
        nickname = findViewById(R.id.nickname);
        commit = findViewById(R.id.commit);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        commit.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        xclModel = new XclModel(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.commit:
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.checkName(nickname.getText().toString());

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

    public void resetUserNameSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            ToastUtil.showToast("修改成功");
            Intent it = new Intent();
            setResult(RESULT_OK, it);
            finish();
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    @NonNull
    private String validate() {
        if (StringUtil.isNullOrEmpty(nickname.getText().toString())) {
            return "请输入昵称";
        }


        return "";
    }


    public void checkNameSuccess(JSONObject json) {

        if (json.getBoolean("success")) {
            String data = json.getString("data");
            if (data.equals("0")) {
                ToastUtil.showToast("昵称已被使用");
            } else {
                xclModel.resetUserName(nickname.getText().toString());
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }

    }

    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
