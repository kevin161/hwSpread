package com.leisurely.spread.UI.activity.setting;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.util.TextUtil;

/**
 * Created by Administrator on 2018/12/18.
 */
public class ServiceActivity extends BaseActivity {

    private TextView wechat;
    private TextView qq;
    private ImageView copy_wechat;
    private ImageView copy_qq;

    private String qqStr;
    private String wechatStr;

    @Override
    protected void initData() {
        super.initData();
        qqStr = getIntent().getStringExtra("qq");
        wechatStr = getIntent().getStringExtra("wechat");
        qq.setText(qqStr);
        wechat.setText(wechatStr);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_service);
        setTitleName("客服");
        wechat = findViewById(R.id.wechat);
        qq = findViewById(R.id.qq);
        copy_wechat = findViewById(R.id.copy_wechat);
        copy_qq = findViewById(R.id.copy_qq);
    }

    @Override
    protected void initListener() {
        copy_qq.setOnClickListener(this);
        copy_wechat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copy_qq:
                TextUtil.onClickCopy(qqStr, this);
                break;
            case R.id.copy_wechat:
                TextUtil.onClickCopy(wechatStr, this);
                break;
            default:

                break;
        }
    }
}
