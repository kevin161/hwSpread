package com.leisurely.spread.UI.activity.money;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.PersonDetailActivity;
import com.leisurely.spread.UI.activity.setting.PhotoViewActivity;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.User;
import com.leisurely.spread.model.enums.YZMEnum;
import com.leisurely.spread.util.FileUtil;
import com.leisurely.spread.util.ImageOptions;
import com.leisurely.spread.util.ImageSelectUtil;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/11.
 */

public class TibiActivity extends BaseActivity {

    private TextView balance;
    private EditText address;
    private EditText number;
    private EditText code;
    private TextView get_captcha;
    private ImageView add_image;
    private TextView confirm_modift;
    private TextView textView_right_title_bar;
    private EditText paypwd;
    private ImageView delete_img;
    private TextView currency;
//    private TextView procedures;
//    private TextView procedures_hint;
//    private TextView true_num;
    private XclModel xclModel;
    private TimeCount mTimeCount;
    private ImageSelectUtil mImageSelectUtil;//图片选择器
    private String imageUrl;
    private TextView procedures;
//    private TextView fund;
//    private TextView fund_hint;

    public ImageSelectUtil getmImageSelectUtil() {
        return mImageSelectUtil;
    }

    private int proceduresDouble;
    private int caps;
    private double pd;
    private double fund_ratio;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tibi);
        setTitleName("提币");
        balance = findViewById(R.id.balance);
        address = findViewById(R.id.address);
        number = findViewById(R.id.number);
        code = findViewById(R.id.code);
        get_captcha = findViewById(R.id.get_captcha);
        add_image = findViewById(R.id.add_image);
        confirm_modift = findViewById(R.id.confirm_modift);
        delete_img = findViewById(R.id.delete_img);
        paypwd = findViewById(R.id.paypwd);
        currency = findViewById(R.id.currency);
        procedures = findViewById(R.id.procedures);
