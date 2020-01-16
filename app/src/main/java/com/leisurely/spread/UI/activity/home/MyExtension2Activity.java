package com.leisurely.spread.UI.activity.home;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.money.MyRecommenderActivity;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.ImageUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;

/**
 * @version V3.0
 * @FileName: com.leisurely.spread.UI.activity.home.MyExtension2Activity.java
 * @author: GYZ
 * asia161@qq.com
 * @date: 2020-01-09 08:27
 */
public class MyExtension2Activity extends BaseActivity {
    private XclModel xclModel;
    private LinearLayout layoutBack;
    private ImageView imgQrCode;
    private TextView txtDetail, txtLink, txtCopy;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_extension);
        layoutBack = findViewById(R.id.layoutBack);
        imgQrCode = findViewById(R.id.imgQrCode);
        txtDetail = findViewById(R.id.txtDetail);
        txtLink = findViewById(R.id.txtLink);
        txtCopy = findViewById(R.id.txtCopy);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        xclModel.registerQrCode();
    }

    @Override
    protected void initListener() {

        layoutBack.setOnClickListener(this);
        txtCopy.setOnClickListener(this);
        txtDetail.setOnClickListener(this);
        txtDetail.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutBack:
                finish();
                break;
            case R.id.txtDetail:
                startActivity(new Intent(this, MyRecommenderActivity.class));

                break;
            case R.id.txtCopy:
                if (StringUtil.isNotNull(txtLink.getText().toString())) {
                    TextUtil.onClickCopy(txtLink.getText().toString(), this);
                }
                break;
        }
    }


    public void getQrcodeSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            final String qrcodeStr = json.getString("data");
            txtLink.setText(qrcodeStr);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageUtil.createQRcodeImage(qrcodeStr, imgQrCode);
                }
            });
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }

    }
}
