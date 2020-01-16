package com.leisurely.spread.UI.activity.setting;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.PersonCenterActivity;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.StringUtil;


public class WaitActivity extends BaseActivity {

    private XclModel xclModel;

    private LinearLayout back;


    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.wait_activity);


        back = findViewById(R.id.back);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        String title = getIntent().getStringExtra("title");
        if(StringUtil.isNotNull(title)){
            setTitleName(title);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
