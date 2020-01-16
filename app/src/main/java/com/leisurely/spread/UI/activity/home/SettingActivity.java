package com.leisurely.spread.UI.activity.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Withdrawal;
import com.leisurely.spread.util.DataCleanManager;
import com.leisurely.spread.util.DateUtil;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.SystemInfoUtils;


public class SettingActivity extends BaseActivity {

    private XclModel xclModel;

    private LinearLayout back;

    private RelativeLayout pwd_re;
    private RelativeLayout pay_pwd_re;
    private RelativeLayout phone_re;
    private TextView pay_pwd;
    private RelativeLayout layoutVersion;
    private RelativeLayout layoutClear;
    private TextView txtVersion, txtCache, txtExit;


    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.setting_activity);

        back = findViewById(R.id.back);
        pwd_re = findViewById(R.id.pwd_re);
        pay_pwd_re = findViewById(R.id.pay_pwd_re);
        pay_pwd = findViewById(R.id.pay_pwd);
        phone_re = findViewById(R.id.phone_re);
        layoutVersion = findViewById(R.id.layoutVersion);
        layoutClear = findViewById(R.id.layoutClear);
        txtVersion = findViewById(R.id.txtVersion);
        txtCache = findViewById(R.id.txtCache);
        txtExit = findViewById(R.id.txtExit);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        pwd_re.setOnClickListener(this);
        pay_pwd_re.setOnClickListener(this);
        layoutVersion.setOnClickListener(this);
        layoutClear.setOnClickListener(this);
        txtExit.setOnClickListener(this);
        phone_re.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        String bb = DataCleanManager.getTotalCacheSize(getApplicationContext());
        txtCache.setText(bb);
        txtVersion.setText("V"+SystemInfoUtils.getVersionName(getApplicationContext(),"com.leisurely.spread"));
        xclModel = new XclModel(this);
        String pay_pwd_text = "设置资金密码";
        if (StringUtil.isNotNull(SharedPreferencesUtil.readString("payPass", ""))) {
            pay_pwd_text = "修改资金密码";
        }
        pay_pwd.setText(pay_pwd_text);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutVersion:
                // TODO: 2020/1/13  todo guoyazhou
                break;
            case R.id.layoutClear:
//                    showClearDialog();
                DataCleanManager.clearAllCache(getApplicationContext());

                String bb = DataCleanManager.getTotalCacheSize(getApplicationContext());
                txtCache.setText(bb);
                break;
            case R.id.txtExit:
                SharedPreferencesUtil.clear();
                setResult(Activity.RESULT_OK,null);
                finish();
                break;
            case R.id.pwd_re:
                startActivity(new Intent(this, ForgetPwdActivity.class)
                        .putExtra("type", 1).putExtra("title", "修改登录密码"));
                break;
            case R.id.pay_pwd_re:
                startActivityForResult(new Intent(this, ForgetPayPwdActivity.class)
                        .putExtra("type", 1).putExtra("title", pay_pwd.getText().toString()), 1111);
                break;
//            case R.id.nickname_re:
//                startActivity(new Intent(this,NickNameActivity.class));
//                break;
            case R.id.phone_re:
                startActivity(new Intent(this, UpdatePhoneOneActivity.class));
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    private void showClearDialog() {
            final View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.withdrawal_dialog, null);
            final Dialog backDialog = new Dialog(getBaseContext(), R.style.officeDialog);
            backDialog.setCancelable(true);
            backDialog.setContentView(view);
            TextView txtTime = view.findViewById(R.id.txtTime);
            TextView txtState = view.findViewById(R.id.txtState);
            TextView txtStateDetail = view.findViewById(R.id.txtStateDetail);
            TextView txtKnow = view.findViewById(R.id.txtKnow);


            txtKnow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backDialog.cancel();
                }
            });

            backDialog.show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1111) {
                initData();
            }
        }
    }


}
