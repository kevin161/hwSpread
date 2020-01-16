package com.leisurely.spread.UI.activity.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.setting.PhotoViewActivity;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.PayWay;
import com.leisurely.spread.util.FileUtil;
import com.leisurely.spread.util.ImageOptions;
import com.leisurely.spread.util.ImageSelectUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AddWechatActivity extends BaseActivity {

    private XclModel xclModel;

    private EditText name;
    private EditText amount;
    private ImageView add_image;
    private ImageView delete_img;
    private TextView confirm_modift;//确认

    private ImageSelectUtil mImageSelectUtil;//图片选择器
    private String imageUrl;

    public ImageSelectUtil getmImageSelectUtil() {
        return mImageSelectUtil;
    }

    private int id;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.activity_add_alipay);
        id = getIntent().getIntExtra("id", 0);
        if (id > 0) {
            setTitleName("修改微信");
        } else {
            setTitleName("添加微信");
        }
        name = findViewById(R.id.name);
        amount = findViewById(R.id.amount);
        add_image = findViewById(R.id.add_image);
        delete_img = findViewById(R.id.delete_img);
        confirm_modift = findViewById(R.id.confirm_modift);
        amount.setHint("请输入微信号");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        confirm_modift.setOnClickListener(this);
        add_image.setOnClickListener(this);
        delete_img.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        if (id > 0) {
            xclModel.userpayway(String.valueOf(id));
        }
        mImageSelectUtil = new ImageSelectUtil(this);
    }

    public void userpaywaySuccess(final PayWay payWay) {
        name.setText(payWay.getName());
        amount.setText(payWay.getAccount());
        if (StringUtil.isNotNull(payWay.getImage())) {
            delete_img.setVisibility(View.VISIBLE);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageOptions.showImage(payWay.getImage(), add_image);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_modift://确认修改
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.savepayway(String.valueOf(id), 2, amount.getText().toString(), imageUrl,
                            name.getText().toString(), null,null);
                } else {
                    ToastUtil.showToast(result);
                }
                break;
            case R.id.back:
                finish();
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
            default:
                break;
        }
    }

    public void modifySuccess(int id) {
//        ToastUtil.showToast("提交成功");
        Intent it = new Intent();
        it.putExtra("msg", name.getText().toString() + " " + amount.getText().toString());
        it.putExtra("id",id);
        setResult(RESULT_OK, it);
        finish();
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


    @NonNull
    private String validate() {
        if (StringUtil.isNullOrEmpty(name.getText().toString())) {
            return "请输入姓名";
        }
        if (StringUtil.isNullOrEmpty(amount.getText().toString())) {
            return "请输入账号";
        }
        return "";
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
    protected void onDestroy() {
        super.onDestroy();
    }
}
