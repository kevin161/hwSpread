package com.leisurely.spread.UI.activity.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.SoftHideKeyBoardUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterOrLoginActivity extends BaseActivity {
public static final String SUB_PAGE="sub_page";
public static final String INVEST_PHONE="invest_phone";

    private XclModel xclModel;


    private TextView confirm_modift;//确认修改
    private EditText phone;
    private EditText invite;
    private EditText get_captcha;
    private TextView forget_get_captcha;

    private LinearLayout register_li;
    private LinearLayout login_li;

//    private RelativeLayout register_re;
//    private RelativeLayout login_re;

//    private TextView login_line;
//    private TextView register_line;

    private TextView login_text;
    private TextView register_text;

    private EditText password2;

    //    private TextView phone_line;
//    private TextView name_line;
//    private TextView invite_line;
//    private TextView code_line;
//    private TextView password_line;
//    private TextView password2_line;

    private EditText password;

    private TimeCount mTimeCount;

    private EditText pwd;
    private EditText username;
    private TextView pwd_line;
    private TextView username_line;

    private TextView login;

    private TextView forget_pwd;
    private LinearLayout back;

    private ScrollView view;
    private CheckBox checkShowPwd;

    private int type;//0 login 1 register
    private String investPhone;
    private int status;// 0 获取验证码 1 注册

    private InputMethodManager imm;

    /**
     * 初始化布局
     */
    protected void initView() {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    setContentView(R.layout.activity_register_or_login);
        type=    getIntent().getIntExtra(SUB_PAGE,0);
        investPhone = getIntent().getStringExtra(INVEST_PHONE);
        phone = findViewById(R.id.phone);
        invite = findViewById(R.id.invite);
        confirm_modift = findViewById(R.id.confirm_modift);
        get_captcha = findViewById(R.id.get_captcha);
        forget_get_captcha = findViewById(R.id.forget_get_captcha);
        register_li = findViewById(R.id.register_li);
        login_li = findViewById(R.id.login_li);
//        register_re = findViewById(R.id.register_re);
//        login_re = findViewById(R.id.login_re);
//        login_line = findViewById(R.id.login_line);
//        register_line = findViewById(R.id.register_line);
        login_text = findViewById(R.id.login_text);
        register_text = findViewById(R.id.register_text);
        password2 = findViewById(R.id.password2);
//        phone_line = findViewById(R.id.phone_line);
//        name_line = findViewById(R.id.name_line);
//        invite_line = findViewById(R.id.invite_line);
//        code_line = findViewById(R.id.code_line);
//        password_line = findViewById(R.id.password_line);
//        password2_line = findViewById(R.id.password2_line);
        password = findViewById(R.id.password);
        pwd = findViewById(R.id.pwd);
        username = findViewById(R.id.username);
        pwd_line = findViewById(R.id.pwd_line);
        username_line = findViewById(R.id.username_line);
        login = findViewById(R.id.login);
        forget_pwd = findViewById(R.id.forget_pwd);
        checkShowPwd = findViewById(R.id.checkShowPwd);
        back = findViewById(R.id.back);
        view = findViewById(R.id.view);
    }

    @Override
    public void onResume() {
        super.onResume();
//        cleanEditView();
    }

    @Override
    protected void initListener() {
        confirm_modift.setOnClickListener(this);
        register_text.setOnClickListener(this);
        login_text.setOnClickListener(this);
        back.setOnClickListener(this);
//        check_phone.setOnClickListener(this);
        forget_get_captcha.setOnClickListener(this);
        login.setOnClickListener(this);
        forget_pwd.setOnClickListener(this);
//        phone.setOnFocusChangeListener(new android.view.View.
//                OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                    ViewGroup.LayoutParams layoutParams = phone_line.getLayoutParams();
//                    layoutParams.height = 5;
//                    phone_line.setLayoutParams(layoutParams);
//                    phone_line.setBackground(getResources().getDrawable(R.color.color_3BA370));
//                } else {
//                    // 此处为失去焦点时的处理内容
//                    ViewGroup.LayoutParams layoutParams = phone_line.getLayoutParams();
//                    layoutParams.height = 2;
//                    phone_line.setLayoutParams(layoutParams);
//                    phone_line.setBackground(getResources().getDrawable(R.color.color_969696));
//                }
//            }
//        });


//        invite.setOnFocusChangeListener(new android.view.View.
//                OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                    ViewGroup.LayoutParams layoutParams = invite_line.getLayoutParams();
//                    layoutParams.height = 5;
//                    invite_line.setLayoutParams(layoutParams);
//                    invite_line.setBackground(getResources().getDrawable(R.color.color_3BA370));
//                } else {
//                    // 此处为失去焦点时的处理内容
//                    ViewGroup.LayoutParams layoutParams = invite_line.getLayoutParams();
//                    layoutParams.height = 2;
//                    invite_line.setLayoutParams(layoutParams);
//                    invite_line.setBackground(getResources().getDrawable(R.color.color_969696));
//                }
//            }
//        });
//
//        get_captcha.setOnFocusChangeListener(new android.view.View.
//                OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                    ViewGroup.LayoutParams layoutParams = code_line.getLayoutParams();
//                    layoutParams.height = 5;
//                    code_line.setLayoutParams(layoutParams);
//                    code_line.setBackground(getResources().getDrawable(R.color.color_3BA370));
//                } else {
//                    // 此处为失去焦点时的处理内容
//                    ViewGroup.LayoutParams layoutParams = code_line.getLayoutParams();
//                    layoutParams.height = 2;
//                    code_line.setLayoutParams(layoutParams);
//                    code_line.setBackground(getResources().getDrawable(R.color.color_969696));
//                }
//            }
//        });
//
//        password.setOnFocusChangeListener(new android.view.View.
//                OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
////                    password_line.setHeight(5);
//                    ViewGroup.LayoutParams layoutParams = password_line.getLayoutParams();
//                    layoutParams.height = 5;
//                    password_line.setLayoutParams(layoutParams);
//                    password_line.setBackground(getResources().getDrawable(R.color.color_3BA370));
//                } else {
//                    // 此处为失去焦点时的处理内容
//                    ViewGroup.LayoutParams layoutParams = password_line.getLayoutParams();
//                    layoutParams.height = 2;
//                    password_line.setLayoutParams(layoutParams);
//                    password_line.setBackground(getResources().getDrawable(R.color.color_969696));
//                }
//            }
//        });
//
//        password2.setOnFocusChangeListener(new android.view.View.
//                OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                    ViewGroup.LayoutParams layoutParams = password2_line.getLayoutParams();
//                    layoutParams.height = 5;
//                    password2_line.setLayoutParams(layoutParams);
//                    password2_line.setBackground(getResources().getDrawable(R.color.color_3BA370));
//                } else {
//                    // 此处为失去焦点时的处理内容
//                    ViewGroup.LayoutParams layoutParams = password2_line.getLayoutParams();
//                    layoutParams.height = 2;
//                    password2_line.setLayoutParams(layoutParams);
//                    password2_line.setBackground(getResources().getDrawable(R.color.color_969696));
////                    view.scrollTo(0,password2.getScrollY());
//                }
//            }
//        });

//        pwd.setOnFocusChangeListener(new android.view.View.
//                OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
        // 此处为得到焦点时的处理内容
//                    ViewGroup.LayoutParams layoutParams = pwd_line.getLayoutParams();
//                    layoutParams.height = 5;
//                    pwd_line.setLayoutParams(layoutParams);
//                    pwd_line.setBackground(getResources().getDrawable(R.color.color_3BA370));
//                } else {
        // 此处为失去焦点时的处理内容
//                    ViewGroup.LayoutParams layoutParams = pwd_line.getLayoutParams();
//                    layoutParams.height = 2;
//                    pwd_line.setLayoutParams(layoutParams);
//                    pwd_line.setBackground(getResources().getDrawable(R.color.color_969696));
//                }
//            }
//        });

        username.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
//                    ViewGroup.LayoutParams layoutParams = username_line.getLayoutParams();
//                    layoutParams.height = 5;
//                    username_line.setLayoutParams(layoutParams);
//                    username_line.setBackground(getResources().getDrawable(R.color.color_3BA370));
                } else {
                    // 此处为失去焦点时的处理内容
//                    ViewGroup.LayoutParams layoutParams = username_line.getLayoutParams();
//                    layoutParams.height = 2;
//                    username_line.setLayoutParams(layoutParams);
//                    username_line.setBackground(getResources().getDrawable(R.color.color_969696));
                }
            }
        });


        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = username.getText().toString();
                if (StringUtil.isNotNull(s)) {
                    if (s.length() > 11) {
                        username.setText(s.substring(0, s.length() - 1));
                        username.setSelection(username.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        setEditTextInhibitInputSpace(phone);
//        setEditTextInhibitInputSpeChat(name);
//        setEditTextInhibitInputSpeChat(username);
//        setEditTextInhibitInputSpace(pwd);
//        setEditTextInhibitInputSpace(password);
//        setEditTextInhibitInputSpace(password2);
//        setEditTextInhibitInputSpace(invite);
//        setEditTextInhibitInputSpace(get_captcha);
        setEditTextInhibitInputSpace(pwd);
        setEditTextInhibitInputSpace(password);
        setEditTextInhibitInputSpace(password2);
//        addEditTextPassword();
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


//    private int computeUsableHeight() {
//        Rect r = new Rect();
//        view.getWindowVisibleDisplayFrame(r); // 全屏模式下：直接返回r.bottom，r.top其实是状态栏的高度
//        return (r.bottom - r.top);
//    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        mTimeCount = new TimeCount(120000, 1000);
        username.setFocusable(true);
        username.requestFocus();

        //注册
        if (type==1){
            login_li.setVisibility(View.GONE);
            register_li.setVisibility(View.VISIBLE);
            login_text.setText("注册");
            register_text.setText("登录");
            login_text.setTextColor(Color.BLACK);
            register_text.setTextColor(getResources().getColor(R.color.color_CF1C20));
            cleanEditView();
            if (!TextUtils.isEmpty(investPhone)){
                invite.setText(investPhone);
            }
        }



//       if(RomUtil.isEmui()){

//       }
//控制底部虚拟键盘
//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
////                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_modift://确认修改
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
//                    xclModel.editPassword(old_password.getText().toString(), new_password.getText().toString());
                    xclModel.checkPhone(phone.getText().toString());
                } else {
                    ToastUtil.showToast(result);
                }
                status = 1;
                break;
//            case R.id.check_invite:
//                String result1 = validateInvite();
//                if (StringUtil.isNullOrEmpty(result1)) {
////                    xclModel.editPassword(old_password.getText().toString(), new_password.getText().toString());
//                    xclModel.checkInvite(invite.getText().toString());
//                } else {
//                    ToastUtil.showToast(result1);
//                }
//                break;
//            case R.id.check_name:
//                String result2 = validateName();
//                if (StringUtil.isNullOrEmpty(result2)) {
////                    xclModel.editPassword(old_password.getText().toString(), new_password.getText().toString());
//                    xclModel.checkName(name.getText().toString());
//                } else {
//                    ToastUtil.showToast(result2);
//                }
//                break;
//            case R.id.check_phone:
//                String result3 = validatePhone();
//                if (StringUtil.isNullOrEmpty(result3)) {
//                    xclModel.checkPhone(phone.getText().toString());
////                    xclModel.editPassword(old_password.getText().toString(), new_password.getText().toString());
//
//                } else {
//                    ToastUtil.showToast(result3);
//                }
//                break;
            case R.id.forget_get_captcha:
                String result4 = validatePhone();
                if (StringUtil.isNullOrEmpty(result4)) {
                    xclModel.checkPhone(phone.getText().toString());
//                    xclModel.editPassword(old_password.getText().toString(), new_password.getText().toString());

                } else {
                    ToastUtil.showToast(result4);
                }
                status = 0;
                break;
            case R.id.register_text:
                if (type != 1) {
//                    register_line.setVisibility(View.VISIBLE);
//                    login_line.setVisibility(View.INVISIBLE);
                    login_li.setVisibility(View.GONE);
                    register_li.setVisibility(View.VISIBLE);
                    login_text.setText("注册");
                    register_text.setText("登录");
                    login_text.setTextColor(Color.BLACK);
                    register_text.setTextColor(getResources().getColor(R.color.color_CF1C20));
                    cleanEditView();
                    type = 1;
                } else {

//                    register_line.setVisibility(View.INVISIBLE);
//                    login_line.setVisibility(View.VISIBLE);
                    register_li.setVisibility(View.GONE);
                    login_text.setText("登录");
                    register_text.setText("注册");
                    login_text.setTextColor(Color.BLACK);
                    register_text.setTextColor(getResources().getColor(R.color.color_CF1C20));
                    username.setFocusable(true);
                    username.requestFocus();
                    cleanEditView();
                    login_li.setVisibility(View.VISIBLE);

                    type = 0;
                }
                break;
            case R.id.login_text:
//                if (type != 0) {
////                    register_line.setVisibility(View.INVISIBLE);
////                    login_line.setVisibility(View.VISIBLE);
//                    login_text.setText("登录");
//                    register_text.setText("注册");
//                    login_text.setTextColor(Color.BLACK);
//                    register_text.setTextColor(getResources().getColor(R.color.color_CF1C20));
//                    register_li.setVisibility(View.GONE);
//                    login_li.setVisibility(View.VISIBLE);
//                    username.setFocusable(true);
//                    username.requestFocus();
//                    cleanEditView();
//                }
//                type = 0;
                break;
            case R.id.login:
                String result2 = validateLogin();
                if (StringUtil.isNullOrEmpty(result2)) {
                    xclModel.login(username.getText().toString(), pwd.getText().toString());
//                    xclModel.editPassword(old_password.getText().toString(), new_password.getText().toString());

                } else {
                    ToastUtil.showToast(result2);
                }
                break;
            case R.id.forget_pwd:
                startActivity(new Intent(this, ForgetPwdActivity.class)
                        .putExtra("type", 0).putExtra("title", "忘记密码"));
                break;
            default:
                break;
        }
    }

    public void cleanEditView() {
        username.setText("");
        pwd.setText("");
        phone.setText("");
        invite.setText("");
        get_captcha.setText("");
        password.setText("");
        password2.setText("");
    }

    public void loginSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            json = json.getJSONObject("jsonData");
            SharedPreferencesUtil.saveString("token", json.getString("token"));
            json = json.getJSONObject("resultInfo");
            SharedPreferencesUtil.saveString("userPhone", json.getString("userPhone"));
            SharedPreferencesUtil.saveString("headimgUrl", json.getString("headimgUrl"));
            SharedPreferencesUtil.saveString("idCard", json.getString("idCard"));
            SharedPreferencesUtil.saveString("uid", json.getString("uid"));
            SharedPreferencesUtil.saveString("name", username.getText().toString());
            SharedPreferencesUtil.saveString("payPass", json.getString("payPass"));
            SharedPreferencesUtil.saveBoolean(SysConstants.ISLOGIN, true);

            xclModel.checkUserAccount();
