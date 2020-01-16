package com.leisurely.spread.UI.activity.money;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.RegisterOrLoginActivity;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.BankBrief;
import com.leisurely.spread.model.bean.BankSub;
import com.leisurely.spread.util.FileUtil;
import com.leisurely.spread.util.ImageSelectUtil;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.widget.SpaceTokenizer;

import org.angmarch.views.NiceSpinner;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 实名邦卡开户
 *
 * @version V3.0
 * @FileName: com.leisurely.spread.UI.activity.money.AuthNameBindCardActivity.java
 * @author: GYZ
 * asia161@qq.com
 * @date: 2020-01-10 10:11
 */
public class AuthNameBindCardActivity extends BaseActivity {

    String bankId = null;
    String subBankId = null;
    private int selectType; //1 上传身份证头像   2 上传身份证国徽面
    private XclModel xclModel;
    private TimeCount mTimeCount;
    private NiceSpinner spinnerSubBank;
    private ImageView imgEmblem, imgHead;
    private TextView txtSendSms, txtSubmit;
    private ImageSelectUtil mImageSelectUtil;//图片选择器
    private String headImagePath, emblemImagePath;
    private MultiAutoCompleteTextView multiTextView;
    private LinearLayout back, layoutSearchSubBank, layoutUploadHead, layoutUploadEmblem,rootView;
    private EditText edtName, edtIdNum, edtBankCardNum, edtBankSubName, edtBankPhone, edtAuthCode;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_auth_bind_card);
        back = findViewById(R.id.back);
        edtName = findViewById(R.id.edtName);
        imgHead = findViewById(R.id.imgHead);
        edtIdNum = findViewById(R.id.edtIdNum);
        rootView = findViewById(R.id.rootView);
        imgEmblem = findViewById(R.id.imgEmblem);
        txtSubmit = findViewById(R.id.txtSubmit);
        txtSendSms = findViewById(R.id.txtSendSms);
        edtAuthCode = findViewById(R.id.edtAuthCode);
        edtBankPhone = findViewById(R.id.edtBankPhone);
        multiTextView = findViewById(R.id.multiTextView);
        edtBankCardNum = findViewById(R.id.edtBankCardNum);
        edtBankSubName = findViewById(R.id.edtBankSubName);
        spinnerSubBank = findViewById(R.id.spinnerSubBank);
        layoutUploadHead = findViewById(R.id.layoutUploadHead);
        layoutUploadEmblem = findViewById(R.id.layoutUploadEmblem);
        layoutSearchSubBank = findViewById(R.id.layoutSearchSubBank);
    }

    @Override
    protected void initData() {

        xclModel = new XclModel(this);
        xclModel.getBank();
        mTimeCount = new TimeCount(120000, 1000);
        mImageSelectUtil = new ImageSelectUtil(this);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        txtSubmit.setOnClickListener(this);
        txtSendSms.setOnClickListener(this);
        layoutUploadHead.setOnClickListener(this);
        layoutUploadEmblem.setOnClickListener(this);
        layoutSearchSubBank.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.layoutUploadHead:
                selectType = 1;
                mImageSelectUtil.startSelect(imgHead, rootView,false, 0);
                break;
            case R.id.layoutUploadEmblem:
                selectType = 2;
                mImageSelectUtil.startSelect(imgEmblem,rootView, false, 0);
                break;
            case R.id.layoutSearchSubBank:
                querySubBank();
                break;
            case R.id.txtSendSms:
                String result = validatePhone();

                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.sendValidateCode(edtIdNum.getText().toString(), edtBankPhone.getText().toString(), edtName.getText().toString(), edtBankCardNum.getText().toString());
                } else {
                    ToastUtil.showToast(result);
                }
                break;
            case R.id.txtSubmit:
                String result1 = validateSubmit();

                if (StringUtil.isNullOrEmpty(result1)) {
                    xclModel.checkUserAccount();
                } else {
                    ToastUtil.showToast(result1);
                }
                break;
        }
    }

    /**
     * 开户数据验证
     *
     * @return
     */
    private String validateSubmit() {
        if (StringUtil.isNullOrEmpty(edtName.getText().toString())) {
            return "请输入用户姓名";
        }
        if (StringUtil.isNullOrEmpty(edtIdNum.getText().toString())) {
            return "请输入身份证号码";
        }
        if (edtIdNum.getText().toString().length() != 18) {
            return "请输入正确的身份证号码";
        }
        if (StringUtil.isNullOrEmpty(edtBankPhone.getText().toString())) {
            return "请输入手机号";
        }
        if (TextUtil.isMobileNumber(edtBankPhone.getText().toString())) {
            return "请输入正确的手机号";
        }

        if (edtBankPhone.getText().toString().length() != 11) {
            return "请输入正确手机号";
        }
        if (StringUtil.isNullOrEmpty(edtBankCardNum.getText().toString())) {
            return "请输入银行卡号";
        }
        if (edtBankCardNum.getText().toString().length() < 15) {
            return "请输入正确的银行卡号";
        }
        if (TextUtils.isEmpty(headImagePath)) {
            return "请上传身份证正面";
        }
        if (TextUtils.isEmpty(emblemImagePath)) {
            return "请上传身份证反面";
        }
        if (StringUtil.isNullOrEmpty(subBankId)) {
            return "请选择开户支行";
        }
        if (StringUtil.isNullOrEmpty(edtAuthCode.getText().toString())) {
            return "请输入验证码";
        }
        if (StringUtil.isNullOrEmpty(bankId)) {
            return "请选择开户银行";
        }

        return null;
    }

    private String validatePhone() {
        if (StringUtil.isNullOrEmpty(edtIdNum.getText().toString())) {
            return "请输入身份证号码";
        }
        if (edtIdNum.getText().toString().length() != 18) {
            return "请输入正确的身份证号码";
        }
        if (StringUtil.isNullOrEmpty(edtBankCardNum.getText().toString())) {
            return "请输入银行卡号";
        }
        if (edtBankCardNum.getText().toString().length() < 15) {
            return "请输入正确的银行卡号";
        }
        if (StringUtil.isNullOrEmpty(edtName.getText().toString())) {
            return "请输入用户姓名";
        }
        if (StringUtil.isNullOrEmpty(edtBankPhone.getText().toString())) {
            return "请输入手机号";
        }
        if (TextUtil.isMobileNumber(edtBankPhone.getText().toString())) {
            return "请输入正确的手机号";
        }

        if (edtBankPhone.getText().toString().length() != 11) {
            return "请输入正确手机号";
        }

        return "";
    }

    public void querySubBank() {
        String bankName = multiTextView.getText().toString();
        if (TextUtils.isEmpty(bankName)) {
            ToastUtil.makeTextAndShow("请填写银行名称");
            return;
        }
        String keyWord = edtBankSubName.getText().toString();
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtil.makeTextAndShow("请填写支行关键字");
            return;
        }

        for (BankBrief bankBrief : bankBriefs) {
            if (bankName.equals(bankBrief.getName())) {
                bankId = bankBrief.getNo();
                break;
            }
        }
        if (!TextUtils.isEmpty(bankId)) {
            xclModel.querySubBank(bankId, keyWord);
        }
    }

    public void uploadSuccess() {
        final File file = new File(mImageSelectUtil.getDestFileName());
        if (file.exists()) {
            xclModel.uploadIdCardFile(FileUtil.compressImage(file.getAbsolutePath()));
            if (selectType == 1) {
                imgHead.setImageBitmap(FileUtil.decodeFile(mImageSelectUtil.getDestFileName()));
            } else {
                imgEmblem.setImageBitmap(FileUtil.decodeFile(mImageSelectUtil.getDestFileName()));
            }
        } else {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        try {
            // 上传文件后续动作
            if (getmImageSelectUtil().onActivityResult(requestCode, resultCode, data)) {
                uploadSuccess();
                return;
            }
        } catch (Exception e) {
            Log.e("上传", e.getMessage(), e);
        }

        super.onActivityResult(requestCode, resultCode, data);


    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            txtSendSms.setText("发送验证码");
            txtSendSms.setTextColor(Color.WHITE);
            txtSendSms.setClickable(true);
            txtSendSms.setBackgroundResource(R.drawable.cb1b1d_2dp);
        }

        @Override
        public void onTick(long arg0) {
            long min = arg0 / 1000;
            txtSendSms.setText(min + "s");
            txtSendSms.setTextColor(getResources().getColor(R.color.color_969696));
            txtSendSms.setBackgroundResource(R.drawable.e2e2e2_2dp);
        }
    }

    public ImageSelectUtil getmImageSelectUtil() {
        return mImageSelectUtil;
    }

    public void uploadIdCardFileSuccess(String path) {
        if (selectType == 1) {
            headImagePath = path;
        } else {
            emblemImagePath = path;
        }
    }


    /**
     * 开户结果
     *
     * @param response
     */
    public void validateAndOpenAcctSuccess(JSONObject response) {
        if ("1".equals(response.getJSONObject("status").getString("success"))) {
            SharedPreferencesUtil.saveString(SysConstants.IS_RISK_AUTH, "1");
        } else {
            ToastUtil.showToast(response.getJSONObject("status").getString("message"));
        }
    }

    public void checkUserAccountSuccess(JSONObject response) {
        if ("1".equals(response.getJSONObject("status").getString("success"))) {
            if ("0".equals(response.getString("result"))) {
                xclModel.validateAndOpenAcct(edtIdNum.getText().toString(), edtBankPhone.getText().toString(), edtName.getText().toString(), edtBankCardNum.getText().toString(),
                        headImagePath, emblemImagePath, edtName.getText().toString(), subBankId, "111111", edtAuthCode.getText().toString(), bankId, multiTextView.getText().toString());
            } else if ("1".equals(response.getString("result"))) {
                ToastUtil.showToast("该账户已开户");
            }
        } else {
            ToastUtil.showToast(response.getJSONObject("status").getString("message"));
        }

    }

    public void sendValidateCodeSuccess(JSONObject response) {
        if ("1".equals(response.getJSONObject("status").getString("success"))) {

            ToastUtil.showToast("验证码发送成功！");
            mTimeCount.start();
            txtSendSms.setClickable(false);
        } else {
            ToastUtil.showToast(response.getJSONObject("status").getString("message"));
        }


    }

    List<BankBrief> bankBriefs;

    public void getBankSuccess(List<BankBrief> bankBriefs) {

        if (bankBriefs.size() == 0) {
            return;
        }
        this.bankBriefs = bankBriefs;
        String[] briefArray = new String[bankBriefs.size()];
        for (int i = 0; i < bankBriefs.size(); i++) {
            BankBrief brief = bankBriefs.get(i);
            briefArray[i] = brief.getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, briefArray);
        multiTextView.setAdapter(adapter);
        multiTextView.setTokenizer(new SpaceTokenizer());
    }

    public void querySubBankSuccess(final List<BankSub> parseArray) {
        if (parseArray.size() == 0) {
            return;
        }
        if (parseArray.size() == 1) {
            subBankId = parseArray.get(0).getBank_id();
        }
        final LinkedList listArray = new LinkedList<String>();
        for (BankSub bankSub : parseArray) {
            listArray.add(bankSub.getBranch_name());

        }
        spinnerSubBank.attachDataSource(listArray);
        spinnerSubBank.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subBankId = parseArray.get(position).getBank_id();
            }
        });

    }
}
