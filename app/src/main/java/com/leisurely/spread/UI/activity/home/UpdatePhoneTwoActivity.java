package com.leisurely.spread.UI.activity.home;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.Editable;
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


public class UpdatePhoneTwoActivity extends BaseActivity {

    private XclModel xclModel;

    private LinearLayout back;
    private EditText code;
    private EditText phone;
    private TextView forget_get_captcha;
    private TextView commit;
    private TextView reset;

    private TimeCount mTimeCount;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.update_phone_one);

        back = findViewById(R.id.back);
        code = findViewById(R.id.code);
        phone = findViewById(R.id.phone);
        forget_get_captcha = findViewById(R.id.forget_get_captcha);
        commit = findViewById(R.id.commit);
        reset = findViewById(R.id.reset);

        setTitle("设置新手机号");
        commit.setText("提交");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        forget_get_captcha.setOnClickListener(this);
        commit.setOnClickListener(this);
        reset.setOnClickListener(this);
        addTextViewListener(code);

    }

    private void addTextViewListener(final EditText mEditText) {
        mEditText.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        // 设置文本的显示方向
        mEditText.setTextDirection(View.TEXT_DIRECTION_RTL);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (StringUtil.isNullOrEmpty(editable.toString())) {
                    mEditText.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                    mEditText.setTextDirection(View.TEXT_DIRECTION_RTL);
                } else {
                    mEditText.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
                    mEditText.setTextDirection(View.TEXT_DIRECTION_LTR);
                }
            }
        });
    }


    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        mTimeCount = new TimeCount(60000, 1000);
//        phone.setText(SharedPreferencesUtil.readString("userPhone", ""));
//        phone.setFocusable(false);
//        phone.setFocusableInTouchMode(false);

    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            forget_get_captcha.setText("发送验证码");
//            get_captcha.setBackground(getResources().getDrawable(R.drawable.fdcd00_20dp_right));
            forget_get_captcha.setTextColor(getResources().getColor(R.color.color_1490D8));
            forget_get_captcha.setClickable(true);
        }

        @Override
        public void onTick(long arg0) {
            long min = arg0 / 1000;
//            String str = "<font>" + min + "</font><small><font> s</font></small>";
            forget_get_captcha.setText(min + "s");
//            get_captcha.setBackground(getResources().getDrawable(R.drawable.cccccc_20dp_right));
            forget_get_captcha.setTextColor(getResources().getColor(R.color.color_969696));
        }
    }

    public void checkPhoneSuccess(JSONObject json) {

        if (json.getBoolean("success")) {
            String data = json.getString("data");
            if (data.equals("0")) {
                ToastUtil.showToast("手机号已注册");
            } else {
                xclModel.getCaptcha(phone.getText().toString());
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_get_captcha:
                String result1 = validatePhone();
                if (StringUtil.isNullOrEmpty(result1)) {
                    xclModel.checkPhone(phone.getText().toString());
                } else {
                    ToastUtil.showToast(result1);
                }

                break;
            case R.id.commit:
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.modifPhone(phone.getText().toString(), code.getText().toString());
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

    public void getCaptchaSuccess(JSONObject json) {
//        ToastUtil.showToast(JSONObject.toJSONString(json));
        mTimeCount.start();
        forget_get_captcha.setClickable(false);
    }

    public void modifPhoneSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            SharedPreferencesUtil.saveString("userPhone", phone.getText().toString());
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
        if (StringUtil.isNullOrEmpty(phone.getText().toString())) {
            return "请输入手机号";
        }
        if (phone.getText().toString().length() != 11) {
            return "请输入正确手机号";
        }
        if (TextUtil.isMobileNumber(phone.getText().toString())) {
            return "请输入正确的手机号";
        }
        if (StringUtil.isNullOrEmpty(code.getText().toString())) {
            return "请输入验证码";
        }


        return "";
    }

    @NonNull
    private String validatePhone() {
        if (StringUtil.isNullOrEmpty(phone.getText().toString())) {
            return "请输入手机号";
        }
        if (phone.getText().toString().length() != 11) {
            return "请输入正确手机号";
        }
        if (TextUtil.isMobileNumber(phone.getText().toString())) {
            return "请输入正确的手机号";
        }

        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
