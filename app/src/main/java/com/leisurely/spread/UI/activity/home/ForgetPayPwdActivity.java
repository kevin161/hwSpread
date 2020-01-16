package com.leisurely.spread.UI.activity.home;

import android.content.Intent;
import android.graphics.Color;
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


public class ForgetPayPwdActivity extends BaseActivity {

    private XclModel xclModel;

    private LinearLayout back;
    private EditText code;
    private EditText phone;
    private EditText pwd;
    //    private EditText pwd2;
    private TextView forget_get_captcha;
    private TextView title;
    private TextView commit;
    private TextView reset;
    private int type;//0 可修改手机号 1 不可修改手机号

    private TimeCount mTimeCount;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.forget_pwd_activity);

        back = findViewById(R.id.back);
        pwd = findViewById(R.id.pwd);
        code = findViewById(R.id.code);
        phone = findViewById(R.id.phone);
        title = findViewById(R.id.textView_title_title_bar);
//        pwd2 = findViewById(R.id.pwd2);
        forget_get_captcha = findViewById(R.id.forget_get_captcha);
        commit = findViewById(R.id.commit);
        reset = findViewById(R.id.reset);
        pwd.setHint("请输入资金密码");
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
//        addTextViewListener(phone);
//        addTextViewListener(code);
//        addTextViewListener(pwd);
//        addTextViewListener(pwd2);
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


    public void checkPhoneSuccess(JSONObject json) {

        if (json.getBoolean("success")) {
            String data = json.getString("data");
            if (data.equals("0")) {
                xclModel.getCaptcha(phone.getText().toString(), "3");
            } else {
                ToastUtil.showToast("手机号未注册");
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }

    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        mTimeCount = new TimeCount(120000, 1000);
        type = getIntent().getIntExtra("type", 1);
        if (type == 1) {
            phone.setText(SharedPreferencesUtil.readString("userPhone", ""));
            phone.setFocusable(false);
            phone.setFocusableInTouchMode(false);
        }
        title.setText("重置交易密码");
//        String title = getIntent().getStringExtra("title");
//        if (StringUtil.isNotNull(title)) {
//            setTitleName(title);
//        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            forget_get_captcha.setText("发送验证码");
            forget_get_captcha.setTextColor(Color.WHITE);
            forget_get_captcha.setClickable(true);
            forget_get_captcha.setBackgroundResource(R.drawable.cb1b1d_2dp);
        }

        @Override
        public void onTick(long arg0) {
            long min = arg0 / 1000;
            forget_get_captcha.setText(min + "s");
            forget_get_captcha.setTextColor(getResources().getColor(R.color.color_969696));
            forget_get_captcha.setBackgroundResource(R.drawable.e2e2e2_2dp);
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
                    xclModel.modifTPws(phone.getText().toString(), code.getText().toString(), pwd.getText().toString());
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
        mTimeCount.start();
        forget_get_captcha.setClickable(false);
    }

    public void modifTPwsSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            SharedPreferencesUtil.saveString("payPass", pwd.getText().toString());
            if (StringUtil.isNotNull(SharedPreferencesUtil.readString("payPass", ""))) {
                ToastUtil.showToast("修改资金密码成功");
            } else {
                ToastUtil.showToast("设置资金密码成功");
            }
            SharedPreferencesUtil.saveString("payPass", pwd.getText().toString());
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

        if (StringUtil.isNullOrEmpty(code.getText().toString())) {
            return "请输入验证码";
        }
        if (StringUtil.isNullOrEmpty(pwd.getText().toString())) {
            return "请输入资金密码";
        }
        if (!TextUtil.isNumeric(pwd.getText().toString())) {
            return "交易密码为6位纯数字";
        }
        if (TextUtil.checkPwd(pwd)) {
            return "请输入6-20位数字密码";
        }
//        if (StringUtil.isNullOrEmpty(pwd2.getText().toString())) {
//            return "请确认资金密码";
//        }
//        if (!pwd.getText().toString().equals(pwd2.getText().toString())) {
//            return "两次密码输入不一致";
//        }

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

        return "";
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
