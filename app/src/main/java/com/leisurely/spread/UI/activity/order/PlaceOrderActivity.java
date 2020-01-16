package com.leisurely.spread.UI.activity.order;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.ForgetPayPwdActivity;
import com.leisurely.spread.UI.activity.home.OrderActivity;
import com.leisurely.spread.UI.activity.money.AddressActivity;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Address;
import com.leisurely.spread.model.bean.GoodsDetail;
import com.leisurely.spread.util.ImageOptions;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.widget.pwd.PayPassDialog;
import com.leisurely.spread.widget.pwd.PayPassView;

import java.util.List;

/**
 * 下单页面
 *
 * @version V3.0
 * @FileName: com.leisurely.spread.UI.activity.order.PlaceOrderActivity.java
 * @author: GYZ
 * asia161@qq.com
 * @date: 2020-01-13 09:46
 */
public class PlaceOrderActivity extends BaseActivity {

    public static final String GOODS_DETAIL = "goodsDetail";
    public static final String GOODS_BUY_AFTER = "buy_after";
    public static final String GOODS_BUY_ADDRESS = "address";

    private XclModel xclModel;
    private LinearLayout back;
    private RelativeLayout layoutAddress;
    private TextView txtAddressDetail, txtAddressCity, txtUserName, txtGoodName, txtPrice, txtCost, txtOrderNum, txtSubmit;
    private ImageView imgGoods;
    private GoodsDetail goodsDetail;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_place_order);
        back = findViewById(R.id.back);
        txtAddressDetail = findViewById(R.id.txtAddressDetail);
        txtAddressCity = findViewById(R.id.txtAddressCity);
        txtUserName = findViewById(R.id.txtUserName);
        txtGoodName = findViewById(R.id.txtGoodName);
        txtPrice = findViewById(R.id.txtPrice);
        txtOrderNum = findViewById(R.id.txtOrderNum);
        layoutAddress = findViewById(R.id.layoutAddress);
        txtSubmit = findViewById(R.id.txtSubmit);
        txtCost = findViewById(R.id.txtCost);
        imgGoods = findViewById(R.id.imgGoods);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        xclModel.findAddressPage(1, 10);
        goodsDetail = getIntent().getParcelableExtra(GOODS_DETAIL);

        if (goodsDetail == null) {
            return;
        }

        ImageOptions.showImage(goodsDetail.getDefaultImgUrl(), imgGoods);
        txtPrice.setText(goodsDetail.getPrice());
        txtCost.setText(goodsDetail.getPrice());
        txtOrderNum.setText("1" + goodsDetail.getUnits());
        txtGoodName.setText(goodsDetail.getName());

    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        txtSubmit.setOnClickListener(this);
        layoutAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.txtSubmit:

                String result = validate();
                if (TextUtils.isEmpty(result)) {
                    payDialog();
                } else {
                    ToastUtil.makeTextAndShow(result);
                }
                break;
            case R.id.layoutAddress:
                startActivityForResult(new Intent(this, AddressActivity.class).putExtra(AddressActivity.TYPE_ADDRESS,AddressActivity.TYPE_SELECT_ADDRESS),789);
                break;
        }
    }

    private String validate() {
        String result = null;
        if (address == null) {
            result = "请选择收货地址";
        }
        if (goodsDetail == null) {
            result = "无法获取商品信息，请重新购买";
        }
        return result;
    }


    private void payDialog() {
        final PayPassDialog dialog = new PayPassDialog(this);
        dialog.setOutColse(true);
        dialog.getPayViewPass().setAmountVisibility(goodsDetail.getPrice());
        dialog.getPayViewPass()
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        //6位输入完成回调
                        xclModel.goodPurchase(String.valueOf(goodsDetail.getId()), address.getId(), "1", goodsDetail.getPrice(), passContent);
                        dialog.dismiss();
                    }

                    @Override
                    public void onPayClose() {
                        dialog.dismiss();
                        //关闭回调
                    }

                    @Override
                    public void onPayForget() {
                        dialog.dismiss();
                        //点击忘记密码回调

                        startActivity(new Intent(getBaseContext(), ForgetPayPwdActivity.class)
                                .putExtra("type", 1));
                    }
                });
    }


    public void findAddressPageSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            JSONArray jsonArray = json.getJSONObject("data").getJSONArray("result");
            if (jsonArray != null) {
                List<Address> list = JSONArray.parseArray(JSONArray.toJSONString(jsonArray), Address.class);
                if (list.size() > 0) {
                    boolean getDefaultAddress = false;
                    for (Address address : list) {
                        if (1 == address.getIsStatus()) {
//                            是默认地址
                            getDefaultAddress = true;
                            initAddress(address);
                            break;
                        }
                    }
                    initAddress(list.get(0));
                }
            }
        } else {
        }
    }


    public void goodPurchaseSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            //跳支付成功页
            startActivityForResult(new Intent(this, PurchaseSuccessActivity.class), 123);
//            setResult(RESULT_OK);
//            finish();
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 123) {
            int subPage = 0;
            if (data != null) {
                subPage = data.getIntExtra(GOODS_BUY_AFTER, 0);
            }
            setResult(Activity.RESULT_OK, new Intent().putExtra(GOODS_BUY_AFTER, subPage));
            finish();
        }
        if (resultCode==Activity.RESULT_OK){
            switch (requestCode){
                case 789:
                    if (data!=null){
                        address = (Address) data.getSerializableExtra(GOODS_BUY_ADDRESS);
                        initAddress(address);
                    }
                    break;
            }
        }
    }

    Address address;

    private void initAddress(Address address) {
        if(address==null){
            return;
        }
        this.address = address;
        txtAddressCity.setText(address.getProvince() + address.getCity() + address.getArea());
        txtUserName.setText(address.getUserName() + "    " + address.getPhone());
        txtAddressDetail.setText(address.getAddress());
    }
}
