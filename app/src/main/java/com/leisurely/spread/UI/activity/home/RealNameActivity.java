package com.leisurely.spread.UI.activity.home;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
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
import com.leisurely.spread.util.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RealNameActivity extends BaseActivity {

    private XclModel xclModel;

    private LinearLayout back;
    private EditText name;
    private EditText code;
    private TextView phone;
    private EditText idcard;
    private TextView forget_get_captcha;
    private TextView commit;
    private TextView reset;

    private TimeCount mTimeCount;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.realname_activity);

        back = findViewById(R.id.back);
        name = findViewById(R.id.name);
        code = findViewById(R.id.code);
        phone = findViewById(R.id.phone);
        idcard = findViewById(R.id.idcard);
        forget_get_captcha = findViewById(R.id.forget_get_captcha);
        commit = findViewById(R.id.commit);
        reset = findViewById(R.id.reset);

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
        setEditTextInut(name);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String result  = name.getText().toString();
                if(StringUtil.isNotNull(result)&& result.length()>10){
                    name.setText(result.substring(0,10));
                    name.setSelection(name.getText().length());
                }
            }
        });
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        mTimeCount = new TimeCount(60000, 1000);
        String title = getIntent().getStringExtra("title");
        if (StringUtil.isNotNull(title)) {
            setTitleName(title);
        }
        phone.setText(SharedPreferencesUtil.readString("userPhone", ""));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_get_captcha:

                String result4 = validatePhone();
                if (StringUtil.isNullOrEmpty(result4)) {
                    xclModel.getCaptcha(phone.getText().toString());

                } else {
                    ToastUtil.showToast(result4);
                }
                break;
            case R.id.commit:
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.realName(name.getText().toString(), code.getText().toString(), idcard.getText().toString());

                } else {
                    ToastUtil.showToast(result);
                }
                break;
            case R.id.reset:
                name.setText("");
                code.setText("");
                idcard.setText("");
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

    public void realNameSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            SharedPreferencesUtil.saveString("realname", name.getText().toString());
            ToastUtil.showToast("提交成功");
            Intent it = new Intent();
            setResult(RESULT_OK, it);
            SharedPreferencesUtil.saveString("isRiskAuth", "1");
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
        if (StringUtil.isNullOrEmpty(name.getText().toString())) {
            return "请输入证件姓名";
        }
        if (StringUtil.isNullOrEmpty(code.getText().toString())) {
            return "请输入验证码";
        }
        if (StringUtil.isNullOrEmpty(idcard.getText().toString())) {
            return "请输入身份证号码";
        }
        if (!(idcard.getText().toString().length() == 15 || idcard.getText().toString().length() == 18)) {
            return "请输入正确身份证号码";
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

        return "";
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 只能输入中文
     *
     * @param editText
     */
    public static void setEditTextInut(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[\\u4E00-\\u9FA5]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (!matcher.find()) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }
}
