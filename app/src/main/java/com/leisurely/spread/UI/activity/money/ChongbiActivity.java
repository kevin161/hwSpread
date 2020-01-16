package com.leisurely.spread.UI.activity.money;

import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.setting.PhotoViewActivity;
import com.leisurely.spread.UI.adapter.ImageAdapter;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.enums.YZMEnum;
import com.leisurely.spread.util.FileUtil;
import com.leisurely.spread.util.ImageCompressUtil;
import com.leisurely.spread.util.ImageOptions;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/11.
 */

public class ChongbiActivity extends BaseActivity {
    private static final int REQUEST_CODE = 0x00000011;
    private TextView balance;
    private ImageView copy;
    private TextView address;
    private ImageView qrcode;
    private EditText number;
    private EditText code;
    private TextView get_captcha;
    private TextView confirm_modift;
    private TextView textView_right_title_bar;
    private RecyclerView rv_image;
    private TextView currency;
    private ImageAdapter mAdapter;
    private EditText voucher_id;
    private ArrayList<String> images = new ArrayList<>();

    private String addressStr;

    private XclModel xclModel;
    private TimeCount mTimeCount;
    private String imageUrl;
    private List<String> imageUrls;
    private int uploadIndex;
    private int imageCount;
    private List<String> cameraUrls = new ArrayList<>();

    public int getCameraUrlsCount() {
        return cameraUrls.size();
    }

    public int getImageUrlsCount() {
        return imageUrls.size();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_chongbi);
        setTitleName("充值");
        balance = findViewById(R.id.balance);
        copy = findViewById(R.id.copy);
        address = findViewById(R.id.address);
        qrcode = findViewById(R.id.qrcode);
        number = findViewById(R.id.number);
        code = findViewById(R.id.code);
        get_captcha = findViewById(R.id.get_captcha);
        rv_image = findViewById(R.id.rv_image);
        confirm_modift = findViewById(R.id.confirm_modift);
        currency = findViewById(R.id.currency);
        voucher_id = findViewById(R.id.voucher_id);
        textView_right_title_bar = findViewById(R.id.textView_right_title_bar);
        textView_right_title_bar.setVisibility(View.VISIBLE);

        rv_image = findViewById(R.id.rv_image);
        rv_image.setLayoutManager(new GridLayoutManager(this, 5));
        mAdapter = new ImageAdapter(this);
        images.add("");
        rv_image.setAdapter(mAdapter);
        mAdapter.refresh(images);
    }

    @Override
    protected void initData() {
        super.initData();
        mTimeCount = new TimeCount(60000, 1000);
        textView_right_title_bar.setText("充值记录");
        xclModel = new XclModel(this);
        xclModel.rechargeindex("1");
        imageUrls = new ArrayList<>();
    }

    @Override
    protected void initListener() {
        copy.setOnClickListener(this);
        get_captcha.setOnClickListener(this);
        confirm_modift.setOnClickListener(this);
        textView_right_title_bar.setOnClickListener(this);
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
        addressStr = json.getString("address");
        final String code = json.getString("code");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageOptions.showImage(code, qrcode);
            }
        });
        this.balance.setText(balance);
        currency.setText(json.getString("currency"));
        address.setText(addressStr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copy:
                if (StringUtil.isNotNull(addressStr)) {
                    TextUtil.onClickCopy(addressStr, this);
                }
                break;
            case R.id.get_captcha:
                xclModel.sendVerficationCode(SharedPreferencesUtil.readString(SysConstants.TELLPHONE, ""),
                        YZMEnum.RECHARGE.getId(), "");
                break;
            case R.id.confirm_modift:
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
                    for (String s : imageUrls) {
                        imageUrl += s + ",";
                    }
                    xclModel.recharge(number.getText().toString(), code.getText().toString(),
                            imageUrl.substring(0, imageUrl.length() - 1), voucher_id.getText().toString());
                } else {
                    ToastUtil.showToast(result);
                }
                break;
            case R.id.textView_right_title_bar:
                startActivity(new Intent(this, ChongbiDetailActivity.class));
                break;
            default:
                break;
        }
    }

    public void openPhotoView(int position) {
        startActivity(new Intent(this, PhotoViewActivity.class)
                .putExtra("urls", JSONArray.toJSONString(imageUrls))
                .putExtra("currentPosition", position));
    }

    @NonNull
    private String validate() {
        if (StringUtil.isNullOrEmpty(number.getText().toString())) {
            return "请输入充值数量";
        }
        if (StringUtil.isNullOrEmpty(code.getText().toString())) {
            return "请输入验证码";
        }
        if (imageUrls.size() == 0) {
            return "请上传转币凭证";
        }
        if (StringUtil.isNullOrEmpty(voucher_id.getText().toString())) {
            return "请输入交易ID";
        }
        return "";
    }

    public void uploadFileSuccess(final String imageUrl) {
        if (StringUtil.isNotNull(imageUrl)) {
            imageUrls.add(imageUrl);
            uploadIndex++;
            if (uploadIndex < imageCount) {
                uploadImg();
            }
//            xclModel.editAvatar(imageUrl);
        }
    }

    public void reomove(int position, String url) {

        for (String s : cameraUrls) {
            if (s.equals(url)) {
                cameraUrls.remove(s);
                break;
            }
        }
        imageUrls.remove(position);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && data != null) {
            boolean isCamera = data.getBooleanExtra("isCamera", false);

            if (cameraUrls.size() > 0) {
                if (!isCamera) {
                    images.clear();
                    images.addAll(cameraUrls);
                    images.addAll(data.getStringArrayListExtra(ImageSelector.SELECT_RESULT));
                } else {
                    cameraUrls.addAll(data.getStringArrayListExtra(ImageSelector.SELECT_RESULT));
                    images.addAll(data.getStringArrayListExtra(ImageSelector.SELECT_RESULT));
                }
            } else {
                if (!isCamera) {
                    images.clear();
                    images.addAll(data.getStringArrayListExtra(ImageSelector.SELECT_RESULT));
                } else {
                    images.addAll(data.getStringArrayListExtra(ImageSelector.SELECT_RESULT));
                    cameraUrls.addAll(data.getStringArrayListExtra(ImageSelector.SELECT_RESULT));
                }
            }
            imageUrls.clear();
            imageUrl = "";
            uploadIndex = 0;
            imageCount = images.size();
            if (images.size() < 5) {
                images.add("");
            }
            mAdapter.refresh(images);
            uploadImg();
        } else {
            if (images.size() < 5) {
                images.add("");
            }
            mAdapter.refresh(images);
        }

    }

    public void uploadImg() {
        String url = SysConstants.LOCAL_URL + "upload/" + StringUtil.makeRandom(15) + ".jpg";
        ImageCompressUtil.compressImageToFile(Uri.fromFile(new File(images.get(uploadIndex))), url);
        xclModel.uploadFile(FileUtil.compressImage(url));
    }

}
