package com.leisurely.spread.UI.activity.setting;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.MainActivity;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.enums.YZMEnum;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;


public class LoginActivity extends BaseActivity {

    private long mPressedTime = 0;

    private LinearLayout login_li;
    private EditText login_phone;
    private EditText login_pwd;
    private TextView to_forget;
    private TextView to_register;
    private TextView login;
    private TextView register;
    private EditText register_recommend;
    private LinearLayout country_code_li;
    private TextView country_code;
    private EditText register_phone;
    private EditText register_captcha;
    private TextView get_captcha;
    private EditText register_pwd;
    private EditText register_pwd2;
    private EditText register_paypwd;
    private EditText register_paypwd2;
    private TextView to_login;
    private LinearLayout register_li;
    private LinearLayout login_lay;
    private LinearLayout to_forget_li;
    private EditText forget_phone;
    private EditText forget_captcha;
    private TextView forget_get_captcha;
    private EditText forget_pwd;
    private EditText forget_pwd2;
    private TextView commit;
    private TextView to_login_lay;


    private TimeCount mTimeCount;
    private TimeCount2 mTimeCount2;
    private XclModel xclModel;
    private int type;//0发送注册验证码 1发送忘记密码验证码


    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.activity_login);
        login_li = findViewById(R.id.login_li);
        login_phone = findViewById(R.id.login_phone);
        login_pwd = findViewById(R.id.login_pwd);
        to_forget = findViewById(R.id.to_forget);
        to_register = findViewById(R.id.to_register);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        register_recommend = findViewById(R.id.register_recommend);
        country_code_li = findViewById(R.id.country_code_li);
        country_code = findViewById(R.id.country_code);
        register_phone = findViewById(R.id.register_phone);
        register_captcha = findViewById(R.id.register_captcha);
        get_captcha = findViewById(R.id.get_captcha);
        register_pwd = findViewById(R.id.register_pwd);
        register_pwd2 = findViewById(R.id.register_pwd2);
        register_paypwd = findViewById(R.id.register_paypwd);
        register_paypwd2 = findViewById(R.id.register_paypwd2);
        to_login = findViewById(R.id.to_login);
        register_li = findViewById(R.id.register_li);
        login_lay = findViewById(R.id.login_lay);
        to_forget_li = findViewById(R.id.to_forget_li);
        forget_phone = findViewById(R.id.forget_phone);
        forget_captcha = findViewById(R.id.forget_captcha);
        forget_get_captcha = findViewById(R.id.forget_get_captcha);
        forget_pwd = findViewById(R.id.forget_pwd);
        forget_pwd2 = findViewById(R.id.forget_pwd2);
        commit = findViewById(R.id.commit);
        to_login_lay = findViewById(R.id.to_login_lay);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        to_forget.setOnClickListener(this);
        to_login.setOnClickListener(this);
        to_register.setOnClickListener(this);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
        get_captcha.setOnClickListener(this);
        country_code_li.setOnClickListener(this);
        commit.setOnClickListener(this);
        forget_get_captcha.setOnClickListener(this);
        to_login_lay.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        mTimeCount = new TimeCount(60000, 1000);
        mTimeCount2 = new TimeCount2(60000, 1000);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            get_captcha.setText("获取验证码");
