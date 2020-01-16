package com.leisurely.spread.UI.activity.home;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.ImageUtil;


public class ShopDetailActivity extends BaseActivity {

    private XclModel xclModel;

    private RelativeLayout head_bc;

    private String name, id;
    private TextView name_text;
    private ImageView qrcode;

    private TextView commit;

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.shop_detail_activity);

        head_bc = findViewById(R.id.head_bc);
        head_bc.setBackgroundColor(getResources().getColor(R.color.color_3BA370));
        setTitleName("店铺二维码");

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");

        name_text = findViewById(R.id.name);
        qrcode = findViewById(R.id.qrcode);
        commit = findViewById(R.id.commit);


        name_text.setText(name);
        ImageUtil.createQRcodeImage(id, qrcode, 400);
    }

    @Override
    public void onResume() {
        super.onResume();
//        xclModel.queryMemberInfo();
    }

    @Override
    protected void initListener() {
        commit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
//        xclModel.queryMemberInfo();
//        xclModel.getShopList(pageNum, pageSize, provinceCode,cityCode,areaCode,fullName);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                startActivityForResult(new Intent(this, ShopPayActivity.class).putExtra("id", id),1111);
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