//        procedures = findViewById(R.id.procedures);
//        procedures_hint = findViewById(R.id.procedures_hint);
//        true_num = findViewById(R.id.true_num);
//        fund = findViewById(R.id.fund);
//        fund_hint = findViewById(R.id.fund_hint);
        textView_right_title_bar = findViewById(R.id.textView_right_title_bar);
        textView_right_title_bar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        super.initData();
        mTimeCount = new TimeCount(60000, 1000);
        textView_right_title_bar.setText("提币记录");
        xclModel = new XclModel(this);
        xclModel.rechargeindex("2");
        mImageSelectUtil = new ImageSelectUtil(this);
    }

    @Override
    protected void initListener() {
        get_captcha.setOnClickListener(this);
        confirm_modift.setOnClickListener(this);
        textView_right_title_bar.setOnClickListener(this);
        add_image.setOnClickListener(this);
        delete_img.setOnClickListener(this);

//        number.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String cashString = number.getText().toString();
//                if (StringUtil.isNotNull(cashString)) {
//                    try {
//                        if (cashString.length() > 1 && cashString.indexOf("0") == 0 && (cashString.indexOf(".") > 1
//                                || cashString.indexOf(".") == -1)) {
//                            number.setText(cashString.substring(1));
//                            cashString = number.getText().toString();
//                        }
//
//                        if (cashString.indexOf(".") > 0) {
//                            if (cashString.indexOf(".") < (cashString.length() - 3)) {
//                                number.setText(cashString.substring(0, cashString.length() - 1));
//                            }
//                        }
//                        double cash = Double.valueOf(cashString);
////                        if (its.getMaximum() < cash * its.getPrice()) {
////                            its_num.setText(TextUtil.get4Double(its.getMaximum() / its.getPrice() - 0.00005));
////                        }
//                    } catch (Exception e) {
//                        if (cashString.length() > 1) {
//                            number.setText(cashString.substring(0, cashString.length() - 1));
//                        } else {
////                        moneyout_et.setText("");
//                            number.setText("0.");
//                        }
//                    }
//                    number.setSelection(number.getText().toString().length());
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                try {
//                    Double d = Double.parseDouble(number.getText().toString());
//                    d = d - (d * fund_ratio > caps ? caps : d * fund_ratio) - pd;
//                    if (d >= 0) {
//                        true_num.setText(TextUtil.get2Double(d));
//                    } else {
//                        true_num.setText("0");
//                    }
//
//                } catch (Exception e) {
//                    true_num.setText("");
//                }
//            }
//        });
    }

    public void commitSuccess() {
        ToastUtil.showToast("提交成功");
        finish();
    }

    public void sendVerificationCodeSuccess() {
        mTimeCount.start();
        get_captcha.setClickable(false);
        ToastUtil.showToast("发送验证码,请注意查收");
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

    public void rechargeindexSuccess(JSONObject json) {
        String balance = json.getString("balance");
//        addressStr = json.getString("address");
        this.balance.setText(balance);
        currency.setText(json.getString("currency"));
//        fund_ratio = Double.parseDouble(json.getString("fund_ratio"));
//        pd = Double.parseDouble(json.getString("procedures"));
        proceduresDouble = Integer.parseInt(json.getString("procedures"));
        procedures.setText("手续费:" + proceduresDouble + "币");
//        caps = Integer.parseInt(json.getString("fund_capping"));
//        procedures.setText("- " + json.getString("procedures") + " " + json.getString("currency"));
//        procedures_hint.setText("(最低" + least + "起提)");
//        fund_hint.setText("(爱心基金封顶" + caps + ")");
//        fund.setText("- " + (fund_ratio * 100) + "%");
//        address.setText(addressStr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_captcha:
                xclModel.sendVerficationCode(SharedPreferencesUtil.readString(SysConstants.TELLPHONE, ""),
                        YZMEnum.WITHDRAWAL.getId(), "");
                break;
            case R.id.add_image:
                if (StringUtil.isNullOrEmpty(imageUrl)) {
                    mImageSelectUtil.startSelect(add_image, false, 0);
                } else {
                    List<String> stringList = new ArrayList<>();
                    stringList.add(imageUrl);
                    startActivity(new Intent(this, PhotoViewActivity.class)
                            .putExtra("urls", JSONArray.toJSONString(stringList)));
                }
                break;
            case R.id.delete_img:
                add_image.setImageResource(R.mipmap.addphoto_icon);
                imageUrl = "";
                delete_img.setVisibility(View.GONE);
                break;
            case R.id.confirm_modift:
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.getUserDetail();
                } else {
                    ToastUtil.showToast(result);
                }
                break;
            case R.id.textView_right_title_bar:
                startActivity(new Intent(this, TibiDetailActivity.class));
                break;
            default:
                break;
        }
    }

    public void getUserInfoSuccess(User user) {
        if (user != null && StringUtil.isNotNull(user.getTrueid())) {
            xclModel.withdrawal(number.getText().toString(), code.getText().toString(),
                    paypwd.getText().toString(), address.getText().toString(), imageUrl);
        } else {
            startActivity(new Intent(this, PersonDetailActivity.class));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @NonNull
    private String validate() {
        if (StringUtil.isNullOrEmpty(address.getText().toString())) {
            return "请输入钱包地址";
        }
        if (StringUtil.isNullOrEmpty(number.getText().toString())) {
            return "请输入申请数量";
        }
//        if (Double.parseDouble(number.getText().toString()) < least) {
//            return "提币数量小于起提数量";
//        }
        if (StringUtil.isNullOrEmpty(code.getText().toString())) {
            return "请输入验证码";
        }
        if (StringUtil.isNullOrEmpty(paypwd.getText().toString())) {
            return "请输入支付密码";
        }

        return "";
    }

    public void uploadLogoSuccess() {
        final File file = new File(mImageSelectUtil.getDestFileName());
        if (!file.exists()) {
            add_image.setImageResource(R.mipmap.addphoto_icon);
            return;
        } else {
            findImageUrl(file);
//                    head_portrait.setImageBitmap(FileUtil.decodeFile(mImageSelectUtil.getDestFileName()));
        }
    }

    public void uploadFileSuccess(final String imageUrl) {
        if (StringUtil.isNotNull(imageUrl)) {
            this.imageUrl = imageUrl;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageOptions.showImage(imageUrl, add_image);
                }
            });
            delete_img.setVisibility(View.VISIBLE);
//            xclModel.editAvatar(imageUrl);
        } else {
            add_image.setImageResource(R.mipmap.addphoto_icon);
            ToastUtil.showToast("上传失败,请重试");
            delete_img.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            // 上传文件后续动作
            if (getmImageSelectUtil().onActivityResult(requestCode, resultCode, data)) {
                uploadLogoSuccess();
                return;
            }
        } catch (Exception e) {
//                Log.e("上传头像", e.getMessage(), e);
        }
    }

    private void findImageUrl(final File file) {
        xclModel.uploadFile(FileUtil.compressImage(file.getAbsolutePath()));
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    resultFileUrl(OkHttpUtils.getInstance().upLoadFile(SysConstants.uploadFile, file.getAbsolutePath(),
//                            AESUtil.decrypt(SysConstants.AES_PASSWORD,
// SharedPreferencesUtil.readString(SysConstants.TOKEN, "")), "img"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

    }

}
