package com.leisurely.spread.UI.activity.home;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.enums.YZMEnum;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;


public class ModifyPayPasswordActivity extends BaseActivity {

    private XclModel xclModel;

    private TextView phone;
    private EditText code;
    private EditText new_password;//新密码
    private EditText repeat_new_password;//确认新密码
    private TextView confirm_modift;//确认修改
    private ImageView back;
    private TextView get_captcha;


    private TimeCount mTimeCount;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.activity_modify_pay_password);
        phone = findViewById(R.id.phone);
        code = findViewById(R.id.code);
        new_password = findViewById(R.id.new_password);
        repeat_new_password = findViewById(R.id.repeat_new_password);
        confirm_modift = findViewById(R.id.confirm_modift);
        back = findViewById(R.id.back);
        get_captcha = findViewById(R.id.get_captcha);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        confirm_modift.setOnClickListener(this);
        back.setOnClickListener(this);
        get_captcha.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        phone.setText(SharedPreferencesUtil.readString(SysConstants.TELLPHONE, ""));
        mTimeCount = new TimeCount(60000, 1000);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_modift://确认修改
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.editPayPassword(phone.getText().toString(), new_password.getText().toString()
                            , code.getText().toString());
                } else {
                    ToastUtil.showToast(result);
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.get_captcha:
                xclModel.sendVerficationCode(phone.getText().toString(),
                        YZMEnum.RESETPAYPWD.getId(),"");
                break;
            default:
                break;
        }
    }

    public void modifySuccess() {
        ToastUtil.showToast(getResources().getString(R.string.modify_success));
        finish();
    }

    public void sendVerificationCodeSuccess() {
        mTimeCount.start();
        get_captcha.setClickable(false);
        ToastUtil.showToast("发送验证码,请注意查收");
    }

    @NonNull
    private String validate() {
        if (StringUtil.isNullOrEmpty(code.getText().toString())) {
            return "请输入验证码";
        }
        if (StringUtil.isNullOrEmpty(new_password.getText().toString())) {
            return "请输入新密码";
        }
        if (new_password.getText().toString().length() !=6) {
            return "支付密码只能为6位数字";
        }
        if (StringUtil.isNullOrEmpty(repeat_new_password.getText().toString())) {
            return "请输入重复新支付密码";
        }
        if (!new_password.getText().toString().equals(repeat_new_password.getText().toString())) {
            return "新支付密码两次输入不一致,请检查";
        }
        return "";
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
