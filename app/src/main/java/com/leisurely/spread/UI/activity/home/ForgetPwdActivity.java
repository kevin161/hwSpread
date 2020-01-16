package com.leisurely.spread.UI.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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


public class ForgetPwdActivity extends BaseActivity {

    private XclModel xclModel;

    private LinearLayout back;
    private EditText code;
    private EditText phone;
    private EditText pwd;
    //    private EditText pwd2;
    private TextView forget_get_captcha;
    private TextView commit;
    private TextView reset;
    private int type;//0 可修改手机号 1 不可修改手机号
    private CheckBox checkShowPwd;
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
//        pwd2 = findViewById(R.id.pwd2);
        forget_get_captcha = findViewById(R.id.forget_get_captcha);
        commit = findViewById(R.id.commit);
        reset = findViewById(R.id.reset);
        checkShowPwd = findViewById(R.id.checkShowPwd);

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

        setEditTextInhibitInputSpace(pwd);
//        setEditTextInhibitInputSpace(pwd2);

        checkShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int index = pwd.getSelectionEnd();
                if (isChecked) {
                    pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                pwd.setSelection(index);
            }
        });
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
                xclModel.getCaptcha(phone.getText().toString(),"2");
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
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            phone.setText(SharedPreferencesUtil.readString("userPhone", ""));
            phone.setFocusable(false);
            phone.setFocusableInTouchMode(false);
        }

        String title = getIntent().getStringExtra("title");
        if (StringUtil.isNotNull(title)) {
            setTitleName(title);
        }
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

                String result4 = validatePhone();
                if (StringUtil.isNullOrEmpty(result4)) {
                    if (type == 0) {
                        xclModel.checkPhone(phone.getText().toString());
                    } else {
                        xclModel.getCaptcha(phone.getText().toString());
                    }

                } else {
                    ToastUtil.showToast(result4);
                }
                break;
            case R.id.commit:
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.modifPws(phone.getText().toString(), code.getText().toString(), pwd.getText().toString(), type);
                } else {
                    ToastUtil.showToast(result);
                }
                break;
            case R.id.reset:
                pwd.setText("");
                code.setText("");
//                pwd2.setText("");
                if (type == 0) {
                    phone.setText("");
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

    public void forgetPwdSuccess(JSONObject json) {
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
        if (StringUtil.isNullOrEmpty(pwd.getText().toString())) {
            return "请输入登录密码";
        }
        if (TextUtil.isNumeric(pwd.getText().toString())) {
            return "密码不能为纯数字";
        }
        if (TextUtil.checkPwd(pwd)) {
            return "请输入6-20位数字、字母";
        }
//        if (StringUtil.isNullOrEmpty(pwd2.getText().toString())) {
//            return "请确认登录密码";
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
        if (TextUtil.isMobileNumber(phone.getText().toString())) {
            return "请输入正确的手机号";
        }

        return "";
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