//            get_captcha.setBackground(getResources().getDrawable(R.drawable.fdcd00_20dp_right));
            get_captcha.setTextColor(getResources().getColor(R.color.color_1490D8));
            get_captcha.setClickable(true);
        }

        @Override
        public void onTick(long arg0) {
            long min = arg0 / 1000;
//            String str = "<font>" + min + "</font><small><font> s</font></small>";
            get_captcha.setText(min + "s");
//            get_captcha.setBackground(getResources().getDrawable(R.drawable.cccccc_20dp_right));
            get_captcha.setTextColor(getResources().getColor(R.color.color_969696));
        }
    }


    class TimeCount2 extends CountDownTimer {
        public TimeCount2(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            forget_get_captcha.setText("获取验证码");
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
            case R.id.to_login:
                login_li.setVisibility(View.VISIBLE);
                register_li.setVisibility(View.GONE);
                break;
            case R.id.to_register:
                login_li.setVisibility(View.GONE);
                register_li.setVisibility(View.VISIBLE);
                break;
            case R.id.to_forget:
                login_lay.setVisibility(View.GONE);
                to_forget_li.setVisibility(View.VISIBLE);
                break;
            case R.id.to_login_lay:
                login_lay.setVisibility(View.VISIBLE);
                to_forget_li.setVisibility(View.GONE);
                break;
            case R.id.register:
                String result = validateRegister();
                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.register(register_phone.getText().toString(), country_code.getText().toString().substring(1),
                            register_pwd.getText().toString(), register_paypwd.getText().toString(),
                            register_captcha.getText().toString(), register_recommend.getText().toString());
                } else {
                    ToastUtil.showToast(result);
                }
                break;
            case R.id.login:
                String result2 = validateLogin();
                if (StringUtil.isNullOrEmpty(result2)) {
                    xclModel.login(login_phone.getText().toString(), login_pwd.getText().toString());
                } else {
                    ToastUtil.showToast(result2);
                }
                break;
            case R.id.get_captcha:
                String result1 = validatePhone();
                if (StringUtil.isNullOrEmpty(result1)) {
                    xclModel.sendVerficationCode(register_phone.getText().toString(),
                            YZMEnum.REGISTER.getId(), country_code.getText().toString().substring(1));
                    type = 0;
                } else {
                    ToastUtil.showToast(result1);
                }
                break;
            case R.id.forget_get_captcha:
                String result3 = validatePhone2();
                if (StringUtil.isNullOrEmpty(result3)) {
                    xclModel.sendVerficationCode(forget_phone.getText().toString(),
                            YZMEnum.RESETPWD.getId(), "");
                    type = 1;
                } else {
                    ToastUtil.showToast(result3);
                }
                break;
            case R.id.country_code_li:
                startActivityForResult(new Intent(this, CountrySelectActivity.class), 1111);
                break;
            case R.id.commit:
                String result4 = validate();
                if (StringUtil.isNullOrEmpty(result4)) {
                    xclModel.resetpwd(forget_phone.getText().toString(), forget_captcha.getText().toString(),
                            forget_pwd.getText().toString());
                } else {
                    ToastUtil.showToast(result4);
                }
                break;
            default:
                break;
        }
    }

    public void registerSuccess(JSONObject json) {
        SharedPreferencesUtil.saveBoolean(SysConstants.ISLOGIN, true);
        SharedPreferencesUtil.saveString(SysConstants.USERID, json.getString("user_id"));
        SharedPreferencesUtil.saveString(SysConstants.TELLPHONE, register_phone.getText().toString());
        SharedPreferencesUtil.saveString(SysConstants.TOKEN, json.getString("token"));
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    public void loginSuccess(JSONObject json) {

        SharedPreferencesUtil.saveBoolean(SysConstants.ISLOGIN, true);
        SharedPreferencesUtil.saveString(SysConstants.TELLPHONE, json.getString("mobile"));
        SharedPreferencesUtil.saveString(SysConstants.USERID, json.getString("user_id"));
        SharedPreferencesUtil.saveString("username", json.getString("username"));
        SharedPreferencesUtil.saveString(SysConstants.AVATAR, json.getString("avatar"));
        SharedPreferencesUtil.saveString(SysConstants.TOKEN, json.getString("token"));
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    private String validateLogin() {
        if (StringUtil.isNullOrEmpty(login_phone.getText().toString())) {
            return "请输入手机号";
        }
        if (StringUtil.isNullOrEmpty(login_pwd.getText().toString())) {
            return "请输入密码";
        }
        return "";
    }

    private String validateRegister() {
        if (StringUtil.isNullOrEmpty(register_phone.getText().toString())) {
            return "请输入手机号";
        }
        if (StringUtil.isNullOrEmpty(register_captcha.getText().toString())) {
            return "请输入验证码";
        }
        if (StringUtil.isNullOrEmpty(register_pwd.getText().toString())) {
            return "请输入密码";
        }
        if (TextUtil.checkPwd(register_pwd)) {
            return "请输入6-20位数字、字母";
        }
        if (!register_pwd.getText().toString().equals(register_pwd2.getText().toString())) {
            return "两次密码输入不一致";
        }
        if (StringUtil.isNullOrEmpty(register_paypwd.getText().toString())) {
            return "请输入支付密码";
        }
        if (!register_paypwd.getText().toString().equals(register_paypwd2.getText().toString())) {
            return "两次支付密码输入不一致";
        }
        return "";
    }

    private String validate() {
        if (StringUtil.isNullOrEmpty(forget_phone.getText().toString())) {
            return "请输入手机号";
        }
        if (StringUtil.isNullOrEmpty(forget_pwd.getText().toString())) {
            return "请输入密码";
        }
        if (!forget_pwd.getText().toString().equals(forget_pwd2.getText().toString())) {
            return "两次密码输入不一致";
        }
        if (StringUtil.isNullOrEmpty(forget_captcha.getText().toString())) {
            return "请输入验证码";
        }
        return "";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1111) {
                country_code.setText(data.getStringExtra("code"));
            }
        }
    }

    public void registerSendSucess() {
        if (type == 0) {
            mTimeCount.start();
            get_captcha.setClickable(false);
        } else if (type == 1) {
            mTimeCount2.start();
            forget_get_captcha.setClickable(false);
        }
        ToastUtil.showToast("发送验证码,请注意查收");
    }

    private String validatePhone() {
        if (StringUtil.isNullOrEmpty(register_phone.getText().toString())) {
            return "请输入手机号";
        }
        return "";
    }

    private String validatePhone2() {
        if (StringUtil.isNullOrEmpty(forget_phone.getText().toString())) {
            return "请输入手机号";
        }
        return "";
    }

    public void resetPwdSuccess() {
        ToastUtil.showToast("密码修改成功");
        login_lay.setVisibility(View.VISIBLE);
        to_forget_li.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mPressedTime) > 2000) {
                showToast(getResources().getString(R.string.toast_system_exit));
                mPressedTime = System.currentTimeMillis();
            } else {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                System.exit(0);
//                    android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