//            startActivity(new Intent(this, HomeActivity.class));
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }


    public void checkUserAccountSuccess(JSONObject response) {
        if ("1".equals(response.getJSONObject("status").getString("success"))) {
            if ("0".equals(response.getString("result"))) {
                SharedPreferencesUtil.saveString(SysConstants.IS_RISK_AUTH, "0");
            } else if ("1".equals(response.getString("result"))) {
                SharedPreferencesUtil.saveString(SysConstants.IS_RISK_AUTH, "1");
            }
            Intent it = new Intent();
            setResult(RESULT_OK, it);
            finish();
        } else {
            Intent it = new Intent();
            setResult(RESULT_OK, it);
            finish();
        }

    }

    public void getCaptchaSuccess(JSONObject json) {
        ToastUtil.showToast("验证码发送成功！");
        mTimeCount.start();
        forget_get_captcha.setClickable(false);
    }

    /**
     * 校验用户电话号码是否已注册
     *
     * @param json
     */
    public void checkPhoneSuccess(JSONObject json) {

        if (json.getBoolean("success")) {
            String data = json.getString("data");
            if (data.equals("0")) {
                ToastUtil.showToast("手机号已注册");
            } else {
                if (status == 1) {
//                    xclModel.checkName(name.getText().toString());
                    if (StringUtil.isNotNull(invite.getText().toString())) {
                        xclModel.checkInvite(invite.getText().toString());
                    } else {
//                        xclModel.registerTest(phone.getText().toString(), name.getText().toString(), get_captcha.getText().toString(),
//                                invite.getText().toString(), password.getText().toString());
                    }
                } else if (status == 0) {
                    xclModel.getCaptcha(phone.getText().toString());
                }
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }

    }

    public void checkNameSuccess(JSONObject json) {

        if (json.getBoolean("success")) {
            String data = json.getString("data");
            if (data.equals("0")) {
                ToastUtil.showToast("昵称已被使用");
            } else {
                if (StringUtil.isNotNull(invite.getText().toString())) {
                    xclModel.checkInvite(invite.getText().toString());
                } else {
                    xclModel.registerTest(phone.getText().toString(), "", get_captcha.getText().toString(),
                            invite.getText().toString(), password.getText().toString());
                }

            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }

    }

    public void checkInviteSuccess(JSONObject json) {

        if (json.getBoolean("success")) {
            String data = json.getString("data");
            if (data.equals("0")) {
                ToastUtil.showToast("推荐人手机号错误");
            } else {
                xclModel.registerTest(phone.getText().toString(), "", get_captcha.getText().toString(),
                        invite.getText().toString(), password.getText().toString());
            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }

    }

    public void modifySuccess(JSONObject json) {
//        ToastUtil.showToast(JSONObject.toJSONString(json));
        if (json.getBoolean("success")) {
            if (type != 0) {
//                login_line.setVisibility(View.VISIBLE);
//                register_line.setVisibility(View.INVISIBLE);
                login_text.setText("登录");
                register_text.setText("注册");
                login_text.setTextColor(Color.BLACK);
                register_li.setVisibility(View.GONE);
                login_li.setVisibility(View.VISIBLE);
            }
            type = 0;
            cleanEditView();
            ToastUtil.showToast("注册成功");
            mTimeCount.onFinish();
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
//        finish();
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            forget_get_captcha.setText("发送验证码");
//            get_captcha.setBackground(getResources().getDrawable(R.drawable.fdcd00_20dp_right));
            forget_get_captcha.setTextColor(Color.WHITE);
            forget_get_captcha.setClickable(true);
            forget_get_captcha.setBackgroundResource(R.drawable.cb1b1d_2dp);
        }

        @Override
        public void onTick(long arg0) {
            long min = arg0 / 1000;
//            String str = "<font>" + min + "</font><small><font> s</font></small>";
            forget_get_captcha.setText(min + "s");
//            get_captcha.setBackground(getResources().getDrawable(R.drawable.cccccc_20dp_right));
            forget_get_captcha.setTextColor(getResources().getColor(R.color.color_969696));
            forget_get_captcha.setBackgroundResource(R.drawable.e2e2e2_2dp);
        }
    }

    @NonNull
    private String validate() {
        if (StringUtil.isNullOrEmpty(phone.getText().toString())) {
            return "请输入手机号";
        }
        if (TextUtil.isMobileNumber(phone.getText().toString())) {
            return "请输入正确的手机号";
        }
        if (TextUtil.isNumeric(password.getText().toString())) {
            return "密码不能为纯数字";
        }
        if (TextUtil.checkPwd(password)) {
            return "请输入6-20位数字、字母";
        }
        if (StringUtil.isNullOrEmpty(get_captcha.getText().toString())) {
            return "请输入验证码";
        }
        if (StringUtil.isNullOrEmpty(password.getText().toString())) {
            return "请输入密码";
        }
        if (StringUtil.isNullOrEmpty(password2.getText().toString())) {
            return "请输入确认密码";
        }
        if (!password.getText().toString().equals(password2.getText().toString())) {
            return "两次密码输入不一致";
        }
        String investStr = invite.getText().toString();
        if (TextUtils.isEmpty(investStr) || investStr.length() != 11) {
            return "请填写正确的推荐人手机号";
        }
        return "";
    }

    @NonNull
    private String validatePhone() {
        if (StringUtil.isNullOrEmpty(phone.getText().toString())) {
            return "请输入手机号";
        }
        if (TextUtil.isMobileNumber(phone.getText().toString())) {
            return "请输入正确的手机号";
        }

        if (phone.getText().toString().length() != 11) {
            return "请输入正确手机号";
        }

        return "";
    }

    @NonNull
    private String validateLogin() {

        if (StringUtil.isNullOrEmpty(username.getText().toString())) {
            return "请输入手机号";
        }
        if (TextUtil.isMobileNumber(username.getText().toString())) {
            return "请输入正确的手机号";
        }
        if (StringUtil.isNullOrEmpty(pwd.getText().toString())) {
            return "请输入密码";
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


    /**
     * 禁止EditText表情
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[a-zA-Z0-9!@#%*()_+=`~\\u4E00-\\u9FA5]+";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (!matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
