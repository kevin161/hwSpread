package com.leisurely.spread.UI.activity.money;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.OrderDetail;
import com.leisurely.spread.util.DateUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;

/**
 * Created by Administrator on 2018/12/29.
 */

public class BuyDetailActivity extends BaseActivity {

    private XclModel xclModel;

    private TextView paytype_text;
    private TextView name;
    private TextView amount;
    private ImageView copy_amount;
    private TextView price;
    private TextView num;
    private TextView orderno;
    private ImageView copy_orderno;
    private TextView time;
    private TextView commit;
    private TextView cancel;

    private OrderDetail order;

    private int from;//0 ITSActivity 1 TTSLogActivity


    @Override
    protected void initView() {
        setContentView(R.layout.activity_buy_detail);
        setTitleName("支付");
        paytype_text = findViewById(R.id.paytype_text);
        name = findViewById(R.id.name);
        amount = findViewById(R.id.amount);
        copy_amount = findViewById(R.id.copy_amount);
        price = findViewById(R.id.price);
        num = findViewById(R.id.num);
        orderno = findViewById(R.id.orderno);
        copy_orderno = findViewById(R.id.copy_orderno);
        time = findViewById(R.id.time);
        commit = findViewById(R.id.commit);
        cancel = findViewById(R.id.cancel);

    }

    @Override
    protected void initData() {
        super.initData();
        from = getIntent().getIntExtra("from", 0);
        xclModel = new XclModel(this);
        xclModel.orderDetails(getIntent().getStringExtra("id"));
    }

    @Override
    protected void initListener() {
        cancel.setOnClickListener(this);
        commit.setOnClickListener(this);
        copy_amount.setOnClickListener(this);
        copy_orderno.setOnClickListener(this);
    }

    public void getOrderDetailSuccess(OrderDetail order) {
        this.order = order;
        if (order.getPaytype() == 1) {
            paytype_text.setText("支付宝");
        } else if (order.getPaytype() == 2) {
            paytype_text.setText("微信");
        } else if (order.getPaytype() == 3) {
            paytype_text.setText("银行卡");
        }
        name.setText(order.getMerchant().getName());
        price.setText(order.getPrice() + " CNY");
        amount.setText(order.getAmount() + " CNY");
        num.setText(order.getNumber() + " ITS");
        time.setText(DateUtil.getdata(order.getAddtime()));
        orderno.setText(order.getOrderno());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                showAlert();
                break;
            case R.id.commit:
                startActivityForResult(new Intent(this, PayDetailActivity.class)
                        .putExtra("paytype", order.getPaytype())
                        .putExtra("purchaseid", order.getMerchant().getId())
                        .putExtra("amount", order.getAmount()), 1111);
                break;
            case R.id.copy_amount:
                TextUtil.onClickCopy(order.getAmount(), this);
                break;
            case R.id.copy_orderno:
                TextUtil.onClickCopy(order.getOrderno(), this);
                break;
            default:
                break;
        }
    }

    public void showAlert() {
        View layout = LayoutInflater.from(this).inflate(R.layout.view_alert, null);
        final Dialog dialog = new Dialog(this, R.style.officeDialog);
        dialog.setCancelable(true);
        dialog.setContentView(layout);

        TextView alert_title = layout.findViewById(R.id.alert_title);
        alert_title.setText("取消交易");
        TextView alert_content = layout.findViewById(R.id.alert_content);
        alert_content.setText("如果您已经向卖家付款, 请千万不要取消交易!");

        TextView text1 = layout.findViewById(R.id.awardpage_button1);
        text1.setText("取消交易");
        text1.setTextColor(this.getResources().getColor(R.color.light));
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView text2 = layout.findViewById(R.id.awardpage_button2);
        text2.setText("继续取消");
        text2.setTextColor(this.getResources().getColor(R.color.color_1490D8));
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xclModel.cancelOrder(order.getId());
                dialog.dismiss();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void cancelOrderSuccess() {
        ToastUtil.showToast("取消成功");
        Intent it = new Intent();
        setResult(RESULT_OK, it);
        this.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1111) {
                if (from != 1) {
                    startActivity(new Intent(this, ITSLogActivity.class));
                }
                finish();
            }
        }
    }
}
