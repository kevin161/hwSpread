package com.leisurely.spread.UI.activity.money;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.FileUtil;
import com.leisurely.spread.util.ImageUtil;
import com.leisurely.spread.util.SharedPreferencesUtil;

/**
 * Created by Administrator on 2018/12/11.
 */

public class ShoubiActivity extends BaseActivity {

    private TextView textView_right_title_bar;

    private XclModel xclModel;

    private ImageView qrcode;
    private TextView invitation;
    private TextView save_pic;
    private Bitmap bitmap;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_shoubi);
        setTitleName("收币");
        textView_right_title_bar = findViewById(R.id.textView_right_title_bar);
        textView_right_title_bar.setVisibility(View.VISIBLE);
        qrcode = findViewById(R.id.qrcode);
        invitation = findViewById(R.id.invitation);
        save_pic = findViewById(R.id.save_pic);
    }

    @Override
    protected void initData() {
        super.initData();
        textView_right_title_bar.setText("收币记录");
        xclModel = new XclModel(this);
        xclModel.rechargeindex("1");
    }

    @Override
    protected void initListener() {
        textView_right_title_bar.setOnClickListener(this);
        save_pic.setOnClickListener(this);
    }

    public void rechargeindexSuccess(JSONObject json) {
        final String address = json.getString("address");
        final String code = json.getString("code");
       final String userId = SharedPreferencesUtil.readString(SysConstants.USERID,"");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject json = new JSONObject();
                json.put("LCHGetCoin",userId);
                bitmap = ImageUtil.createQRcodeImage(JSONObject.toJSONString(json), qrcode);
            }
        });
        this.invitation.setText("收币账号: " +userId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_right_title_bar:
                startActivity(new Intent(this, ShoubiDetailActivity.class));
                break;
            case R.id.save_pic:
                if (bitmap != null) {
                    FileUtil.saveImageToGallery(this, bitmap);
                }
                break;
            default:
                break;
        }
    }

}
