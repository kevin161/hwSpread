package com.leisurely.spread.UI.activity.money;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.FileUtil;
import com.leisurely.spread.util.ImageOptions;
import com.leisurely.spread.util.ImageSelectUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RechargeActivity extends BaseActivity {

    private XclModel xclModel;

    private LinearLayout back;

    private ImageView bank_logo;
    private TextView bank_name;
    private TextView account_name;
    private TextView bank_code;
    private TextView bank_subname;
    private EditText user_name;
    private EditText user_bank_code;
    private EditText money;
    private LinearLayout upload_img_li;
    private LinearLayout recharge_detail_li;
    private ImageView img;
    private TextView commit;
    private TextView copy_card;
    private TextView copy_name;
    private TextView copy_zhihang;
    private ImageSelectUtil mImageSelectUtil;//图片选择器
    private String imageUrl;

    public ImageSelectUtil getmImageSelectUtil() {
        return mImageSelectUtil;
    }

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.recharge_activity);

        back = findViewById(R.id.back);
        bank_logo = findViewById(R.id.bank_logo);
        bank_name = findViewById(R.id.bank_name);
        account_name = findViewById(R.id.account_name);
        bank_code = findViewById(R.id.bank_code);
        bank_subname = findViewById(R.id.bank_subname);
        user_name = findViewById(R.id.user_name);
        user_bank_code = findViewById(R.id.user_bank_code);
        money = findViewById(R.id.money);
        upload_img_li = findViewById(R.id.upload_img_li);
        img = findViewById(R.id.img);
        commit = findViewById(R.id.commit);
        recharge_detail_li = findViewById(R.id.recharge_detail_li);
        copy_name = findViewById(R.id.copy_name);
        copy_card = findViewById(R.id.copy_card);
        copy_zhihang = findViewById(R.id.copy_zhihang);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        commit.setOnClickListener(this);
        upload_img_li.setOnClickListener(this);
        img.setOnClickListener(this);
        copy_name.setOnClickListener(this);
        copy_card.setOnClickListener(this);
        copy_zhihang.setOnClickListener(this);
        recharge_detail_li.setOnClickListener(this);
        setEditTextInut(user_name);

        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String cashString = money.getText().toString();
                if (StringUtil.isNotNull(cashString)) {
                    try {
                        if (cashString.length() > 1 && cashString.indexOf("0") == 0 && (cashString.indexOf(".") > 1
                                || cashString.indexOf(".") == -1)) {
                            money.setText(cashString.substring(1));
                            cashString = money.getText().toString();
                        }


                        if (cashString.indexOf(".") > 0) {
                            if (cashString.indexOf(".") < (cashString.length() - 3)) {
                                money.setText(cashString.substring(0, cashString.length() - 1));
                            }
                            if (cashString.indexOf(".") != cashString.lastIndexOf(".")) {
                                money.setText(cashString.substring(0, cashString.length() - 1));
                            }
                        } else if (cashString.indexOf(".") == 0) {
                            money.setText("0.");
                        }


                    } catch (Exception e) {
                        if (cashString.length() > 1) {
                            money.setText(cashString.substring(0, cashString.length() - 1));
                        } else {
//                        moneyout_et.setText("");
                            money.setText("0.");
                        }
                    }
                    money.setSelection(money.getText().toString().length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        user_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {

                } else {
                    user_name.setSelection(0);
                }
            }
        });


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

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        String title = getIntent().getStringExtra("title");
        if (StringUtil.isNotNull(title)) {
            setTitleName(title);
        }
        mImageSelectUtil = new ImageSelectUtil(this);
        xclModel.offlineAccount();

    }

    public void offlineAccountSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            json = json.getJSONObject("data");
            final String logo = json.getString("bankLogo");
            if (StringUtil.isNotNull(logo)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageOptions.showImage(logo, bank_logo);
                    }
                });
            }
            bank_name.setText(json.getString("bankName"));
            bank_code.setText(json.getString("bankCode"));
            bank_subname.setText(json.getString("bankSubName"));
            account_name.setText(json.getString("bankAccountName"));
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }


    }

    public void offlineSubmitSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            user_name.setText("");
            user_bank_code.setText("");
            money.setText("");
            imageUrl = "";
            showSuccess();
            img.setVisibility(View.GONE);
            upload_img_li.setVisibility(View.VISIBLE);
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    public void showSuccess() {
        View layout = LayoutInflater.from(this).inflate(R.layout.view_alert, null);
        final Dialog dialog1 = new Dialog(this, R.style.officeDialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(layout);

        TextView alert_title = layout.findViewById(R.id.alert_title);
        alert_title.setText("提交成功");
        TextView alert_content = layout.findViewById(R.id.alert_content);
        alert_content.setText("充值已提交,请等待审核!");
        alert_content.setVisibility(View.GONE);
        TextView text1 = layout.findViewById(R.id.awardpage_button1);
        text1.setText("取消");
        text1.setVisibility(View.GONE);
        text1.setTextColor(this.getResources().getColor(R.color.light));
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        TextView text2 = layout.findViewById(R.id.awardpage_button2);
        text2.setText("确认");
        text2.setTextColor(this.getResources().getColor(R.color.color_1490D8));
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img:
            case R.id.upload_img_li:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1001);
                    } else {
                        mImageSelectUtil.startSelect(img, false, 0);
                    }
                } else {
                    mImageSelectUtil.startSelect(img, false, 0);
                }

                break;
            case R.id.commit:
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
//                    xclModel.editPassword(old_password.getText().toString(), new_password.getText().toString());
                    xclModel.offlineSubmit(user_name.getText().toString(), user_bank_code.getText().toString(),
                            money.getText().toString(), imageUrl, "1");
                } else {
                    ToastUtil.showToast(result);
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.recharge_detail_li:
                startActivity(new Intent(this, MoneyHistoryLogActivity.class)
                        .putExtra("type", 0).putExtra("title", "充值"));
                break;
            case R.id.copy_name:
                TextUtil.onClickCopy(account_name.getText().toString(), this,"复制打款人姓名成功");
                break;
            case R.id.copy_card:
                TextUtil.onClickCopy(bank_code.getText().toString(), this,"复制打款人账号成功");
                break;
            case R.id.copy_zhihang:
                TextUtil.onClickCopy(bank_subname.getText().toString(), this,"复制开户支行成功");
                break;
            default:
                break;
        }
    }

    public void uploadLogoSuccess() {
        final File file = new File(mImageSelectUtil.getDestFileName());
        if (!file.exists()) {
            img.setVisibility(View.GONE);
            upload_img_li.setVisibility(View.VISIBLE);
            return;
        } else {
            findImageUrl(file);
//                    head_portrait.setImageBitmap(FileUtil.decodeFile(mImageSelectUtil.getDestFileName()));
        }
    }

    @NonNull
    private String validate() {
        if (StringUtil.isNullOrEmpty(user_name.getText().toString())) {
            return "请输入打款人姓名";
        }
        if (StringUtil.isNullOrEmpty(user_bank_code.getText().toString())) {
            return "请输入打款人银行账号";
        }
        if (StringUtil.isNullOrEmpty(money.getText().toString())) {
            return "请输入转账金额";
        }
        if (StringUtil.isNullOrEmpty(imageUrl)) {
            return "请上传凭证图片";
        }

        return "";
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

    }

    public void uploadFileSuccess(final String imageUrl) {
        if (StringUtil.isNotNull(imageUrl)) {
            this.imageUrl = imageUrl;
            img.setVisibility(View.VISIBLE);
            upload_img_li.setVisibility(View.GONE);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageOptions.showImage(imageUrl, img);
                }
            });
            mImageSelectUtil = new ImageSelectUtil(this);
//            xclModel.editAvatar(imageUrl);
        } else {
            img.setVisibility(View.GONE);
            upload_img_li.setVisibility(View.VISIBLE);
            ToastUtil.showToast("上传失败,请重试");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
